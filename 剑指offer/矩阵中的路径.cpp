class Solution {
public:
    bool hasPath(char* matrix, int rows, int cols, char* str)
    {
        vector<bool> visited(rows*cols,false);
        bool condition=false;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                //matrix表示矩阵，visited表示是否被访问过，str代表要找的字符串，path表示已经找到的字符串，
                condition=condition||dfs(matrix,visited,str,i,j,rows,cols);
                if(condition){return condition;}
            }
        }
        return condition;
    }
    bool dfs(char * matrix,vector<bool> visited,char* str,int x,int y, int rows,int cols){
        if(x<0||y<0||x>=rows||y>=cols){return false;}
        if(matrix[x*cols+y]==*str&&visited[x*cols+y]==false){
            visited[x*cols+y]=true;
            if(*(str+1)=='\0'){return true;}
            bool condition=dfs(matrix,visited,(str+1),x-1,y,rows,cols)||
                dfs(matrix,visited,(str+1),(x+1),y,rows,cols)||
                dfs(matrix,visited,(str+1),x,y-1,rows,cols)||
                dfs(matrix,visited,(str+1),x,y+1,rows,cols);
            if(condition==false){
                visited[x+cols+y]=false;
            }
            return condition;
        }else{return false;}
        
    }
};