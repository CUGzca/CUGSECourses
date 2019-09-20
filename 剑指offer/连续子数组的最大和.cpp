public class Solution {
    public int FindGreatestSumOfSubArray(int[] array) {
        int length=array.length;
        if(length==0){return 0;}
        int curSum=0;int maxSum=Integer.MIN_VALUE;
        for(int i=0;i<length;i++){
            if(curSum<=0){curSum=array[i];}
            else{
                curSum+=array[i];
            }
            if(curSum>maxSum){
                maxSum=curSum;
            }
        }
        return maxSum;
    }
}