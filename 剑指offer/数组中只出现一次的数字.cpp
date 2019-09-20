class Solution {
public:
    void FindNumsAppearOnce(vector<int> data,int* num1,int *num2) {
        int n=0;
        for(int i=0;i<data.size();i++){
            n=n^data[i];
        }
        int index=findFirst1(n);
        *num1=*num2=0;
        for(int i=0;i<data.size();i++){
            if(((data[i])>>index)&1){*num1=*num1^data[i];}
            else{*num2=*num2^data[i];}
        }
    }
    int findFirst1(int num){
        int index=1;
        while(index<8*sizeof(int)){
            if(isBit1(num,index)){return index;}
            index++;
        }
        return index;
    }
    bool isBit1(int num,int index){
        num=num>>index;
        return num&1;
    }
};