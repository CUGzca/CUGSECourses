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
        vector<vector<int> > Print(TreeNode* pRoot) {
            vector<vector<int>> res;
            if(pRoot==NULL){return res;}
            queue<TreeNode*> q1;
            queue<TreeNode*> q2;
            q1.push(pRoot);
            while(!q1.empty()||!q2.empty()){
                vector<int > temp;
                if(!q1.empty()){
                    while(!q1.empty()){
                        TreeNode *t=q1.front();
                        q1.pop();
                        temp.push_back(t->val);
                        if(t->left){q2.push(t->left);}
                        if(t->right){q2.push(t->right);}
                    }
                }else if(!q2.empty()){
                    while(!q2.empty()){
                        TreeNode* t=q2.front();
                        q2.pop();
                        temp.push_back(t->val);
                        if(t->left){q1.push(t->left);}
                        if(t->right){q1.push(t->right);}
                    }
                }
                res.push_back(temp);
            }
            return res;
        }

};