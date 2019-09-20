import java.util.Stack;

public class Solution {

     Stack<Integer> NumberStack=new Stack<>();
    Stack<Integer> helpStack=new Stack<>();
    public void push(int node) {
        NumberStack.push(node);
        if(helpStack.size()==0||node<helpStack.peek()){
            helpStack.push(node);
        }else{
            helpStack.push(helpStack.peek());
        }
    }
    public void pop() {
        NumberStack.pop();
        helpStack.pop();
    }

    public int top() {
        return helpStack.peek();
    }

    public int min() {
        return helpStack.peek();
    }
}