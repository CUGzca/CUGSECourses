import java.util.ArrayList;

public class Main {
    public static void main(String argv[]){
        int seat_type=1;
        Flight flight=new Flight("中国(除兰州/乌鲁木齐)","乌兹别克斯坦",seat_type);
        flight.flight_type=2;

        ArrayList<Luggage> luggages=new ArrayList<>();
        Luggage luggage1=new Luggage(42,10,10,10,flight);
        Luggage luggage2=new Luggage(32,10,10,10,flight);
        Luggage luggage3=new Luggage(32,10,10,10,flight);
        Luggage luggage4=new Luggage(30,10,10,10,flight);
        Luggage luggage5=new Luggage(30,10,10,10,flight);

        luggages.add(luggage1);
        luggages.add(luggage2);
        luggages.add(luggage3);
        luggages.add(luggage4);
        luggages.add(luggage5);

        Calculate calculate=new Calculate(flight,luggages);
        System.out.println(calculate.toZone1());
       // System.out.println(calculate.toZone3());
    }
}
