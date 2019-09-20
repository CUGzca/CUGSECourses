/*
struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) :
            val(x), next(NULL) {
    }
};*/
class Solution {
public:
    ListNode* ReverseList(ListNode* pHead) {
        ListNode * reverseHead=nullptr;
        ListNode *currentNode=pHead;
        ListNode* prevNode=nullptr;
        while(currentNode!=nullptr){
            ListNode* next=currentNode->next;
            if(next==nullptr){reverseHead=currentNode;}
            currentNode->next=prevNode;
            prevNode=currentNode;
            currentNode=next;
        }
        return reverseHead;
    }
};