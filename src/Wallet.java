import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet implements Serializable {

    public ArrayList<Transaction> UTXO = new ArrayList<>();

    public PrivateKey privateKey;
    public PublicKey publicKey;


    public Wallet(){
        generateKeyPair();
    }


    public Boolean Send(String Sendto , float sendvalue) throws ClassNotFoundException, SQLException, GeneralSecurityException, IOException {
        Mysql_DB mysql_db = new Mysql_DB();
        Transaction transaction = new Transaction(StringUtil.applySha256(this.publicKey.toString()), StringUtil.applySha256(Sendto.toString()), sendvalue);
        if(transaction.value == 0){
            System.out.println("Transaction Value must not be 0!!!");
            return false;
        }
        if(Balance(StringUtil.applySha256(this.publicKey.toString())) >= transaction.value){
            Blockchain.BlockChain.get(main.N_Block()).Transactions.add(transaction);
            mysql_db.Transaction_update(transaction, Blockchain.BlockChain.get(main.N_Block()));
            return true;
        }else {
            System.out.println("TRANSACTION VOIDED NOT ENOUGH FUNDS!!!");
        }

        transaction = null;
        return false;
    }

    public float Balance(String walletpublickey) {
        float bal = 0;
        for (Block block : Blockchain.BlockChain) {
            for (Transaction transaction : block.Transactions) {
                UTXO.add(transaction);
//                System.out.println("UTXO SIZE: " + this.UTXO.size());
            }
        }

        for (Transaction transaction : UTXO) {
            if (transaction.from_address.matches(StringUtil.applySha256(this.publicKey.toString())) & transaction.verified == 1) {
                if (transaction.value <= 0.000001) {
                    bal = 0;
                }

                if(transaction.from_address.matches(StringUtil.applySha256(this.publicKey.toString())) & transaction.verified == 1){
                    if(transaction.value >= bal){

                    }else {
                        bal = bal - transaction.value;
                    }
                }
            }


            if (transaction.Recpt_address.matches(StringUtil.applySha256(this.publicKey.toString())) & transaction.verified == 1) {
                bal = bal + transaction.value;
            }

        }
        for(Block block : Blockchain.BlockChain){
            UTXO.removeAll(block.Transactions);
        }
        return bal;
    }


    public void generateKeyPair() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair

            publicKey = keyPair.getPublic();
            publicKey = keyPair.getPublic();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
