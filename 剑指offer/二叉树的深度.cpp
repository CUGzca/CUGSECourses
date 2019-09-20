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
    int TreeDepth(TreeNode* pRoot)
    {
        if(pRoot==NULL){return 0;}
        int l=1+TreeDepth(pRoot->left);
        int r=1+TreeDepth(pRoot->right);
        return max(l,r);
    }
};