import java.net.SocketAddress;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class for information about users
 */
public class User {
    String pass;
    String login;
    SocketAddress number;
    Queue<StudyGroup> StudyGroup=new PriorityQueue();

}
