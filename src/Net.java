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

    public static Package_Blocks package_blocks;

    public static void APINETWORK() {
        System.out.println("TRYING");
        try {
            while (true) {
                package_blocks = null;
                Pack_ME();
                System.out.println("WAITING FOR CONNECTION");
                ServerSocket serverSocket = new ServerSocket(Settings.INET_Trans_Port);
                Socket socket = serverSocket.accept();
                System.out.println("CONNECTED!!!");
                socket.setSoTimeout(10000);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                String req = (String) objectInputStream.readObject();

                if(req.matches("Node_Update")){
                    objectOutputStream.writeObject(package_blocks);
                    System.out.println("SEND UPDATE TO NODE: "+ socket.getInetAddress());
                    objectInputStream.close();
                    objectOutputStream.close();
                }

                if(req.matches("PUSH_MBLOCK")){
                    ArrayList<Block> New_Blocks = (ArrayList<Block>) objectInputStream.readObject();
                    objectInputStream.close();
                    objectOutputStream.close();
                    for(Block block: New_Blocks){
                        if(!Blockchain.MBlocks_NV.contains(block)){
                            System.out.println("GOT NEW BLOCK: "+ block);
                            Blockchain.MBlocks_NV.add(block);
                            //Blockchain.BlockChain.add(block);
                        }
                    }
                }






                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                serverSocket.close();
            }

        } catch (Exception ex) {
            System.out.println(Settings.RED + ex);
        }

    }

    public static void Pack_ME(){
        Package_Blocks package_blocks1 = new Package_Blocks();
        package_blocks1.blockchain = Blockchain.BlockChain;
        package_blocks1.Newly_MinedBlocks = Blockchain.MBlocks_NV;
        package_blocks1.Newly_CreatedTransactions = Blockchain.Mine_Transactions;
        package_blocks = package_blocks1;
        return;
    }


}
