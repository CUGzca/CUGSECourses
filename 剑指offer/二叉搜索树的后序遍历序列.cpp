public class Solution {
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length==0){return false;}
        return dfs(sequence,0,sequence.length-1);
    }
    public boolean dfs(int[] sequence,int start,int end){
        if(start>=end){return true;}
        int root=sequence[end];
        int k=start;
        while(k<end&&sequence[k]<root){k++;}
        for(int i=k;i<end;i++){
            if(sequence[i]<root){return false;}
        }
        return dfs(sequence,start,k-1)&&dfs(sequence,k+1,end-1);
    }
}