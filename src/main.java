import org.bouncycastle.pqc.jcajce.provider.Rainbow;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Random;

public class main {

    public static void main(String[] args) throws Exception {

        System.out.println("RUNNING TEST!!!");



        Mysql_DB mysql_db = new Mysql_DB();
        Validation validation = new Validation();

        mysql_db.CLR_DB_BLOCK();
        mysql_db.CLR_DB_TRANS();
        /////////////////////////////////////////////////////////////////adds blocks and transactions
        Wallet from = new Wallet();
        Wallet too = new Wallet();
        from = new Wallet();
        too = new Wallet();



        /////////////////////////////G-BLOCK/////////////////
        Block block = new Block(Settings.G_Block_Hash);
        Blockchain.BlockChain.add(block);
        mysql_db.ADD_blocks(block);




        Systest systest = new Systest();
        systest.test();

        Blk_Update();
        System.out.println(block.MR_HASHLIST);
        Net net = new Net();
        net.main();



    }

    public static int N_Block() throws SQLException, ClassNotFoundException, GeneralSecurityException, IOException {

        Mysql_DB mysql_db = new Mysql_DB();

        int lastBlockID = Blockchain.BlockChain.lastIndexOf(Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1));
        int Max_Trans = Settings.Max_Block_Transactions;

        if(Blockchain.BlockChain.get(lastBlockID).Transactions.size() == Max_Trans){
            Block block = new Block(Blockchain.BlockChain.get(lastBlockID).blockHash);
            Blockchain.BlockChain.add(block);
            mysql_db.ADD_blocks(block);

            if(mysql_db.FIND_Block(block)){
                System.out.println("Added New Block!!!");
            }
            return Blockchain.BlockChain.indexOf(block);
        }
        return lastBlockID;
    }

    public static void Blk_Update() throws SQLException, ClassNotFoundException {
        for(Block block: Blockchain.BlockChain){
            for(Transaction transaction: block.Transactions){
                block.MR_HASHLIST.add(StringUtil.applySha256(transaction.transhash));
                block.Merkleroot = StringUtil.applySha256(block.Merkleroot + transaction.transhash);
                Mysql_DB mysql_db = new Mysql_DB();
                mysql_db.Block_update(transaction, block);
            }
        }

    }


}
