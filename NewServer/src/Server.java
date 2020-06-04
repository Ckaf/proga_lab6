import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

public class Server {
    private DatagramSocket server;
    private boolean running;
    private ByteBuffer buffersend;
    static int port;
    static final int DEFAULT_BUFFER_SIZE = 131072;
    static final SerializationManager<Answer> serializationManagerAnswer = new SerializationManager<Answer>();
    static final SerializationManager<Information> serializationManager = new SerializationManager<Information>();
    private static final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    private static SocketAddress address;
    static DatagramChannel channel;
    private static DatagramSocket datagramSocket;
    private static int flagg = 0;
    public static DatagramPacket datagramPacket;

    public static void connect(int port) {

        try {
            address = new InetSocketAddress(port);
            datagramSocket = new DatagramSocket(address);
            //datagramSocket.bind(address);
            Logger.login(Level.INFO, "Сервер начинает работу");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void run() throws Exception {
        while (true) {
            readServerCmd();
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                datagramSocket.receive(datagramPacket);
            } catch (SocketTimeoutException socketTimeoutException) {
                socketTimeoutException.printStackTrace();
            }
            MessageHandling.AcceptedFile(buffer);
            MessageHandling.Handling(buffer);

            try {
                byte[] commandInBytes = serializationManagerAnswer.writeObject(AllCmd.answerr);
                DatagramPacket datagramPacket1 = new DatagramPacket(commandInBytes, commandInBytes.length, datagramPacket.getSocketAddress());
                datagramSocket.send(datagramPacket1);
                AllCmd.answerr.wrong = 0;
                AllCmd.answerr.answer1 = null;
            } catch (IOException | ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readServerCmd() {
        try {
            if (System.in.available() > 0) {
                String[] line = new String[0];
                String line1;
                Scanner scanner = new Scanner(System.in);
                if ((line1 = scanner.nextLine()) != null) {
                    line = line1.trim().split(" ");
                    try {
                        if (line[0].equals("save")) {
                            for (int i = 0; i < MessageHandling.UserList.size(); i++) {
                                User user;
                                user = MessageHandling.UserList.get(i);
                                String string = String.valueOf(user.number);
                                if (line[1].trim().equals(string)) {
                                    AllCmd.save(user.StudyGroup);
                                    Logger.login(Level.INFO, "Файл успешно сохранен");
                                }
                            }
                        } else if (line[0].equals("exit")) {
                            Logger.login(Level.INFO, "exiting");
                            scanner.close();
                            System.exit(0);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        Logger.login(Level.WARNING, "Неправильно введена серверная команда");
                    }
                } else {
                    Logger.login(Level.INFO, "no such command");
                }
            }
        } catch (NoSuchElementException | IOException e) {

        }
    }

}