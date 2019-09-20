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
    ListNode* Merge(ListNode* pHead1, ListNode* pHead2)
    {
        ListNode *current=new ListNode(0);
        ListNode *merge=current;
        while(pHead1!=NULL&&pHead2!=NULL){
          if(pHead1->val<=pHead2->val){
              current->next=pHead1;
              pHead1=pHead1->next;
          }else if(pHead2->val<=pHead1->val){
              current->next=pHead2;
              pHead2=pHead2->next;
          }
            current=current->next;
        }
        if(pHead1==NULL){
            current->next=pHead2;
        }
        if(pHead2==NULL){
            current->next=pHead1;
        }
        return merge->next;
    }
};