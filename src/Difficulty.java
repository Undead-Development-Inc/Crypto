public class Difficulty {

    int Blockid_Curr = 0;

    public int difficulty(){
        new Difficulty().Blockid_current();

        System.out.println(Settings.PURPLE + "NEW DIFFICULTY: "+ Blockid_Curr * 2);
        return 2;
    }

    public void Blockid_current(){
        Blockid_Curr = Blockchain.BlockChain.size();
        return;
    }
}
