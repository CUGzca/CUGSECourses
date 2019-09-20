import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
public class Solution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> result=new ArrayList<>();
        int length=input.length;
        if(k>length||k==0){return result;}
        PriorityQueue<Integer> maxHeap=new PriorityQueue<>(k,new Comparator<Integer>(){
           public int compare(Integer o1,Integer o2){
               return o2.compareTo(o1);
           } 
        });
        
        for(int i=0;i<length;i++){
            if(maxHeap.size()!=k){
                maxHeap.offer(input[i]);
            }else if(maxHeap.peek()>input[i]){
                maxHeap.poll();
                maxHeap.offer(input[i]);
            }
        }

        for(Integer it:maxHeap){
            result.add(it);
        }
        return result;
    }
}