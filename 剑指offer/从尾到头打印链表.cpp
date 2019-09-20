/**
*  struct ListNode {
*        int val;
*        struct ListNode *next;
*        ListNode(int x) :
*              val(x), next(NULL) {
*        }
*  };
*/
class Solution {
public:
    vector<int> printListFromTailToHead(ListNode* head) {
        ListNode *current=head;
        vector<int> result;
        stack<int> s;
        while(current!=NULL){
            s.push(current->val);
            current=current->next;
        }
        while(s.size()){
            result.push_back(s.top());
            s.pop();
        }
        return result;
    }
};