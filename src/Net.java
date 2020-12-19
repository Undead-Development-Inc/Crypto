import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Net {

    private ServerSocket ss = null;

    public void main() {
        System.out.println("Main thread");
        new Thread(this::Server).start();
    }

    private void Server() {
        System.out.println("Inner Thread");
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

}
