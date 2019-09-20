import java.util.HashSet;
public class Solution {
    // Parameters:
    //    numbers:     an array of integers
    //    length:      the length of array numbers
    //    duplication: (Output) 
    //the duplicated number in the array number,length of 
    //duplication array is 1,so using duplication[0] = ? in implementation;
    //                  Here duplication like pointor in C/C++, duplication[0] equal *duplication in C/C++
    //    这里要特别注意~返回任意重复的一个，赋值duplication[0]
    // Return value:       true if the input is valid, and there are some duplications in the array number
    //                     otherwise false
    public boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers==null||numbers.length<=0||duplication==null||duplication.length==0){return false;}
        HashSet set=new HashSet();
        boolean result=false;
        for(int i=0;i<numbers.length;i++){
            if(!set.contains(numbers[i])){
                set.add(numbers[i]);
            }else{
                duplication[0]=numbers[i];
                result=true;
                break;
            }
        }
        return result;
    }
}