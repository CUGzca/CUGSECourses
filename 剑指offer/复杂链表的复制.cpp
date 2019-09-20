/*
public class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
*/
public class Solution {
    public RandomListNode Clone(RandomListNode pHead)
    {
        
        for(RandomListNode p=pHead;p!=null;p=p.next.next){
            RandomListNode np=new RandomListNode(p.label);
            np.next=p.next;
            p.next=np;
        }
        for(RandomListNode p=pHead;p!=null;p=p.next.next){
            if(p.random!=null){
                p.next.random=p.random.next;
            }
        }
        RandomListNode p=pHead;
        RandomListNode pClonedHead=null;
        RandomListNode pCloneNode=null;
        if(p!=null){
            pClonedHead=pCloneNode=p.next;
            p.next=pCloneNode.next;
            p=p.next;
        }
        while(p!=null){
            pCloneNode.next=p.next;
            pCloneNode=pCloneNode.next;
            p.next=pCloneNode.next;
            p=p.next;
        }
        return pClonedHead;
    }
}