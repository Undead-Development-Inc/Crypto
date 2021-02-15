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

    public static void Add_To_Chain(){

        ArrayList<Block> temp = new ArrayList<>();
        ArrayList<Block> longest_temp = new ArrayList<>();
        ArrayList<Block> official_block = new ArrayList<>();

        for(Block block: BlockChain){


        }

        for(Block block: MBlocks_NV){
            Block block1 = block;
            System.out.println("BLOCK: "+ block1.getBlockHash());
            for(int x = 0; x <= MBlocks_NV.size(); x++){
                Block block2 = MBlocks_NV.get(x);
                System.out.println("BLOCK 2: "+ block2.getBlockHash());

            }
        }

        return;


    }


}
