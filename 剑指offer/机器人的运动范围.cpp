class Solution {
public:
    int movingCount(int threshold, int rows, int cols)
    {
        bool *visited=new bool[rows*cols];
        memset(visited,false,rows*cols*sizeof(bool));
        return movingCount(threshold,rows,cols,0,0,visited);
    }
    int movingCount(int threshold,int rows,int cols,int row,int col,bool* visited)
    {
        if(row<0||col<0||row>=rows||col>=cols){return 0;}
        if(visited[row*cols+col]){return 0;}
        if(!canReach(row,col,threshold)){return 0;}
        visited[row*cols+col]=true;
        int n=1+movingCount(threshold,rows,cols,row-1,col,visited)+
            movingCount(threshold,rows,cols,row+1,col,visited)+
            movingCount(threshold,rows,cols,row,col-1,visited)+
            movingCount(threshold,rows,cols,row,col+1,visited);
        return n;
    }
    bool canReach(int row,int col,int k){
        int s=0;
        while(row){s+=row%10;row=row/10;}
        while(col){s+=col%10;col=col/10;}
        if(s>k){return false;}
        return true;
    }
};