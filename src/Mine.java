import jdk.jshell.spi.ExecutionControlProvider;


import java.security.PublicKey;
import java.util.Date;

public class Mine {

    public void Mine_Block_Start(){
        System.out.println("Starting Miner");

        return;

    }



    public void mine(PublicKey miner) {
        try {

            Block block1 = new Block(Blockchain.Mine_Transactions, Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1).getBlockHash(), new Date().getTime(), miner);
            new Chain_Verification().givenBlockchain_whenNewBlockAdded_thenSuccess(block1);
            new Chain_Verification().givenBlockchain_whenValidated_thenSuccess();
            System.out.println(block1);
            System.out.println("Transactions in Block: "+ block1.transaction_pool.transactions.get(0).value);
        }catch (Exception ex){

        }
    }




}
