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
    vector<int> PrintFromTopToBottom(TreeNode* root) {
        vector<int> res;
       if(root==NULL){return res;}
        queue<TreeNode*> q;
       q.push(root);
        while(q.size()!=0){
            TreeNode *temp=q.front();
            q.pop();
            res.push_back(temp->val);
            if(temp->left){q.push(temp->left);}
            if(temp->right){q.push(temp->right);}
        }
        return res;
    }
};