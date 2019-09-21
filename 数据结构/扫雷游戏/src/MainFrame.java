import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private SweepMineMuneBar sweepMineMuneBar=new SweepMineMuneBar(this);
    private BombPanel bombPanel=new BombPanel(this);

    MainFrame(){
        //设置图片的方式
        this.setIconImage(new ImageIcon("image/icon.gif").getImage());
        this.setTitle("扫雷");
        JMenuBar bar=new JMenuBar();
        bar.add(sweepMineMuneBar);
        this.setJMenuBar(bar);
        this.setSize(300,300);

        Container container=getContentPane();
        container.add(bombPanel);

        setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //居中显示
        this.pack();
        //让他自适应
        this.setVisible(true);
        BackGroudMusic bgm=new BackGroudMusic();
    }
    public void reStart(){
        this.remove(bombPanel);
        bombPanel=new BombPanel(this);
        this.add(bombPanel);
        this.pack();
        this.validate();
    }
    public void init(){
        //this.setMenuBar();
    }
    public static void main(String[] argv){
        new MainFrame();
    }
}
