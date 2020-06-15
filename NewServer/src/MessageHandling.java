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
    static Queue<StudyGroup> StudyGroupPriorityQueue = new PriorityQueue<StudyGroup>(XMLReader.countComparator);
    private static final SerializationManager<Information> serializationManager = new SerializationManager<>();
    static ArrayList<User> UserList = new ArrayList<>();

    public static void AcceptedFile(byte[] buffer) throws Exception {
        Answer answer = new Answer();
        Information information = serializationManager.readObject(buffer);
        if (UserNumber.contains(Server.datagramPacket.getSocketAddress()) == false) {
            Logger.login(Level.INFO, "Подключается новый клиент с адресом: " + Server.datagramPacket.getSocketAddress());
            UserNumber.add(Server.datagramPacket.getSocketAddress());
            User user = new User();
            user.number = Server.datagramPacket.getSocketAddress();
            if (Command.isExistingUser(information.login,information.pass).equals("NOTHING") && information.regtype.equalsIgnoreCase("reg")) {
                Logger.login(Level.INFO,"Зарегестрировался пользователь с логином: "+information.login);
                Command.registrationUser(information.login,information.pass);
                AllCmd.answerr.autorizatonflag = "regOk";
                UserList.add(user);
            } else if (information.regtype.equalsIgnoreCase("aut")) {
                if (!Command.isExistingUser(information.login,information.pass).equals("NOTHING")) {
                    Logger.login(Level.INFO,"Авторизировался пользователь с логином: "+information.login);
                    AllCmd.answerr.autorizatonflag = "autOk";
                }
                else  AllCmd.answerr.autorizatonflag = "fail";
            } else AllCmd.answerr.autorizatonflag = "fail";

        } else
            Logger.login(Level.INFO, "Пришел запрос от клиента с адресом: " + Server.datagramPacket.getSocketAddress());
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
            AllCmd.add(information.name, information.count, information.exp, information.form, information.semestr, information.groupAdmin, information.height, information.weight, information.eyeColor, information.X, information.Y, StudyGroupPriorityQueue,information);
            Logger.login(Level.INFO, "Принимаем пакет с коммандой add");
        }
        if (information.cmdtype.equalsIgnoreCase("update")) {
            Logger.login(Level.INFO, "Принимаем пакет с коммандой update");
            AllCmd.update(information);
        }
        if (information.cmdtype.equalsIgnoreCase("remove_by_id")) {
            AllCmd.remove_by_id(information);
            Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_by_id");
        }
        if (information.cmdtype.equalsIgnoreCase("clear")) {
            AllCmd.clear(information);
            Logger.login(Level.INFO, "Принимаем пакет с коммандой clear");
        }
        if (information.cmdtype.equalsIgnoreCase("head")) {
            AllCmd.head(StudyGroupPriorityQueue);
            Logger.login(Level.INFO, "Принимаем пакет с коммандой head");
        }
        if (information.cmdtype.equalsIgnoreCase("remove_head")) {
            AllCmd.remove_head(information);
            Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_head");
        }
        if (information.cmdtype.equalsIgnoreCase("remove_lover")) {
            AllCmd.remove_lover(information);
            Logger.login(Level.INFO, "Принимаем пакет с коммандой remove_lower");
        }
        if (information.cmdtype.equalsIgnoreCase("remove_any_by_form_of_education")) {
            AllCmd.remove_any_by_form_of_education(information);
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
        if (information.cmdtype.equalsIgnoreCase("exit")) {
            AllCmd.save(StudyGroupPriorityQueue);
            Logger.login(Level.INFO, "Сохраняем изменения");
            AllCmd.exit();
        }
        if (information.cmdtype.equalsIgnoreCase("connect")) {
                AllCmd.answerr.wrong = 0;
                try {
                    if (AllCmd.answerr.autorizatonflag.equals("regOk"))
                        AllCmd.answerr.answer = "Пользователь зарегестрирован";
                    if (AllCmd.answerr.autorizatonflag.equals("autOk"))
                        AllCmd.answerr.answer = "Пользователь авторизован";
                    if (AllCmd.answerr.autorizatonflag.equals("fail"))
                        AllCmd.answerr.answer="Ошибка авторизации";
                }
               catch (NullPointerException e){}
            }
        }
    }


