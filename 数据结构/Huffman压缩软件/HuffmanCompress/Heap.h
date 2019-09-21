#pragma once
#define _CRT_SECURE_NO_WARNINGS 1
#include<iostream>
#include<string>
#include<vector>
#include<assert.h>
#include<algorithm>
using namespace std;

template<class T>
struct LessThan
{
	T left; T right;
	bool operator() (const T& left, const T& right){
		return left < right;
	}
};
template<class T>
struct MoreThan
{
	T left; T right;
	bool operator()(const T& left,const T& right) {
		return left > right;
	}
};

template<class T,class Compare=LessThan<T>>
class Heap {
private:
	vector<T> _a;
public:
	Heap() {}
	Heap(T* a,size_t n) {
		_a.reserve(n);//对_a进行扩容
		for (int i = 0; i < n; i++) { _a.push_back(a[i]); }
		//建堆
		for (int i = (_a.size() - 2) / 2; i >= 0; i--)
		{
			AdjustDown(i);
		}
	}
	void AdjustDown(int root) {
		Compare con;
		int parent = root;
		int child = 2 * root + 1;
		while(child<_a.size()){
			if (child != _a.size() - 1&&con(_a[child+1],_a[child])) {//不是最后最后一个
				child++;
			}
			if (con(_a[child], _a[parent])) {
				swap(_a[child], _a[parent]);
				child = parent;
				parent = (parent - 1) / 2;
			}
			else {
				break;

			}
		}
	}
	void Push(const T& d) {
		_a.push_back(d);
		AdjustDown(_a.size()-1);
	}
	void Pop() {
		swap(_a[0], _a[_a.size() - 1]);
		_a.pop_back();
		AdjustDown(0);
	}
	T& top() { return _a[0]; }
	size_t Size() { return _a.size();}
};