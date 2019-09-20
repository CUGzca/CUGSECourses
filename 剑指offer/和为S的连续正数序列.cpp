class Solution {
public:
    vector<vector<int> > FindContinuousSequence(int sum) {
        vector<vector<int>> res;
        if(sum<3){return res;}
        int low=1,high=2;
        while(low<high){
            int s=0;
            for(int i=low;i<=high;i++){s+=i;}
            if(s<sum){high++;}
            if(s>sum){low++;}
            if(s==sum){
                vector<int> t;
                for(int i=low;i<=high;i++){
                    t.push_back(i);
                }
                res.push_back(t);
                low++;
            }
        }
        return res;
    }
};