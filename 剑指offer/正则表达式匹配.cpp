class Solution {
public:
    bool match(char* str, char* pattern)
    {
        if(*str=='\0'&&*pattern=='\0'){return true;}
        if(*str=='\0'&&*pattern=='\0'){return false;}
        if(*(pattern+1)!='*'){
            if(*str==*pattern||(*str!='\0'&&*pattern=='.')){
                //加入*str！=‘\0’是为了防止abc与模式abc.这样的情况出现，这两个是不匹配的。
                return match(str+1,pattern+1);
            }else{
                return false;
            }
        }else{
            //如果下一个字符是'*'
            if(*str==*pattern||(*str!='\0'&&*pattern=='.'))
            {
                //分别对应abbc和模式ab*c，abc和模式ab*bc,即*前匹配和*后匹配
                return match(str+1,pattern)||match(str,pattern+2);
            }else{
                return match(str,pattern+2);
            }
        }
    }
};