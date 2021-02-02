import java.util.ArrayList;
import java.util.Date;

public class FreeDomCrypto {


    public static void main(String[] args) throws Exception {
        new G_BLOCK().SETUP_GBLOCK();
        Thread thread = new Thread(Net::APINETWORK);
        Thread thread1 = new Thread(Net::NETWORKPUSH);
        thread.start();
        thread1.start();

//        new menu().Menu();

    }
}
