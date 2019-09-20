class Solution {
public:
    bool IsPopOrder(vector<int> pushV,vector<int> popV) {
        if(pushV.size()!=popV.size()){return false;}
        stack<int> s;
        int indexToPopV=0;
        for(int i=0;i<pushV.size();i++){
            s.push(pushV[i]);
            while(!s.empty()&&s.top()==popV[indexToPopV]){
                s.pop();
                indexToPopV++;
            }
        }
        if(indexToPopV==popV.size()){
            return true;
        }else{return false;}
    }
};