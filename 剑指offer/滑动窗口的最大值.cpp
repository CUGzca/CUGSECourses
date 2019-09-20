class Solution {
public:
    vector<int> maxInWindows(const vector<int>& num, unsigned int size)
    {
        int n=num.size();
        vector<int> res;
        if(size<1||size>n){return res;}
        for(int i=0;i+size<=n;i++){
            int max=INT_MIN;
            for(int j=0;j<size&&i+j<n;j++){
                if(num[i+j]>max){max=num[i+j];}
            }
            res.push_back(max);
        }
        return res;
    }
};