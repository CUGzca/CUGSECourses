#encoding:utf-8
from flask import Flask,render_template,request,redirect,url_for,session
from werkzeug.utils import secure_filename
import os
from exts import db
import config
from models import User,Picture
from classify import *

app = Flask(__name__)
app.config.from_object(config) #绑定测试变量
db.init_app(app)#初始化app

@app.context_processor
def my_context_processor():
    user=session.get('username')
    if user:
        return {'login_user':user}
    return {}

@app.route('/init/')
def init():
    db.drop_all()
    db.create_all()
    return 'init success'

@app.route('/')
def index():
    content = {
        'pictures': Picture.query.all()
    }
    return render_template("index.html",**content)

#登录
@app.route('/login/',methods=['POST','GET'])
def login():
    if request.method=='GET':
        return render_template('login.html')
    else:
        telephone=request.form.get('telephone')
        print(telephone)
        password=request.form.get('password')
        print(password)
        user=User.query.filter(User.telephone==telephone,User.password==password).first()
        print(user)
        if user:
            session['username']=user.username
            session.permanent=True
            return render_template('index.html')
        else:
            return u'用户名或者密码错误'

#登出
@app.route('/logout/',methods=['POST','GET'])
def logout():
    session.clear()
    return render_template('index.html')
#注册
@app.route('/logup/',methods=['POST','GET'])
def logup():
    if request.method=='GET':
        return render_template('logup.html')
    else:
        telephone=request.form.get('telephone')
        username=request.form.get('username')
        password1=request.form.get('password1')
        password2=request.form.get('password2')

        user=User.query.filter(User.telephone==telephone).first()
        if user:
            return u'该手机号码已经被注册了'
        else:
            if password1==password2:
                user=User(telephone=telephone,username=username,password=password1)
                db.session.add(user)
                db.session.commit()
                return u'注册成功'
            else:
                return u'两次密码不一致'

@app.route('/upload/',methods=['POST','GET'])
def upload():
    if request.method=='POST':
        f=request.files['file']
        basepath=os.path.dirname(__file__)#当前文件路径
        upload_path=os.path.join(basepath,'static/uploads/',secure_filename(f.filename))
        f.save(upload_path)
        print(upload_path)
        
        # vedio=Vedio(vediopath=basepath,vedio=f.save(upload_path))
        # db.session.add(vedio)
        # db.session.commit()
        #print(upload_path)
        # f.save(upload_path)
        #file=request.files['file'].read()

        classification,description,path=classify_all(upload_path)
        
        print('path分类为'+path)
        picture=Picture(path=path,classification=classification, description=description)

        db.session.add(picture)
        db.session.commit()

        return redirect(url_for('index'))
    else:
        #return render_template('uploadfile.html')
        return render_template('uploadfile.html')

@app.route('/MicroService/classification',methods=['POST','GET'])
def classification():
    if request.method=='POST':
        f=request.files['file']
        basepath=os.path.dirname(__file__)#当前文件路径
        upload_path=os.path.join(basepath,'static/uploads/',secure_filename(f.filename))
        f.save(upload_path)
        print(upload_path)
        
    
        classification=classify_just(upload_path)
        
        return classification

    else:
        #return render_template('uploadfile.html')
        return "请使用post请求"

@app.route('/MicroService/description',methods=['POST','GET'])
def description():
    if request.method=='POST':
        f=request.files['file']
        basepath=os.path.dirname(__file__)#当前文件路径
        upload_path=os.path.join(basepath,'static/uploads/',secure_filename(f.filename))
        f.save(upload_path)
        print(upload_path)
    
        description=describe_just(upload_path)
        return description

    else:
        #return render_template('uploadfile.html')
        return "请使用post请求"


if __name__ == '__main__':
    app.run(host='0.0.0.0',port=4399)
