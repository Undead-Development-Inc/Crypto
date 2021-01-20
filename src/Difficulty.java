public class Difficulty {

    int Blockid_Curr = 0;

    public int difficulty(){
        new Difficulty().Blockid_current();

        if(Blockchain.BlockChain.get(Blockid_Curr).diff <= 10){
//            return Blockchain.BlockChain.get(Blockid_Curr).diff =+ 1;
            return 2;

        }else {
            return Blockchain.BlockChain.get(Blockid_Curr).diff =- 1;
        }
    }

    public void Blockid_current(){
        Blockid_Curr = Blockchain.BlockChain.size();
        return;
    }
}
