import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Random;

public class TEST_FUNCTIONS {
    public static ArrayList<Wallet> RANDOM_TEST_WALLETS = new ArrayList<>();//THIS WELL CARRY TEST RANDOM WALLETS

    public Wallet Test_Wallet(){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        System.out.println("ADDED TEST WALLET; "+ wallet);
        return wallet;
    }
    public void Random_Wallets(){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        for(int x = 0; x <= 200; x++){
            wallet = new Wallet();
            if(!RANDOM_TEST_WALLETS.contains(wallet)){
                RANDOM_TEST_WALLETS.add(wallet);//THIS ADDS THE WALLET TO THE TEST ARRAY LIST
                if(RANDOM_TEST_WALLETS.contains(wallet)){
                    System.out.println("ADDED TEST WALLET: "+ wallet);
                }else {
                    System.out.println("ERROR!!!");
                    System.out.println("TEST WALLET NOT ADDED!!!");
                }
            }
        }
    }
    public void CREATE_RANDOM_TRANSACTION(){
        Random r = new Random();
        Wallet Random_Wallet = new Wallet();
        Random_Wallet = new Wallet();
        Wallet wallet = Test_Wallet();
        for(int x = 0; x <= r.nextInt(200); x++){
            Transaction transaction = new Transaction(wallet, "0x0", 100, wallet.privateKey); //BY DEFAULT VALID TRANSACTION
            Transaction transaction1 = new Transaction(wallet,"0x0", r.nextInt(400), Random_Wallet.privateKey); ///BY DEFAULT INVALID TRANSACTION
            Transaction transaction2 = new Transaction(RANDOM_TEST_WALLETS.get(r.nextInt(200)),StringUtil.applySha256(RANDOM_TEST_WALLETS.get(r.nextInt(200)).publicKey.toString()), r.nextInt(1000), Random_Wallet.privateKey); ///BY DEFAULT VALID TRANSACTION
            Blockchain.Test_Mine_Transactions.add(transaction);
            Blockchain.Test_Mine_Transactions.add(transaction1);
            Blockchain.Test_Mine_Transactions.add(transaction2);
            System.out.println("ADDED TEST TRANSACTION: "+ transaction);
            System.out.println("ADDED TEST TRANSACTION: "+ transaction1);
            System.out.println("ADDED TEST TRANSACTION: "+ transaction2);
        }

    }
    public void Modify_CHAIN(int BlockID){
        Random random = new Random();
        random = new Random();
        Blockchain.TEST_BlockCain.get(BlockID).diff += random.nextInt(20);
        Blockchain.TEST_BlockCain.get(BlockID).Merkleroot = "303FJFNMSM"+random.nextInt(20000);
        Blockchain.TEST_BlockCain.get(BlockID).calculateBlockHash();
        System.out.println("CHANGED DATA IN BLOCK: "+ Blockchain.TEST_BlockCain.get(BlockID));
        return;
    }
}
