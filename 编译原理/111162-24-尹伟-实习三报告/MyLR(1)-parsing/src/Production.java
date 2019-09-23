import java.util.ArrayList;

public class Production {
    public char leftHand;
    public ArrayList<String> rightHand;

    public Production(char leftHand,ArrayList<String> rightHand){
        this.leftHand=leftHand;
        this.rightHand=rightHand;
    }
    public boolean isEqual(Production p1,Production p2,int index){//判断两个产生式是否相同。
        if(p1.leftHand==p2.leftHand&&p1.rightHand.get(0).equals(p2.rightHand.get(index))){
            return true;
//            for(int i=0;i<p1.rightHand.size();i++){
//                boolean ok=true&p1.rightHand.get(i).equals(p2.rightHand.get(i));
//                    if(ok==false){
//                        return false;
//                    }
//                }

        }else {
            return false;
        }
    }
    public Production(){
        rightHand=new ArrayList<>();
    }
    public void setProduction(String str){
        if(str.length()==0){return;}
        this.leftHand=str.charAt(0);
        String s=str.substring(3);
        String right[]=s.split("\\|");
        for(int i=0;i<right.length;i++){
            if(!("|".equals(right[i]))){
                rightHand.add(right[i]);
            }
        }
    }
    public void printProduction(){
        System.out.println(leftHand+"->"+rightHand);
    }
}
