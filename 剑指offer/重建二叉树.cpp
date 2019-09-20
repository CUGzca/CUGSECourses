/**
 * Definition for binary tree
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
public:
    TreeNode* reConstructBinaryTree(vector<int> pre,vector<int> vin) {
        return R(pre,0,pre.size()-1,vin,0,vin.size()-1);
    }
    TreeNode* R(vector<int> a,int abegin,int aend,vector<int> b,int bbegin,int bend){
        if(abegin>aend||bbegin>bend){return NULL;}
        TreeNode* root=new TreeNode(a[abegin]);
        int pivot;
        for(int i=0;i<b.size();i++){
            if(b[i]==a[abegin]){
                pivot=i;break;
            }
        }
        root->left=R(a,abegin+1,abegin+pivot-bbegin,b,bbegin,pivot-1);
        root->right=R(a,abegin+pivot-bbegin+1,aend,b,pivot+1,bend);
        return root;
    }
};