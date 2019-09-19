import cv2
import numpy as np
import torchvision.transforms as transforms 
import torch
from collections import Iterable
import math
def extract_feats(files,model,tran):
    """extract features from videos
    
    Parameter
    ----------
    files:list or tuple
    video file paths(str) and start_time(float),end_time(float),time_point(float)
    
    Return
    ------
    features of videos(batch*80*49)
    """
    if isinstance(files,tuple):
        files=[files]
    if not isinstance(files,Iterable):
        raise TypeError(('files is not iterable!'))
    #Read videos and extract features in batches
    # for file in filenames:
    #vid = imageio.get_reader(file, 'ffmpeg')
    batch_size=10
    if 80%batch_size!=0:
        raise ValueError(('batch size(%d) is error!must bu factor of 80.'%(batch_size)))
    batch_video_features=[]
    for tup in files:
        file,start_time,end_time,time_point=tup
        v_cap=cv2.VideoCapture()
        if not v_cap.open(file):
            raise IOError((file+' no such file.'))
        vid=[]
        rate=(time_point-start_time)/(end_time-start_time)
        frame_count=v_cap.get(7)
        target_frame=int(rate*frame_count)
        left=int(max(target_frame-39,0))
        right=int(min(target_frame+40,frame_count-1))
        frame_list=range(left,right+1)
        if len(frame_list)!=80:
            diff=80-len(frame_list)
            left_add=int(diff/2+0.5)
            right_add=diff-left_add
            temp=[left]*left_add
            temp.extend(frame_list)
            temp.extend([right]*right_add)
            frame_list=temp
        for j in range(80)[::batch_size]:
            input_numpy=np.ndarray((batch_size,3,224,224))
            temp=0
            for index in frame_list[j:j+batch_size]:
                v_cap.set(cv2.CAP_PROP_POS_FRAMES,index)
                _,f=v_cap.read()
                if index>=frame_count or index<0:
                    raise ValueError(('{0} out [0,{1}]'.format(index,frame_count)))
                input_numpy[temp]=tran(f).numpy()
                temp+=1
            out=model(torch.from_numpy(input_numpy)).detach()
            vid.extend(out.cpu().numpy().tolist())
        batch_video_features.append(vid)
    return np.array(batch_video_features)

if __name__ == "__main__":
    print('nothing!')