class Solution {
public:
    int Add(int num1, int num2)
    {
        int sum,carry;
        while(num2){
            sum=num1^num2;//求加法
            carry=(num1&num2)<<1;//求进位
            num1=sum;
            num2=carry;
        }
        return num1;
    }
};