#encoding:utf-8
#在这个文件里，是连接数据库的配置信息

import os
#dialect,选择连接数据库的DB API种类（即选择是用pymysql还是mysqldb）
DIALECT='mysql'
#驱动
DRIVER='mysqldb'
USERNAME='root'
PASSWORD='yinwei'
HOST='127.0.0.1'
PORT='3306'
DATABASE='finalintership'
DB_URI="{}+{}://{}:{}@{}:{}/{}?charset=utf8".format(DIALECT,DRIVER,USERNAME,PASSWORD,HOST,PORT,DATABASE)

SQLALCHEMY_DATABASE_URI=DB_URI

SQLALCHEMY_TRACK_MODIFICATIONS=False

SECRET_KEY='123456'
