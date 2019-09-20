public class Solution {
    public int NumberOf1(int n) {
        int count=0;
        for(int i=0;i<32;i++){
            int m=n>>i;
            if((m&1)==1){count++;}
        }
        return count;
    }
}