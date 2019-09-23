import java.util.ArrayList;
import java.util.HashSet;

public class item {
    public char leftHand;
    public HashSet lookAheadSet;//展望字符
    public String leftOfDot;//点的左部
    public String rightCharOfDot;//点的右部
    public String rightOfDot;//这里是除了下一个字符的右部




    public boolean isEnd;

    public item(){}
    public char getLeftHand(){return this.leftHand;}
    public String getLeftOfDot(){return this.leftOfDot;}
    public String getRightOfDot(){return this.rightOfDot;}
    public String getRightCharOfDot(){return this.rightCharOfDot;}
    public HashSet getLookAheadSet(){return this.lookAheadSet;}

    public void setLookAheadSet(HashSet lookAheadSet){this.lookAheadSet=lookAheadSet;}
    public void setRightCharOfDot(String rightCharOfDot){this.rightCharOfDot=rightCharOfDot;}
    public void setLeftOfDot(String leftOfDot){this.leftOfDot=leftOfDot;}
    public void setRightOfDot(String rightOfDot){this.rightOfDot=rightOfDot;}

    public boolean isVisited=false;
    public void setVisited(boolean isVisited){this.isVisited=isVisited;}
    public item(char leftHand,String leftOfDot,String rightCharOfDot,String rightOfDot,HashSet lookAheadSet){
        this.leftHand=leftHand;
        this.leftOfDot=leftOfDot;
        this.rightCharOfDot=rightCharOfDot;
        this.lookAheadSet=lookAheadSet;
        this.rightOfDot=rightOfDot;
        isEnd=false;
    }
    public void setEnd(boolean end){this.isEnd=end;}
    public void printItem(){
        System.out.println(leftHand+"-->"+leftOfDot+"."+rightCharOfDot+rightOfDot+"   *****展望字符："+lookAheadSet);
    }

}
