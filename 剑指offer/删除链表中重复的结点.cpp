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
    ListNode* deleteDuplication(ListNode* pHead)
    {
       if(pHead==NULL||pHead->next==NULL){return pHead;}
        ListNode *head=new ListNode(0);
        head->next=pHead;
        ListNode *pre=head;
        ListNode *last=head->next;
        while(last!=NULL){
            if(last->next&&last->val==last->next->val){
                while(last->next&&last->val==last->next->val){
                    last=last->next;
                }
                pre->next=last->next;
                last=last->next;
            }else{
                pre=pre->next;
                last=last->next;
            }
        }
        return head->next;
    }
};