import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public class grammar {
    public ArrayList<Production> g;
    public ArrayList<Production> singleProduces;

    public grammar(){
        g=new ArrayList<>();
        singleProduces=new ArrayList<>();
    }
    public void transform(){
        for(int i=0;i<g.size();i++){
            for(int j=0;j<g.get(i).rightHand.size();j++){
                char ch=g.get(i).leftHand;
                String rightHand=g.get(i).rightHand.get(j);
                ArrayList<String> temp=new ArrayList<>();
                temp.add(rightHand);
                Production p=new Production(ch,temp);
                singleProduces.add(p);
            }
        }
    }

    public void addProduction(Production production){
        g.add(production);
        //transform();
    }
    public int findProduction(Production p1){
        int count=0;
        for(int i=0;i<g.size();i++){
            Production p2=g.get(i);
            for(int j=0;j<p2.rightHand.size();j++){
                if(p2.isEqual(p1,p2,j)){
                    return count;
                }else{
                    count=count+1;
                }
            }

        }
        return count;
    }

    public void parseString(String input){
        int j=0;
        for(int i=0;i<input.length();i++){
            if(input.substring(i,i+1).equals(";")){
                String productionString=input.substring(j,i);
                char leftHand=productionString.charAt(0);
                ArrayList<String> rightHand=new ArrayList<>();

                String right=productionString.substring(3);
                if(right.contains("|")){
                    String[] s=right.split("\\|");
                    for(int k=0;k<s.length;k++){
                            rightHand.add(s[k]);
                    }
                }else{
                    rightHand.add(right);
                }
                Production p=new Production(leftHand,rightHand);
                addProduction(p);
                j=i+1;
            }
        }
    }
    public void printGrammar(){
        for(int i=0;i<g.size();i++){
            g.get(i).printProduction();
            //System.out.println();
        }

    }
    public Production getFirstProduction(){
        return g.get(0);
    }//还回第一条产生式
    public boolean isAlphabet(char c){
        return c>='A'&&c<='Z';
    }

    public void firstSet(String str,HashSet firstset){//传入一个字符或者字符串，求他的first集
        if(str.length()==0){return;}
        String s0=str.substring(0,1);
        if(isUpper(s0)){
            for(Production p:g){
                if(s0.equals(String.valueOf(p.leftHand))){
                    for(int j=0;j<p.rightHand.size();j++){
                        if(p.rightHand.get(j).equals("@")){
                            if(str.length()!=1){
                                firstSet(str.substring(1),firstset);
                                //firstset.add(result);
                            }else{
                                firstset.add("@");

                            }
                        }else{
                            firstSet(p.rightHand.get(j),firstset);
                        }
                    }
                }
            }
        }else{
            firstset.add(s0);
        }
    }
    public boolean isUpper(String s0){
        return Pattern.matches("[A-Z]",s0);
    }
}
