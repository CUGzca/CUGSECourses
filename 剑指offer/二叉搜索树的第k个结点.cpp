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
    int count=0;
    TreeNode* KthNode(TreeNode* pRoot, int k)
    {
        vector<TreeNode*> res(1000,NULL);
        if(pRoot==NULL){return pRoot;}
        if(k<=0){return NULL;}
        F(pRoot,k,res);
        return res[k-1];
    }
    void F(TreeNode* root,int k,vector<TreeNode*> &res){
        TreeNode* p=root;
        stack<TreeNode*> s;
       do{
           while(p!=NULL){
               s.push(p);
               p=p->left;
           }
           if(!s.empty()){
               p=s.top();
               s.pop();
               res[count++]=p;
               p=p->right;
           }
       }while(p!=NULL||!s.empty());
    }    
};