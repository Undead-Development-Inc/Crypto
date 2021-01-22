import java.security.PrivateKey;

public class Identifier {
    public String type = "";
    public Transaction transaction;
    public byte[] ID_Sig;


    public Identifier(String type, Transaction transaction, PrivateKey fromkey){
        this.type = type;
        this.transaction = transaction;
        System.out.println("recpt key: "+ this.transaction.Recpt_address);
        if(type == "Send"){
            System.out.println("1111");
            this.ID_Sig = StringUtil.applyECDSASig(fromkey, StringUtil.applySha256(this.transaction.Recpt_address));
        }else {
            this.ID_Sig = StringUtil.applyECDSASig(fromkey, this.transaction.Recpt_address);
        }
    }
}
