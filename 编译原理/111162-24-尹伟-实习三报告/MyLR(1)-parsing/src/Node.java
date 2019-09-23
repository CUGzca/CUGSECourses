public class Node{
    public int state;
    public String symbol;
    public String action;
    public Node(){}
    public Node(int state,String symbol,String action){
        this.state=state;
        this.symbol=symbol;
        this.action=action;
    }
    public void printNode(){
        System.out.println("状态："+state+"--"+symbol+"-->"+action);
    }
    public boolean isEqual(Node node1,Node node2){
        return node1.state==node2.state&&
                node1.symbol.equals(node2.symbol)&&
                node1.action.equals(node2.action);
    }
}