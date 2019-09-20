import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Solution {
    private char[] chs;
    private ArrayList<String> res;
    public ArrayList<String> Permutation(String str) {
        chs=str.toCharArray();
        res=new ArrayList<>();
        if(str.length()==0){return res;}
        Permutation(chs,0);
        Collections.sort(res, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return res;
    }
    public void Permutation(char[] chs,int k){
        if(k==chs.length){
            String str="";
            for(int i=0;i<chs.length;i++){str+=chs[i];}
            if(!res.contains(str)){res.add(str);}
            return;
        }
        for(int i=k;i<chs.length;i++){
            char temp=chs[i];chs[i]=chs[k];chs[k]=temp;
            Permutation(chs,k+1);
            temp=chs[i];chs[i]=chs[k];chs[k]=temp;
        }
    }
}