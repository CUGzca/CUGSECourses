import unittest
import torch
import numpy as np
from torch.autograd import Variable
from torch.utils.data import Dataset
from torch.utils.data import DataLoader


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


PATH = '/root/20.npz'


class TestNetwork(unittest.TestCase):

    def test_classifaction(self):
        x, y = [], []
        soccer = np.load(PATH)
        for array in soccer:
            x.append(soccer[array][:, :448])
            y.append(soccer[array][0, 448:].astype(int))
        net = torch.load('/root/classificationNetwork.pth')
        batch_size = 4
        train_set = SoccerDataset(x, y)
        train_loader = DataLoader(train_set, batch_size=batch_size, shuffle=True)
        count, acc = 0, 0
        for img, label in train_loader:
            va = Variable(img)
            output_fc = net(va)
            target = label[:, 0].long()
            count += batch_size
            acc += ((output_fc.max(dim=1)[1] == target).sum(dim=0))
        print('\naccuracy: {}%'.format(100*int(acc)/count))
        self.assertGreaterEqual(acc, 0.6)

    def test_key_frame(self):
        x, y = [], []
        '''
        for fileName in os.listdir('tda'):
            soccer = np.load('tda/'+fileName)
            for array in soccer:
                x.append(soccer[array][:, :448])
                y.append(soccer[array][0, 448:].astype(int))
        '''
        soccer = np.load(PATH)
        for array in soccer:
            x.append(soccer[array][:, :448])
            y.append(soccer[array][0, 448:].astype(int))
        net = torch.load('/root/keyFrameNetwork.pth')
        batch_size = 4
        train_set = SoccerDataset(x, y)
        train_loader = DataLoader(train_set, batch_size=batch_size, shuffle=True)
        count, acc = 0, 0
        for img, label in train_loader:
            va = Variable(img)
            output_fc = net(va)
            target = label[:, 1].long()
            count += batch_size
            acc += ((output_fc.max(dim=1)[1] == target).sum(dim=0))
        print('\naccuracy: {}%'.format(100*int(acc)/count))
        self.assertGreaterEqual(acc, 0.7)


if __name__ == '__main__':
    unittest.main()
