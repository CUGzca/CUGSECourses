import java.util.ArrayList;

public class Calculate {
    public int price=1000;

    public int passenger_type=1;
    /**=1为成人 =2为不占座婴儿*/
    public void setPrice(int price){this.price=price;}

    public Flight flight;//航班信息，包括起始点、终点站，座位类型
    public ArrayList<Luggage> luggages;
    public int vip=0;
    /**vip=0为无
     * vip=1为金卡用户
     * vip=2为银卡用户
     * vip=3为留学生
     * */

    Calculate(Flight flight,ArrayList<Luggage> luggages){
        this.flight=flight;
        this.luggages=luggages;
    }

    Calculate(Flight flight,ArrayList<Luggage> luggages,int passenger_type,int vip){
        this.flight=flight;
        this.luggages=luggages;
        this.passenger_type=passenger_type;
    }
    /**前往本国内的航线*/
    /**=1代表头等舱，
      =2代表公务舱,
      =3经济舱，
      =4代表明珠经济舱
    */
    public double toZone0(){//本国内，国内只看重重量
        double weight=0;
        for(int i=0;i<luggages.size();i++){
            if(luggages.get(i).isALuggage()==false){
                System.out.println("您的行李不符合要求，请转货运");
                System.exit(1);
            }
            weight+=luggages.get(i).weight;
        }
        if(flight.seat_type==1){/**头等舱*/
            if(passenger_type==1){
                if(vip==0){return (weight-40)>0?0.015*price*(weight-40):0;}
                if(vip==1){return (weight-60)>0?0.015*price*(weight-60):0;}
                if(vip==2){return (weight-50)>0?0.015*price*(weight-50):0;}
                if(vip==3){return (weight-80)>0?0.015*price*(weight-80):0;}
            }else if(passenger_type==2){
                if(vip!=0){System.out.println("婴儿乘客不能有vip");System.exit(1);}
                return weight-50>0?(weight-50)*price*0.15:0;
            }
        }
        else if(flight.seat_type==2){/**公务舱*/
            if(passenger_type==1){
                if(vip==0){return (weight-30)>0?0.015*price*(weight-30):0;}
                if(vip==1){return (weight-50)>0?0.015*price*(weight-50):0;}
                if(vip==2){return (weight-40)>0?0.015*price*(weight-40):0;}
                if(vip==3){return (weight-60)>0?0.015*price*(weight-60):0;}
            }
            if(passenger_type==2){
                if(vip!=0){System.out.println("婴儿乘客不能有vip");System.exit(1);}
                return weight-40>0?(weight-40)*price*0.15:0;
            }
        }
        else if(flight.seat_type==3){/**经济舱*/
            if(passenger_type==1){
                if(vip==0){return (weight-20)>0?0.015*price*(weight-20):0;}
                if(vip==1){return (weight-40)>0?0.015*price*(weight-40):0;}
                if(vip==2){return (weight-30)>0?0.015*price*(weight-30):0;}
                if(vip==3){return (weight-40)>0?0.015*price*(weight-40):0;}
            }
            if(passenger_type==2){
                if(vip!=0){System.out.println("婴儿乘客不能有vip ");System.exit(1);}
                return weight-30>0?(weight-30)*price*0.15:0;
            }
        }
        return -1;
    }
    public double toZone1(){
        double money=0;
        if(passenger_type==2){
            boolean ok=luggages.size()<=1;/**婴儿行李只能是一件*/
            if(!ok){System.out.println("婴儿行李超数量");System.exit(1);}
            if(luggages.get(0).getSum()>115){money+=1000;}
            if(luggages.get(0).weight>10){money+=3000;}
            return money;
        }
        /********************成人乘客**********************/
        /**头等舱*/ /**公务舱*/
        if(flight.seat_type==1||flight.seat_type==2){
            for(int i=0;i<luggages.size();i++){
                Luggage luggage=luggages.get(i);
                int size=luggage.getSum();
                if(size>159&&size<=300){//一超尺寸就交钱
                    money+=1000;
                }else if(size>300){
                    System.out.println("您的行李不符合要求，请转货运 ");
                    System.exit(1);
                }
                if(luggage.weight>32&&luggage.weight<=45){//超重就交钱
                    money+=3000;
                }else if(luggage.weight>45){
                    System.out.println("您的行李不符合要求，请转货运");
                    System.exit(1);
                }
                /****不是vip****/
                if (vip == 0) {
                    if(flight.seat_type==1){
                        if(i==3){/**有三件免费额度*/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("您的行李不符合要求,请转货运");
                                System.exit(1);
                            }
                        }
                    }
                    if(flight.seat_type==2){
                        if(i==2){/**有两件免费额度*/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=3){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("您的行李不符合要求,请转货运");
                                System.exit(1);
                            }
                        }
                    }

                }else{/**他是vip*/
                    if(flight.seat_type==1){
                        if(i==4){/**会多出一件，也就是免费额度是4件**/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=5){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                    if(flight.seat_type==2){
                        if(i==3){/**会多出一件，也就是免费额度是4件**/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                }
            }
        }
        /**经济舱*/ /**代表明珠经济舱*/
        if(flight.seat_type==3||flight.seat_type==4){
            for (int i = 0; i < luggages.size(); i++) {
                Luggage luggage = luggages.get(i);
                int size = luggage.getSum();
                if (size > 159 && size <= 300) {//一超尺寸就交钱
                    money += 1000;
                } else if (size > 300) {
                    System.out.println(" 您 的行李不符合要求，请转货运 ");
                    System.exit(1);
                }
                if (luggage.weight > 23 && luggage.weight <= 32) {//超重就交钱
                    money += 1000;
                } else if (luggage.weight <= 45) {
                    money += 3000;
                } else {
                    System.out.println("您的 行李不符合要求， 请转货运");
                    System.exit(1);
                }
                /****不是vip****/
                /**注意明珠经济舱和经济舱的标准和数量都是一样的，所以不用区分*/
                if (vip == 0) {
                    if (i == 2) {/**有两件23kg免费额度*/
                        if (luggage.weight <= 23 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if (i >= 3) {//超出第二件或更多
                        if (luggage.weight <= 23 && size <= 158) {
                            money += 2000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                } else {/**他是vip*/
                    if (i == 3) {//超出第一件
                        if (luggage.weight <= 23 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if (i >= 4) {//超出第二件或更多
                        if (luggage.weight <= 23 && size <= 158) {
                            money += 2000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        return money;
    }

    public double toZone2() {
        double money=0;
        /**婴儿乘客*/
        if(passenger_type==2){
            boolean ok=luggages.size()<=1;/**婴儿行李只能是一件*/
            if(!ok){System.out.println("婴儿行李超数量 ");System.exit(1);}
            if(luggages.get(0).getSum()>115){money+=1000;}
            if(luggages.get(0).weight>10){money+=3000;}
            return money;
        }
        /********************成人乘客**********************/
        /**头等舱*/ /**公务舱*/ /**经济舱*/ /**明珠经济舱*/
        for(int i=0;i<luggages.size();i++){
            Luggage luggage=luggages.get(i);
            int size=luggage.getSum();
            if(size>159&&size<=300){//一超尺寸就交钱
                money+=1000;
            }else if(size>300){
                System.out.println("你的行李不符合要求，请转货运 ");
                System.exit(1);
            }
            if(luggage.weight>32&&luggage.weight<=45){//超重就交钱
                money+=3000;
            }else if(luggage.weight>45){
                System.out.println("您的行李不符合要求 ，请转货运");
                System.exit(1);
            }
            /****不是vip****/
            if (vip == 0) {
                if(flight.seat_type==1){
                    if(i==3){/**有三件免费额度*/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=4){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("您的行李不符合要求,请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==2){/**公务舱*/
                    if(i==2){/**有两件免费额度*/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=3){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("您的行李不符合要求,请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==3||flight.seat_type==4){/**只有一件免费额度*/
                    if(i==1){/**有两件免费额度*/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=2){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("您的行李不符合要求,请转货运");
                            System.exit(1);
                        }
                    }
                }
            }else{/**他是vip*/
                if(flight.seat_type==1){
                    if(i==4){/**会多出一件，也就是免费额度是4件**/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=5){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("你的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==2){
                    if(i==3){/**会多出一件，也就是免费额度是3件**/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 你的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=4){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("你的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==3||flight.seat_type==4){
                    if(i==1){/**有三件免费额度*/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 你的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=2){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("你的行李不符合要求,请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        return money;
    }
    public double toZone3(){
        double money=0;
        /**婴儿乘客*/
        if(passenger_type==2){
            boolean ok=luggages.size()<=1;/**婴儿行李只能是一件*/
            if(!ok){System.out.println("婴儿行李超数量  ");System.exit(1);}
            if(luggages.get(0).getSum()>115){money+=1000;}
            if(luggages.get(0).weight>10){money+=3000;}
            return money;
        }
        /********************成人乘客**********************/
        /**头等舱*/ /**公务舱*/
        if(flight.seat_type==1||flight.seat_type==2){
            for(int i=0;i<luggages.size();i++){
                Luggage luggage=luggages.get(i);
                int size=luggage.getSum();
                if(size>159&&size<=300){//一超尺寸就交钱
                    money+=1000;
                }else if(size>300){
                    System.out.println("您的行李不符合要求，请转货运");
                    System.exit(1);
                }
                if(luggage.weight>32&&luggage.weight<=45){//超重就交钱
                    money+=3000;
                }else if(luggage.weight>45){
                    System.out.println("您的行李不符合要求，请转货运 ");
                    System.exit(1);
                }
                /****不是vip****/
                if (vip == 0) {
                    if(flight.seat_type==1){
                        if(i==3){/**有三件免费额度*/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("您 的行李不符合要求,请转货运");
                                System.exit(1);
                            }
                        }
                    }
                    if(flight.seat_type==2){
                        if(i==2){/**有两件免费额度*/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=3){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("您的行李不符合要求, 请转货运");
                                System.exit(1);
                            }
                        }
                    }

                }else{/**他是vip*/
                    if(flight.seat_type==1){
                        if(i==4){/**会多出一件，也就是免费额度是4件**/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要 求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=5){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("你的行李不符合要 求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                    if(flight.seat_type==2){
                        if(i==3){/**会多出一件，也就是免费额度是3件**/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println("你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println(" 你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                }
            }
        }

        /**经济舱*/ /**代表明珠经济舱*/
        if(flight.seat_type==3||flight.seat_type==4){
            for(int i=0;i<luggages.size();i++){
                Luggage luggage=luggages.get(i);
                int size=luggage.getSum();
                if(size>159&&size<=300){//一超尺寸就交钱
                    money+=1000;
                }else if(size>300){
                    System.out.println("您的行李不符合 要求，请转货运 ");
                    System.exit(1);
                }
                if(luggage.weight>23&&luggage.weight<=32){//超重就交钱
                    money+=2000;
                }else if(luggage.weight<=45){
                    money+=3000;
                }else {
                    System.out.println("您的行李不符合要求， 请转货运");
                    System.exit(1);
                }
                /****不是vip****/
                /**注意明珠经济舱和经济舱的标准和数量都是一样的，所以不用区分*/
                if (vip == 0) {
                    if(i==2){/**有两件23kg免费额度*/
                        if(luggage.weight<=23&&luggage.getSum()<=158){
                            money+=1000;
                        }else{
                            System.out.println("您 的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=3){//超出第二件或更多
                        if(luggage.weight<=23&&size<=158){
                            money+=2000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }else{/**他是vip*/
                    if(i==3){//超出第一件
                        if(luggage.weight<=23&&luggage.getSum()<=158){
                            money+=1000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=4){//超出第二件或更多
                        if(luggage.weight<=23&&size<=158){
                            money+=2000;
                        }else{
                            System.out.println("您 的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        return money;
    }
    public double toZone4() {
        double money = 0;
        /**婴儿乘客*/
        if (passenger_type == 2) {
            boolean ok = luggages.size() <= 1;/**婴儿行李只能是一件*/
            if (!ok) {
                System.out.println(" 婴儿行李超数量");
                System.exit(1);
            }
            if (luggages.get(0).getSum() > 115) {
                money += 1000;
            }
            if (luggages.get(0).weight > 10) {
                money += 3000;
            }
            return money;
        }
        /********************成人乘客**********************/
        /**头等舱*/
        if (flight.seat_type ==1) {
            for (int i = 0; i < luggages.size(); i++) {
                Luggage luggage = luggages.get(i);
                int size = luggage.getSum();
                if (size > 159 && size <= 300) {//一超尺寸就交钱
                    money += 1000;
                } else if (size > 300) {
                    System.out.println("您的行李不符合要求 ，请转货运");
                    System.exit(1);
                }
                if (luggage.weight > 32 && luggage.weight <= 45) {//超重就交钱
                    money += 3000;
                } else if (luggage.weight > 45) {
                    System.out.println("您的行李不符合要求 ，请转货运 ");
                    System.exit(1);
                }
                /****不是vip****/
                if (vip == 0){
                    if(i==3){/**有三件免费额度*/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=4){//超出第二件或更多
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("您的行李不符合要求,请转货运");
                            System.exit(1);
                        }
                    }
                } else {/**他是vip*/
                    if(i==4){/**就有4件免费额度*/
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=450;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=5){
                        if(luggage.weight<=32&&size<=158){
                            money+=1300;
                        }else{
                            System.out.println("您的行李不符合要求,请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        /**公务舱*//**经济舱*/ /**代表明珠经济舱*/
        if(flight.seat_type==2||flight.seat_type==3||flight.seat_type==4){
            if(flight.seat_type==2){
                for(int i=0;i<luggages.size();i++){
                    Luggage luggage=luggages.get(i);
                    int size=luggage.getSum();
                    if(size>159&&size<=300){//一超尺寸就交钱
                        money+=1000;
                    }else if(size>300){
                        System.out.println(" 您的行李不符合要求，请转货运 ");
                        System.exit(1);
                    }
                    if(luggage.weight>23&&luggage.weight<=32){//超重就交钱
                        money+=1000;
                    }else if(luggage.weight<=45){
                        money+=3000;
                    }else {
                        System.out.println("您的行李不符合要求， 请转货运");
                        System.exit(1);
                    }
                    /****不是vip****/
                    if (vip == 0) {
                        if(i==3){/**有三件23kg免费额度*/
                            if(luggage.weight<=23&&luggage.getSum()<=158){
                                money+=450;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=23&&size<=158){
                                money+=1300;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }else{/**他是vip*/
                        if(i==4){//超出第一件
                            if(luggage.weight<=23&&luggage.getSum()<=158){
                                money+=450;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=5){//超出第二件或更多
                            if(luggage.weight<=23&&size<=158){
                                money+=1300;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                }
            }
            if(flight.seat_type==3){
                for(int i=0;i<luggages.size();i++){
                    Luggage luggage=luggages.get(i);
                    int size=luggage.getSum();
                    if(size>159&&size<=300){//一超尺寸就交钱
                        money+=1000;
                    }else if(size>300){
                        System.out.println(" 您的行李不符合要求，请转货运 ");
                        System.exit(1);
                    }
                    if(luggage.weight>23&&luggage.weight<=32){//超重就交钱
                        money+=1000;
                    }else if(luggage.weight<=45){
                        money+=3000;
                    }else {
                        System.out.println("您的行李不符合要求， 请转货运");
                        System.exit(1);
                    }
                    /****不是vip****/
                    if (vip == 0) {
                        if(i==2){/**有两件23kg免费额度*/
                            if(luggage.weight<=23&&luggage.getSum()<=158){
                                money+=450;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=3){//超出第二件或更多
                            if(luggage.weight<=23&&size<=158){
                                money+=1300;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }else{/**他是vip*/
                        if(i==3){//超出第一件
                            if(luggage.weight<=23&&luggage.getSum()<=158){
                                money+=450;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=23&&size<=158){
                                money+=1300;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                }
            }
            if(flight.seat_type==4){
                for(int i=0;i<luggages.size();i++){
                    Luggage luggage=luggages.get(i);
                    int size=luggage.getSum();
                    if(size>159&&size<=300){//一超尺寸就交钱
                        money+=1000;
                    }else if(size>300){
                        System.out.println(" 您的行李不符合要求，请转货运 ");
                        System.exit(1);
                    }
                    if(luggage.weight>23&&luggage.weight<=32){//超重就交钱
                        money+=1000;
                    }else if(luggage.weight<=45){
                        money+=3000;
                    }else {
                        System.out.println("您的行李不符合要求， 请转货运");
                        System.exit(1);
                    }
                    /****不是vip****/
                    if (vip == 0) {
                        if(i==1){/**有1件23kg免费额度*/
                            if(luggage.weight<=23&&luggage.getSum()<=158){
                                money+=450;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=2){//超出第二件或更多
                            if(luggage.weight<=23&&size<=158){
                                money+=1300;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }else{/**他是vip*/
                        if(i==2){//超出第一件
                            if(luggage.weight<=23&&luggage.getSum()<=158){
                                money+=450;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=3){//超出第二件或更多
                            if(luggage.weight<=23&&size<=158){
                                money+=1300;
                            }else{
                                System.out.println("您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                }
            }

        }
        return money;
    }
    public double lanzhouTOdibai(){
        double money=weizhanzuoyinger();
        if(money>0){return money;}
        /********************成人乘客**********************/
        /**头等舱*/ /**公务舱*/
        if(flight.seat_type==1||flight.seat_type==2){
            for(int i=0;i<luggages.size();i++){
                Luggage luggage=luggages.get(i);
                int size=luggage.getSum();
                if(size>159&&size<=300){//一超尺寸就交钱
                    money+=1000;
                }else if(size>300){
                    System.out.println("您的行李不符合要求，请转货运 ");
                    System.exit(1);
                }
                if(luggage.weight>32&&luggage.weight<=45){//超重就交钱
                    money+=3000;
                }else if(luggage.weight>45){
                    System.out.println("您的行李不符合要求，请转货运");
                    System.exit(1);
                }
                /****不是vip****/
                if (vip == 0) {
                    if(flight.seat_type==1){
                        if(i==3){/**有三件免费额度*/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("您的行李不符合要求,请转货运");
                                System.exit(1);
                            }
                        }
                    }
                    if(flight.seat_type==2){
                        if(i==2){/**有两件免费额度*/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=3){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("您的行李不符合要求,请转货运");
                                System.exit(1);
                            }
                        }
                    }

                }else{/**他是vip*/
                    if(flight.seat_type==1){
                        if(i==4){/**会多出一件，也就是免费额度是4件**/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 您的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=5){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                    if(flight.seat_type==2){
                        if(i==3){/**会多出一件，也就是免费额度是4件**/
                            if(luggage.weight<=32&&luggage.getSum()<=158){
                                money+=1000;
                            }else{
                                System.out.println(" 你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                        if(i>=4){//超出第二件或更多
                            if(luggage.weight<=32&&size<=158){
                                money+=2000;
                            }else{
                                System.out.println("你的行李不符合要求，请转货运");
                                System.exit(1);
                            }
                        }
                    }
                }
            }
        }
        /**经济舱*/ /**代表明珠经济舱*/
        if(flight.seat_type==3){
            for (int i = 0; i < luggages.size(); i++) {
                Luggage luggage = luggages.get(i);
                int size = luggage.getSum();
                if (size > 159 && size <= 300) {//一超尺寸就交钱
                    money += 1000;
                } else if (size > 300) {
                    System.out.println(" 您 的行李不 符合要求，请转货运 ");
                    System.exit(1);
                }
                if (luggage.weight > 32 && luggage.weight <= 45) {//超重就交钱
                    money += 3000;
                } else {
                    System.out.println("您的 行李不 符合要求， 请转货运");
                    System.exit(1);
                }
                /****不是vip****/
                /**注意明珠经济舱和经济舱的标准和数量都是一样的，所以不用区分*/
                if (vip == 0) {
                    if (i == 1) {/**有1件32kg免费额度*/
                        if (luggage.weight <= 23 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if (i>=2) {//超出第二件或更多
                        if (luggage.weight <= 23 && size <= 158) {
                            money += 2000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                } else {/**他是vip*/
                    if (i == 2) {//超出第一件
                        if (luggage.weight <= 23 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println("您的 行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if (i >= 3) {//超出第二件或更多
                        if (luggage.weight <= 23 && size <= 158) {
                            money += 2000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        if(flight.seat_type==4){
            for (int i = 0; i < luggages.size(); i++) {
                Luggage luggage = luggages.get(i);
                int size = luggage.getSum();
                if (size > 159 && size <= 300) {//一超尺寸就交钱
                    money += 1000;
                } else if (size > 300) {
                    System.out.println(" 您 的行李不 符合要求，请转货运 ");
                    System.exit(1);
                }
                if (luggage.weight > 23 && luggage.weight <= 32) {//超重就交钱
                    money += 1000;
                } else if(luggage.weight > 32){
                    money += 3000;
                } else {
                    System.out.println("您的 行李不 符合要求， 请转货运");
                    System.exit(1);
                }
                /****不是vip****/
                /**注意明珠经济舱和经济舱的标准和数量都是一样的，所以不用区分*/
                if (vip == 0) {
                    if (i == 1) {/**有1件23kg免费额度*/
                        if (luggage.weight <= 23 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if (i>=2) {//超出第二件或更多
                        if (luggage.weight <= 23 && size <= 158) {
                            money += 2000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                } else {/**他是vip*/
                    if (i == 2) {//超出第一件
                        if (luggage.weight <= 23 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println("您的 行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if (i >= 3) {//超出第二件或更多
                        if (luggage.weight <= 23 && size <= 158) {
                            money += 2000;
                        } else {
                            System.out.println("您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        return money;
    }
    public double weizhanzuoyinger(){
        double money=0;
        if(passenger_type==2){
            boolean ok=luggages.size()<=1;/**婴儿行李只能是一件*/
            if(!ok){System.out.println("婴儿行李超数量");System.exit(1);}
            if(luggages.get(0).getSum()>115){money+=1000;}
            if(luggages.get(0).weight>10){money+=3000;}
        }
        return money;
    }
    public double hanguoToZhongguo(){
        double money=0;
        weizhanzuoyinger();
        if(money>0){return money;}
        for(int i=0;i<luggages.size();i++){
            Luggage luggage=luggages.get(i);
            int size=luggage.getSum();
            if(size>159&&size<=300){
                money+=1000;
            }else if(size>300){
                System.out.println("你输入的行李不合理");
                System.exit(1);
            }
            if(luggage.weight>32&&luggage.weight<=45){
                money+=3000;
            }else if(luggage.weight>45){
                System.out.println("你输入的行李不合理");
                System.exit(1);
            }
            if(vip==0){
                if(flight.seat_type==1){
                    if(i==3) {
                        if (luggage.weight <= 32 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=4){
                        if (luggage.weight <= 32 && luggage.getSum() <= 158) {
                            money += 3000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==2){
                    if(i==2){
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=1000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>3){
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=3000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==3){
                    if(i==1){
                        if(luggage.weight<=23&&luggage.getSum()<=158){
                            money+=1000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>2){
                        if(luggage.weight<=23&&luggage.getSum()<=158){
                            money+=3000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
            else{
                if(flight.seat_type==1){
                    if(i==4) {
                        if (luggage.weight <= 32 && luggage.getSum() <= 158) {
                            money += 1000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>=5){
                        if (luggage.weight <= 32 && luggage.getSum() <= 158) {
                            money += 3000;
                        } else {
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==2){
                    if(i==3){
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=1000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>4){
                        if(luggage.weight<=32&&luggage.getSum()<=158){
                            money+=3000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
                if(flight.seat_type==3){
                    if(i==2){
                        if(luggage.weight<=23&&luggage.getSum()<=158){
                            money+=1000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                    if(i>3){
                        if(luggage.weight<=23&&luggage.getSum()<=158){
                            money+=3000;
                        }else{
                            System.out.println(" 您的行李不符合要求，请转货运");
                            System.exit(1);
                        }
                    }
                }
            }
        }
        return money;
    }
}
