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
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

public class Wallet implements Serializable {

    public ArrayList<Transaction> UTXO = new ArrayList<>();

    public PrivateKey privateKey;
    public PublicKey publicKey;


    public Wallet(){
        generateKeyPair();
    }


    public Boolean Send(String Sendto , float sendvalue, PrivateKey mykey) throws ClassNotFoundException, SQLException, GeneralSecurityException, IOException {
        Mysql_DB mysql_db = new Mysql_DB();
        Transaction transaction = new Transaction((Wallet) this.publicKey, StringUtil.applySha256(Sendto), sendvalue, mykey);
        transaction.Signature = StringUtil.applyECDSASig(mykey, transaction.toString());
        System.out.println("My Private Key: "+ mykey);
        System.out.println("Transaction Signature: "+ transaction.Signature);
        if(transaction.value == 0){
            System.out.println("Transaction Value must not be 0!!!");
            return false;
        }


        transaction = null;
        return false;
    }

    public float Balance(String key) {
        float bal = 0;
        try {
            for (Block block : Blockchain.BlockChain) {
                for (Transaction transaction : block.transaction_pool.transactions) {
                    UTXO.add(transaction);
//                System.out.println("UTXO SIZE: " + this.UTXO.size());

                }
            }

            for (Transaction transaction : UTXO) {
                String myhash = key;
                System.out.println("THIS IS THE HASH GENERATED: "+ myhash);
                System.out.println("THIS IS TRANSACTION RECPT HASH: "+ StringUtil.applySha256(transaction.Recpt_address));
                if(StringUtil.verifyECDSASig(transaction.from_address, myhash, transaction.identifier.ID_Sig)){
                    System.out.println("Hello I am new");//IAMRECIVING TRANSACTION
                }else {
                    if (StringUtil.verifyECDSASig(this.publicKey, transaction.toString(), transaction.identifier.ID_Sig)) {
                        System.out.println("I SENT TRANSACTION");//I SENT TRANSACTIONS
                    }////WORKING ON!!!!
                }


            }

        }catch (Exception ex){

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
            privateKey = keyPair.getPrivate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
