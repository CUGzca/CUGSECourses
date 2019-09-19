public class Luggage {
    public float weight;
    public int length;
    public int width;
    public int height;
    public Flight flight;
    public int getSum(){return width+height+length;}
    public boolean isALuggage(){
        if(flight.flight_type==1){
            return this.weight<=50&&this.length<=40&&this.width<=60&&this.height<=100;
        }else{
            if(flight.startPosition.equals("中国")&&flight.endPosition.equals("美国")){
                return this.weight<=45&&this.length+this.width+this.height<=158;
            }else{
                return this.weight<=32&&this.length+this.width+this.height<=158;
            }
        }
    }
    public int getMaxLuggageWeightbyFlightType(int flight_type,String endPosition){
        if(flight_type==1){return 50;}
        else{
            if(flight_type==2&&endPosition.equals("美国")){
                return 45;
            }else{
                return 32;
            }
        }
    }
    Luggage(int weight,int length,int width,int height,Flight flight){
        this.weight=weight;
        this.length=length;
        this.width=width;
        this.height=height;
        this.flight=flight;
    }


}
