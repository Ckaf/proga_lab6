package com.company;

//import clientServer.RunnableArgs;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {
    private DatagramSocket server;
    private boolean running;
    private ByteBuffer buffersend;
    private int port=4000;
    static final int DEFAULT_BUFFER_SIZE = 65536;
    static final SerializationManager<answer> responseSerializationManager = new SerializationManager<answer>();
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
                } while (address == null);
                answer answerr=new answer();
                MessageHandling.Handling(buffer);


                byte[] answer = responseSerializationManager.writeObject(answerr);
               // System.out.println(answer);
                byteBuffer = ByteBuffer.wrap(answer);
                channel.send(byteBuffer, address);
            }
        } catch (ClassNotFoundException | IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}

