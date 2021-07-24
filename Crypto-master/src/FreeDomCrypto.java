import java.util.ArrayList;
import java.util.Date;

public class FreeDomCrypto {

    public static String Ver = "";

    public static void main(String[] args) throws Exception {
        StringUtil.HASH();
        new G_BLOCK().SETUP_GBLOCK();
        new TEST_SEQ().Test_Chain();
        LoadBalancer_Control.Get_Masters();
        Thread thread = new Thread(Net::APINETWORK);
        System.out.println("GENESES BLOCK HAS IS: "+ Blockchain.BlockChain.get(0).getBlockHash());
        thread.start();



//        new menu().Menu();

    }
}
