import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
public class main {
    public static void main(String []argv){

        ParsingAutomation parsingAutomation=new ParsingAutomation();
        parsingAutomation.gramma.parseString("W->S;S->[B;A->i|[B;B->]|C;C->A]|A,C;");
        System.out.println("你输入的语法是：");
        parsingAutomation.gramma.printGrammar();//打印
        parsingAutomation.createAutomation();
        int count=parsingAutomation.countItem();
        System.out.println("总共有"+count+"个项目");
        parsingAutomation.parseLR1("[i,i]");

    }
}
