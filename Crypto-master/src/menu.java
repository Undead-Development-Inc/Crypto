import jdk.jshell.EvalException;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Scanner;

public class menu {

    public static ArrayList<String> OPTIONS = new ArrayList<>();

    public void Menu() throws Exception {
        OPTIONS_SETUP();
        GUI();
        Scanner scanner_input = new Scanner(System.in);
        String request = scanner_input.nextLine();
        /////IF STATEMENTS FOR MENU!!
        if(request.matches(OPTIONS.get(0))){
            //THIS CREATE NEW WALLET
            new Functions_MENU().Create_Wallet();
            new menu().Menu();
        }
        if(request.matches(OPTIONS.get(1))){
            //THIS STARTS MINING
            if(Functions_MENU.wallets.isEmpty()){
                System.out.println("NO WALLETS!!!");
                new menu().Menu();
            }else {
                System.out.println(Settings.RED + "Times?: ");
                Scanner scanner = new Scanner(System.in);
                int times = scanner.nextInt();
                for(int i = 0; i <= times; i++){
                    new Mine().mine(Functions_MENU.wallets.get(0).publicKey);
                }
                new menu().Menu();
            }
        }
        if(request.matches(OPTIONS.get(2))){
            ///THIS FUNCTION SENDS FUNDS FROM YOUR WALLET
            int x = 1;
            System.out.println("Please Enter the number from the list below to with wallet to send from: ");
            for(Wallet wallet: Functions_MENU.wallets){
                System.out.println("Type "+ x + " For this wallet: "+ wallet);
                x++;
            }
            Scanner scanner = new Scanner(System.in);
            int wallet_op = scanner.nextInt();
            if(Functions_MENU.wallets.size() >= wallet_op){
                Wallet wallet = Functions_MENU.wallets.get(wallet_op -1);
                System.out.println("ENTER TOO WALLET ADDRESS: ");
                Scanner scanner1 = new Scanner(System.in);
                String Send_Addr = scanner1.nextLine();
                System.out.println("VALUE: ");
                Scanner scanner2 = new Scanner(System.in);
                Float value = scanner2.nextFloat();
                if(!Send_Addr.isEmpty()){
                    wallet.Send_Funds(wallet, Send_Addr, value);
                    new menu().Menu();
                }else {
                    System.out.println("CANNOT SEND TO NO ADDRESS!!!!!");
                    new menu().Menu();
                }
            }else {
                System.out.println("ERROR THE NUMBER YOU TYPED IN IS INCORRECT!!!");
                new menu().Menu();
            }
        }
        if(request.matches(OPTIONS.get(3))){
            int x = 1;
            System.out.println("Please Enter the number from the list below to with wallet to send from: ");
            for(Wallet wallet: Functions_MENU.wallets){
                System.out.println("Type "+ x + " For this wallet: "+ wallet);
                x++;
            }
            Scanner scanner = new Scanner(System.in);
            int wallet_op = scanner.nextInt();
            if(Functions_MENU.wallets.size() >= wallet_op){
                Wallet wallet = Functions_MENU.wallets.get(wallet_op -1);
                System.out.println("WALLET " + wallet + " Balance is "+ wallet.Balance(wallet));


            }else {
                System.out.println("ERROR THE NUMBER YOU TYPED IN IS INCORRECT!!!");
                new menu().Menu();
            }
        }
        new menu().Menu();
    }





    public void GUI(){
        System.out.println(Settings.PURPLE +"BlockChain Size: " + Blockchain.BlockChain.size());
        System.out.println(Settings.RED_BACKGROUND + Settings.WHITE + "\n \n OPTIONS: \n \n");
        System.out.println("TYPE THE OPTIONS BELOW FOR WHAT YOU WANT: \n \n");
        System.out.println("Create_Wallet: --TO CREATE A NEW WALLET");
        System.out.println("New_Mine: ---TO START MINING!! MUST HAVE WALLET CREATED!!");
        System.out.println("SEND_FUND: ---TO SEND FUNDS");
        System.out.println("WALL_BAL: --TO CHECK WALLET BALANCE");

        System.out.println(Settings.RED+ "-------------------------------------");
        System.out.println("ENTER OPTION: ");

        return;
    }
    public void OPTIONS_SETUP (){
        OPTIONS.add("Create_Wallet"); //#0
        OPTIONS.add("New_Mine"); //#1
        OPTIONS.add("SEND_FUND"); //#2
        OPTIONS.add("WALL_BAL"); //#3
        return;
    }
}
