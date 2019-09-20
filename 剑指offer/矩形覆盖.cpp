public class Solution {
    public int RectCover(int target) {
        if(target<=0){return 0;}
        if(target==1||target==2){return target;}
        int dp[]=new int[target+1];
        dp[0]=0;dp[1]=1;dp[2]=2;
        for(int i=3;i<=target;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[target];
    }
}