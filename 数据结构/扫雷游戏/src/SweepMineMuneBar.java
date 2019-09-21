import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SweepMineMuneBar extends JMenuBar {
    JMenu menuGame=new JMenu("游戏");
    JMenu menuHelp=new JMenu("帮助");
    JMenuItem  menuItemStart=new JMenuItem("开局");
    JMenuItem menuItemEasy=new JMenuItem("初级");
    JMenuItem menuItemMiddle=new JMenuItem("中级");
    JMenuItem menuItemHard=new JMenuItem("高级");

    JMenuItem menuItemExit=new JMenuItem("退出");
    JMenuItem menuItemHelp=new JMenuItem("关于扫雷");

    MainFrame mainFrame;//父类
    SweepMineMuneBar(MainFrame main){
        this.mainFrame=main;
        init();
    }
    private void init(){
        menuGame.setMnemonic('G');//设置快捷键
        menuHelp.setMnemonic('H');

        menuGame.add(menuItemStart);//开始
        menuItemStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.reStart();
            }
        });
        menuGame.addSeparator();//设置分割线

        menuGame.add(menuItemEasy);
        menuItemEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaticTool.Row=9;
                StaticTool.Column=9;
                StaticTool.BombNum=10;
            }
        });
        menuGame.add(menuItemMiddle);
        menuItemMiddle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaticTool.Row=16;
                StaticTool.Column=16;
                StaticTool.BombNum=40;
            }
        });
        menuGame.add(menuItemHard);
        menuItemHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaticTool.Row=16;
                StaticTool.Column=30;
                StaticTool.BombNum=80;
            }
        });
        menuGame.addSeparator();


        menuGame.add(menuItemExit);
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(menuGame);
        this.add(menuHelp);
    }



}
