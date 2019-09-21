#pragma once
#include "Heap.h"

template<class T>
struct HuffmanTreeNode {
	HuffmanTreeNode<T>* left;
	HuffmanTreeNode<T>* right;
	HuffmanTreeNode<T>* parent;
	T data;
	HuffmanTreeNode(const T& data) { 
		this->data = data; 
		left = nullptr;
		right = nullptr;
		parent = nullptr;
	}
};

template<class T>
struct WeightLessThan
{
	bool operator()(HuffmanTreeNode<T>* left,HuffmanTreeNode<T>* right) {
		return left->data < right->data;
	}
};
template<class T>
struct WeightMoreThan{
	bool operator()(const HuffmanTreeNode<T>* left, const HuffmanTreeNode<T>* right) {
		return left->data > right->data;
	}
};

template<class T>
class HuffmanTree {
	typedef HuffmanTreeNode<T> Node;
private:
	Node* root;
public:
	HuffmanTree() { root = nullptr; }
	HuffmanTree(T* a,size_t n,T invalid) {
		//������С����ѡ����С��������
		//������С��
		Heap<Node*,WeightLessThan<T>> minHeap;
		for (auto i = 0; i < n; i++) {
			if (a[i] != invalid) {
				minHeap.Push(new Node(a[i]));

			}
		}
		//����Huffman��
		while (minHeap.Size() > 1) {//ע�������ѭ��������Ϊʲô��1

			//ѡ��Ȩֵ��С��������
			Node* left = minHeap.top();
			minHeap.Pop();
			Node* right = minHeap.top();
			minHeap.Pop();
			//���츸�ڵ�Ȼ����������
			Node* parent = new Node(left->data + right->data);
			//parentָ��
			parent->left = left;
			parent->right = right;
			left->parent = parent;
			right->parent = parent;
			//���Ӻ�֮��Ҫ�Ż�ȥ�Ƚ�
			minHeap.Push(parent);
		}
		root = minHeap.top();//ע�⣬����Ϊʲô��minHeap.top();
	}
	Node* getRoot() { return root; }
};
