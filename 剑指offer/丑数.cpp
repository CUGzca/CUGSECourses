
public class Solution {
    public int GetUglyNumber_Solution(int index) {
        if(index==0){return 0;}
        int uglyNumber[]=new int[index+1];
        uglyNumber[0]=0;uglyNumber[1]=1;
        int count=2;
        int multiplyP2=1;
        int multiplyP3=1;
        int multiplyP5=1;
        while(count<=index){
            uglyNumber[count]=min(2*uglyNumber[multiplyP2],3*uglyNumber[multiplyP3],5*uglyNumber[multiplyP5]);
            while(2*uglyNumber[multiplyP2]<=uglyNumber[count]){multiplyP2++;}
            while(3*uglyNumber[multiplyP3]<=uglyNumber[count]){multiplyP3++;}
            while(5*uglyNumber[multiplyP5]<=uglyNumber[count]){multiplyP5++;}
            count++;
        }
        return uglyNumber[index];
    }
    public int min(int a,int b,int c){
        return Math.min(a,Math.min(b,c));
    }
}