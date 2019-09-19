import java.util.HashSet;

public class Flight {
    public int flight_type=1;
    //=1代表是国内航班，
    // =2代表去区域1的航程，
    // =3代表去区域2的航程，
    // =4代表去区域3的航程,
    // =5除上述区域的地方
    public String startPosition="中国";
    public String endPosition="美国";

    public int seat_type=1;
    //=1代表头等舱，
    // =2代表公务舱,
    // =3经济舱，
    // =4代表明珠经济舱，
    // 暂时不设婴儿舱

    Flight(String startPosition,String endPosition,int seat_type){
        this.seat_type=seat_type;
        HashSet AreaSet1=new HashSet();
        HashSet AreaSet2=new HashSet();
        HashSet AreaSet3=new HashSet();
        AreaSet1.add("日本");AreaSet1.add("美洲");AreaSet1.add("澳新");AreaSet1.add("俄罗斯");AreaSet1.add("新加坡");AreaSet1.add("迪拜");
        AreaSet2.add("乌兹别克斯坦");AreaSet2.add("塔吉克斯坦");AreaSet2.add("哈萨克斯坦");AreaSet2.add("吉尔吉斯斯坦");AreaSet2.add("土库曼斯坦");AreaSet2.add("伊朗");
        AreaSet2.add("巴基斯坦");AreaSet2.add("阿塞拜疆");AreaSet2.add("格鲁吉亚");
        AreaSet3.add("内罗毕");AreaSet3.add("土耳其");

        if(startPosition.equals("中国(除兰州/乌鲁木齐)")){
            if(endPosition.equals("中国(除兰州/乌鲁木齐)")){flight_type=1;}
            else if(AreaSet1.contains(endPosition)){flight_type=2;}
            else if(AreaSet2.contains(endPosition)){flight_type=3;}
            else if(AreaSet3.contains(endPosition)){flight_type=4;}
            else flight_type=5;
        }
    }
}
