class Solution {
public:
    vector<int> multiply(const vector<int>& A) {
        vector<int> B(A.size(),1);
        vector<int> C(A.size(),1);//上三角
        vector<int> D(A.size(),1);//下三角
        if(A.size()==0){return A;}
        int n=A.size();
        for(int i=1;i<n;i++){
            C[i]=C[i-1]*A[i-1];
        }
        for(int i=n-2;i>=0;i--){
            D[i]=D[i+1]*A[i+1];
        }
        for(int i=0;i<n;i++){
            B[i]=C[i]*D[i];
        }
        return B;
    }
};