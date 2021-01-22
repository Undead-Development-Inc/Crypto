public class Block_Reward {

    public int Block_Rew(){
        int Pendingtransactions = Blockchain.Mine_Transactions.size();
        int BlocksinDB = Blockchain.BlockChain.size();
        int DEFAULT_BLOCK_REWARD = 50;

        return 50;
    }
}
