# !/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon May  6 09:34:04 2019

@author: tang
"""
from module import *
import torch
from torch import nn
from utils import *
import pretrainedmodels
import pretrainedmodels.utils
import numpy as np
import os

BATCH_SIZE = 1
vovab_size = len(word_counts)


def load_data():
    data = np.load('/root/sd/0.npy')
    data = torch.from_numpy(data)
    print(1)
    model = models.vgg16()
    model.classifier[6] = pretrainedmodels.utils.Identity()
    model = model.double()
    print(2)
    print(model(torch.randn(1,3,224,224).double()))
    print(3)
    print(model(data))
    exit()


def write_txt(caps, labels=False):
    mark = 'predict: '
    if labels:
        mark = 'label: '
    with open("./testing_log.txt", 'a+') as f:
        for cap in caps:
            f.write(mark + "%s\n" % (cap))

from torchvision import models

def val_extract_feature(start, end, file_name):
    #print('VGG16 loading...')
    #model = pretrainedmodels.__dict__['vgg16']()
    #model.last_linear = pretrainedmodels.utils.Identity()
    #model.eval()
    #model.double()
    #mean = model.mean
    #std = model.std
    #for param in model.parameters():
    #    param.require_grad = False
    # ld_img=pretrainedmodels.utils.LoadImage()
    # model_img=pretrainedmodels.utils.TransformImage(model)
    tran = transforms.Compose([ \
        transforms.ToPILImage(), \
        transforms.Grayscale(3), \
        transforms.Resize((224, 224)), \
        transforms.ToTensor()
    ])
    #model = models.vgg16()
    #model.classifier[6] = pretrainedmodels.utils.Identity()
    #model = model.double()
    #print('VGG16 loaded')
    cap = cv2.VideoCapture()
    if not cap.open(file_name):
        raise ValueError(('File doesn\'t exist.'))
    frame_count = cap.get(7)
    target_frame = int(0.5 * (frame_count - 1))
    left = int(max(target_frame - 223, 0))
    right = int(min(target_frame + 224, frame_count - 1))
    find_list = range(left, right + 1)
    left_add = 0
    right_add = 0
    if len(find_list) < 448:
        diff_len = 448 - len(find_list)
        left_add = int(diff_len / 2 + 0.5)
        right_add = diff_len - left_add
        temp_list = [left] * left_add
        temp_list.extend(find_list)
        temp_list.extend([right] * right_add)
        find_list = temp_list
    batch = []
    vid = []
    batch_video_features = []
    num = 0
    for j in find_list[start:end + 1]:
        cap.set(cv2.CAP_PROP_POS_FRAMES, j)
        _, f = cap.read()
        batch.append(f)
        if len(batch) == 8:
            input_ = np.ndarray((8, 3, 224, 224))
            for m in range(len(batch)):
                input_[m] = tran(batch[m]).numpy()
            batch.clear()
            np.save('/root/sd/'+str(num)+'.npy', input_)
            num+=1
            #out = model(input_)
            #out = out.detach()
            #vid.extend(out.numpy().tolist())
    #batch_video_features.append(vid)
    return #np.array(batch_video_features)


def get_caption_a_video(ambit, file_name):
    load_data()
    ans = ''
    start, end = ambit
    val_extract_feature(start, end, file_name)
    features = load_data()
    features = torch.FloatTensor(features)
    s2vt = S2VT(vocab_size=vovab_size, batch_size=1)
    # s2vt = s2vt.cuda()
    s2vt.load_state_dict(torch.load("/root/s2vt_params.pkl"))
    s2vt.eval()
    cap_out = s2vt(features)
    captions = []
    for tensor in cap_out:
        captions.append(tensor.tolist())
    captions = [[row[i] for row in captions] for i in range(len(captions[0]))]
    captions_english = [[id2word[word] for word in c] for c in captions]
    for cap in captions_english:
        if '<EOS>' in cap:
            cap = cap[0:cap.index('<EOS>')]
        ans = ' '.join(cap)
        return ans


def validate():
    s2vt = S2VT(vocab_size=vovab_size, batch_size=BATCH_SIZE)
    # s2vt = s2vt.cuda()
    # s2vt.load_state_dict(torch.load("./s2vt_params.pkl"))
    # s2vt.eval()
    loss_func = nn.CrossEntropyLoss()
    total_loss = 0
    total_num = 0

    print('VGG16 loading...')
    model = pretrainedmodels.__dict__['vgg16']()
    model.last_linear = pretrainedmodels.utils.Identity()
    model.eval()
    model.double()
    mean = model.mean
    std = model.std
    for param in model.parameters():
        param.require_grad = False
    # ld_img=pretrainedmodels.utils.LoadImage()
    # model_img=pretrainedmodels.utils.TransformImage(model)
    tran = transforms.Compose([ \
        transforms.ToPILImage(), \
        transforms.Grayscale(3), \
        transforms.Resize((224, 224)), \
        transforms.ToTensor(), \
        transforms.Normalize(mean, std)
    ])
    print('VGG16 loaded')
    for video, caption, cap_mask in fetch_val_data(BATCH_SIZE, model, tran):
        video, caption, cap_mask = torch.FloatTensor(video), torch.LongTensor(caption), \
                                   torch.FloatTensor(cap_mask)
        s2vt.train()
        cap_out = s2vt(video, caption)
        cap_labels = caption[:, 1:].contiguous().view(-1)  # size [batch_size, 79]
        cap_mask = cap_mask[:, 1:].contiguous().view(-1)  # size [batch_size, 79]

        logit_loss = loss_func(cap_out, cap_labels)
        masked_loss = logit_loss * cap_mask
        loss = float(torch.sum(masked_loss) / torch.sum(cap_mask))
        total_loss += loss
        total_num += cap_labels.shape[0]
        captions = []
        s2vt.eval()
        cap_out = s2vt(video)
        for tensor in cap_out:
            captions.append(tensor.tolist())

        captions = [[row[i] for row in captions] for i in range(len(captions[0]))]
        captions_english = [[id2word[word] for word in c] for c in captions]
        captions_labels = [[id2word[int(word)] for word in c] for c in caption]
        write_txt(captions_english)
        write_txt(captions_labels, True)
    print("total loss: %f" % (total_loss / total_num))
    return total_loss / total_num


if __name__ == "__main__":
    print(get_caption_a_video((0, 79), '/home/ftp/soccernet-60s-clips/rename/video5.avi'))


