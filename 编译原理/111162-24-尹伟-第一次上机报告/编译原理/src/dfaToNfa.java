import java.util.*;
import java.util.Queue;
public class dfaToNfa {
    public void epsilonClosure(NFA nfa,int state,HashSet result){
        if(state<0){return;}
        for(Transtion t:nfa.transtions){
            if(t.stateFrom==state&&t.transtionChar=='@'){
                if (result.add(t.stateTo)){
                    epsilonClosure(nfa,t.stateTo,result);
                }else{
                    return;
                }
                //if()
            }
        }
    }
    public HashSet move(NFA nfa,int state,char c){
        //ArrayList<Integer> result=new ArrayList<>();
        HashSet resultHashSet=new HashSet();
        for(Transtion t:nfa.transtions){
            if(t.stateFrom==state&&t.transtionChar==c){
                resultHashSet.add(t.stateTo);
                HashSet r=new HashSet();
                epsilonClosure(nfa,t.stateTo,r);
                Iterator iterator=r.iterator();
                while(iterator.hasNext()){
                    int num=(int)iterator.next();
                    resultHashSet.add(num);
                }
            }
        }
        return resultHashSet;
    }
    public HashSet move(NFA nfa,ArrayList<Integer> states,char c){
        HashSet resltSet=new HashSet();
        for(int i=0;i<states.size();i++){
            HashSet hashSet=move(nfa,states.get(i),c);
            resltSet.addAll(hashSet);
        }
        return resltSet;
    }
    public int transtion(ArrayList<Node> nodes,int state,char c){//经过字符c转换到哪一个状态
        for(Node node:nodes){
            if(node.fromState==state&&node.trans==c){
                return node.toState;
            }
        }
        return -1;
    }
    public ArrayList<Node> nfaTodfa(NFA nfa,ArrayList<Character> charSet){

        ArrayList<Node> result=new ArrayList<>();
        Queue<HashSet>  queue=new LinkedList<>();

        HashSet stateZeroEpsilonClosure=new HashSet();
        epsilonClosure(nfa,0,stateZeroEpsilonClosure);//求0状态的集

        ArrayList<HashSet> D=new ArrayList<>();//就是D
        D.add(stateZeroEpsilonClosure);

        queue.add(stateZeroEpsilonClosure);
        int i=0,j=1;
        while(!queue.isEmpty()){

            HashSet set=queue.peek();

            int size=set.size();
            Integer[] setList=new Integer[size];
            set.toArray(setList);
            ArrayList<Integer> states=new ArrayList<>(Arrays.asList(setList));

            for(Character c:charSet){
                HashSet U=move(nfa,states,c);

                Node node=new Node(set,c,U,i,-1);
                //result.add(node);
//                if(U.size()>0){
//                    result.add(node);
//                }

                if(U.size()>0&&(!D.contains(U))){
                   D.add(U);
                   ((LinkedList<HashSet>) queue).addLast(U);
                    node.setToState(j);//新生成一个状态所以j++
                    j++;
                }
                else if(D.contains(U)){
                    int k=D.indexOf(U);
                    node.setToState(k);
                }
                result.add(node);
            }
            i++;
            ((LinkedList<HashSet>) queue).pollFirst();
        }
        return result;
    }
    public void print(ArrayList<Node> nodes){
        for(Node node:nodes){
            System.out.println(node.fromState+" "+node.fromSet+" "+node.trans+" "+node.toSet+" "+node.toState);
        }
//        for(Node node:nodes){
//            System.out.println(node.fromState+" "+node.trans+" "+node.toState);
//        }
    }
    public ArrayList<Character> splitCharacter(String regex){
        HashSet r=new HashSet();
        for(int i=0;i<regex.length();i++){
            if(regex.charAt(i)>='a'&&regex.charAt(i)<='z'){
                r.add(regex.charAt(i));
            }
        }

        int size=r.size();
        Character[] chars=new Character[size];
        r.toArray(chars);

        ArrayList<Character> result=new ArrayList<>(Arrays.asList(chars));
        return result;
    }
    /*public ArrayList<Node> mergeState(ArrayList<Node> nodes,ArrayList<Integer> states){

    }*/
    public ArrayList<Node> minimization(int nfaFinalState,ArrayList<Node> nodes,ArrayList<Character> chars){
        ArrayList<Node> result=new ArrayList<>();

        int size=nfaFinalState;
        HashSet finalState=new HashSet();
        HashSet nonfinalState=new HashSet();
        HashSet state=new HashSet();
        //ArrayList<Integer> finalState=new ArrayList<>();
        //ArrayList<Integer> nonfinalState=new ArrayList<>();

        //分为终态和非终态
        for(int i=0;i<nodes.size();i++){
            if(nodes.get(i).fromSet.contains(size)){
                finalState.add(nodes.get(i).fromState);
            }else{
                nonfinalState.add(nodes.get(i).fromState);
            }
        }
        state.addAll(finalState);
        state.addAll(nonfinalState);

        boolean[][] table=new boolean[state.size()][state.size()];
        for(int i=0;i<state.size();i++){
            for(int j=0;j<i;j++){
                if((finalState.contains(i)&&nonfinalState.contains(j))||(nonfinalState.contains(i)&&finalState.contains(j))){
                    table[i][j]=true;
                    table[j][i]=true;
                }else{
                    table[i][j]=false;
                }
            }
        }
        boolean flag=true;
        while (flag){
            flag=false;
            for(int i=0;i<state.size();i++){
                for(int j=0;j<i;j++){
                    if(table[i][j]==false){
                        for(Character c:chars){
                            //查看i号状态经过字符charAt(k)后变成哪一个状态 x
                            //查看j号状态经过字符charAt(k)后变成哪一个状态 y
                            //如果x y 被标记或者y x被标记，则将table[i][j]标记
                            int x=transtion(nodes,i,c);
                            int y=transtion(nodes,j,c);

                            //只要做出改变就会循环
                            if(x>=0&&y<0){
                                table[i][j]=true;
                                table[j][i]=true;
                                flag=true;
                            }else if(x<0&&y>=0){
                                table[i][j]=true;
                                table[j][i]=true;
                                flag=true;
                            }else if(x>=0&&y>=0&&table[x][y]==true){
                                table[i][j]=true;
                                table[j][i]=true;
                                flag=true;
                            }
                        }
                    }
                }
            }
        }

        HashSet mergeStates=new HashSet();
        boolean isChange=false;
        for(int i=0;i<state.size();i++){
            for(int j=0;j<i;j++){
                if(table[i][j]==false){
                    //mergeStates.add(i);
                    //mergeStates.add(j);
                    isChange=true;
                    System.out.println("合并状态"+i+" "+j);
                    //Node node=new Node();
                    //result.add(node);
                }
            }
        }
        if(!isChange){
            System.out.println("不能进行合并");
        }
        return new ArrayList<>();
    }
}
