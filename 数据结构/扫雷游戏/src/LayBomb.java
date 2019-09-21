import java.util.Random;

public class LayBomb {
    public int[][] createBomb(){
        int BombMap[][]=new int[StaticTool.Row][StaticTool.Column];
        int Mine_x;
        int Mine_y;
        Random r=new Random();
        int currentNumOfMine=0;
        int calNumOfMine[]={-1,0,1};

        while(currentNumOfMine!=StaticTool.BombNum){
            Mine_x=r.nextInt(StaticTool.Row);
            Mine_y=r.nextInt(StaticTool.Column);
            //防止重复生成雷
            if(BombMap[Mine_x][Mine_y]<1000){
                BombMap[Mine_x][Mine_y]=1000;
                currentNumOfMine++;
            }
            else{continue;}
            for(int dx:calNumOfMine){
                for(int dy:calNumOfMine){
                    if(Mine_x+dx>=0&&Mine_x+dx<StaticTool.Row&&dy+Mine_y>=0&&dy+Mine_y<StaticTool.Column){
                        BombMap[Mine_x+dx][Mine_y+dy]++;
                    }
                }
            }
        }
        return BombMap;
    }

}
