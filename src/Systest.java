import com.sun.tools.javac.Main;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Random;

public class Systest {



    public void test() throws Exception {

        Mysql_DB mysql_db = new Mysql_DB();
        Validation validation = new Validation();

//        mysql_db.CLR_DB_BLOCK();
//        mysql_db.CLR_DB_TRANS();
        /////////////////////////////////////////////////////////////////adds blocks and transactions
        Wallet from = new Wallet();
        Wallet too = new Wallet();
        from = new Wallet();
        too = new Wallet();







        System.out.println("FROM----Wallet Balance: "+ from.Balance(from.publicKey.toString()));
        System.out.println("TOO----Wallet Balance: "+ too.Balance(too.publicKey.toString()));

//        too.Send(StringUtil.applySha256(from.publicKey.toString()), 0, too.privateKey);
        System.out.println("MY WALLET---FROM: "+ StringUtil.applySha256(from.publicKey.toString()));
        System.out.println("MY WALLET---TOO: "+ StringUtil.applySha256(too.publicKey.toString()));
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("FROM----Wallet Balance: "+ from.Balance(from.publicKey.toString()));
        System.out.println("TOO----Wallet Balance: "+ too.Balance(too.publicKey.toString()));




    }





}
