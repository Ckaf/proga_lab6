

//import clientServer.RunnableArgs;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {
    private DatagramSocket server;
    private boolean running;
    private ByteBuffer buffersend;
    private int port=8000;
    static final int DEFAULT_BUFFER_SIZE = 65536;
    static final SerializationManager<Answer> serializationManagerAnswer = new SerializationManager<Answer>();
    static final SerializationManager<Information> serializationManager = new SerializationManager<Information>();
    private static final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    static SocketAddress address;
    static DatagramChannel channel;

    public static void connect(int port) {

        address = new InetSocketAddress(port);
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(address);
        } catch (IOException e) {
            //   throw new ConnectionError();
        }
    }

    public static void run() throws Exception {
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                do {
                    address = channel.receive(byteBuffer);
                } while (address == null) ;
               // Answer answerr=new Answer();

                MessageHandling.AcceptedFile(buffer);
                MessageHandling.Handling(buffer);
                String response= String.valueOf(AllCmd.getAnswer());
               // System.out.println(response.answer);
               // System.out.println();
                Answer resp=new Answer();
                resp.setAnswer(response);
                byte[] answer = serializationManagerAnswer.writeObject(resp);
             //   System.out.println(answerr.getAnswer());
            //    System.out.println(answer);
                byteBuffer = ByteBuffer.wrap(answer);
                channel.send(byteBuffer, address);
                byteBuffer.clear();
            }
        } catch (ClassNotFoundException | IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}

