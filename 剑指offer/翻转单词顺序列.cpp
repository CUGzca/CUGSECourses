class Solution {
public:
    string ReverseSentence(string str) {
        if(str.length()==0){return str;}
        string res="";
        string s="";
        reverse(str.begin(),str.end());
        for(int i=0;i<str.length();i++){
            if(str[i]!=' '){s+=str[i];}
            else{
                reverse(s.begin(),s.end());
                res=res+s+" ";
                s="";
            }
        }
        reverse(s.begin(),s.end());
        res=res+s;
        return res;
    }
};