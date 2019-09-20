import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solution {
    public String PrintMinNumber(int [] numbers) {
        int length=numbers.length;
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<length;i++){
            list.add(numbers[i]);
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                String str1=o1+""+o2;
                String str2=o2+""+o1;
                return str1.compareTo(str2);
            }
        });
        String res="";
        for(int i=0;i<length;i++){
            res+=list.get(i);
        }
        return res;
    }
}