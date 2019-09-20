/*
struct TreeLinkNode {
    int val;
    struct TreeLinkNode *left;
    struct TreeLinkNode *right;
    struct TreeLinkNode *next;
    TreeLinkNode(int x) :val(x), left(NULL), right(NULL), next(NULL) {
        
    }
};
*/
class Solution {
public:
    TreeLinkNode* GetNext(TreeLinkNode* pNode)
    {
        if(pNode==NULL){return NULL;}
        if(pNode->right){
            pNode=pNode->right;
            while(pNode->left){
                pNode=pNode->left;
            }
            return pNode;
        }else{
            while(pNode->next&&pNode!=pNode->next->left){
                pNode=pNode->next;
            }
            return pNode->next;
        }
    }
};