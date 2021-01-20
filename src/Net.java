import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;


public class Net {

    private ServerSocket ss = null;

    public void main(){
        System.out.println("Main thread");
        new Thread(this::API_Req).start();

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





                objectOutputStream.close();
                objectInputStream.close();
                ss.close();
                socket.close();
            }catch (Exception ex){

            }
        }

    }

    public void DB_NETWORK(String req, int BlockID) throws IOException, ClassNotFoundException {
        try {
            Socket socket = new Socket("142.44.212.205", Settings.DB_STORAGE_NET);
            System.out.println(Settings.BLACK_BACKGROUND + Settings.GREEN + "CONNECTED!!");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(req);

            if (req.matches("GET_Block")) {
                int objectoinput = (int) objectInputStream.readObject();
                for (int i = 0; i <= objectoinput; i++) {
                    Block block = (Block) objectInputStream.readObject();
                    if (!Blockchain.BlockChain.contains(block)) {
                        System.out.println(Settings.GREEN + "Added Block: " + Blockchain.BlockChain.add(block));
                    }
                }
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                return;
            }

            if (req.matches("ADD_Block")) {
                Block block = Blockchain.BlockChain.get(BlockID);
            objectOutputStream.writeObject(block);

            }

            if (req.matches("Get_Transaction")) {
                objectOutputStream.writeObject(req);
                ArrayList<Transaction> DB_GET_TRR = (ArrayList<Transaction>) objectInputStream.readObject();
                for (Block block : Blockchain.BlockChain) {
                    for (Transaction transaction : DB_GET_TRR) {
                        System.out.println("GOT TRANSACTION: " + transaction);
                        if (!block.transaction_pool.transactions.contains(transaction)) {
                            block.transaction_pool.transactions.add(transaction);
                            System.out.println("Added Transaction: " + transaction);
                            DB_GET_TRR.remove(transaction);
                        }
                    }
                }
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
                ss.close();
                return;
            }

            if (req.matches("ADD_Transaction")) {
                objectOutputStream.writeObject(req);
                for (Block block : Blockchain.BlockChain) {
                    for (Transaction transaction : block.transaction_pool.transactions) {
                        Blockchain.DB_STORAGE_TRANSACTIONS.add(transaction);
                    }
                }
                objectOutputStream.writeObject(Blockchain.DB_STORAGE_TRANSACTIONS);
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
                ss.close();
                Blockchain.DB_STORAGE_TRANSACTIONS.clear();
                System.out.println("Sent ALL TRANSACTIONS TO STORAGE SERVER!!!");
                return;
            }

            if (req.matches("GET_BLOCKCHAIN")) {
                objectOutputStream.writeObject(req);
                Blockchain blockchain = (Blockchain) objectInputStream.readObject();
                Blockchain.BlockChain.clear();
                Blockchain.BlockChain.addAll((Collection<? extends Block>) blockchain);
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
                ss.close();
                return;
            }

            if (req.matches("ADD_BLOCKCHAIN")) {
                objectOutputStream.writeObject(req);
                objectOutputStream.writeObject(Blockchain.BlockChain);
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                ss.close();
                return;
            }

            if (req.matches("CLR_BLOCKS")) {
                objectOutputStream.writeObject("CLR_DB_BLK");
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
                ss.close();
                return;
            }

            if (req.matches("CLR_TRA")) {
                objectOutputStream.writeObject("CLR_DB_TRA");
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
                ss.close();
                return;
            }

            return;
        }catch (Exception ex){

        }
    }


}
