import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Blockchain implements Serializable {

    /////THIS IS BLOCKCHAIN(BC)
    public static ArrayList<Block> BlockChain = new ArrayList<>();

    ////THIS IS TRANSACTION-STORAGE-ARRAY!!!
    public static ArrayList<Transaction> DB_STORAGE_TRANSACTIONS =  new ArrayList<>();
    ////////////This is The array of transactions I got to put in new Block(Temporary Storage)
    public static ArrayList<Transaction> Mine_Transactions = new ArrayList<>();

    public static ArrayList<Block> temp_blocks = new ArrayList<>();//for checking blocks

    public static ArrayList<Block> MBlocks_NV = new ArrayList<>(); //THIS IS BLOCKS THAT WERE MINED BUT NOT YET VERIFIED!!




    public static String LastBlockHash(){
        if(Blockchain.BlockChain.size() == 1){
            return "";
        }else {
            return Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1).getBlockHash();
        }
    }

    public static void Add_To_Chain(){
        ArrayList<Integer> localid = new ArrayList<Integer>();
        ArrayList<Block> pairs = new ArrayList<>();
        ArrayList<Block> temp = new ArrayList<>();



        for (Block block: MBlocks_NV){
            try{
                temp_blocks.add(MBlocks_NV.get(MBlocks_NV.lastIndexOf(block) + 1));
                System.out.println("ADDING INTO TEMP BLOCKS: "+ block);
            }catch (IndexOutOfBoundsException in){
                System.out.println("OUT OF BOUNDS!!");
            }
        }

        for(Block block: MBlocks_NV){
            if(!temp_blocks.contains(block)){
                if(temp_blocks.contains(block.getBlockHash())){
                    if(temp_blocks.contains(block.getBlockHash())){
                        for(int x = 0; x <= temp_blocks.size() -1; x++){
                            if(temp_blocks.get(x).getPreviousHash().matches(block.getBlockHash())){
                                pairs.add(temp_blocks.get(x));
                                System.out.println("FOUND PAIR!!!");
                                System.out.println("ADDING PAIR!!!");
                            }
                        }
                    }
                }
                if(pairs.contains(block.getBlockHash())){
                    if(!temp_blocks.contains(block)){
                        temp_blocks.add(block);
                        System.out.println("ADDED: "+ block + " TO PAIR!!");
                    }
                }
                if(!(pairs.size() >= 2)){
                    for(Block block1: pairs){
                        for(int x = 0; x <= pairs.size(); x++){
                            x++;
                            if(block1.transactions.size() >= pairs.get(x).transactions.size()){
                                for(Block block6: MBlocks_NV){
                                    if(block6.getBlockHash().matches(block1.getPreviousHash())){
                                        temp.add(block6);
                                    }
                                }
                                temp.add(block1);
                            }
                        }
                    }
                }
            }
        }




        Blockchain.BlockChain.addAll(temp);
        temp.clear();



        return;


    }


}
