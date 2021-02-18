import java.util.ArrayList;
import java.util.Date;

public class FreeDomCrypto {


    public static void main(String[] args) throws Exception {
        new G_BLOCK().SETUP_GBLOCK();
        Thread thread2 = new Thread(Blockchain::Add_To_Chain);
        Thread thread = new Thread(Net::APINETWORK);
        System.out.println("GENESES BLOCK HAS IS: "+ Blockchain.BlockChain.get(0).getBlockHash());
        thread.start();
        thread2.start();

//        new menu().Menu();

    }
}
