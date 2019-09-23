import java.util.Stack;

public class reglarExpressionToNFA {
    public boolean isAlpha(char c){
        return c>='a'&&c<='z';
    }
    public boolean isRegexOperator(char c){
        return c=='&'||c=='|'||c=='*'||c=='('||c==')';
    }
    public boolean isValidateRegexChar(char c){return isAlpha(c)||isRegexOperator(c);}
    public boolean isValidateRegex(String regex){
        if(regex.length()<=0){return false;}
        for(char c:regex.toCharArray()){
            if(!isValidateRegexChar(c)){
                return false;
            }
        }
        return true;
    }
    public NFA kleene(NFA nfa){
        NFA reslutNFA=new NFA(nfa.states.size()+2);
        Transtion t1=new Transtion(0,1,'@');
        reslutNFA.transtions.add(t1);
        for(Transtion n:nfa.transtions){
            int stateFrom=n.stateFrom;
            int stateTo=n.stateTo;
            char transtionChar=n.transtionChar;

            Transtion t=new Transtion(stateFrom+1,stateTo+1,transtionChar);
            reslutNFA.transtions.add(t);
        }
        int nfaStateSize=nfa.states.size();
        Transtion t2=new Transtion(nfaStateSize,nfaStateSize+1,'@');

        reslutNFA.transtions.add(t2);
        reslutNFA.transtions.add(new Transtion(nfaStateSize,1,'@'));
        reslutNFA.transtions.add(new Transtion(0,nfaStateSize+1,'@'));


        reslutNFA.finalState+=2;
        return reslutNFA;
    }
    public NFA concatenation(NFA nfa1,NFA nfa2){//对应AB的状态

        int nfa1StateSize=nfa1.states.size();
        int nfa2StateSize=nfa2.states.size();

        nfa2.states.remove(0);//删除第一条状态
        for(Transtion transtion:nfa2.transtions){
            Transtion t=new Transtion(transtion.stateFrom+nfa1StateSize-1,transtion.stateTo+nfa1StateSize-1,transtion.transtionChar);
            nfa1.transtions.add(t);
        }
        for(Integer num:nfa2.states){
            nfa1.states.add(num+nfa1.states.size()-1);
        }
        nfa1.finalState=nfa1StateSize+nfa2StateSize-2;
        return nfa1;

    }
    public NFA union(NFA nfa1,NFA nfa2){//对应A|B的状态
        int size=nfa1.states.size()+nfa2.states.size()+2;
        NFA resultNFA=new NFA(size);

        resultNFA.transtions.add(new Transtion(0,1,'@'));
        for(Transtion transtion:nfa1.transtions){
            Transtion t=new Transtion(transtion.stateFrom+1,transtion.stateTo+1,transtion.transtionChar);
            resultNFA.transtions.add(t);
        }
        int nfa1StateSize=nfa1.states.size();
        int nfa2StateSize=nfa2.states.size();

        resultNFA.transtions.add(new Transtion(nfa1StateSize,nfa1StateSize+nfa2StateSize+1,'@'));
        resultNFA.transtions.add(new Transtion(0,nfa1StateSize+1,'@'));

        for(Transtion transtion:nfa2.transtions){
            Transtion t=new Transtion(nfa1StateSize+transtion.stateFrom+1,nfa1StateSize+transtion.stateTo+1,transtion.transtionChar);
            resultNFA.transtions.add(t);
        }

        resultNFA.transtions.add(new Transtion(nfa1StateSize+nfa2StateSize,nfa1StateSize+nfa2StateSize+1,'@'));
        resultNFA.finalState=nfa1StateSize+nfa2StateSize+1;

        return resultNFA;
    }
    public NFA postfixToNFA(String postfix){
        if(!isValidateRegex(postfix)){
            System.out.println("正则式无效");
            System.exit(1);
        }
        Stack<NFA> operator=new Stack<>();
        for(int i=0;i<postfix.length();i++){
            char c=postfix.charAt(i);
            if(isAlpha(c)){
                NFA nfa=new NFA(c);
                operator.push(nfa);
            }else if(c=='*'){
                NFA nfa=operator.pop();
                operator.push(kleene(nfa));
            }else if(c=='|'){
                NFA nfa2=operator.pop();
                NFA nfa1=operator.pop();
                operator.push(union(nfa1,nfa2));
            }else if(c=='&'){
                NFA nfa2=operator.pop();
                NFA nfa1=operator.pop();
                operator.push(concatenation(nfa1,nfa2));//还回的是第一条
            }
        }
        NFA resultNFA=new NFA();
        resultNFA=operator.pop();
        if(!operator.isEmpty()){
            System.out.println("错误，符号栈中还有剩余的符号");
            System.exit(1);
        }
        return resultNFA;
    }
    public NFA compile(String regex){
        if(!isValidateRegex(regex)){
            System.out.println("正则式无效");
            return new NFA();
        }

        Stack<Character> operators=new Stack<>();
        Stack<NFA> operands=new Stack<>();
        Stack<NFA> concatStack=new Stack<>();

        boolean ccFlag=false;
        char op,c;
        int paraCount=0;
        NFA nfa1,nfa2;
        for(int i=0;i<regex.length();i++){
            c=regex.charAt(i);
            if(isAlpha(c)){//如果是字母
                NFA nfa=new NFA(c);
                operands.push(nfa);//放到NFA栈中
                if(ccFlag==true){
                    operators.push('.');//这里使用点代表连接
                }else{
                    ccFlag=true;
                }
            }else{
                if(c==')'){
                    ccFlag=false;
                    if(paraCount==0){
                        System.out.println("括号的数量有问题");
                        System.exit(1);
                    }else{
                        paraCount--;
                    }
                    while(!operators.isEmpty()&&operators.peek()!='('){
                        op=operators.pop();
                        if(op=='.'){
                            nfa2=operands.pop();
                            nfa1=operands.pop();
                            operands.push(concatenation(nfa1,nfa2));
                        }else if(op=='|'){
                            nfa2 = operands.pop();
                            if(!operators.empty()&&operators.peek()=='.'){
                                concatStack.push(operands.pop());
                                while(!operators.empty()&&operators.peek()=='.'){
                                    concatStack.push(operands.pop());
                                    operators.pop();
                                }
                                nfa1=concatenation(concatStack.pop(),concatStack.pop());
                                while(concatStack.size()>0){
                                    nfa1=concatenation(nfa1,concatStack.pop());
                                }
                            }else{
                                nfa1=operands.pop();
                            }
                            operands.push(union(nfa1,nfa2));
                        }
                    }
                } else if(c=='*'){
                    operands.push(kleene(operands.pop()));
                    ccFlag=true;
                }else if(c=='('){
                    operators.push(c);
                    paraCount++;
                }else if(c=='|'){
                    operators.push(c);
                    ccFlag=false;
                }
            }
        }
        while(operators.size()>0){
            if(operands.empty()){
                System.out.println("错误,操作数和操作符不匹配");
                System.exit(1);
            }
            op=operators.pop();
            if(op=='.'){
                nfa2=operands.pop();
                nfa1=operands.pop();
                operands.push(concatenation(nfa1,nfa2));
            }else if(op=='|'){
                nfa2=operands.pop();
                if(!operators.empty()&&operators.peek()=='.'){
                    concatStack.push(operands.pop());
                    while(!operators.empty()&&operators.peek()=='.'){
                        concatStack.push(operands.pop());
                        operators.pop();
                    }
                    nfa1=concatenation(concatStack.pop(),concatStack.pop());
                    while(concatStack.size()>0){
                        nfa1=concatenation(nfa1,concatStack.pop());
                    }
                }
                else{
                    nfa1=operands.pop();
                }
                operands.push(union(nfa1,nfa2));
            }
        }
        return operands.pop();
    }
}
