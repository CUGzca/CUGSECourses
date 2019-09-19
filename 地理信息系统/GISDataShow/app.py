#encoding:utf-8

from flask import Flask,render_template
import pymssql

# server    数据库服务器名称或IP
# user      用户名
# password  密码
# database  数据库名称
server='localhost'
user='sa'
password='yinwei'
database='GIS'

app = Flask(__name__)

liuliang=[
"select count(*) 数量 from liuliangtu20140507_1",
"select count(*) 数量 from liuliangtu20140507_2",
"select count(*) 数量 from liuliangtu20140507_3",
"select count(*) 数量 from liuliangtu20140507_4",
"select count(*) 数量 from liuliangtu20140507_5",
"select count(*) 数量 from liuliangtu20140507_6",
"select count(*) 数量 from liuliangtu20140507_7",
"select count(*) 数量 from liuliangtu20140507_8",
"select count(*) 数量 from liuliangtu20140507_9",
"select count(*) 数量 from liuliangtu20140507_10",
"select count(*) 数量 from liuliangtu20140507_11",
"select count(*) 数量 from liuliangtu20140507_12",
"select count(*) 数量 from liuliangtu20140507_13",
"select count(*) 数量 from liuliangtu20140507_14",
"select count(*) 数量 from liuliangtu20140507_15",
"select count(*) 数量 from liuliangtu20140507_16",
"select count(*) 数量 from liuliangtu20140507_17",
"select count(*) 数量 from liuliangtu20140507_18",
"select count(*) 数量 from liuliangtu20140507_19",
"select count(*) 数量 from liuliangtu20140507_20",
"select count(*) 数量 from liuliangtu20140507_21",
"select count(*) 数量 from liuliangtu20140507_22",
"select count(*) 数量 from liuliangtu20140507_23",
"select count(*) 数量 from liuliangtu20140507_24",
]
sudus=[
"select *  from sudu20140507_1",
"select *  from sudu20140507_2",
"select *  from sudu20140507_3",
"select *  from sudu20140507_4",
"select *  from sudu20140507_5",
"select *  from sudu20140507_6",
"select *  from sudu20140507_7",
"select *  from sudu20140507_8",
"select *  from sudu20140507_9",
"select *  from sudu20140507_10",
"select *  from sudu20140507_11",
"select *  from sudu20140507_12",
"select *  from sudu20140507_13",
"select *  from sudu20140507_14",
"select *  from sudu20140507_15",
"select *  from sudu20140507_16",
"select *  from sudu20140507_17",
"select *  from sudu20140507_18",
"select *  from sudu20140507_19",
"select *  from sudu20140507_20",
"select *  from sudu20140507_21",
"select *  from sudu20140507_22",
"select *  from sudu20140507_23",
"select *  from sudu20140507_24",
]

remember=[]
def search():
    conn = pymssql.connect(server, user, password, database)
    cursor = conn.cursor()
    result=[]
    if len(remember) == 48:
        return remember

    for i in range(24):
        # sql = "select count(*) 数量 from taxi0507 where 时间>'%s' and 时间<'%s'" % (Time[i], Time[i+1])
        sql = liuliang[i]
        print('-------------------------')
        print(sql)
        cursor.execute(sql)
        print('----------')
        row = cursor.fetchone()
        print(row[0])
        result.append(row[0])
        remember.append(row[0])
    for i in range(24):
        print(sudus[i])
        sql = sudus[i]
        print('****************')
        print(sql)
        cursor.execute(sql)
        print('************')
        row = cursor.fetchone()
        print(row[0])
        result.append(row[0])
        remember.append(row[0])
    conn.close()
    return result
# def suduSearch():
#     conn = pymssql.connect(server, user, password, database)
#     cursor = conn.cursor()
#     result=[]
#     for i in range(24):
#         print(sudus[i])
#         sql=sudus[i]
#         print('****************')
#         print(sql)
#         cursor.execute(sql)
#         print('************')
#         row=cursor.fetchone()
#         print(row[0])
#         result.append(row[0])
#     conn.close()
#     return result

@app.route('/')
def jiaotongliuliang():
   #cursor.execute(" select * from taxi0507")
   nums=search()
   print(nums)
   context={
       'datas':nums
   }
   return render_template("index.html",**context)

# @app.route('/s/')
# def sudu():
#     nums=suduSearch()
#     print(nums)
#     return "nihao"

if __name__ == '__main__':
    app.run()

