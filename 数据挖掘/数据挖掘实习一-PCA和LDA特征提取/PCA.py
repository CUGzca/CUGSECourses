#encoding:utf-8
import scipy.io
import numpy as np
from PCA import pca
import matplotlib
import matplotlib.pyplot as plt

def zeroMean(dataMat):
    meanVal=np.mean(dataMat,axis=0)#按列求均值，即求各个特征的均值
    newData=dataMat-meanVal
    return newData,meanVal

def pca(dataMat,n):
    newData,meanVal=zeroMean(dataMat=dataMat)
    covMat=np.cov(newData,rowvar=0)#rowvar=0,很重要，说明传入的数据一行代表一个样本

    eigVals,eigVects=np.linalg.eig(np.mat(covMat))
    #print("eigVals=\n",eigVals)
    eigValIndice=np.argsort(eigVals)#对特征值的下标从小到大排序

    # sum = 0
    # count = 0
    #
    # for indice in eigValIndice[::-1]:
    #     sum += eigVals[indice]
    #     count += 1
    #     if sum / np.sum(eigVals) >= 0.85:
    #         print("降低为"+str(count)+"维")
    #         n = count
    #         break


    #print("eigValIndice=\n",eigValIndice)
    n_eigValIndice=eigValIndice[-1:-(n+1):-1]#[-1，-2),其实也就是第一个
    #print("n_eigValIndice=\n",n_eigValIndice)

    n_eigVect=eigVects[:,n_eigValIndice]#二维的
    #print("n_eigVect=\n",n_eigVect)

    lowDataMat=newData*n_eigVect
    reconMat=(lowDataMat*n_eigVect.T)+meanVal
    return lowDataMat,reconMat

if '__main__'==__name__:
    data=scipy.io.loadmat("BU3D_feature.mat")
    dataMat=data.get("data")
    y=np.array(dataMat)[:,-1]
    dataMat = np.delete(dataMat, -1, axis=1)
    dataMat,reconMat=pca(dataMat=dataMat,n=2)


    #plotData(dataMat=dataMat,reconMat=reconMat)
    plt.scatter(dataMat[:, 0].tolist(), dataMat[:, 1].tolist(),marker='o',c=y)

    plt.title('PCA')
    plt.show()
