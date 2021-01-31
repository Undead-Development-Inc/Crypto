import java.io.Serializable;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;

public class Wallet implements Serializable {


    public PrivateKey privateKey;
    public PublicKey publicKey;


    public Wallet(){
        generateKeyPair();
    }


    public float Balance(Wallet mywallet){
        ArrayList<Transaction> ISENT = new ArrayList<>();
        ArrayList<Transaction> ISENT_ALR = new ArrayList<>();
        ArrayList<Transaction> IREC = new ArrayList<>();
        ArrayList<Transaction> IREC_ALR = new ArrayList<>();

        float Bal = 0;
        for(Block block : Blockchain.BlockChain){
            for(Transaction transaction: block.transactions){
                for(Transaction transaction1: transaction.transaction_outputs){
                    if(transaction1.Recpt_address.matches(StringUtil.applySha256(mywallet.publicKey.toString()))){
                        IREC.add(transaction);
                    }
                    if(StringUtil.verifyECDSASig(mywallet.publicKey, transaction.toString(), transaction.Signature)){
                        ISENT.add(transaction);
                    }
                    System.out.println("Transaction: "+ transaction);
                }
            }
        }

        for(Transaction transaction: IREC){
            if(!IREC_ALR.contains(transaction)){
                Bal +=transaction.value;
                System.out.println("I RECIVED TRANSACTION: "+ transaction.transhash);
            }
        }
        for(Transaction transaction: ISENT){
            if(!ISENT_ALR.contains(transaction)){
                Bal -=transaction.value;

                System.out.println("I SENT TRANSACTION: "+ transaction);
            }
        }
        return Bal;
    }

    public Boolean Send_Funds(Wallet wallet, String too, Float value){
        if(wallet.Balance(wallet) != 0 & wallet.Balance(wallet) >= value){
            Transaction transaction = new Transaction(wallet, too, value, wallet.privateKey);
            transaction.Fees = 1;
            transaction.value -= transaction.Fees;
            transaction.transaction_outputs.add(transaction);
            transaction.Signature = StringUtil.applyECDSASig(wallet.privateKey, transaction.toString());
            Blockchain.Mine_Transactions.add(transaction);
            if(Blockchain.Mine_Transactions.contains(transaction)){
                System.out.println("SEND TRANSACTION: "+ transaction.transhash);
                return true;
            }else {
                return false;
            }
        }
        System.out.println("CANNOT SEND FUNDS IF BALANCE IS 0 OR LESS THAN OF VALUE");
        return false;
    }

    public ArrayList<Transaction> UTXO(Wallet wallet){
        ArrayList<Transaction> Sent_Transactions = new ArrayList<>();
        ArrayList<Transaction> Recpt_Transactions = new ArrayList<>();
        ArrayList<Transaction> UTXO_TEMP = new ArrayList<>();

        for(Block block : Blockchain.BlockChain){
            for(Transaction transaction: block.transactions){
                if(transaction.Recpt_address.matches(StringUtil.applySha256(wallet.publicKey.toString()))){
                    Recpt_Transactions.add(transaction);
                }
                if(StringUtil.verifyECDSASig(wallet.publicKey, transaction.toString(), transaction.Signature)){
                    Sent_Transactions.add(transaction);
                }
                System.out.println("Transaction: "+ transaction);
            }

        }

        for(Transaction transaction: Recpt_Transactions){
            UTXO_TEMP.add(transaction);
        }
        for(Transaction transaction: Sent_Transactions){
            for(Transaction transaction1: transaction.transaction_outputs){
                if(transaction1.value <= transaction.value){
                    UTXO_TEMP.add(transaction);
                }
            }
        }
        return UTXO_TEMP;
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
