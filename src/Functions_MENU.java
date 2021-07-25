import java.util.ArrayList;

public class Functions_MENU {

    public static ArrayList<Wallet> wallets = new ArrayList<>();

    public void Create_Wallet(){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        wallets.add(wallet);
        System.out.println(Settings.BLUE + Settings.BLACK_BACKGROUND + "NEW WALLET: "+ wallet);
        System.out.println(Settings.BLUE + Settings.BLACK_BACKGROUND + "NEW WALLET ADDR: "+ StringUtil.applySha256(wallet.publicKey.toString()));
        return;
    }
}
