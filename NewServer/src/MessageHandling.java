import java.io.*;
import java.nio.channels.ByteChannel;
import java.util.Queue;

public class MessageHandling {
    static Queue<StudyGroup> StudyGroupPriorityQueue;
    static ByteChannel byteChannel;

    private static final SerializationManager<Information> serializationManager = new SerializationManager<>();
    public static void AcceptedFile(byte[] buffer) throws Exception {
    Information information=serializationManager.readObject(buffer);

       // ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
       // ObjectInputStream obs = new ObjectInputStream(byteStream);
     //   ByteChannel byteChannel = null;
      //  byteChannel.read(ByteBuffer.wrap(buffer));
       // ObjectInputStream objectInputStream = new ObjectInputStream((InputStream) byteChannel);
        //information information = (information) objectInputStream.readObject();
        if (information.cmdtype.equalsIgnoreCase("file")) {
            FileInputStream fileInputStream = new FileInputStream(information.file);
            XMLReader.main(fileInputStream);
            System.out.println("файл передан");

        }
    }

    public static void Handling(byte[] buffer) throws Exception {
        StudyGroupPriorityQueue = XMLReader.StudyGroupPriorityQueue;
        System.out.println(StudyGroupPriorityQueue);
       // byteChannel.read(buffer);
        Information information=serializationManager.readObject(buffer);

        if (information.cmdtype.equalsIgnoreCase("help")) AllCmd.help();
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
        Answer answer = new Answer();
        answer.answer = AllCmd.answer;
    }

}

