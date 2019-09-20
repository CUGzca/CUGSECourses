import java.util.ArrayList;
public class Solution {
    public void reOrderArray(int [] array) {
         ArrayList<Integer> odd=new ArrayList<>();
        ArrayList<Integer> oven=new ArrayList<>();
        for(int i=0;i<array.length;i++){
            if((array[i]&1)==1){
                odd.add(array[i]);
            }else{
                oven.add(array[i]);
            }
        }
        int i=0;
        for(Integer num:odd){
            array[i++]=num;
        }
        for(Integer num:oven){
            array[i++]=num;
        }
    }
}