class Solution {
public:
    bool isNumeric(char* str)
    {
        bool sign=false,dec=false,hasE=false;//标记正负号、小数点、e是否出现过
        for(int i=0;i<strlen(str);i++){
            if(str[i]=='e'||str[i]=='E'){
                if(hasE||i==strlen(str)-1){return false;}//不能同时出现两个E,E后面一定得接一个数
                hasE=true;
            }else if(str[i]=='+'||str[i]=='-'){
                //第二次出在+-号，必须接在E后面
                if(sign){
                    if(str[i-1]=='E'||str[i-1]=='e'){
                        continue;
                    }else{
                        return false;
                    }
                }
                if(!sign){
                    //第一次出现+-号，且不是在字符串开头，则也必须紧接在e之后
                    if(i==0){sign=true;}
                    else{
                        sign=true;
                        if(str[i-1]=='e'||str[i-1]=='E'){
                            continue;
                        }else{
                            return false;
                        }
                    }
                }
               
            }else if(str[i]=='.'){//e后面不能接小数点且小数点不能出现两次
                if(hasE||dec){return false;}
                dec=true;
            }else if(str[i]<'0'||str[i]>'9'){return false;}
        }
        return true;
    }

};