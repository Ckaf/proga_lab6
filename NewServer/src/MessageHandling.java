import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;

public class MessageHandling {
    static HashSet<Integer> UserNumber = new HashSet<>();
    static Queue<StudyGroup> StudyGroupPriorityQueue = new PriorityQueue<StudyGroup>();
    private static final SerializationManager<Information> serializationManager = new SerializationManager<>();
    static ArrayList<User> UserList = new ArrayList<>();

    public static void AcceptedFile(byte[] buffer) throws Exception {
        Information information = serializationManager.readObject(buffer);
        if (UserNumber.contains(information.number) == false) {
            Logger.login(Level.INFO, "Подключается новый клиент с id: "+information.number);
            UserNumber.add(information.number);
            User user = new User();
            user.number = information.number;
            UserList.add(user);
        }else Logger.login(Level.INFO, "Пришел запрос от клиента с id: "+information.number);
        if (information.cmdtype.equalsIgnoreCase("file")) {
            FileInputStream fileInputStream = new FileInputStream(information.file);
            XMLReader.main(fileInputStream, information.number);
        }
    }

    public static void Handling(byte[] buffer) throws Exception {
        //   StudyGroupPriorityQueue = XMLReader.StudyGroupPriorityQueue;
        Information information = serializationManager.readObject(buffer);
        for (int i = 0; i < UserList.size(); i++) {
            User user = UserList.get(i);
            if (information.number == user.number) {
                StudyGroupPriorityQueue = user.StudyGroup;
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
                    UserList.set(i, user);
                }
            }
        }
    }

}

