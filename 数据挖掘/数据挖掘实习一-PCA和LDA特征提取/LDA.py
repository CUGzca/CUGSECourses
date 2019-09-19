#encoding:utf-8
import numpy as np
import scipy.io
import matplotlib
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis

def Iris_label(s):
    it={b'Iris-setosa':0, b'Iris-versicolor':1, b'Iris-virginica':2 }
    return it[s]

def LDA_reduce_dimension(X,y,nComponents):
    '''输入：X为数据集（m*n）,y为label(m*1),nComponents为目标维数'''
    '''输出：W矩阵n*nComponents'''
    labels=list(set(y))#将集合转化成列表
    #将样本分类
    xClasses={}
    for label in labels:
        xClasses[label]=np.array( [X[i]  for i in range(len(X)) if  y[i]==label])

    # 整体均值
    meanAll = np.mean(X, axis=0)  # 按列求均值，结果为1*n(行向量)
    meanClasses = {}

    # 求各类均值
    for label in labels:
        meanClasses[label] = np.mean(xClasses[label], axis=0)  # 1*n

    # 全局散度矩阵
    St = np.zeros((len(meanAll), len(meanAll)))
    St = np.dot((X - meanAll).T, X - meanAll)

    # 求类内散度矩阵
    # Sw=sum(np.dot((Xi-ui).T, Xi-ui))   i=1...m
    Sw = np.zeros((len(meanAll), len(meanAll)))  # n*n

    for i in labels:
        Sw += np.dot((xClasses[i] - meanClasses[i]).T,(xClasses[i] - meanClasses[i]))
    print(Sw)
        # 求类间散度矩阵
    Sb = np.zeros((len(meanAll), len(meanAll)))  # n*n
    Sb = St - Sw

    # 求类间散度矩阵
    # Sb=sum(len(Xj) * np.dot((uj-u).T,uj-u))  j=1...k
    # Sb=np.zeros((len(meanAll), len(meanAll) )) # n*n
    # for i in labels:
    #     Sb+= len(xClasses[i]) * np.dot( (meanClasses[i]-meanAll).T.reshape(len(meanAll),1),
    #                                     (meanClasses[i]-meanAll).reshape(1,len(meanAll))
    #                                )

    # 计算Sw-1*Sb的特征值和特征矩阵

    eigenValues, eigenVectors = np.linalg.eig(
            np.dot(np.linalg.pinv(Sw), Sb)
    )
    # 提取前nComponents个特征向量
    sortedIndices = np.argsort(eigenValues)  # 特征值排序
    W = eigenVectors[:, sortedIndices[:-nComponents - 1:-1]]  # 提取前nComponents个特征向量
    return W

if '__main__'==__name__:
    data = scipy.io.loadmat("BU3D_feature.mat")
    dataMat = data.get("data")
    y=np.array(dataMat)[:,-1]
    print(y.shape)

    dataMat = np.delete(dataMat, -1, axis=1)
    X=dataMat
    stdsc=StandardScaler()
    X=stdsc.fit_transform(X)

    W=LDA_reduce_dimension(X=X,y=y,nComponents=2)
    newX=np.dot(X,W)#m*n
    matplotlib.rcParams['font.sans-serif'] = ['SimHei']
    plt.figure(1)
    print(y.shape)
    plt.scatter(newX[:,0],newX[:,1],marker='o', c=y)
    plt.title('自行实现LDA')

    X=dataMat
    #4.与sklearn自带库函数对比
    lda_Sklearn=LinearDiscriminantAnalysis(n_components=2)
    lda_Sklearn.fit(X,y)
    newX1=lda_Sklearn.transform(X)
    plt.figure(2)
    plt.scatter(newX1[:, 0], newX1[:, 1],marker='o', c=y)
    plt.title('sklearn自带LDA库函数')
    plt.show()