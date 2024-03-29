// HuffmanCompress.cpp : 此文件包含 "main" 函数。程序执行将在此处开始并结束。
//
#define _CRT_SECURE_NO_WARNINGS 1

#include "pch.h"
#include "HuffmanTree.h"
#include<vector>
#include<assert.h>
#include <iostream>
using namespace std;
typedef long long LongType;
struct CharInfo {
	char ch;
	LongType count;
	string HuffmanCode;

	bool operator < (const CharInfo& ch) {
		return this->count < ch.count;
	}

	bool operator !=(const CharInfo& ch) {
		return this->count != ch.count;
	}

	CharInfo operator+(const CharInfo& ch) {
		CharInfo res;
		res.count = this->count + ch.count;
		return res;
	}
	
	
};

class FileComprass {
public:
	typedef HuffmanTreeNode<CharInfo> CharNode;
private:
	CharInfo Info[256];
public:
	FileComprass() 
	{
		for (auto i = 0; i < 256; i++) {
			Info[i].ch = i;
			Info[i].count = 0;

		}
	}
	struct ConfigurationInformation {
		char ch;
		LongType count;
	};
	void Comprass(const char* filename) {
		assert(filename);
		FILE* fout = fopen(filename,"rb");//二进制读取
		assert(fout);//读取是否成功
		//统计字符出现的次数
		size_t  chcount=0;
		int ch=fgetc(fout);
		while (!feof(fout)) {
			Info[(char)ch].count++;
			chcount++;
			ch = fgetc(fout);
		}

		cout << "原文件字符总数：" << chcount << endl;
		//搭建出现字母的赫夫曼树，将文件中出现的字母进行建堆，
		//没有出现就不需要建堆了 所以需要用的invalid
		CharInfo invalidNode;
		invalidNode.count = 0;
		HuffmanTree<CharInfo> h(Info, 256, invalidNode);//出现次数为0的字母包

		string code;
		getHuffmanCode(h.getRoot(),code);//得到所有节点的Huffman编码

		//写配置信息
		string ComprassFilename = filename ;
		ComprassFilename += ".huffman";
		FILE* fin = fopen(ComprassFilename.c_str(),"wb");
		assert(fin);

		//写入二进制信息
		ConfigurationInformation cinfo;
		for (auto i = 0; i < 256; i++) {
			if (Info[i].count != 0) {
				cinfo.ch = Info[i].ch;//这个字符
				cinfo.count = Info[i].count;//这个字符出现的次数
			    fwrite(&cinfo,sizeof(ConfigurationInformation),1,fin);
			}
		}
		cinfo.count = 0;//這里相当于写一个结束标志方便后面解压的时候配合
		fwrite(&cinfo,sizeof(ConfigurationInformation),1,fin);
		
		char value = 0;
		int count = 0;
		int comprasschcount = 0;
		int comprassBytes = 0;
		int countBytes = 0;
		fseek(fout, 0, SEEK_SET);
		char ch1 = fgetc(fout);
		while (!feof(fout)) {
			string code = Info[(unsigned char)ch1].HuffmanCode;
			
			for (auto i = 0; i < code.size(); i++) {
				value = value << 1;
				if (code[i] == '1') {
					value |= 1;
				}
				else {
					value |= 0;
				}
				count++;
				if (count == 8) {
					fputc(value, fin);
					value = 0;
					count = 0;
					countBytes++;
				}
			}
			ch1 = fgetc(fout);//处理下一个
			comprasschcount++;
		}
		if (count != 0) {//处理为满8个的情况
			value = value << (8 - count);
			fputc(value, fin);
			countBytes++;
		}

		fclose(fin);
		fclose(fout);
		cout << "压缩了" << comprasschcount << "个字符" << endl;
		cout << "节约了" << comprasschcount - countBytes << "个字符" << endl;
	}
	//从高处往下递归递归下去，遇到叶子结点就把编码放到字母包的赫夫曼编码里面去。
	void getHuffmanCode(CharNode* root,string code) {
		if (root == NULL) { return; }
		if (root->left == NULL && root->right == NULL) {
			Info[(unsigned char)root->data.ch].HuffmanCode = code;
			return;
		}
		getHuffmanCode(root->left, code + '0');
		getHuffmanCode(root->right, code + '1');
	}
	void UnComprass(const char* filename) {
		assert(filename);
		string uncomprassfile = filename;
		int pos = uncomprassfile.rfind('.');
		uncomprassfile=uncomprassfile.substr(0,pos);
		FILE* fin = fopen(uncomprassfile.c_str(),"wb");
		assert(fin);
		FILE* fout = fopen(filename, "rb");
		//压缩文件二进制写，则解压的时候使用二进制方式读
		assert(fout);

		while (true) {
			ConfigurationInformation cinfo;
			fread(&cinfo,sizeof(ConfigurationInformation),1,fout);
			
			if (cinfo.count) {
				Info[(unsigned char)cinfo.ch].ch = cinfo.ch;
				Info[(unsigned char)cinfo.ch].count = cinfo.count;
			}
			else {
				break;
			}
		}
		//重建Huffman树
		CharInfo invalid;
		invalid.count = 0;
		HuffmanTree<CharInfo> h(Info,256,invalid);
		CharNode* root = h.getRoot();
		int charCount = root->data.count;//记录原文件中一共有多少字符

		char value = fgetc(fout);
		CharNode* cur = root;
		LongType ChCount = 0;
		while (!feof(fout)) {
			for (int pos = 7; pos >= 0; pos--) {
				if (value&(1 << pos)) {//如果是1，就从Huffman树往右走
					cur = cur->right;
				}
				else {
					cur = cur->left;
				}
				if (cur->left == nullptr&&cur->right == nullptr) {
					fputc(cur->data.ch, fin);
					ChCount++;
					cur = root;
					if (ChCount == charCount) {
						break;
					}
				}
			}
			value = fgetc(fout);
		}
		fclose(fin);
		fclose(fout);
		cout << "解压了" << ChCount << "个字符" << endl;
	}
};
void testPictureComprass() {
	FileComprass filecomprass;
	filecomprass.Comprass("IMG_20190730_183959.jpg");
}

void testDocumentComprass() {
	FileComprass filecomprass;
	filecomprass.Comprass("DocumentTest.doc");
}
void testDocumentUnComprass() {
	FileComprass filecomprass;
	filecomprass.UnComprass("DocumentTest.doc.huffman");
}
void testPictureUnComprass() {
	FileComprass filecomprass;
	filecomprass.UnComprass("IMG_20190730_183959.jpg.huffman");
}
int main()
{
	//testComprass();
	//testPictureComprass();
	testDocumentComprass();
	testDocumentUnComprass();
	//testPictureComprass();
	//testPictureUnComprass();

    std::cout << "Hello World!\n"; 
}

// 运行程序: Ctrl + F5 或调试 >“开始执行(不调试)”菜单
// 调试程序: F5 或调试 >“开始调试”菜单

// 入门提示: 
//   1. 使用解决方案资源管理器窗口添加/管理文件
//   2. 使用团队资源管理器窗口连接到源代码管理
//   3. 使用输出窗口查看生成输出和其他消息
//   4. 使用错误列表窗口查看错误
//   5. 转到“项目”>“添加新项”以创建新的代码文件，或转到“项目”>“添加现有项”以将现有代码文件添加到项目
//   6. 将来，若要再次打开此项目，请转到“文件”>“打开”>“项目”并选择 .sln 文件
