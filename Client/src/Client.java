import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private static final SerializationManager<Information> commandSerializationManager = new SerializationManager<>();
    private static final SerializationManager<Answer> responseSerializationManager = new SerializationManager<>();
    private static ByteBuffer buffer;
    private static int BUFFER_SIZE = 65536;
    private static final int TIMEOUT = 5000;
    static SocketAddress address;
    static DatagramChannel channel;
    static int flag;
    public Client() {
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();
    }

    public static void connect(String host, int port) {
        address = new InetSocketAddress(host, port);
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(address);
            Information information = new Information();
            information.cmdtype = "connect";
            run(information);
        } catch (IOException e) {
            System.out.println("Ошибка подключения");
        }
    }


    public static void run(Information information) {
        try {
            byte[] commandInBytes = commandSerializationManager.writeObject(information);
            buffer = ByteBuffer.wrap(commandInBytes);
            channel.send(buffer, address);
            buffer.clear();

            byte[] answerInBytes = new byte[BUFFER_SIZE];
            System.out.println("Запрос отправлен на сервер...");
            buffer = ByteBuffer.wrap(answerInBytes);
            address = null;
            do {
                try {

                    try {
                        address = channel.receive(buffer);
                    } catch (PortUnreachableException e) {
                        System.out.println("Сервер недоступен");
                        System.exit(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (address == null);
            Answer result = new Answer();
            result = responseSerializationManager.readObject(answerInBytes);
            if (result.wrong == -1) flag = 1;
            else {
                if (result.wrong != 2) {
                    System.out.println("Получен ответ от сервера: ");
                    System.out.print(result.getAnswer());
                    System.out.println();
                    buffer.clear();
                } else {
                    System.out.println(result.answer);
                    Saver.save(MainC.FILENAME, result);
                }
            }
            try {
                if (result.getWrong() == 1) System.exit(0);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}