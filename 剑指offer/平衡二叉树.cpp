class Solution {
public:
    bool IsBalanced_Solution(TreeNode* pRoot) {
        if(pRoot==NULL){return true;}
        int h=TreeDepth(pRoot->left)-TreeDepth(pRoot->right);
        if(h<=1&&h>=-1){return IsBalanced_Solution(pRoot->left)&&IsBalanced_Solution(pRoot->right);}
        else{return false;}
        
    }
    int TreeDepth(TreeNode* pRoot){
        if(pRoot==NULL){return 0;}
        int l=1+TreeDepth(pRoot->left);
        int r=1+TreeDepth(pRoot->right);
        return max(l,r);
    }
    
};