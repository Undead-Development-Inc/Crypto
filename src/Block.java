import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Block implements Serializable{
    /////////TRANSACTIONS IN BLOCK//////////////

    Date date= new Date();
    long time = date.getTime();
    //Timestamp ts = new Timestamp(time);
    ///////////////////////////
    /////THIS IS BLOCK!!!
    private String PrevHash;
    private String blockHash = "";
    public Transaction_Pool transaction_pool;
    private int nonce;
    private ArrayList<Transaction> data;
    public String Merkleroot = ""; //Final Hash of ALL Transactions in Block
    public ArrayList<String> MR_HASHLIST = new ArrayList<>(); //List of All Transaction Hashes in Block
    public long timeStamp;
    public float BlockReward; //Total block reward
    public int diff = 1;

    public Block(ArrayList<Transaction> data, String previousHash, long timeStamp) throws NullPointerException{

        this.Merkleroot = Calculate_MerkleRoot();
        this.timeStamp = timeStamp;
        this.data = data;
        this.blockHash = calculateBlockHash();
        this.PrevHash = previousHash;



    }
    public String Calculate_MerkleRoot(){
        String MR = "";
        MR = StringUtil.applySha256(this.transaction_pool.transactions.toString());
        return MR;
    }

    public String calculateBlockHash() {
        String dataToHash = this.PrevHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + data;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!this.blockHash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            System.out.println(Settings.RED_BOLD + Settings.BLACK_BACKGROUND +"Trying: "+ this.blockHash);
            this.blockHash = calculateBlockHash();
        }
        System.out.println(Settings.GREEN_BOLD + Settings.BLACK_BACKGROUND +"FOUND: "+ this.blockHash);
        return this.blockHash;
    }
    public String getPreviousHash() {
        return this.PrevHash;
    }
    public String getBlockHash(){
        return this.blockHash;
    }
    public void setData(ArrayList<Transaction> data) {
        this.data = data;
    }
    public void setBlock(String hash, String PrevHash, String Merkleroot, int Difficulty, int Nonce, long timestamp, ArrayList<Transaction> Data){
        this.blockHash = hash;
        this.PrevHash = PrevHash;
        this.Merkleroot = Merkleroot;
        this.diff = Difficulty;
        this.nonce = Nonce;
        this.timeStamp = timestamp;
        this.data = Data;
        return;
    }

    public int get_nonce(){
        return this.nonce;
    }
    public ArrayList<Transaction> get_DATA(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction: this.transaction_pool.transactions){
            transactions.add(transaction);
        }
        return transactions;
    }
    public int getDiff(){
        return this.diff;
    }



}

