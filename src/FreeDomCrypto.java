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
        Block block = new Block(transactions, Settings.G_Block_Hash, new Date().getTime(), from.publicKey);

        //block.mineBlock(1);
        Blockchain.BlockChain.add(block);
        System.out.println(Settings.RED_BOLD + Blockchain.BlockChain.size());

        new functions().Mine_Block(from);

        System.out.println(StringUtil.applySha256(from.publicKey.toString()));
        System.out.println("My Wallet Balance: "+ from.Balance(StringUtil.applySha256(from.publicKey.toString())));



    }







}



