import org.junit.jupiter.api.extension.ExtendWith;

public class TEST_FUNCTIONS {

    public void Test_Wallet(){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        System.out.println("Writing: "+ wallet.toString());
        wallet.Encrypt_Write(wallet.toString());
        wallet.FileENCDecrypt(wallet.secretKey, wallet.toString());
    }
}
