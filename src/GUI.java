import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class GUI {

    public static void main(String[] args) throws InterruptedException {

        while(true){
            System.out.println("Hello");
            TimeUnit.SECONDS.sleep(1);
        }
    }



    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
