import org.bouncycastle.jcajce.provider.digest.SHA256;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

public class Transaction implements Serializable {


    public String from_address;
    public String Recpt_address;
    public float value;
    public String transhash;
    public int verified;
    public Boolean ISmined;
    public float Fees;


    public Transaction(String from, String recpt, float value){
        this.from_address = from;
        this.Recpt_address = recpt;
        this.ISmined = false;
        this.verified = 1;
        this.value = value;
        Random random = new Random();
        this.transhash = StringUtil.applySha256(Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1).blockHash + random.nextInt(9999999* 500));
    }
}
