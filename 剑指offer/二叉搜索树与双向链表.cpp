/*
struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
    TreeNode(int x) :
            val(x), left(NULL), right(NULL) {
    }
};*/
class Solution {
public:
    TreeNode* Convert(TreeNode* pRootOfTree)
    {
        if(pRootOfTree==NULL){return NULL;}
        TreeNode* pre=NULL;
        ConvertHelper(pRootOfTree,pre);
        while(pre->left){
            pre=pre->left;
        }
        return pre;
    }
    void ConvertHelper(TreeNode* root,TreeNode* &pre){
        if(root==NULL){return ;}
        ConvertHelper(root->left,pre);
        root->left=pre;
        if(pre!=NULL){pre->right=root;}
        pre=root;
        ConvertHelper(root->right,pre);
    }
};