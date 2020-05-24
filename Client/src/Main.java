import java.util.Random;

public class Main {
    static int i=0;
    public static void main(String[] args) throws Exception {

        Random random=new Random();
        i=random.nextInt()*100;
        MainC.main(i);
    }
}
