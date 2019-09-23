import java.util.ArrayList;
import java.util.HashSet;

public class main {
    public static void main(String argv[]){
        infixToPostfix in=new infixToPostfix();
        //String s=in.insertConcat("((a&b)|c)*(a|b)");
        //String s=in.insertConcat("(ab|c)*");
        //String s=in.insertConcat("((a|b)*c)*");
        //String s=in.insertConcat("(a*b)|c*");

        String s=in.insertConcat("(a*b*)|c*");//都是终态的情况
        //String s=in.insertConcat("(abc*|d*)*fg");
        //String s=in.insertConcat("(ad|bd*)*(e*|f)|eh*k*");
        //String s=in.insertConcat("(ad|b(d*))*(e*|f)|(e(h*))((k)h*)");

        //String s=in.insertConcat("(abc|d*)*");


        System.out.println(s);
        //String s=in.insertConcat("((a|b)*c)*");

        String a=in.regexToPostfix(s);
        System.out.println(a);

        reglarExpressionToNFA r=new reglarExpressionToNFA();
        NFA n=r.postfixToNFA(a);
        System.out.println("******************************************");
        n.display();
        System.out.println("******************************************");
        dfaToNfa d=new dfaToNfa();

        ArrayList<Character> chars=d.splitCharacter(s);
        //ArrayList<Character> chars=new ArrayList<>();
        /*chars.add('a');
        chars.add('b');
        chars.add('d');
        chars.add('e');
        chars.add('h');
        chars.add('f');
        chars.add('k');*/

        ArrayList<Node> nodes=d.nfaTodfa(n,chars);
        d.print(nodes);
        System.out.println("******************************************");
        //d.minimization(28,nodes,chars);

        d.minimization(28,nodes,chars);
        System.out.println("******************************************");


    }
}
