/*
struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) :
        val(x), next(NULL) {
    }
};
*/
class Solution {
public:
    ListNode* EntryNodeOfLoop(ListNode* pHead)
    {
        if(pHead==NULL||pHead->next==NULL){return NULL;}
        ListNode *fast=pHead;
        ListNode *low=pHead;
        while(fast&&fast->next){
            fast=fast->next->next;
            low=low->next;
            if(fast==low){//第一次相遇
                low=pHead;//放到头结点
                break;
            }
        }
        while(fast&&fast!=low){//防止没有环
            fast=fast->next;
            low=low->next;
        }
        return fast;
    }
};