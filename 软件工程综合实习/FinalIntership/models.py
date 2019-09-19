#encoding:utf-8
#专门用来存放模型的py文件

from exts import db

from datetime import datetime

class User(db.Model):
        __tablename__="user"
        id=db.Column(db.Integer,primary_key=True,autoincrement=True)
        telephone=db.Column(db.String(11),nullable=False)
        username=db.Column(db.String(100),nullable=False)
        password=db.Column(db.String(100),nullable=False)
        create_time=db.Column(db.DateTime,default=datetime.now)


class Picture(db.Model):
        __tablename__='picture'
        id=db.Column(db.Integer,primary_key=True,autoincrement=True)
        path=db.Column(db.String(100),nullable=False)
        classification=db.Column(db.String(100),nullable=False)
        description=db.Column(db.String(1000),nullable=False)
