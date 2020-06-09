import java.io.ByteArrayInputStream;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;

/**
 * A class that processes incoming commands
 */
public class MessageHandling {
    static HashSet<SocketAddress> UserNumber = new HashSet<java.net.SocketAddress>();
    static Queue<StudyGroup> StudyGroupPriorityQueue = new PriorityQueue<StudyGroup>();
    private static final SerializationManager<Information> serializationManager = new SerializationManager<>();
    static ArrayList<User> UserList = new ArrayList<>();

    public static void AcceptedFile(byte[] buffer) throws Exception {
        Information information = serializationManager.readObject(buffer);
        if (UserNumber.contains(Server.datagramPacket.getSocketAddress()) == false ) {
            Logger.login(Level.INFO, "Подключается новый клиент с адресом: "+Server.datagramPacket.getSocketAddress());
            UserNumber.add(Server.datagramPacket.getSocketAddress());
            User user = new User();
            user.number = Server.datagramPacket.getSocketAddress();
            UserList.add(user);
        }else Logger.login(Level.INFO, "Пришел запрос от клиента с адресом: "+Server.datagramPacket.getSocketAddress());
        if (information.cmdtype.equalsIgnoreCase("file")) {
            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(information.file);
            XMLReader.main(fileInputStream);
        }
    }

    public static void Handling(byte[] buffer) throws Exception {
        //   StudyGroupPriorityQueue = XMLReader.StudyGroupPriorityQueue;
        Information information = serializationManager.readObject(buffer);

                if (information.cmdtype.equalsIgnoreCase("help")) {
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой help");
                    AllCmd.help();
                }
                if (information.cmdtype.equalsIgnoreCase("show")) {
                    AllCmd.show(StudyGroupPriorityQueue);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой show");
                }
                if (information.cmdtype.equalsIgnoreCase("info")) {
                    AllCmd.info(StudyGroupPriorityQueue);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой info");
                }
                if (information.cmdtype.equalsIgnoreCase("add")) {
                    AllCmd.add(information.name, information.count, information.exp, information.form, information.semestr, information.groupAdmin, information.height, information.weight, information.eyeColor, information.X, information.Y, StudyGroupPriorityQueue);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой add");
                }
                if (information.cmdtype.equalsIgnoreCase("update")) {
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой update");
                    AllCmd.update(StudyGroupPriorityQueue, information.idstr, information.name, information.count, information.exp, information.form, information.semestr, information.groupAdmin, information.height, information.weight, information.eyeColor, information.X, information.Y);
                }
                if (information.cmdtype.equalsIgnoreCase("remove_by_id")) {
                    AllCmd.remove_by_id(StudyGroupPriorityQueue, information.idstr);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_by_id");
                }
                if (information.cmdtype.equalsIgnoreCase("clear")) {
                    AllCmd.clear(StudyGroupPriorityQueue);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой clear");
                }
                if (information.cmdtype.equalsIgnoreCase("head")) {
                    AllCmd.head(StudyGroupPriorityQueue);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой head");
                }
                if (information.cmdtype.equalsIgnoreCase("remove_head")) {
                    AllCmd.remove_head(StudyGroupPriorityQueue);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_head");
                }
                if (information.cmdtype.equalsIgnoreCase("remove_lover")) {
                    AllCmd.remove_lover(StudyGroupPriorityQueue, Long.parseLong(information.count));
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_lower");
                }
                if (information.cmdtype.equalsIgnoreCase("remove_any_by_form_of_education")) {
                    AllCmd.remove_any_by_form_of_education(StudyGroupPriorityQueue, information.form);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_any_by_form_of_education");
                }
                if (information.cmdtype.equalsIgnoreCase("filter_starts_with_name")) {
                    AllCmd.filter_starts_with_name(StudyGroupPriorityQueue, information.name);
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой filter_starts_with_name");
                }
                if (information.cmdtype.equalsIgnoreCase("filter_greater_than_students_count")) {
                    AllCmd.filter_greater_than_students_count(StudyGroupPriorityQueue, Long.parseLong(information.count));
                    Logger.login(Level.INFO, "Принимаем пакет с коммандой filter_greater_than_students_count");
                }
                if (information.cmdtype.equalsIgnoreCase("file")) {
                    AllCmd.file();
                }
                if (information.cmdtype.equalsIgnoreCase("exit")){
                    AllCmd.save(StudyGroupPriorityQueue);
                    Logger.login(Level.INFO,"Сохраняем изменения");
                    AllCmd.exit();
                }
                if (information.cmdtype.equalsIgnoreCase("connect")){
                   if (StudyGroupPriorityQueue.stream().count()==0){
                       AllCmd.answerr.wrong=-1;
                   }
                   else {
                       AllCmd.answerr.wrong=0;
                       AllCmd.answerr.answer="Коллекция уже существует, введите команду";
                   }
                }
            }
        }


