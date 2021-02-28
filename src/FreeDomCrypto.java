import java.util.ArrayList;
import java.util.Date;

public class FreeDomCrypto {


    public static void main(String[] args) throws Exception {
        new G_BLOCK().SETUP_GBLOCK();

        Thread thread = new Thread(Net::APINETWORK);
        System.out.println("GENESES BLOCK HAS IS: "+ Blockchain.BlockChain.get(0).getBlockHash());
        thread.start();


//        new menu().Menu();

    }
}
