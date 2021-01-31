import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;

public class Blockchain implements Serializable {

    /////THIS IS BLOCKCHAIN(BC)
    public static ArrayList<Block> BlockChain = new ArrayList<>();

    ////THIS IS TRANSACTION-STORAGE-ARRAY!!!
    public static ArrayList<Transaction> DB_STORAGE_TRANSACTIONS =  new ArrayList<>();
    /////THIS IS TEMP CHAIN
    public static ArrayList<Block> V_BlockChain = new ArrayList<>();
    ////////////This is The array of transactions I got to put in new Block(Temporary Storage)
    public static ArrayList<Transaction> Mine_Transactions = new ArrayList<>();

    public static ArrayList<Block> temp_blocks = new ArrayList<>();//for providing clients

    public static ArrayList<Block> MBlocks_NV = new ArrayList<>(); //THIS IS BLOCKS THAT WERE MINED BUT NOT YET VERIFIED!!




    public static String LastBlockHash(){
        if(Blockchain.BlockChain.size() == 1){
            return "";
        }else {
            return Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1).getBlockHash();
        }
    }

}
