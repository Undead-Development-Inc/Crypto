import java.util.ArrayList;
import java.io.Serializable;
public class Block implements Serializable{
    /////////TRANSACTIONS IN BLOCK//////////////
    public ArrayList<Transaction> Transactions = new ArrayList<>();
    ///////////////////////////
    /////THIS IS BLOCK!!!
    public String PrevHash;
    public String blockHash = "";
    public Transaction transaction;
    public Block(String prevBlockhash){
        this.blockHash = StringUtil.applySha256(this.PrevHash+Blockchain.BlockChain.size());
        this.PrevHash = prevBlockhash;


    }
}
