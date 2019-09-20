class Solution {
public:
    int minNumberInRotateArray(vector<int> rotateArray) {
        int l=0,r=rotateArray.size()-1;
        while(l<r){
            int mid=(l+r)>>1;
            if(rotateArray[mid]<=rotateArray[rotateArray.size()-1]){r=mid;}
            else{l=mid+1;}
        }
        return rotateArray[l];
    }
};