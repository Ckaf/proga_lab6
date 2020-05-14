import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SocketC implements Serializable {

    private static DatagramSocket datagramSocket;
    SocketC() throws UnknownHostException, IOException {
        int portS = 0;
        int pornC = 0;
        InetAddress ipAdress = InetAddress.getByName("se.ifmo.ru");
        try {
            SocketAddress socketAddress=new InetSocketAddress(ipAdress,portS);
            DatagramChannel datagramChannel=DatagramChannel.open();
            datagramChannel.connect(socketAddress);
          //  datagramSocket = new DatagramSocket(portS,ipAdress);
           // datagramSocket.connect(ipAdress, portS);
        } catch (Exception e) {
            System.out.println("Соединение не удалось");
        }
    }

    public static void send(information information) throws IOException {
        String path =null;
                //"C:\\Users\\dns\\Desktop\\ooo.txt";
        int portS = 0;
        InetAddress ipAdress = InetAddress.getByName("se.ifmo.ru");
        DatagramChannel datagramChannel=DatagramChannel.open();
        SocketAddress socketAddress=new InetSocketAddress(ipAdress,portS);
        datagramChannel.connect(socketAddress);
        FileOutputStream fileOutputStream=new FileOutputStream(path);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(information);
        byte b[]=information.cmdtype.getBytes();
        DatagramPacket datagramPacket=new DatagramPacket(b,b.length,ipAdress,portS);
        datagramChannel.send(ByteBuffer.wrap(b),socketAddress);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

}


