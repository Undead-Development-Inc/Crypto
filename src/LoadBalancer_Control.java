import java.util.ArrayList;

public class LoadBalancer_Control {

    public static ArrayList<String> Masters = new ArrayList<>(); //This is an array of all the master servers IP's

    public static void Get_Masters(){
        Thread networktest = new Thread(Net::Master_Info);
        networktest.start();
        Net.Get_Master_NETINFO();
    }
}
