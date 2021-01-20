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


}
