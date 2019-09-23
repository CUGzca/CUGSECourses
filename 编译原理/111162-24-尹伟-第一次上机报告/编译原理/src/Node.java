import java.util.HashSet;

public class Node {
    int fromState;
    int toState;
    HashSet fromSet;
    char trans;
    HashSet toSet;
    public Node(HashSet fromSet,char trans,HashSet toSet,int fromState,int toState){
        this.fromSet=fromSet;
        this.trans=trans;
        this.toSet=toSet;
        this.fromState=fromState;
        this.toState=toState;
    }
    public void setToState(int toState){
        this.toState=toState;
    }
}


