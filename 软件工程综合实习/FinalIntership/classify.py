import os
import cv2
import torch
from VGG_extract_soccer_video_features import Utils
from validate import get_caption_a_video

index2label = {
    0: 'whistle',
    1: 'soccer-ball',
    2: 'normal',
    3: 'corner',
    4: 'y-card',
    5: 'substitution',
    6: 'time',
    7: 'injury',
    8: 'funfact',
    9: 'soccer_ball_own',
    10: 'r_card',
    11: 'penalty',
    12: 'penalty-missed',
    13: 'yr-card',
    14: 'attendance'
}


def classify_all(videoFileName):
    name = videoFileName.split('.')[0].split('/')[-1]+'.jpg'
    path = '/root/FinalIntership/static/images/' + name
    features = torch.tensor(Utils().extract_feature_a_video(videoFileName), dtype=torch.float).unsqueeze(0).unsqueeze(0)
    net1 = torch.load('/root/classificationNetwork.pth')
    label = int(net1(features).max(dim=1)[1][0])
    del net1
    net2 = torch.load('/root/keyFrameNetwork.pth')
    keyFrame = int(net2(features).max(dim=1)[1][0])
    del net2
    cameraCapture = cv2.VideoCapture(videoFileName)
    frame = 0
    for _ in range(int(keyFrame/448*cameraCapture.get(7))+1):
        _, frame = cameraCapture.read()
    cv2.imwrite(path, frame)
    if keyFrame < 40:
        keyFrame = 40
    if keyFrame >= 409:
        keyFrame = 408
    return index2label[label], get_caption_a_video((keyFrame-40, keyFrame+39), videoFileName), name


def classify_just(videoFileName):
    name = videoFileName.split('.')[0]+'.jpg'
    path = os.getcwd() + '/' + name
    features = torch.tensor(Utils().extract_feature_a_video(videoFileName), dtype=torch.float).unsqueeze(0).unsqueeze(0)
    net1 = torch.load('/root/classificationNetwork.pth')
    label = int(net1(features).max(dim=1)[1][0])
    del net1
    net2 = torch.load('/root/keyFrameNetwork.pth')
    keyFrame = int(net2(features).max(dim=1)[1][0])
    del net2
    return index2label[label]


def describe_just(videoFileName):
    features = torch.tensor(Utils().extract_feature_a_video(videoFileName), dtype=torch.float).unsqueeze(0).unsqueeze(0)
    net2 = torch.load('/root/keyFrameNetwork.pth')
    keyFrame = int(net2(features).max(dim=1)[1][0])
    del net2
    if keyFrame < 40:
        keyFrame = 40
    if keyFrame >= 409:
        keyFrame = 408
    return get_caption_a_video((keyFrame-40, keyFrame+39), videoFileName)


if __name__ == '__main__':
    print(describe_just('video0.avi'))
