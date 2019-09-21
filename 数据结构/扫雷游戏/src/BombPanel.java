import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

public class BombPanel extends JPanel {
    MainFrame mainFrame;
    JLabel imageLabel[][]=new JLabel[StaticTool.Row][StaticTool.Column];
    private boolean isRightClicked[][];
    private LayBomb layBomb;
    private int[][] bomb;
    private boolean checkFinished=false;
    private boolean checkFailed=false;
    //只是创建了5个对象而已
    //public static Icon iconBlank=new ImageIcon("./image/blank.gif");
    //public JLabel j=new JLabel();
    BombPanel(MainFrame frame){
        this.mainFrame=frame;
        layBomb=new LayBomb();
        bomb=layBomb.createBomb();
        isRightClicked=new boolean[StaticTool.Row][StaticTool.Column];

        this.setLayout(new GridLayout(StaticTool.Row,StaticTool.Column));
        for(int x=0;x<isRightClicked.length;x++){
            for(int y=0;y<isRightClicked[0].length;y++){
                isRightClicked[x][y]=false;
            }
        }
        //j.setIcon(iconBlank);
        //this.add(j);
        init();
    }
    private void init(){
        for(int i=0;i<imageLabel.length;i++){
            for(int j=0;j<imageLabel[i].length;j++){
                imageLabel[i][j]=new JLabel();
                imageLabel[i][j].setIcon(BombImage.iconBlank);
                imageLabel[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel JlabelSource=(JLabel) e.getSource();
                        int Row=JlabelSource.getY()/16;
                        int Column=JlabelSource.getX()/16;
                        if(e.getModifiers()==InputEvent.BUTTON1_MASK&&isRightClicked[Row][Column]==false
                                &&checkFinished==false&&checkFailed==false){
                            //左键
                           if(bomb[Row][Column]<1000&&bomb[Row][Column]>=0){
                               int num=bomb[Row][Column];
                               if(num!=0){
                                   imageLabel[Row][Column].setIcon(BombImage.NumOfBomb[num]);
                               }
                               else{//就要找出他周围所有为0的点了
                                   TraceBack(Row,Column);
                               }

                           }
                           else if(bomb[Row][Column]>=1000){
                               for(int i=0;i<imageLabel.length;i++){
                                   for(int j=0;j<imageLabel[i].length;j++){
                                       if(bomb[i][j]>=1000){
                                           imageLabel[i][j].setIcon(BombImage.bombIcon);
                                       }
                                   }
                               }
                               imageLabel[Row][Column].setIcon(BombImage.bloodIcon);
                               checkFailed=true;
                           }
                        }///////////////////////////////////////////////没成功也没失败
                        if(e.getModifiers()==InputEvent.BUTTON3_MASK&&checkFinished==false&&checkFailed==false){
                          //右键
                            if(isRightClicked[Row][Column]==false){
                                imageLabel[Row][Column].setIcon(BombImage.flagIcon);
                            }
                            else{
                                imageLabel[Row][Column].setIcon(BombImage.iconBlank);
                            }
                            isRightClicked[Row][Column]=!isRightClicked[Row][Column];
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //System.out.println();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                this.add(imageLabel[i][j]);
            }
        }
        Border borderLow=BorderFactory.createLoweredBevelBorder();
        Border borderEmpty=BorderFactory.createEmptyBorder(5,5,5,5);
        Border borderComl=BorderFactory.createCompoundBorder(borderEmpty,borderLow);
        this.setBorder(borderComl);
        this.setBackground(Color.LIGHT_GRAY);
    }
    public void TraceBack(int x,int y){
        if(x<0||y<0||x>=bomb.length||y>=bomb[0].length){
            return;
        }
        if(bomb[x][y]==0){
            imageLabel[x][y].setIcon(BombImage.NumOfBomb[0]);
            bomb[x][y]=-1;
            TraceBack(x-1,y);
            TraceBack(x,y+1);
            TraceBack(x+1,y);
            TraceBack(x,y-1);
        }
        else if(bomb[x][y]>0&&bomb[x][y]<1000){
            imageLabel[x][y].setIcon(BombImage.NumOfBomb[bomb[x][y]]);
        }
    }
}
