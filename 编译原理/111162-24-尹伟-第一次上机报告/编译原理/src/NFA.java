import java.util.ArrayList;

public class NFA {
    public ArrayList<Transtion> transtions;//变换
    public ArrayList<Integer> states;//状态
    public int finalState=0;

    public NFA(){
        this.transtions=new ArrayList<>();
        this.states=new ArrayList<>();
        this.finalState=0;
    }

    public NFA(int size){
        this.transtions=new ArrayList<>();
        this.states=new ArrayList<>();
        this.finalState=0;
        this.setStates(size);
    }

    public NFA(char c){
        this.states=new ArrayList<>();
        this.transtions=new ArrayList<>();
        this.finalState=1;
        this.setStates(2);
        Transtion transtion=new Transtion(0,1,c);
        this.transtions.add(transtion);
    }

    public void setStates(int size){//设置状态
        if(size<0){return ;}
        for(int i=0;i<size;i++){
            this.states.add(i);
        }
    }

    public void display(){
        for(Transtion t:transtions){
            System.out.println(" 起点 "+t.stateFrom+" 转换符 "+t.transtionChar+" 终点 "+t.stateTo);
        }

    }
}
