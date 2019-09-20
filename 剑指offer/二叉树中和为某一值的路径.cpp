import java.util.ArrayList;
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    ArrayList<ArrayList<Integer>> res=new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> path=new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        dfs(root,target);
        return res;
    }
    public void dfs(TreeNode root,int target){
        if(root==null){return;}
        path.add(root.val);
        target=target-root.val;
        if(root.left==null&&root.right==null&&target==0){
            res.add(new ArrayList<>(path));
        }
        if(root.left!=null){dfs(root.left,target);}
        if(root.right!=null){dfs(root.right,target);}
        path.remove(path.size()-1);
    }
}