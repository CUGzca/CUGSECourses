class Solution
{
public:
    void push(int node) {
        stack1.push(node);
    }

    int pop() {
        while(!stack1.empty()){
            stack2.push(stack1.top());
            stack1.pop();
        }
        if(!stack2.empty()){
            int res=stack2.top();
            stack2.pop();
            while(!stack2.empty()){
                stack1.push(stack2.top());
                stack2.pop();
            }
            return res;
        }else{
            return 0;
        }
    }

private:
    stack<int> stack1;
    stack<int> stack2;
};