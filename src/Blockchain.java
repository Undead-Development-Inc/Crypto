import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Blockchain implements Serializable {

    /////THIS IS BLOCKCHAIN(BC)
    public static ArrayList<Block> BlockChain = new ArrayList<>();

    public static ArrayList<Block> ORPH_BLOCKS = new ArrayList<>();

    ////THIS IS TRANSACTION-STORAGE-ARRAY!!!
    public static ArrayList<Transaction> DB_STORAGE_TRANSACTIONS =  new ArrayList<>();
    ////////////This is The array of transactions I got to put in new Block(Temporary Storage)
    public static ArrayList<Transaction> Mine_Transactions = new ArrayList<>();

    public static ArrayList<Block> temp_blocks = new ArrayList<>();//for checking blocks

    public static ArrayList<Block> MBlocks_NV = new ArrayList<>(); //THIS IS BLOCKS THAT WERE MINED BUT NOT YET VERIFIED!!

    public static ArrayList<Wallet> Net_Wallets = new ArrayList<>();

    public static ArrayList<String> Net_IPs_Recent = new ArrayList<>();





    public static String LastBlockHash(){
        if(Blockchain.BlockChain.size() == 1){
            return "";
        }else {
            return Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1).getBlockHash();
        }
    }

    public static void Add_To_Chain(){
        ArrayList<Float> highest_value = new ArrayList<>();
        ArrayList<Block> BL_HG_VAL = new ArrayList<>();
        ArrayList<Integer> localid = new ArrayList<Integer>();
        ArrayList<Block> blocks_temp = new ArrayList<>();
        ArrayList<Block> temp = new ArrayList<>();

        System.out.println("WORKING@@@");

            int Highest_Transactions = 0;//The current highest ammount of transactions ---AVERAGE
            float Highest_TransactionVAL = 0;



            for(Block block: Blockchain.MBlocks_NV){
                if(!(block.transactions.size() >= Highest_Transactions)){
                    if(Highest_Transactions == 0){
                        Highest_Transactions += block.transactions.size();
                        System.out.println("THE NEWEST HIGHEST AMOUNT OF TRANSACTION'S IS: "+ Highest_Transactions);
                    }
                }
                if(block.transactions.size() >= Highest_Transactions){
                    Highest_Transactions += block.transactions.size();
                    System.out.println("THE NEWEST HIGHEST AMOUNT OF TRANSACTION'S IS: "+ Highest_Transactions);
                }
            }

            for(Block block: Blockchain.MBlocks_NV){
                if(block.transactions.size() == Highest_Transactions){
                    blocks_temp.add(block);
                }
            }
            for(Block block: blocks_temp){
                if(blocks_temp.size() >= 1){
                    float v = 0;
                    for(Transaction transaction: block.transactions){
                        v += transaction.value + transaction.Fees;
                    }
                    highest_value.add(v);
                    BL_HG_VAL.add(block);
                }
            }
            for(Block block: BL_HG_VAL){
                if(highest_value.get(BL_HG_VAL.indexOf(block)) >= Highest_TransactionVAL){
                    Highest_TransactionVAL += highest_value.get(BL_HG_VAL.indexOf(block));
                }else{
                    BL_HG_VAL.remove(block);
                }
            }
            if(BL_HG_VAL.size() == 1){
                Blockchain.BlockChain.addAll(BL_HG_VAL);
                ORPH_BLOCKS.addAll(MBlocks_NV);
                MBlocks_NV.clear();
            }

            Highest_Transactions =0;
            Highest_TransactionVAL =0;
            BL_HG_VAL.clear();
            temp.clear();


        }


}





