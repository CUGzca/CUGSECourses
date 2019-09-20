class Solution {
public:
    int count=0;
    vector<int> copy;
    vector<int> nums;
    int InversePairs(vector<int> data) {
        copy=data;
        nums=data;
        MergeSort(0,nums.size()-1);
        return count;
    }
    void MergeSort(int left,int right){
        if(left>=right){return;}
        int mid=left+right>>1;
        MergeSort(left,mid);
        MergeSort(mid+1,right);
        Merge(left,mid,right);
    }
    void Merge(int left,int mid,int right){
        for(int k=left;k<=right;k++){
            copy[k]=nums[k];
        }
        int s1=mid,s2=right,t=right;
        while(s1>=left&&s2>=mid+1){
            if(copy[s1]>copy[s2]){
                nums[t]=copy[s1];
                t--;s1--;
                count=count+(s2-mid);
                count%=1000000007;
            }else{
                nums[t]=copy[s2];
                t--;s2--;
            }
        }
        while(s1>=left){
            nums[t]=copy[s1];
            t--;s1--;
        }
        while(s2>=mid+1){
            nums[t]=copy[s2];
            t--;s2--;
        }
    }
    
};