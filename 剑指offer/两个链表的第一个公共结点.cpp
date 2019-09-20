/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        int length1=getListLength(pHead1);
        int length2=getListLength(pHead2);
        int length=length1>length2?length1-length2:length2-length1;
        
        ListNode p1=pHead1;
        ListNode p2=pHead2;
        if(length1>=length2){
            int count=0;
            while(count<length){p1=p1.next;count++;}
            while(p1!=p2){p1=p1.next;p2=p2.next;}
            return p1;
        }
        else{
            int count=0;
            while(count<length){p2=p2.next;count++;}
            while(p1!=p2){p1=p1.next;p2=p2.next;count++;}
            return p2;
        }
    }
    public int getListLength(ListNode pHead1){
        int length=0;
        ListNode p=pHead1;
        while(p!=null){length++;p=p.next;}
        return length;
    }
}