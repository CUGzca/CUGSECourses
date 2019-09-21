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
		//利用最小堆挑选出最小的两个数
		//建立最小堆
		Heap<Node*,WeightLessThan<T>> minHeap;
		for (auto i = 0; i < n; i++) {
			if (a[i] != invalid) {
				minHeap.Push(new Node(a[i]));

			}
		}
		//建立Huffman树
		while (minHeap.Size() > 1) {//注意这里的循环条件，为什么是1

			//选出权值最小的两个点
			Node* left = minHeap.top();
			minHeap.Pop();
			Node* right = minHeap.top();
			minHeap.Pop();
			//构造父节点然后三点相连
			Node* parent = new Node(left->data + right->data);
			//parent指针
			parent->left = left;
			parent->right = right;
			left->parent = parent;
			right->parent = parent;
			//连接好之后还要放回去比较
			minHeap.Push(parent);
		}
		root = minHeap.top();//注意，这里为什么是minHeap.top();
	}
	Node* getRoot() { return root; }
};
