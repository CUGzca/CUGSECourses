import java.util.Stack;
import java.util.StringTokenizer;

public class infixToPostfix {

    public boolean isAlpha(char c){
        return c>='a'&&c<='z';
    }
    public String inputAddOperation(String input){
        StringBuffer sb=new StringBuffer();
        sb.append(input);

        for(int i=0;i<sb.length();i++){
            char c=sb.charAt(i);
            if(isAlpha(c)){
                if(i+1<sb.length()&&isAlpha(sb.charAt(i+1))){
                    sb.insert(i+1,'&');
                }
            }
        }
        return sb.toString();
    }
    public String insertConcat(String regex){
        StringBuilder result=new StringBuilder();
        char currentChar,nextChar;
        for(int i=0;i<regex.length();i++){
            currentChar=regex.charAt(i);
            if(i+1<regex.length()) {
                nextChar = regex.charAt(i + 1);
                result.append(currentChar);
                if (currentChar != '(' && currentChar != '|' && nextChar != '*' && nextChar != '|' && nextChar != ')') {
                    result.append('&');
                }
            }
        }
        result.append(regex.charAt(regex.length()-1));
        return result.toString();
    }

    /*public String infixToPostfix(String infix){
        StringBuffer postfix=new StringBuffer();
        Stack<Character> stack=new Stack<>();
        //StringTokenizer tokens=new StringTokenizer(infix,"()*|+.");

        for(int i=0;i<infix.length();i++){
            char c=infix.charAt(i);
            if(isAlpha(c)){//1、c是字母直接输出
                postfix.append(c);
            }else{
                if(c=='*'){
                    postfix.append(c);
                    //postfix.append('&');
                }
                else if(c=='&'){
                    if(stack.isEmpty()||stack.peek()=='('||stack.peek()=='|'){
                        stack.push(c);
                    }else{
                        while(!stack.isEmpty()&&(stack.peek()=='&'||stack.peek()=='*')){
                            postfix.append(stack.peek());
                            stack.pop();
                        }
                        stack.push(c);
                    }
                }else if(c=='('){//左括号直接入栈
                    stack.push(c);
                }else if(c==')'){//退栈直到遇到'('
                    while(!stack.isEmpty()&&stack.peek()!='('){
                        postfix.append(stack.peek());
                        stack.pop();
                    }
                    stack.pop();
                }else if(c=='|'){
                    if(stack.isEmpty()||stack.peek()=='('){
                        stack.push(c);
                    }else{
                        while(!stack.isEmpty()&&stack.peek()!='('){
                            postfix.append(stack.peek());
                            stack.pop();
                        }
                        stack.push(c);
                    }

                }
            }
        }
        while(!stack.isEmpty()){
            postfix.append(stack.peek());
            stack.pop();
        }
        return postfix.toString();
    }
    */
    public String regexToPostfix(String regex){
        StringBuffer postfix=new StringBuffer();
        Stack<Character> stack=new Stack<>();
        for(int i=0;i<regex.length();i++){
            char c=regex.charAt(i);
            if(isAlpha(c)){
                postfix.append(c);
            }else if(c=='('){
                stack.push(c);
            }else if(c==')'){
                while(!stack.isEmpty()&&stack.peek()!='('){
                    postfix.append(stack.peek());
                    stack.pop();
                }
                stack.pop();//弹出''(
            }else{
                while(!stack.isEmpty()){
                    if(priority(stack.peek())>=priority(c)){//优先级大可以直接入栈，否则先贪占
                        postfix.append(stack.pop());
                    }else{break;}
                }
                stack.push(c);
            }
        }
        while(!stack.isEmpty()){
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    public int priority(char c){
        switch (c){
           // case '(':return 4;
            case '*':return 3;
            case '&':return 2;
            case '|':return 1;
            default:return 0;
        }
    }
}
