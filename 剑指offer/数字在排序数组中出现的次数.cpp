public class Solution {
    public int GetNumberOfK(int [] array , int k) {
       if(array.length==0){return 0;}
        int left=0,right=array.length-1;

        while(left<right){
            int mid=(left+right)/2;
            if(array[mid]<k){left=mid+1;}
            else{right=mid;}
        }
        if(array[left]!=k){return 0;}
        int l=left;

        left=0;right=array.length-1;
        while(left<right){
            int mid=(left+right+1)/2;
            if(array[mid]<=k){left=mid;}
            else{right=mid-1;}
        }
        return right-l+1;
    }
}