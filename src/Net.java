import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Net {

    public static void main(String[] args){

    }

    public static void APINETWORK() {
        System.out.println("TRYING");
        try {
            while (true) {
                System.out.println("WAITING FOR CONNECTION");
                ServerSocket serverSocket = new ServerSocket(Settings.INET_Trans_Port);
                Socket socket = serverSocket.accept();
                System.out.println("CONNECTED!!!");
                socket.setSoTimeout(10000);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());



                String req = (String) objectInputStream.readObject();

                if (req.matches("Get_Balance")) { //RETURNS BALANCE OF WALLET
                    Wallet wallet = (Wallet) objectInputStream.readObject();
                    objectOutputStream.writeObject(wallet.Balance(wallet));

                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    serverSocket.close();
                }

                if (req.matches("Get_BCUpdate")) { //GIVES CLIENTS FULL BLOCKCHAIN UPDATE
                    System.out.println("Size: "+ Blockchain.BlockChain.size());
                    objectOutputStream.writeObject(Blockchain.BlockChain);


                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    serverSocket.close();
                }

                if (req.matches("PUT_Transaction")) { //API CLIENT WANTS TO SEND TRANSACTION
                    Transaction transaction = (Transaction) objectInputStream.readObject();
                    Blockchain.Mine_Transactions.add(transaction);
                    if(Blockchain.Mine_Transactions.contains(transaction)){
                        objectOutputStream.writeObject("SUCCESS");
                    }else {
                        objectOutputStream.writeObject("FAILED");
                    }

                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    serverSocket.close();
                }
                if (req.matches("Get_Transactions")) {
                    ///THIS GIVES TRANSACTIONS TO INCLUDE IN MINED BLOCK
                    objectOutputStream.writeObject(Blockchain.Mine_Transactions);

                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    serverSocket.close();
                }
                if (req.matches("PUSH_MBLOCK")) {

                    ArrayList<Block> recived_newBlocks = new ArrayList<>();
                    recived_newBlocks = (ArrayList<Block>) objectInputStream.readObject();
                    objectInputStream.reset();

                    System.out.println("RECIVED:");

                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    serverSocket.close();
                }

                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                serverSocket.close();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void NETWORKPUSH() {
        System.out.println("TRYING 2");
        try {
            while(true) {
                ArrayList<Block> Mined_Blocks_Verified = new ArrayList<>();
                ServerSocket serverSocket1 = new ServerSocket(Settings.NetPUSH_port);
                Socket socket1 = serverSocket1.accept();
                ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket1.getOutputStream());

                objectOutputStream1.writeObject(Blockchain.MBlocks_NV);
                objectOutputStream1.writeObject(Blockchain.Mine_Transactions);

                objectOutputStream1.close();
                socket1.close();
                serverSocket1.close();
            }
        } catch (Exception ex) {

        }
    }
}
