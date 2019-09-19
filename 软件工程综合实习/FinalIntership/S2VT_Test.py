#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue May  7 22:14:42 2019

@author: tang
"""
import sys
sys.path.insert(0, './')
import validate
import unittest
class S2VTTest(unittest.TestCase):
    def test_validation(self):
        mean_loss=validate.validate()
        self.assertGreaterEqual(3,mean_loss)

if __name__ == '__main__':
    unittest.main()
