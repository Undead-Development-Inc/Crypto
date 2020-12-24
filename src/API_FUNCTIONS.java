public class API_FUNCTIONS {

    public float Remote_CheckBalance (String Key){
        Wallet wallet = new Wallet();
        wallet = new Wallet();
        float Bal = wallet.Balance(Key);
        System.out.println("Balance: "+ Bal);
        return Bal;
    }
}
