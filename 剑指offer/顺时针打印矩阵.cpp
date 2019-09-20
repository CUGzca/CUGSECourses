import java.util.ArrayList;
public class Solution {
    ArrayList<Integer> result=new ArrayList<>();
    public ArrayList<Integer> printMatrix(int [][]array){
        int rows=array.length;
        int columns=array[0].length;
        PrintMatrixClockwisely(array,columns,rows);
        return result;
    }
    public void PrintMatrixClockwisely(int[][] array,int columns,int rows){
        if(array.length==0||columns<0||rows<0){
            return;
        }
        int start=0;
        while(columns>start*2&&rows>start*2){
            PrintMatrixInCircle(array,columns,rows,start);
            ++start;
        }
    }
    public void PrintMatrixInCircle(int[][] array,int columns,int rows,int start){
        int endX=columns-1-start;
        int endY=rows-1-start;
        for(int i=start;i<=endX;i++){
            int number=array[start][i];
            result.add(number);
        }
        //从上到下打印一列
        if(start<endY){
            for(int i=start+1;i<=endY;i++){
                int number=array[i][endX];
                result.add(number);
            }
        }
        //从右到左打印一行
        if(start<endX&&start<endY){
            for(int i=endX-1;i>=start;i--){
                int number=array[endY][i];
                result.add(number);
            }
        }
        //从下到上打印一列
        if(start<endX&&start<endY-1){
            for(int i=endY-1;i>=start+1;i--){
                int number=array[i][start];
                result.add(number);
            }
        }
    }
}