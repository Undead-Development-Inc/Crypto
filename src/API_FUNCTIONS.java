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

public class API_FUNCTIONS {

    public float Remote_CheckBalance (String Key){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        float Bal = wallet.Balance(Key);
        System.out.println("Balance: "+ Bal);
        return Bal;
    }

    public Boolean Remote_Send(PublicKey myPub, PrivateKey myPriv, Transaction transaction) {
        try {
            Wallet wallet = new Wallet();
            wallet = new Wallet();
            wallet.publicKey = myPub;
            wallet.privateKey = myPriv;

            if (wallet.Send(StringUtil.applySha256(transaction.Recpt_address.toString()), transaction.value)) {
                System.out.println("SENDER: "+ StringUtil.applySha256(transaction.from_address.toString()));
                System.out.println("Transaction Sent: "+ transaction.transhash);
                return true;
            }else {
                System.out.println("Sender: "+ StringUtil.applySha256(transaction.from_address));
                System.out.println("Transaction Failed!!");
                return false;
            }
        }catch (Exception ex){

        }
        return false;
    }
}
