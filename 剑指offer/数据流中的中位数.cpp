import java.util.PriorityQueue;
import java.util.Comparator;

public class Solution {
 
    static PriorityQueue<Integer> min=new PriorityQueue<>(20, new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    });
    PriorityQueue<Integer> max=new PriorityQueue<>(20,new Comparator<Integer>(){
        public int compare(Integer o1,Integer o2){
            return o2-o1;
        }
    });
    public void Insert(Integer num) {
        max.add(num);
        //如果发生逆序，就交换
        if(min.size()!=0&&max.peek()>min.peek()){
            int maxV=max.peek();int minV=min.peek();
            max.remove();min.remove();
            min.add(maxV);max.add(minV);
        }
        if(max.size()-min.size()>1){
            min.add(max.peek());
            max.remove();
        }
    
    }

    public Double GetMedian() {
        if(min.size()<max.size()){
            return (double)max.peek();
        }
        return (min.peek()+max.peek())/2.0;
    }


}