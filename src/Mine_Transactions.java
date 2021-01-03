import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

public class Mine_Transactions {

    PrivateKey privateKey;

    public Transaction New_M_Transaction(String skey, String miner){

        Random r = new Random();

        int Diff = 1;
        Transaction transaction = new Transaction(miner.toString(), StringUtil.applySha256(miner + skey), 100);
        transaction.transhash = StringUtil.applySha256(transaction.transhash +  r.nextInt(10) + Diff);
        return transaction;
    }

}
