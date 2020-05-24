import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class MessageHandling {
    static HashSet<Integer> UserNumber=new HashSet<>();
    static Queue<StudyGroup> StudyGroupPriorityQueue=new PriorityQueue<StudyGroup>();
    private static final SerializationManager<Information> serializationManager = new SerializationManager<>();
    static ArrayList<User> UserList=new ArrayList<>();
    public static void AcceptedFile(byte[] buffer) throws Exception {
        Information information = serializationManager.readObject(buffer);
        if (UserNumber.contains(information.number) == false) {
        UserNumber.add(information.number);
        User user=new User();
        user.number=information.number;
        UserList.add(user);
        }
        if (information.cmdtype.equalsIgnoreCase("file")) {
            FileInputStream fileInputStream = new FileInputStream(information.file);
            XMLReader.main(fileInputStream,information.number);
        }
    }

    public static void Handling(byte[] buffer) throws Exception {
     //   StudyGroupPriorityQueue = XMLReader.StudyGroupPriorityQueue;
        Information information = serializationManager.readObject(buffer);
        for (int i = 0; i < UserList.size(); i++) {
            User user = UserList.get(i);
            if (information.number == user.number) {
                System.out.println(information.number);
                StudyGroupPriorityQueue=user.StudyGroup;
                if (information.cmdtype.equalsIgnoreCase("help")) AllCmd.help();
                if (information.cmdtype.equalsIgnoreCase("show")) AllCmd.show(StudyGroupPriorityQueue);
                if (information.cmdtype.equalsIgnoreCase("info")) AllCmd.info(StudyGroupPriorityQueue);
                if (information.cmdtype.equalsIgnoreCase("add"))
                    AllCmd.add(information.name, information.count, information.exp, information.form, information.semestr, information.groupAdmin, information.height, information.weight, information.eyeColor, information.X, information.Y, StudyGroupPriorityQueue);
                if (information.cmdtype.equalsIgnoreCase("update"))
                    AllCmd.update(StudyGroupPriorityQueue, information.idstr, information.name, information.count, information.exp, information.form, information.semestr, information.groupAdmin, information.height, information.weight, information.eyeColor, information.X, information.Y);
                if (information.cmdtype.equalsIgnoreCase("remove_by_id"))
                    AllCmd.remove_by_id(StudyGroupPriorityQueue, information.idstr);
                if (information.cmdtype.equalsIgnoreCase("clear")) AllCmd.clear(StudyGroupPriorityQueue);
                if (information.cmdtype.equalsIgnoreCase("head")) AllCmd.head(StudyGroupPriorityQueue);
                if (information.cmdtype.equalsIgnoreCase("remove_head")) AllCmd.remove_head(StudyGroupPriorityQueue);
                if (information.cmdtype.equalsIgnoreCase("remove_lover"))
                    AllCmd.remove_lover(StudyGroupPriorityQueue, Long.parseLong(information.count));
                if (information.cmdtype.equalsIgnoreCase("remove_any_by_form_of_education"))
                    AllCmd.remove_any_by_form_of_education(StudyGroupPriorityQueue, information.form);
                if (information.cmdtype.equalsIgnoreCase("filter_starts_with_name"))
                    AllCmd.filter_starts_with_name(StudyGroupPriorityQueue, information.name);
                if (information.cmdtype.equalsIgnoreCase("filter_greater_than_students_count"))
                    AllCmd.filter_greater_than_students_count(StudyGroupPriorityQueue, Long.parseLong(information.count));
                if (information.cmdtype.equalsIgnoreCase("file")) AllCmd.file();
                UserList.set(i,user);
            }
        }
    }

}

