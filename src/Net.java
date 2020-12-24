import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;


public class Net {

    private ServerSocket ss = null;

    public void main(){
        System.out.println("Main thread");
        new Thread(this::Block_Update).start();
        new Thread(this::API_Req).start();

    }

    private void Block_Update() {
        System.out.println("Starting Blockchain Network");
        while (true) {
            try {
                ss = new ServerSocket(Settings.Net_port);
                Socket socket = ss.accept();
                System.out.println("CLIENT CONNECTED AT: " + ss.getInetAddress());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                for (Block block : Blockchain.BlockChain) {
                    objectOutputStream.writeObject(block);
                }

                ss.close();
            }catch (Exception ex){

            }
        }
    }

    private void API_Req() {
        while (true){

            try {
                System.out.println("Starting API_NET");
                ss = new ServerSocket(Settings.API_NET_PORT);
                Socket socket = ss.accept();
                System.out.println("CLIENT CONNECTED AT: " + ss.getInetAddress());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                String msg1 = (String) objectInputStream.readObject();
                if(msg1.equals("Size")){
                    objectOutputStream.writeObject(Blockchain.BlockChain.size());
                    objectOutputStream.flush();
                }
                String msg = (String) objectInputStream.readObject();

                if(msg.equals("BCUPDATE")){
                    for(Block block: Blockchain.BlockChain){
                        objectOutputStream.writeObject(block);
                    }
                }

                if(msg.equals("WalletBalance")){
                    System.out.println("API CONNECTED WALLET:");
                    String Wkey = (String) objectInputStream.readObject();
                    API_FUNCTIONS api_functions = new API_FUNCTIONS();
                    System.out.println(Wkey);
                    System.out.println("BALANCE IS: "+ api_functions.Remote_CheckBalance(Wkey));
                    objectOutputStream.writeObject(api_functions.Remote_CheckBalance(Wkey));
                    objectOutputStream.close();
                    objectInputStream.close();
                    ss.close();
                    socket.close();
                }

                if(msg.equals("SendFunds")){
                    System.out.println("API CONNECTED SENDFUNDS:");
                    PrivateKey PrivKey = (PrivateKey) objectInputStream.readObject();
                    PublicKey PubKey = (PublicKey) objectInputStream.readObject();
                    PublicKey publicKey = PubKey;
                    PrivateKey privateKey = PrivKey;
                    Transaction transaction = (Transaction) objectInputStream.readObject();
                    Transaction new_transaction = transaction;
                    API_FUNCTIONS api_functions = new API_FUNCTIONS();
                    System.out.println("Recived!!");
                    objectOutputStream.writeObject("Transaction Sent: "+ api_functions.Remote_Send(publicKey, privateKey, transaction));
                    objectOutputStream.close();
                    objectInputStream.close();
                    ss.close();
                    socket.close();
                }


                objectOutputStream.close();
                objectInputStream.close();
                ss.close();
                socket.close();
            }catch (Exception ex){

            }
        }

    }


}
