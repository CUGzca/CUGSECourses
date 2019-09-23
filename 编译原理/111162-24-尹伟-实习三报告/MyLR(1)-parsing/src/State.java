import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public class State {
    public int state;
    public ArrayList<item> items;
    public State(int state,ArrayList<item> items){
        this.items=items;
        this.state=state;
    }

    public State(){items=new ArrayList<>();}
    public int getState(){return state;}
    public void addItem(item it){
        items.add(it);
    }
    public void setState(int state){this.state=state;}
    public ArrayList<String> getLookAHeadSet(ArrayList<String> lookAHead,String rightOfDot){
        ArrayList<String> oldLookAHeadSet=(ArrayList<String>)lookAHead.clone();
        ArrayList<String> newLookAHeadSet=new ArrayList<>();
        if(rightOfDot.length()==0){//右部没有字符则等于First(rb)=First(b)
            return oldLookAHeadSet;
        }else{
            //newLookAHeadSet=FirstSet(rightOfDot);
        }
        return newLookAHeadSet;
    }
    public void printState(){
        //System.out.println("状态"+state+":");
        for(int i=0;i<items.size();i++){
            items.get(i).printItem();
        }

    }
    public ArrayList<item> getItems(){return this.items;}
}
