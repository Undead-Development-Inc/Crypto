import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Net {

    public void main(String[] args) {
        new Thread(this::APINETWORK);
    }

    public void APINETWORK() {
        try {
            while (true) {
                ServerSocket serverSocket = new ServerSocket(Settings.API_NET_PORT);
                Socket socket = serverSocket.accept();
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
                    objectOutputStream.writeObject(Blockchain.BlockChain);

                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    serverSocket.close();
                }

                if (req.matches("PUT_Transaction")) { //API CLIENT WANTS TO SEND TRANSACTION
                    Transaction transaction = (Transaction) objectInputStream.readObject();
                    Blockchain.Mine_Transactions.add(transaction);

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
                    Block block = (Block) objectInputStream.readObject();
                    Blockchain.MBlocks_NV.add(block);

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

        }

    }

    public void NETWORKPUSH() {
        try {
            while(true) {
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
