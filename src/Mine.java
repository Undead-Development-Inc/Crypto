import jdk.jshell.spi.ExecutionControlProvider;


import java.util.Date;

public class Mine {

    public void Mine_Block_Start(){
        System.out.println("Starting Miner");
        new Thread(this::mine);
    }



    public void mine() {
        try {

                Block block = new Block(Blockchain.Mine_Transactions, Blockchain.LastBlockHash(), new Date().getTime());
                block.mineBlock(new Difficulty().difficulty());
                new Chain_Verification().givenBlockchain_whenNewBlockAdded_thenSuccess(block);
                System.out.println(block);
        }catch (Exception ex){

        }
    }




}
