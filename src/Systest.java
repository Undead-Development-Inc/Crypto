import com.sun.tools.javac.Main;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Random;

public class Systest {



    public void test() throws Exception {

        Mysql_DB mysql_db = new Mysql_DB();
        Validation validation = new Validation();

        mysql_db.CLR_DB_BLOCK();
        mysql_db.CLR_DB_TRANS();
        /////////////////////////////////////////////////////////////////adds blocks and transactions
        Wallet from = new Wallet();
        Wallet too = new Wallet();
        from = new Wallet();
        too = new Wallet();




        for(int i = 0; i <= 50; i++){
            if(Blockchain.BlockChain.get(main.N_Block()).Transactions.size() != Settings.Max_Block_Transactions){
                Random random = new Random();
                Transaction transaction = new Transaction(StringUtil.applySha256(from.publicKey.toString()), StringUtil.applySha256(too.publicKey.toString()),1);
                Blockchain.BlockChain.get(main.N_Block()).Transactions.add(transaction);

            }
        }
        for(Block block : Blockchain.BlockChain){
            System.out.println("-----------------------------------------------------------------");
            System.out.println("BLOCK ID: "+ Blockchain.BlockChain.lastIndexOf(block));
            for(Transaction transaction: block.Transactions){
                mysql_db.Transaction_update(transaction, block);
                System.out.println("Transaction Hash: "+ transaction.transhash);
            }
        }

        for (Block block1 : Blockchain.BlockChain){
            validation.CheckBlock(block1);
            validation.check_transaction(block1.transaction);
        }


        System.out.println("FROM----Wallet Balance: "+ from.Balance(from.publicKey.toString()));
        System.out.println("TOO----Wallet Balance: "+ too.Balance(too.publicKey.toString()));

        too.Send(StringUtil.applySha256(from.publicKey.toString()), 0);
        System.out.println("MY WALLET---FROM: "+ StringUtil.applySha256(from.publicKey.toString()));
        System.out.println("MY WALLET---TOO: "+ StringUtil.applySha256(too.publicKey.toString()));
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("FROM----Wallet Balance: "+ from.Balance(from.publicKey.toString()));
        System.out.println("TOO----Wallet Balance: "+ too.Balance(too.publicKey.toString()));




    }





}
