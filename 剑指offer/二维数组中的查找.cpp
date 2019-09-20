class Solution {
public:
    bool Find(int target, vector<vector<int>> array) {
        if(array.size()==0){return false;}
        bool found=false;
        int row=0,column=array[0].size()-1;
        while(row<array.size()&&column>=0){
            if(array[row][column]>target){
                column--;
            }else if(array[row][column]==target){found=true;break;}
            else{
                row++;
            }
        }
        return found;
    }
};