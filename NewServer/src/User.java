import java.net.SocketAddress;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class for information about users
 */
public class User {
    SocketAddress number;
    Queue<StudyGroup> StudyGroup=new PriorityQueue();

}
