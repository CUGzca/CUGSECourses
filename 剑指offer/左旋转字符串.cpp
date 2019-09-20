class Solution {
public:
    string LeftRotateString(string str, int n) {
        reverse(str.begin(),str.end());
        reverse(str.begin(),str.begin()+str.length()-n);
        reverse(str.begin()+str.length()-n,str.end());
        return str;
    }
  
    
};