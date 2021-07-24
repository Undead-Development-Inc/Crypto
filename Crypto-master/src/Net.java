import jdk.jshell.spi.ExecutionControlProvider;

import javax.swing.plaf.synth.SynthDesktopIconUI;
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
    public static ArrayList<String> IPDNS_SYNC_NODES = new ArrayList<>();

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



                if(req.matches("PUSH_MBLOCK")){
                    Block New_Blocks = (Block) objectInputStream.readObject();
                    System.out.println("GOT NEW BLOCK: "+ New_Blocks);
                    Blockchain.MBlocks_NV.add(New_Blocks);
                    System.out.println("BLOCK-SIZE: "+ Blockchain.MBlocks_NV.size());
                    if(!Blockchain.MBlocks_NV.contains(New_Blocks)){
                        throw new Exception("BLOCK NOT FOUND!!!");
                    }
                    Blockchain.Add_To_Chain();//
//                    Blockchain.BlockChain.add(New_Blocks);
                    objectInputStream.close();
                    objectOutputStream.close();
                }

                if(req.matches("PUSH_N_TRANSACTIONS")){
                    ArrayList<Transaction> New_Transactions = (ArrayList<Transaction>) objectInputStream.readObject();
                    for(Transaction transaction: New_Transactions){
                        if(!Blockchain.Mine_Transactions.contains(transaction)){
                            System.out.println("ADDED NEW TRANSACTION: "+ transaction);
                            Blockchain.Mine_Transactions.add(transaction);
                        }
                    }
                }

                if(req.matches("Wallet")){
                    if(Blockchain.Net_Wallets.size() == 0){
                        Wallet newwallet = new Wallet();
                        newwallet = new Wallet();
                        Blockchain.Net_Wallets.add(newwallet);
                        objectOutputStream.writeObject(newwallet);
                        System.out.println("MADE AND SENT NEW WALLET");
                    }else {
                        objectOutputStream.writeObject(Blockchain.Net_Wallets.get(0));
                        System.out.println("SEND WALLET");
                    }
                }

                if(req.matches("Get_Balance")){
                    Wallet wallet = (Wallet) objectInputStream.readObject();
                    objectOutputStream.writeObject(wallet.Balance(wallet));
                    System.out.println("RETURNED BALLET BALANCE");
                }

                if(req.matches("Get_Difficulty")){
                    objectOutputStream.writeObject(new Difficulty().difficulty());
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

    public static void Network_Sync(){
        Pack_ME();
        try{
            for(String ip : IPDNS_SYNC_NODES){
                Socket socket = new Socket(ip, 9000);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//CREATES OUTPUT STREAM
                System.out.println("SEND UPDATE TO DNS NODE: "+ socket.getInetAddress());
                objectOutputStream.writeObject(package_blocks);
            }
        }catch (Exception ex){

        }
    }
    public static void Master_Info(){
        while(true) {
            try {
                ServerSocket serverSocket = new ServerSocket(900);
                Socket socket = serverSocket.accept();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                objectOutputStream.writeObject(FreeDomCrypto.Ver);

                String Res = (String) objectInputStream.readObject();

                if (Res.matches("good")) {
                    System.out.println("VER MATCHES!!");
                } else {
                    System.out.println("OLD VER DEACTIVATING");
                    throw new Exception("OLD VER DEACTIVATING");
                }

                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                serverSocket.close();
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
    }
    public static void Get_Master_NETINFO(){
        try{
            Socket socket = new Socket("20.151.118.19", 10000);
            socket.setSoTimeout(60);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(FreeDomCrypto.Ver);
            objectOutputStream.writeObject(FreeDomCrypto.Ver);

            int result = (int) objectInputStream.readObject();
            Boolean isempty = (Boolean) objectInputStream.readObject();

            if(!isempty) {
                if (result == 1) {
                    ArrayList<String> IPs = (ArrayList<String>) objectInputStream.readObject();
                    for (String ip : IPs) {
                        System.out.println("GOT MASTER IP: " + ip);
                        LoadBalancer_Control.Masters.add(ip);
                    }
                } else {
                    System.out.println("OUT OF DATE MASTER!!!");
                    throw new Exception("OUT OF DATE MASTER");
                }
            }


        }catch (Exception ex){
            System.out.println("ex: "+ ex);
        }
    }




}
