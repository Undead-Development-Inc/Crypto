import org.bouncycastle.math.ec.ECLookupTable;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class Validation {

    public Boolean CheckBlock(Block block) throws Exception {

        Mysql_DB mysql_db = new Mysql_DB();
        ErrorMgr errorMgr = new ErrorMgr();

        if(Blockchain.BlockChain.contains(block) && mysql_db.FIND_Block(block))
        if(mysql_db.FIND_Block(block) && !Blockchain.BlockChain.contains(block)) errorMgr.errormgr(10);
        if(Blockchain.BlockChain.size() == 0) errorMgr.errormgr(11);
        return true;

    }

    public Boolean check_transaction(Transaction transaction) throws Exception {
        Mysql_DB mysql_db = new Mysql_DB();
        ErrorMgr errorMgr = new ErrorMgr();

        for(Block block: Blockchain.BlockChain){
            for(Transaction transaction1 : block.Transactions){
                if(mysql_db.FIND_Transaction(transaction1)){
                    continue;
                }
            }
        }
        return true;
    }
}
