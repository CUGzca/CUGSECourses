import java.util.ArrayList;

public class firstSetNode {
    public char nonTerminal;
    public ArrayList<String> firstSet;
    public firstSetNode(){
        firstSet=new ArrayList<>();
    }
    public firstSetNode(char nonTerminal,ArrayList<String> firstSet){
        this.firstSet=firstSet;
        this.nonTerminal=nonTerminal;
    }
}
