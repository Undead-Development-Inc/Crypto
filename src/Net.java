import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Net {

    public void main(String[] args){
        new Thread(this::APINETWORK);
    }

    public void APINETWORK() {
        try {
            ServerSocket serverSocket = new ServerSocket(Settings.API_NET_PORT);
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            String req = (String) objectInputStream.readObject();

            if(req.matches("Get_Balance")){ //RETURNS BALANCE OF WALLET
                Wallet wallet = (Wallet) objectInputStream.readObject();
                objectOutputStream.writeObject(wallet.Balance(wallet));
            }

            if(req.matches("Get_Update")){ //GIVES CLIENTS FULL BLOCKCHAIN UPDATE
                objectOutputStream.writeObject(Blockchain.BlockChain);
            }

            if(req.matches("PUT_Transaction")){ //API CLIENT WANTS TO SEND TRANSACTION
                Transaction transaction = (Transaction) objectInputStream.readObject();
                Blockchain.Mine_Transactions.add(transaction);
            }
            if(req.matches("Get_Transactions")){
                ///THIS GIVES TRANSACTIONS TO INCLUDE IN MINED BLOCK
                objectOutputStream.writeObject(Blockchain.Mine_Transactions);
            }
            if(req.matches("PUSH_MBLOCK")){

            }

        }catch (Exception ex){

        }

    }
}
