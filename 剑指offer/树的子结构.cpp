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
    /*bool HasSubtree(TreeNode* pRoot1, TreeNode* pRoot2)
    {
        if(pRoot2==NULL){return false;}
        if(isSame(pRoot1,pRoot2)){return true;}
        bool result=false;
        if(!result){result=isSame(pRoot1->left,pRoot2);}
        if(!result){result=isSame(pRoot1->right,pRoot2);}
        return result;
    }
    bool isSame(TreeNode* pRoot1,TreeNode *pRoot2)
    {
        if(pRoot1==NULL&&pRoot2!=NULL){return false;}
        if(pRoot1!=NULL&&pRoot1==NULL){return false;}
        if(pRoot1==NULL&&pRoot2==NULL){return true;}
        if(pRoot1->val!=pRoot2->val){return false;}
        bool result=false;
        if(!result){result=isSame(pRoot1->left,pRoot2->left);}
        if(!result){result=isSame(pRoot1->right,pRoot2->right);}
        return result;
    }*/
    bool HasSubtree(TreeNode* pRoot1, TreeNode* pRoot2)
    {
        if(!pRoot1 || !pRoot2) return false;
        bool result=false;
        if(pRoot1->val == pRoot2->val)result=isSubtree(pRoot1,pRoot2); // 找到判断子树
        if(!result) result=HasSubtree(pRoot1->left,pRoot2); // 未找到匹配的根节点以及匹配的子树，则继续向下递归
        if(!result) result=HasSubtree(pRoot1->right,pRoot2);
        return result;
    }
     
    bool isSubtree(TreeNode* pRoot1, TreeNode* pRoot2)
    {
        if(!pRoot2) return true; // 子树遍历完成（关键语句）
        if(!pRoot1) return false; // 主树异常时的输出（关键语句：提高鲁棒性）
        bool result=true;
        if(pRoot1->val!=pRoot2->val) result=false;
        if(result) result=isSubtree(pRoot1->left,pRoot2->left);
        if(result) result=isSubtree(pRoot1->right,pRoot2->right);
        return result;
    }
};