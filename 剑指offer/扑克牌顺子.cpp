class Solution {
public:
    bool IsContinuous( vector<int> numbers ) {
        if(numbers.size()==0){return false;}
        int count[14]={0};
        int max=-1;
        int min=15;
        for(int i=0;i<numbers.size();i++){
            if(numbers[i]==0){continue;}
            if(count[numbers[i]]>=1){return false;}
            else{count[numbers[i]]=count[numbers[i]]+1;}
            if(numbers[i]<min){min=numbers[i];}//记录最小值
            if(numbers[i]>max){max=numbers[i];}//记录最大值
            
        }
        if(max-min<5){return true;}
        return false;
    }
};