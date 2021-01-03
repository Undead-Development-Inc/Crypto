import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Status {
    public void main(){
        new Thread(this::status).start();
    }

    //////THIS IS THE STATUS FUNCTION
    private void status(){
        String format = "|%1$-10s|%2$-10s|%3$-20s|\n";
        while (true){
            try {

//                for(Block block: Blockchain.BlockChain){
//                    for(Transaction transaction: block.Transactions){
//                        int times = 0;
//                        if(times <= 6){
//                            System.out.println(Settings.RED + "Transaction Hash" + " \t \t" + Settings.GREEN + "\t \t " + " \t \t \t Block Hash");
//                            System.out.println(Settings.BLUE + transaction.transhash + " \t \t" + Settings.PURPLE + "\t \t " + block.blockHash);
//                            times =+ 1;
//                        }else {
//                            times = 0;
//                            System.out.println("\f");
//                            System.out.println(Settings.BLUE + transaction.transhash + " \t \t" + Settings.PURPLE + "\t \t " + block.blockHash);
//                        }
//                    }
//                }
            }catch (Exception ex){

            }
        }
    }


}
