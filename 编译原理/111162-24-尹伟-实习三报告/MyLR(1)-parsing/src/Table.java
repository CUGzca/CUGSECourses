import java.util.ArrayList;

public class Table {
    public ArrayList<Node> nodes;
    public void addNode(Node node){
        if(nodes==null){
            nodes=new ArrayList<>();
            nodes.add(node);
        }else{
            nodes.add(node);
        }
    }
    public Table(){nodes=new ArrayList<>();}
    public ArrayList<Node> getNodes(){return nodes;}
    public boolean isInTable(Node node){
        boolean ok=true;
        for(int i=0;i<nodes.size();i++){
            ok=true&node.isEqual(node,nodes.get(i));
            if(ok==true){return true;}
        }
        return false;
    }
    public int size(){return nodes.size();}
    public void printTable(){
        for(Node n:nodes){
            n.printNode();
        }
    }
}
