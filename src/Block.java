import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import java.util.Date;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.io.Serializable;
public class Block implements Serializable{

    /////////TRANSACTIONS IN BLOCK//////////////
    public ArrayList<Transaction> Transactions = new ArrayList<>();
    Date date= new Date();
    long time = date.getTime();
    //Timestamp ts = new Timestamp(time);
    ///////////////////////////
    /////THIS IS BLOCK!!!
    public String PrevHash;
    public String blockHash = "";
    public Transaction transaction;
    public String Merkleroot = ""; //Final Hash of ALL Transactions in Block
    public ArrayList<String> MR_HASHLIST = new ArrayList<>(); //List of All Transaction Hashes in Block
    public String timestamp;
    public float BlockReward; //Total block reward
    public int diff = 1;

    public Block(String prevBlockhash){


        this.blockHash = StringUtil.applySha256(this.PrevHash+Blockchain.BlockChain.size());
        this.PrevHash = prevBlockhash;



    }
}
