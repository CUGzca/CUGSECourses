import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;

import java.util.*;
import java.util.regex.Pattern;

public class ParsingAutomation {
    public grammar gramma;
    public ArrayList<State> states;
    public Table table;
    public Stack<Integer> stateStack;
    public Stack<String>  symbolStack;

    public void print(){
        for(int i=0;i<states.size();i++){
            System.out.println("状态"+i);
            states.get(i).printState();
        }
    }
    public void printStateStack(){
        Stack<Integer> stack=new Stack<>();
        ArrayList<Integer> list=new ArrayList<>();
        while(!stateStack.isEmpty()){
            stack.push(stateStack.pop());
        }
        while(!stack.isEmpty()){
            stateStack.push(stack.peek());
            list.add(stack.peek());
            stack.pop();
        }
        for(int i=0;i<list.size();i++){

            System.out.print(list.get(i));
//            System.out.print(String.format("%-12s",list.get(i)));
        }
        //System.out.println();
    }
    public void printSymbolStack(){
        Stack<String> stack=new Stack<>();
        ArrayList<String> list=new ArrayList<>();
        while(!symbolStack.isEmpty()){
            stack.push(symbolStack.pop());
        }
        while(!stack.isEmpty()){
            symbolStack.push(stack.peek());
            list.add(stack.peek());
            stack.pop();
        }
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i));
        }
//        System.out.println();
    }
    public ParsingAutomation(){
        this.gramma=new grammar();
        states=new ArrayList<>();
        table=new Table();
        stateStack=new Stack<>();
        symbolStack=new Stack<>();
    }
    public boolean isAlpha(char ch){
        return ch>='A'&&ch<='Z';
    }
    public ArrayList<String> getNonterminal(){
        ArrayList<String> result=new ArrayList<>();
        for(Production p:gramma.g){
            if(isAlpha(p.leftHand)){
                if(!result.contains(p.leftHand)){
                    result.add(String.valueOf(p.leftHand));
                }

            }
        }
        return result;
    }
    public boolean isNonterminal(String s){
        return getNonterminal().contains(s);
    }
    public boolean isTerminal(String s){
        return getTerminal().contains(s);
    }
    public ArrayList<String> getTerminal(){
        ArrayList<String> result=new ArrayList<>();
        for(Production p:gramma.g){
            for(int i=0;i<p.rightHand.size();i++){
                for(int j=0;j<p.rightHand.get(i).length();j++){
                    char ch=p.rightHand.get(i).charAt(j);
                    if(!isAlpha(ch)){
                        if(!result.contains(String.valueOf(ch))){
                            result.add(String.valueOf(ch));
                        }

                    }
                }

            }
        }
        return result;
    }
    public ArrayList<String> getSymbols(){
        ArrayList<String> result=new ArrayList<>();
        ArrayList<String> nonTerminal=getNonterminal();
        ArrayList<String> terminal=getTerminal();
        for(String s:terminal){
            result.add(s);
        }
        result.add("$");
        for(String s:nonTerminal){
            result.add(s);
        }
        return result;
    }

    public boolean stateIsInState(State state,ArrayList<State> States,int inState){
        boolean ok=false;
        for(int i=0;i<States.size();i++){
            State s=States.get(i);
            if(s.getItems().size()!=state.getItems().size()){continue;}

            for(int j=0;j<s.getItems().size();j++){
                ok=true&isInState(s.getItems().get(j),state);
            }
            if(ok==true){
                inState=i;
                return true;
            }
        }
        return false;
    }
    public int stateIsInState1(State state,ArrayList<State> States){
        boolean ok=false;
        for(int i=0;i<States.size();i++){
            State s=States.get(i);
            if(s.getItems().size()!=state.getItems().size()){continue;}

            for(int j=0;j<s.getItems().size();j++){
                ok=true&isInState(s.getItems().get(j),state);
            }
            if(ok==true){
                return i;
            }
        }
        return -1;
    }
    public void createAutomation(){
        Production production=this.gramma.getFirstProduction();
        State initState=createInitialState(production);//找到W->.S
        constructState(initState.items.get(0));

        ArrayList<String> symbols=getSymbols();
        for(int i=0;i<states.size();i++){
            State s=states.get(i);
            for(int j=0;j<symbols.size();j++){//字符
                State state=new State();
                String symbol=symbols.get(j);

                for(int k=0;k<s.getItems().size();k++){
                    item it=s.getItems().get(k);//得到第j个项目
                    //字符
                    String rightCharOfDot=it.getRightCharOfDot();//右部第一个字符
                    if(rightCharOfDot.length()==0){//对应W->S.
                        if(it.getLookAheadSet().contains(symbol)){
                            Node node=new Node();
                            node.state=i;
                            node.symbol=symbol;
                            char c=it.leftHand;

                            ArrayList<String> rightHand=new ArrayList<>();
                            rightHand.add(it.leftOfDot);

                            Production production1=new Production(c,rightHand);//这是一条单一的产生式
                            int productionNum=gramma.findProduction(production1);
                            if(it.leftHand=='W'&&it.getLeftOfDot().equals("S")&&symbol.equals("$")){
                                node.action="a-1";
                            }else{
                                node.action="r"+productionNum;
                            }
                            if(!table.isInTable(node)){
                                table.addNode(node);
                            }
                        }
                        continue;
                    }
                    if(rightCharOfDot.equals(symbol)){/**向右移一步*/
                        char ch=it.getLeftHand();
                        String leftOfDot=it.getLeftOfDot();//左部
                        String rightOfDot=it.getRightOfDot();//右部
                        HashSet lookAHeadSet=it.getLookAheadSet();//展望字符

                        leftOfDot=leftOfDot+rightCharOfDot;

                        if(rightOfDot.length()>=1){
                            rightCharOfDot=rightOfDot.substring(0,1);
                            rightOfDot=rightOfDot.substring(1);
                        }else{
                            rightCharOfDot="";
                            rightOfDot="";
                        }

                        /***移进项目***/
                        item Item=new item(ch,leftOfDot,rightCharOfDot,rightOfDot,lookAHeadSet);
                        ArrayList<item> closureItems=closureSet1(Item);
                        for(int o=0;o<closureItems.size();o++) {
                            if(!isInState(closureItems.get(o),state)){
                                state.addItem(closureItems.get(o));
                            }
                        }
                    }
                }
                if(state.getItems().size()!=0){
                    int inState=-1;
                    if(!stateIsInState(state,states,inState)){//不在状态中
                        states.add(state);
                        Node node=new Node();
                        int size=states.size()-1;
                        if(isNonterminal(symbol)){
                            node.state=i;
                            node.symbol=symbol;
                            node.action=""+size;
                        }else if(isTerminal(symbol)){
                            node.state=i;
                            node.symbol=symbol;
                            node.action="s"+size;
                        }
                        if(!table.isInTable(node)){
                            table.addNode(node);
                        }
                    }else{//已经存在
                        int ss=stateIsInState1(state,states);
                        Node node=new Node();
                        if(isNonterminal(symbol)){
                            node.state=i;
                            node.symbol=symbol;
                            node.action=""+ss;
                        }else if(isTerminal(symbol)){
                            node.state=i;
                            node.symbol=symbol;
                            node.action="s"+ss;
                        }
                        if(!table.isInTable(node)){
                            table.addNode(node);
                        }
                    }


                }
            }

        }
        System.out.println("*****************************************************");
        System.out.println("自动机构造完成：");
        print();
        System.out.println("*****************************************************");
        System.out.println("LR1分析表：");
        table.printTable();
        System.out.println("共有"+ table.size()+"个转换关系");
        System.out.println("*****************************************************");
        gramma.transform();

    }
    public int getStateStackPeek(){return stateStack.peek();}
    public String getSymbolStackPeek(){return symbolStack.peek();}
    public void parseLR1(String inputString){
        inputString=inputString+"$";
        stateStack.push(0);
        symbolStack.push("$");

        System.out.println("*****************************************************");
        System.out.println("匹配过程：");
        System.out.print("状态栈："+" 符号栈:"+"  输入字符");
        System.out.println();
        System.out.print(getStateStackPeek()+"       "+getSymbolStackPeek()+"      "+inputString);
        System.out.println();
        int index=0;
        while(true){
            int peekState=stateStack.peek();
            String s=inputString.substring(index,index+1);
            String action=findTable(peekState,s);

            if(action.length()==0){
                System.out.println("匹配不成功");
                return;
            }
            else if(action.substring(0,1).equals("s")){//移进
                int actionState=Integer.parseInt(action.substring(1));
                stateStack.push(actionState);
                symbolStack.push(s);
                index=index+1;

                printStateStack();
                System.out.print("       ");
                printSymbolStack();
                System.out.print("      ");
                if(index<inputString.length()){
                    System.out.print(inputString.substring(index));
                }
                System.out.println();
            }else if(action.substring(0,1).equals("r")){
                int actionState=Integer.parseInt(action.substring(1));
                Production p=gramma.singleProduces.get(actionState);
                int length=p.rightHand.get(0).length();
                for(int i=0;i<length;i++){
                    symbolStack.pop();
                    stateStack.pop();
                }
                symbolStack.push(String.valueOf(p.leftHand));
                int peek=stateStack.peek();
                String gotoState=findTable(peek,String.valueOf(p.leftHand));
                int gState=Integer.parseInt(gotoState);
                stateStack.push(gState);

                printStateStack();
                System.out.print("       ");
                printSymbolStack();
                System.out.print("      ");
                if(index<inputString.length()){
                    System.out.print(inputString.substring(index));
                }
                System.out.println();
            }else if(action.substring(0,1).equals("a")){
                System.out.println("匹配成功");
                return;
            }else{
                return;
            }
        }
    }
    public String findTable(int state,String string){
        for(Node t:table.nodes){
            if(state==t.state&&string.equals(t.symbol)){
                return t.action;
            }
        }
        return "";
    }

    public State createInitialState(Production p){//构造初始化的状态，W->.S
        HashSet lookAHeadSet=new HashSet();
        lookAHeadSet.add("$");//添加展望字符
        char leftHand=p.leftHand;//左部
        String leftOfDot="";//点的左边
        String rightCharOfDot=p.rightHand.get(0);//点的右边，rightHand是个ArrayList
        State initstate=new State();
        item it=new item(leftHand,leftOfDot,rightCharOfDot,"",lookAHeadSet);
        //项目的左部，点的左部，右部，展望字符
        initstate.setState(0);
        initstate.addItem(it);
        return initstate;
    }
    public ArrayList<item> closureSet1(item it){
        ArrayList<item> result=new ArrayList<>();
        result.add(it);//将项目加入到项目集中
        if(it.getRightCharOfDot().length()==0){//如果是W->S.的情况
            return result;
        }
        for(int i=0;i<result.size();i++){
            item ithItem=result.get(i);
            char rightCharOfDot=ithItem.rightCharOfDot.charAt(0);//得到项目的下一个字符
            if(isAlpha(rightCharOfDot)){
                Production p = new Production();//找到对应的产生式
                for (Production t : gramma.g) {
                    if (t.leftHand == rightCharOfDot) {
                        p = t;
                        break;
                    }
                }
                for(int k=0;k<p.rightHand.size();k++){//计算第一跳
                    String rightHand=p.rightHand.get(k);

                    char leftHand=p.leftHand;
                    String leftOfDot="";
                    String newrightCharOfDot;
                    if(rightHand.length()>=1){
                        newrightCharOfDot=rightHand.substring(0,1);
                    }else{
                        newrightCharOfDot="";
                    }

                    String rightOfDot;
                    if(rightHand.length()>=2){
                        rightOfDot=rightHand.substring(1);
                    }else{
                        rightOfDot="";
                    }
                    /*求展望字符*/
                    HashSet lookAheadSet=new HashSet();

                    if(result.get(i).rightOfDot.length()==0){
                        lookAheadSet=result.get(i).lookAheadSet;
                    }else{
                        gramma.firstSet(result.get(i).rightOfDot,lookAheadSet);
                    }

                    item Item=new item(leftHand,leftOfDot,newrightCharOfDot,rightOfDot,lookAheadSet);
                    if(!result.contains(Item)){
                        result.add(Item);
                    }

                }
            }
        }

        return result;
    }
    public void constructState(item it){//输入一个初始项目和一个状态，构造他的状态
        if(it.getRightCharOfDot().length()==0){//如果W->S.的情况，已经是结束的状态了
            State state=new State();
            state.addItem(it);
            states.add(state);
            return;
        }
//        ArrayList<item> items=closureSet(it);
        ArrayList<item> items=closureSet1(it);
        State state=new State();
//        state.setState(s);
//        state.addItem(it);
        for(int i=0;i<items.size();i++){
            if(!isInState(items.get(i),state)){//本状态不包括该项目，才能添加
                state.addItem(items.get(i));
            }
        }
        states.add(state);
    }
    public int countItem(){
        int count=0;
        for(int i=0;i<states.size();i++){
            State s=states.get(i);
            count=count+s.getItems().size();
        }
        return count;
    }
    public boolean isInState(item it,State state){//在这个状态中是否包含项目it
        for(item temp:state.getItems()){
            boolean ok=it.getLeftHand()==temp.getLeftHand()&&
                    it.leftOfDot.equals(temp.getLeftOfDot())&&
                    it.getRightCharOfDot().equals(temp.getRightCharOfDot())&&
                    it.getRightOfDot().equals(temp.getRightOfDot())&&
                    it.getLookAheadSet().equals(temp.getLookAheadSet());
            if(ok==true){
                return true;
            }
        }
        return false;
    }
}
