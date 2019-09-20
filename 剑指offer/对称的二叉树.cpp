/*
struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
    TreeNode(int x) :
            val(x), left(NULL), right(NULL) {
    }
};
*/
class Solution {
public:
    bool isSymmetrical(TreeNode* pRoot)
    {
        TreeNode *p=getMirror(pRoot);
        return isSame(pRoot,p);
    }
    TreeNode* getMirror(TreeNode* p){
        if(p==NULL){return NULL;}
        TreeNode *root=new TreeNode(p->val);
        root->right=getMirror(p->left);
        root->left=getMirror(p->right);
        return root;
    }
    bool isSame(TreeNode* p1,TreeNode* p2){
        if(p1==NULL&&p2==NULL){return true;}
        if(p1==NULL||p2==NULL){return false;}
        if(p1->val!=p2->val){return false;}
        return isSame(p1->left,p2->left)&&isSame(p1->right,p2->right);
    }
};