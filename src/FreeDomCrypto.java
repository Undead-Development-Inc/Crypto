import org.bouncycastle.pqc.jcajce.provider.Rainbow;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FreeDomCrypto {

    public static void main(String[] args) throws Exception {

        System.out.println("RUNNING TEST!!!");



        Mysql_DB mysql_db = new Mysql_DB();
        Validation validation = new Validation();

//        mysql_db.CLR_DB_BLOCK();
//        mysql_db.CLR_DB_TRANS();
        /////////////////////////////////////////////////////////////////adds blocks and transactions
        Wallet from = new Wallet();
        Wallet too = new Wallet();
        from = new Wallet();
        too = new Wallet();


        ArrayList<Transaction> transactions = new ArrayList<>();

        /////////////////////////////G-BLOCK/////////////////
        Block block = new Block(transactions, Settings.G_Block_Hash, new Date().getTime());
        block.mineBlock(1);
        Blockchain.BlockChain.add(block);
        System.out.println(Settings.RED_BOLD + Blockchain.BlockChain.size());
        //mysql_db.ADD_blocks(1);

        Transaction transaction = new Transaction(from.publicKey, too.publicKey.toString(), 100, from.privateKey, block.getBlockHash());
        block.transaction_pool.transactions.add(transaction);
        System.out.println("Transaction Hash: "+ transaction.transhash);
        transaction.Signature = StringUtil.applyECDSASig(from.privateKey, transaction.toString());
        new Validation().Check_Trans_Sig(transaction);
        for(int i =0; i<= 5; i++){
            Block block1 = new Block(Blockchain.Mine_Transactions, Blockchain.BlockChain.get(Blockchain.BlockChain.size() -1).getBlockHash(), new Date().getTime());
            block1.mineBlock(new Difficulty().difficulty());
            new Chain_Verification().givenBlockchain_whenNewBlockAdded_thenSuccess(block1);
            System.out.println(block1);
        }




    }







}



