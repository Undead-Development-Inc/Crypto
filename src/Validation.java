import org.bouncycastle.asn1.ocsp.Signature;
import org.bouncycastle.math.ec.ECLookupTable;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.sql.SQLException;

public class Validation {

    public Boolean CLOUD_CheckBlock(Block block) throws Exception {

        Mysql_DB mysql_db = new Mysql_DB();
        ErrorMgr errorMgr = new ErrorMgr();

//        if(Blockchain.BlockChain.contains(block) && mysql_db.FIND_Block(block))
//        if(mysql_db.FIND_Block(block) && !Blockchain.BlockChain.contains(block)) errorMgr.errormgr(10);
        if(Blockchain.BlockChain.size() == 0) errorMgr.errormgr(11);
        return true;

    }



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
