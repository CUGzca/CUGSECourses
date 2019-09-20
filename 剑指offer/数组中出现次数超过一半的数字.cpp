public class Solution {
    public int MoreThanHalfNum_Solution(int [] array) {
        int count=0,value=-1;
        for(int i=0;i<array.length;i++){
            if(count==0){
                value=array[i];
                count=1;
            }
            else{
                if(array[i]==value){count++;}
                else{count--;}
            }
        }
        int cnt=0;
        for(int i=0;i<array.length;i++){
            if(value==array[i]){cnt++;}
        }
        if(cnt*2>array.length){return value;}
        //if(count==0){return 0;}
        return 0;
    }
}