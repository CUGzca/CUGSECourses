import java.util.ArrayList;
public class Solution {
    public int NumberOf1Between1AndN_Solution(int n) {
        if(n==0){return 0;}
        ArrayList<Integer> number=new ArrayList<>();
        while(n!=0){number.add(n%10);n=n/10;}
        int res=0;
        for(int i=number.size()-1;i>=0;i--){
            int left=0,right=0,t=1;
            for(int j=number.size()-1;j>i;j--){left=left*10+number.get(j);}
            for(int j=i-1;j>=0;j--){right=right*10+number.get(j);t*=10;}
            res+=left*t;
            if(number.get(i)==1){res+=right+1;}
            else if(number.get(i)>1){res+=t;}
        }
        return res;
    }
    public int NumberOf1(int n){
        int res=0;
        String str=String.valueOf(n);
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='1'){res++;}
        }
        return res;
    }
}