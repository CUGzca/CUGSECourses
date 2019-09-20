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
    vector<vector<int> > Print(TreeNode* pRoot)
    {
        vector<vector<int>> res;
        if (pRoot == NULL) { return res; }
        stack<TreeNode*> stack1;
        stack<TreeNode*> stack2;
        stack1.push(pRoot);
        while (!stack1.empty() || !stack2.empty()) 
        {
            vector<int> data;
            if (!stack1.empty()) 
            {
                while (!stack1.empty())
                {
                    TreeNode* temp = stack1.top();
                    stack1.pop();
                    data.push_back(temp->val);
                    if (temp->left) { stack2.push(temp->left); }
                    if (temp->right) { stack2.push(temp->right); }
                }
            }
            else if (!stack2.empty())
            {
                while (!stack2.empty())
                {
                    TreeNode* temp = stack2.top();
                    stack2.pop();
                    data.push_back(temp->val);
                    if (temp->right) { stack1.push(temp->right); }
                    if (temp->left) { stack1.push(temp->left); }
                }
            }
            res.push_back(data);
        }
    return res;
    }
    
};