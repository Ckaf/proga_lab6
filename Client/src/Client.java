import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private static final SerializationManager<information> commandSerializationManager = new SerializationManager<>();
    private static final SerializationManager<information> responseSerializationManager = new SerializationManager<>();
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

   /* public void run(String host, int port, information information) throws IOException, ClassNotFoundException {
        connect(host, port);
        buffer = ByteBuffer.allocate(2048);
        input = new ByteArrayInputStream(buffer.array());
        ByteChannel byteChannel = null;
       byteChannel.read(buffer);
        buffer.clear();
        ObjectInputStream objectInputStream = new ObjectInputStream(input);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream) byteChannel);
        while (channel.isConnected()) {
            objectOutputStream.writeObject(information);
            byteChannel.write(buffer);
            channel.send(buffer, address);
            buffer.clear();
            address = channel.receive(buffer);
            buffer= (ByteBuffer) objectInputStream.readObject();
            System.out.println(buffer);
        }
  }
  */
    public ByteBuffer getBuffer(){return buffer;}

    public static void run(information information) {
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

            String result = responseSerializationManager.readObject(answerInBytes).getAnswer();
            System.out.println("Получен ответ от сервера: ");
            System.out.print(result);

          /*  if (command instanceof CommandExit) {
                command.execute();
            }

           */
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}