import org.bouncycastle.asn1.ocsp.Signature;
import org.bouncycastle.math.ec.ECLookupTable;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.sql.SQLException;

public class Validation {





    public Boolean Check_Trans_Sig(Transaction transaction){
        PublicKey sender = transaction.from_address;
        byte[] Signature = transaction.Signature;
        String DATA = transaction.toString();

        if(StringUtil.verifyECDSASig(sender, DATA, Signature)){
            System.out.println("Transaction Signature is Valid!!!");
            return true;
        }else{
            return false;
        }
    }
}
