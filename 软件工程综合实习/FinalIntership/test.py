import torch
from torchvision import models

data = torch.randn(8, 3, 224, 224)
net = models.vgg16()

print(net(data))
