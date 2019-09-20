public class Solution {
    public double Pow(double base, int exponent) {
        double x=base;
        int n=exponent;
        double ans=1;
        while(n>0){
            if((n&1)==1){
                ans=base*ans;
                base*=base;
            }
            n=n>>1;
        }
        return ans;
  }
    public double Power(double base,int exponent){
        if(exponent>0){return Pow(base,exponent);}
        if(exponent<0){return 1/Pow(base,-exponent);}
        if(exponent==0){return 1;}
        return 0;
    }
}