/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode FindKthToTail(ListNode head,int k) {
        if(k==0||head==null){return null;}
        int count=0;
        ListNode p1=head;
        ListNode p2=head;
        while(count<k){
            if(p2==null){return null;}
            p2=p2.next;
            count++;
        }
        while(p2!=null){
            p1=p1.next;
            p2=p2.next;
        }
        return p1;
    }
}