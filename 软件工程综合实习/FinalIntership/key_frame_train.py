import os
import time
import torch
import numpy as np
import torch.nn as nn
import torch.optim as optim
from torchvision import models
from torch.autograd import Variable
from torch.utils.data import Dataset
from torch.utils.data import DataLoader

x, y = [], []
for fileName in os.listdir('npz'):
    soccer = np.load('npz/'+fileName)
    for array in soccer:
        x.append(soccer[array][:, :448])
        y.append(soccer[array][0, 448:].astype(int))

class SoccerDataset(Dataset):
    
    def __init__(self, x, y): 
        super(SoccerDataset, self).__init__()
        self.x = x
        self.y = y
        self.length = len(x)
        
    def __getitem__(self, index):
        img = torch.tensor(self.x[index], dtype=torch.float).unsqueeze(0)
        label = self.y[index]
        return img, label
    
    def __len__(self):
        return self.length

epoch = 0
lr = 1e-3
decay = 1e-8

net = models.vgg16().cuda()
net.features[0] = nn.Conv2d(1, 64, kernel_size=(3, 3), stride=(1, 1), padding=(1, 1)).cuda()
net.features = net.features[:30]
net.classifier[6] = nn.Linear(in_features=4096, out_features=448, bias=True).cuda()

loss = nn.CrossEntropyLoss()
optimizer = optim.Adam(net.parameters(),lr = lr,weight_decay=decay)

batch_size = 4
train_set = SoccerDataset(x, y)
train_loader = DataLoader(train_set, batch_size=batch_size, shuffle=True)

print("轮数", "损失", "样本数", "准确率")

for epoch in range(5):
    print(time.asctime(time.localtime(time.time())))
    running_loss, count, acc = 0., 0, 0
    for img, label in train_loader:
        va = Variable(img).cuda()
        output_fc = net(va)
        target = label[:, 1].cuda().long()
        optimizer.zero_grad()
        ls = loss(output_fc, target)
        ls.backward()
        optimizer.step()
        running_loss += ls.item()
        count += batch_size
        acc += ((output_fc.max(dim=1)[1] == target).sum(dim=0))
        if count % 180 == 0:
            print(epoch+1, running_loss, count, int(acc)/count)
            running_loss = 0.

torch.save(net, 'key_frame_network.pth')