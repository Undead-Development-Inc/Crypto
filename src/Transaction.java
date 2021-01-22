import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

public class Transaction implements Serializable {


    public PublicKey from_address;
    public String Recpt_address;
    public Identifier identifier;
    public float value;
    public String transhash;
    public int verified;
    public Boolean ISmined;
    public float Fees;
    public byte[] Signature;
    public String BlockHash;
    public PublicKey transaction_MIN;
    public static ArrayList<Transaction_Outputs> transaction_outputs = new ArrayList<>();
    public static ArrayList<Transaction> inputs = new ArrayList<>();


    public Transaction(Wallet from, String recpt, float value, PrivateKey fromkey){
        this.from_address = from.publicKey;
        this.Recpt_address = recpt;
        this.ISmined = false;
        this.verified = 1;
        this.value = value;
        Random random = new Random();
        this.transhash = StringUtil.applySha256(from + recpt + value + BlockHash);
        this.BlockHash = "";
        GET_UTXO(from.publicKey);
        this.identifier = new Identifier("Send", this, fromkey);
        set_identifier();

        //this.RECPT = StringUtil.applySha256(from.toString());



    }

    public void set_identifier(){
    return;

    }
    public void GET_UTXO(PublicKey key){
        ArrayList<Transaction> Spent_Transactions = new ArrayList<>();
        ArrayList<Transaction> Recived_Transactions = new ArrayList<>();

        for(Block block: Blockchain.BlockChain){
            for(Transaction transaction: block.transaction_pool.transactions){
                if(transaction.verified >= 1){
                    if(transaction.from_address == key){
                        Spent_Transactions.add(transaction);
                    }else {
                        Recived_Transactions.add(transaction);
                    }
                }
            }
        }

        for(Transaction transaction: Recived_Transactions){
            inputs.add(transaction);
        }

        return;

    }

}
