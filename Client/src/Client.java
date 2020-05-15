import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private static final SerializationManager<Information> commandSerializationManager = new SerializationManager<>();
    private static final SerializationManager<String> responseSerializationManager = new SerializationManager<>();
    DatagramChannel channel;
    private ByteBuffer buffer;
    private SocketAddress address;
    private static int BUFFER_SIZE = 65536;
    private static final int TIMEOUT = 5000;
    private ByteArrayInputStream input;
    private static SocketAddress socketAddress;
    private static DatagramSocket datagramSocket;

    public Client() {
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();
    }

    public static void connect(String host, int port) {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            System.out.println("Подключение к " + inetAddress);
            socketAddress = new InetSocketAddress(inetAddress, port);
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(socketAddress);
        } catch (UnknownHostException | SocketException e) {
            //throw new ConnectionError();
        }

    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public static void run(Information information) {
        try {
            byte[] commandInBytes = commandSerializationManager.writeObject(information);
            DatagramPacket datagramPacket = new DatagramPacket(commandInBytes, commandInBytes.length, socketAddress);
            datagramSocket.send(datagramPacket);
            System.out.println("Запрос отправлен на сервер...");
            byte[] answerInBytes = new byte[BUFFER_SIZE];
            datagramPacket = new DatagramPacket(answerInBytes, answerInBytes.length);
            datagramSocket.setSoTimeout(TIMEOUT);
            try {

                datagramSocket.receive(datagramPacket);
            } catch (SocketTimeoutException socketTimeoutException) {
                //     throw new TimeoutError();
            }

            Answer result = new Answer();
            String resultt = responseSerializationManager.readObject(answerInBytes);
            result.setAnswer(resultt);
            System.out.println("Получен ответ от сервера: ");
            System.out.print(result.getAnswer());

          /*  if (command instanceof CommandExit) {
                command.execute();
            }

           */
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}