class Solution {
public:
    vector<int> FindNumbersWithSum(vector<int> array,int sum) {
        vector<int > res;
        if(array.size()<2){return res;}
        int low=0,high=array.size()-1;
        int i=high,j=high;
        while(low<high){
            int s=array[low]+array[high];
            if(s<sum){low++;}
            if(s>sum){high--;}
            if(s==sum){
                if(array[low]*array[high]<array[i]*array[j]){
                    i=low,j=high;
                }
                low++;
            }
        }
        if(i==array.size()-1&&j==array.size()-1){
            return res;
        }
        res.push_back(array[i]);
        res.push_back(array[j]);
        return res;
    }
};