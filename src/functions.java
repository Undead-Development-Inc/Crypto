import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class functions {



    public void sendtoo(Wallet wallet, String recptpublickey, float value) throws ClassNotFoundException, SQLException, GeneralSecurityException, IOException {
        wallet.Send(recptpublickey, value, wallet.privateKey);
        return;
    }

    public void Mine_Block(Wallet wallet){
        new Mine().mine(wallet().publicKey);
        return;
    }

    public float Get_Balance(Wallet wallet){
        System.out.println(wallet.Balance(wallet.publicKey.toString()));
        return wallet.Balance(wallet.publicKey.toString());
    }
    public String GET_BLOCK_HASH(int blockid){
        return Blockchain.BlockChain.get(blockid).getBlockHash();
    }

    public Wallet wallet(){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        return wallet;
    }


}
