import javax.swing.*;

public class BombImage extends JLabel {
    public static Icon iconBlank=new ImageIcon("./image/blank.gif");
    public static Icon bloodIcon=new ImageIcon("./image/blood.gif");
    //点击炸弹后的反应
    public static Icon bombIcon=new ImageIcon("./image/Bomb.gif");
    //纯炸弹
    public static Icon bombErrorIcon=new ImageIcon("./image/BombError.gif");
    //炸弹出错
    public static Icon flagIcon=new ImageIcon("./image/flag.gif");
    //旗子
    public static Icon NumOfBomb[]=new Icon[9];
//////为什么这里要加static关键字
   static{
       for(int i=0;i<NumOfBomb.length;i++){
           NumOfBomb[i]=new ImageIcon("./image/"+i+".gif");
       }
   }



}
