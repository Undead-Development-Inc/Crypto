import java.util.Date;

public class TEST_SEQ {
    public void Test_Chain(){
        Block block = new Block(null, "0", new Date().getTime(), null);
        block.diff = 0;
        Blockchain.TEST_BlockCain.add(block);
        CONTROL();


    }
    public void CONTROL(){
        for(int x = 0; x <= 100; x++){
            new TEST_FUNCTIONS().Random_Wallets();
            new TEST_FUNCTIONS().CREATE_RANDOM_TRANSACTION();
            new Mine().TEST_mine();
            if(Test_Blocks()){
                System.out.println("TEST PASSED!!");
            }
            System.out.println("BLOCKCHAIN TEST SIZE: "+ Blockchain.TEST_BlockCain.size());
        }
        return;
    }
    public boolean Test_Blocks() {
        Boolean flag = false;
        try {
            for(Block block: Blockchain.TEST_BlockCain){
                int i = Blockchain.TEST_BlockCain.lastIndexOf(block);
                new TEST_FUNCTIONS().Modify_CHAIN(i);
            }
            for (Block block : Blockchain.TEST_BlockCain) {
                flag = !Blockchain.TEST_BlockCain.get(Blockchain.TEST_BlockCain.lastIndexOf(block)).getBlockHash().matches(Blockchain.TEST_BlockCain.get(Blockchain.TEST_BlockCain.lastIndexOf(block)).calculateBlockHash());
                flag = !Blockchain.TEST_BlockCain.get(Blockchain.TEST_BlockCain.lastIndexOf(block)).getPreviousHash().matches(Blockchain.TEST_BlockCain.get(Blockchain.TEST_BlockCain.lastIndexOf(block) -1).calculateBlockHash());
                flag = !Blockchain.TEST_BlockCain.get(Blockchain.TEST_BlockCain.lastIndexOf(block)).Calculate_MerkleRoot().matches(Blockchain.TEST_BlockCain.get(Blockchain.TEST_BlockCain.lastIndexOf(block)).Merkleroot);
                flag = block.transactions.size() == 0;
                if(flag){
                    System.out.println(Settings.RED_BOLD +"FAILED AT BLOCK: "+ block);
                }
            }
            return flag;
        }catch (Exception ex){

        }
        return flag;
    }

}
