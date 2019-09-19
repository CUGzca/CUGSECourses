# -*- coding: utf-8 -*-
"""
Created on Tue Nov 20 21:05:44 2018

@author: 唐琳杰
"""
"""Soccer video数据集类"""
import torch.utils.data as data
import json
import cv2
import numpy as np
"""
def compare(ele1,ele2):
    ele1_number=ele1.split('.')[0]
    ele2_number=ele2.split('.')[0]
    if int(ele1_number)>int(ele2_number):
        return -1
    elif int(ele1_number)==int(ele2_number):
        return 0
    else:
        return 1
"""
label2index={'whistle':0, 'soccer-ball':1, '':2, 'corner':3, 'y-card':4,\
                 'substitution':5, 'time':6, 'injury':7, 'funfact':8, 'soccer-ball-own':9,\
                 'r-card':10, 'penalty':11, 'penalty-missed':12, 'yr-card':13,\
                 'attendance':14}
class SoccerTrainDataset(data.Dataset):
    #all_data_path='/home/hy/tanglinjie-dachuang/3Ddata/'
    all_data_path='/home/ftp/soccernet-60s-clips/rename/'
    json_path='/home/hy/tanglinjie-dachuang/'
    json_name='new_data.json'
    def __init__(self,transforms=None,target_transforms=None):
        self.train_data=[] #训练数据
        self.train_label=[] #训练数据的标签
        self.transforms=transforms
        self.target_transforms=target_transforms
        with open(self.json_path+self.json_name) as f:
            self.clips=json.load(f)
        self.get_train()
    #获得测试集和训练集
    def get_train(self):
        videos_list=self.clips['videos']
        sentences_list=self.clips['sentences']
        for i in sentences_list:
            ind=int(i['video_id'][5:])
            v_inf=videos_list[ind]
            if v_inf['split']=='train':
                self.train_data.append((self.all_data_path+i['video_id']+'.avi',\
                                        float(v_inf['start time']),float(v_inf['end time']\
                                            ),float(i['time_point'])))
                self.train_label.append(label2index[i['labels']])
    def __getitem__(self, index):
        cap=cv2.VideoCapture()
        if not cap.open(self.train_data[index][0]):
            raise ValueError(('File doesn\'t exist.'))
        rate=(self.train_data[index][3]-self.train_data[index][1])/ \
        (self.train_data[index][2]-self.train_data[index][1])
        frame=int(rate*(cap.get(7)-1))
        cap.set(cv2.CAP_PROP_POS_FRAMES,frame)
        image=cap.read()[1]
        label=self.train_label[index]
        if self.transforms is not None:
            image=self.transforms(image)
        if self.target_transforms is not None:
            label=self.target_transforms(label)
        return image,label
    def __len__(self):
        return len(self.train_data)

class SoccerTestDataset(data.Dataset):
    #all_data_path='/home/hy/tanglinjie-dachuang/3Ddata/'
    all_data_path='/home/ftp/soccernet-60s-clips/rename/'
    json_name='new_data.json'
    json_path='/home/hy/tanglinjie-dachuang/'
    def __init__(self,transforms=None,target_transforms=None):
        self.test_data=[] #测试数据
        self.test_label=[] #测试标签
        self.transforms=transforms
        self.target_transforms=target_transforms
        with open(self.json_path+self.json_name) as f:
            self.clips=json.load(f)
        self.get_test()
    #获得测试集和训练集
    def get_test(self):
        videos_list=self.clips['videos']
        sentences_list=self.clips['sentences']
        for i in sentences_list:
            ind=int(i['video_id'][5:])
            v_inf=videos_list[ind]
            if v_inf['split']=='test':
                self.test_data.append((self.all_data_path+i['video_id']+'.avi',\
                                        float(v_inf['start time']),float(v_inf['end time']\
                                            ),float(i['time_point'])))
                self.test_label.append(label2index[i['labels']])
    def __getitem__(self, index):
        cap=cv2.VideoCapture()
        if not cap.open(self.test_data[index][0]):
            raise ValueError(('File doesn\'t exist.'))
        rate=(self.test_data[index][3]-self.test_data[index][1])/ \
        (self.test_data[index][2]-self.test_data[index][1])
        frame=int(rate*(cap.get(7)-1))
        cap.set(cv2.CAP_PROP_POS_FRAMES,frame)
        image=cap.read()[1]
        label=self.test_label[index]
        if self.transforms is not None:
            image=self.transforms(image)
        if self.target_transforms is not None:
            label=self.target_transforms(label)
        return image,label
    def __len__(self):
        return len(self.test_data)
import torch
import torchvision.transforms as transforms
import pretrainedmodels
import pretrainedmodels.utils 
"""
#ImageNet数据集增加标签
class SingleImageFolderAddLabels:
    def __inti__(self,f,labelPath,imgPath):
        self.imageFolder=f
        self.path
        self.addLabels()
    def addLabels(self):
        for i in range(1000):
            self.f.classes=[]
            self.f.classes[i]=str(i)
            self.f.class_to_idx={}
            self.f.class_to_idx[str[i]]=i
"""
            
        
#卷积层定义
class VGG(torch.nn.Module):
    def __init__(self):
        #扩展的VGG
        super(VGG,self).__init__() 
        self.model=pretrainedmodels.resnet18()
        in_dim=self.model.last_linear.in_features
        classifier=torch.nn.Sequential(
                torch.nn.Linear(in_dim,448),
                torch.nn.ReLU(),
                torch.nn.Dropout(0.2),
                torch.nn.Linear(448,15)
                )
        self.model.last_linear=classifier
        for param in self.parameters():
            param.require_grad=True
    def forward(self,x):
        x=self.model(x)
        return x
"""test=ExtendVGG(224,dic['vgg16'],False,10)
for m in test.modules():
    print(m)
"""
class Utils():
    def test(self,model,testloader):
        #计算模型分类正确率
        count=0.0
        acc=0.0
        for d in testloader:
            image,label=d
            #if count==1:
            #    print(image)
            image=image.cuda()
            label=label.cuda()
            out=model(image)
            _, out=torch.max(out,1)
            acc+=(out==label).sum().item()
            count+=label.size(0)
        print('accuracy is %f'%(acc/count))
    def train(self,trainloader,model):
        plt.figure(1)
        #绘制损失值变化曲线和正确率变化曲线
        #optim the weights
        loss=torch.nn.CrossEntropyLoss()
        #print(list(model.classifier_add.parameters()))
        params=list(map(id,model.model.last_linear.parameters()))
        features_params=filter(lambda p:id(p) not in params,model.model.parameters())
        optimer=torch.optim.SGD([{'params':model.model.last_linear.parameters(),\
                                   'lr':0.01},{'params':features_params,'lr':0.001}],\
                                                        momentum=0.9)
        j=0
        count=0
        acc=0
        statistic_loss=0
        for i in range(1):
            for train_data in trainloader:
                optimer.zero_grad()
                image,label=train_data
                #if count==0:
                #    print(image)
                image=image.cuda()
                label=label.cuda()
                out=model(image)
                #print(out.requires_grad)
                _, label_format_out=torch.max(out,1)
                #print(out.cpu().data.numpy())
                #print(label.cpu().data.numpy())
                #label_format_label=label
                #out=out.reshape(2,1).cpu()
                #print(out.requires_grad)
                #label=label.reshape(4,1).cpu()
                #out = torch.zeros(2, 10).scatter_(1, out, 1).cuda()
                #out.requires_grad=True
                #print(out.requires_grad)
                #label= torch.zeros(4, 10).scatter_(1, label, 1).cuda()
                l=loss(out,label)   #l为tensor类型,不能将l、out的requires_grad设为True，requires_grad只能为叶子变量设置
                #print(l)
                #print(l.requires_grad)
                l.backward()
                #print(out.grad)
                #print(l.grad_fn)
                optimer.step()                
                j+=1
                statistic_loss+=l.data
                count+=label.size(0)
                acc+=(label_format_out==label).sum().item()
                #print(count)
                #print(acc)
                if j%100==0:
                    print(str(j)+' step:curent accuracy is %f'%(acc/count))
                    print('the mean loss of mini-batch is %f'%(statistic_loss/100))
                    plot_loss.append(statistic_loss/100)
                    plot_epoch.append(j)
                    plot_accurity.append(acc/count)
                    #for name, value in model.classifier_add.named_parameters():
                    #    print(name+"  ")
                    #    print(value.grad)
                    count=0
                    acc=0
                    statistic_loss=0
    def extract_feature(self,model,tran):
            c=model.model.last_linear[0]
            model.model.last_linear=pretrainedmodels.utils.Identity()
            all_data_path='/home/ftp/soccernet-60s-clips/rename/'
            with open('/home/hy/tanglinjie-dachuang/new_data.json') as f:
                js=json.load(f)
            cur=[]
            no=1
            for i in js['sentences']:
                file_path=all_data_path+i['video_id']+'.avi'
                #features_file.create_group(i['video_id'])
                ind=int(i['video_id'][5:])
                v_inf=js['videos'][ind]
                start_time,end_time=float(v_inf['start time']),float(v_inf['end time'])
                time_point=float(i['time_point'])
                rate=(time_point-start_time)/(end_time-start_time)
                cap=cv2.VideoCapture()
                cap.open(file_path)
                frame_count=cap.get(7)
                target_frame=int(rate*(frame_count-1))
                left=int(max(target_frame-223,0))
                right=int(min(target_frame+224,frame_count-1))
                find_list=range(left,right+1)
                left_add=0
                right_add=0
                if len(find_list)<448:
                    diff_len=448-len(find_list)
                    left_add=int(diff_len/2+0.5)
                    right_add=diff_len-left_add
                    temp_list=[left]*left_add
                    temp_list.extend(find_list)
                    temp_list.extend([right]*right_add)
                    find_list=temp_list
                frame_freatures=[]
                batch=[]
                for j in find_list:
                    cap.set(cv2.CAP_PROP_POS_FRAMES,j)
                    image=cap.read()[1]
                    batch.append(image)
                    if len(batch)==8:
                        input_=np.ndarray((8,3,224,224))
                        for m in range(len(batch)):
                            input_[m]=tran(batch[m]).numpy()
                        batch.clear()
                        out=c(model(torch.as_tensor(input_,dtype=torch.float).cuda()))
                        out=out.detach()
                        out=out.cpu().numpy().tolist()
                        frame_freatures.extend(out)
                for temp in frame_freatures:
                    temp.append(label2index[i['labels']])
                    temp.append(target_frame-left+left_add)
                    temp.append(left-left_add)
                cur.append(np.array(frame_freatures))
                if len(cur)==100:
                    np.savez('./soccer_features/'+str(no),*cur)
                    cur.clear()
                    no+=1
                """
                with h5py.File('./'+i['video_id'],'w') as features_file:
                    features_file['data']=np.array(frame_freatures)
                    features_file['label']=label2index[i['labels']]
                    features_file['target_time']=target_frame-left+left_add
                    features_file['left']=left-left_add
                """
    def extract_feature_a_video(self,file_name):
        model=VGG()
        model.load_state_dict(torch.load('/root/VGG_Extend_for_soccer_data.pa', map_location='cpu'))
        model=model.eval()
        tran=transforms.Compose([\
            transforms.ToPILImage(),\
            transforms.Resize((224,224)),\
            #transforms.RandomHorizontalFlip(),
            transforms.ToTensor(),\
            # transforms.Normalize(mean = model.model.mean,\
            #             std = model.model.std)
            ])
        for param in model.parameters():
            param.require_grad=False
        c=model.model.last_linear[0]
        model.model.last_linear=pretrainedmodels.utils.Identity()
        cap=cv2.VideoCapture()
        if not cap.open(file_name):
            raise ValueError(('File doesn\'t exist.'))
        frame_count=cap.get(7)
        target_frame=int(0.5*(frame_count-1))
        left=int(max(target_frame-223,0))
        right=int(min(target_frame+224,frame_count-1))
        find_list=range(left,right+1)
        left_add=0
        right_add=0
        if len(find_list)<448:
            diff_len=448-len(find_list)
            left_add=int(diff_len/2+0.5)
            right_add=diff_len-left_add
            temp_list=[left]*left_add
            temp_list.extend(find_list)
            temp_list.extend([right]*right_add)
            find_list=temp_list
        frame_freatures=[]
        batch=[]
        for j in find_list:
            cap.set(cv2.CAP_PROP_POS_FRAMES,j)
            image=cap.read()[1]
            batch.append(image)
            if len(batch)==8:
                input_=np.ndarray((8,3,224,224))
                for m in range(len(batch)):
                    input_[m]=tran(batch[m]).numpy()
                batch.clear()
                out=c(model(torch.as_tensor(input_,dtype=torch.float)))
                out=out.detach()
                out=out.cpu().numpy().tolist()
                frame_freatures.extend(out)
        return np.array(frame_freatures)
                
if __name__=='__main__':
    utils=Utils()
    model=VGG().cuda()
    #加载数据集    
    tran=transforms.Compose([\
    transforms.ToPILImage(),\
    transforms.Resize((224,224)),\
    #transforms.RandomHorizontalFlip(),
    transforms.ToTensor(),\
    transforms.Normalize(mean = model.model.mean,\
                         std = model.model.std)
    ])
    #transforms.Normalize(mean = [ 0.5, 0.5, 0.5 ],
                         #std = [ 0.5, 0.5, 0.5 ]),])

    #transforms.Normalize(mean = [ 0, 0, 0])
    #ImageFolder不支持自动下载，需要使用下载好的数据集，training和validation data都用一个类处理
    #trainDataSet=torchvision.datasets.ImageFolder('./train',tran)
    #trainloader=torch.utils.data.DataLoader(trainDataSet,4,True)
    #testDataSet=torchvision.datasets.ImageFolder('D:\\imagenet数据集',tran)
    #testloader=torch.utils.data.DataLoader(testDataSet,4,True)
    trainDataSet=SoccerTrainDataset(transforms=tran)
    trainloader=torch.utils.data.DataLoader(trainDataSet,5,True)
    testDataSet=SoccerTestDataset(transforms=tran)
    testloader=torch.utils.data.DataLoader(testDataSet,5,True)
    #print(testloader.__len__())
    #utils.accurity_all(model,testloader)
    tr=False
    if tr:
        print('train start:')
        utils.train(trainloader,model)
        torch.save(model.state_dict(),'./VGG_Extend_for_soccer_data.pa')
        print('test start:')
        utils.test(model,testloader)
    else:
        model.load_state_dict(torch.load('./VGG_Extend_for_soccer_data.pa'))
    print('extract features:')
    #model=torch.load('./VGG_Extend_for_soccer_data.pa')
    fea=utils.extract_feature_a_video('/home/ftp/soccernet-60s-clips/rename/video0.avi')
    print(fea.shape)
    """
    model=utils.get_trained_model(224,num_classifier=7).cuda()
    utils.extract_feature(model)
    """
    
