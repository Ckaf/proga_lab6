package com.company;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MessageHandling {
    static Queue<StudyGroup> StudyGroupPriorityQueue;
    static Server server = new Server();
    static int port=4000;
    public static void Handling(byte[] buffer) throws Exception {

        ByteChannel byteChannel = null;
        byteChannel.read(ByteBuffer.wrap(buffer));
        ObjectInputStream objectInputStream = new ObjectInputStream((InputStream) byteChannel);
        information information = (information) objectInputStream.readObject();
        if (information.cmdtype.equalsIgnoreCase("file")){
            FileInputStream fileInputStream=new FileInputStream(information.file);
            XMLReader.main(fileInputStream);
        }
        if (information.cmdtype.equalsIgnoreCase("help")) AllCmd.help();
        if (information.cmdtype.equalsIgnoreCase("info")) AllCmd.info(StudyGroupPriorityQueue);
        if (information.cmdtype.equalsIgnoreCase("add"))
            AllCmd.add(information.name, information.count, information.exp, information.form, information.semestr,information.groupAdmin,information.height,information.weight,information.eyeColor,information.X,information.Y,StudyGroupPriorityQueue);
        if (information.cmdtype.equalsIgnoreCase("update")) AllCmd.update(StudyGroupPriorityQueue,information.idstr,information.name, information.count, information.exp, information.form, information.semestr,information.groupAdmin,information.height,information.weight,information.eyeColor,information.X,information.Y);
        if (information.cmdtype.equalsIgnoreCase("remove_by_id")) AllCmd.remove_by_id(StudyGroupPriorityQueue,information.idstr);
        if (information.cmdtype.equalsIgnoreCase("clear")) AllCmd.clear(StudyGroupPriorityQueue);
        if (information.cmdtype.equalsIgnoreCase("head")) AllCmd.head(StudyGroupPriorityQueue);
        if (information.cmdtype.equalsIgnoreCase("remove_head")) AllCmd.remove_head(StudyGroupPriorityQueue);
        if (information.cmdtype.equalsIgnoreCase("remove_lover")) AllCmd.remove_lover(StudyGroupPriorityQueue, Long.parseLong(information.count));
        if (information.cmdtype.equalsIgnoreCase("remove_any_by_form_of_education")) AllCmd.remove_any_by_form_of_education(StudyGroupPriorityQueue, information.form);
        if (information.cmdtype.equalsIgnoreCase("filter_starts_with_name")) AllCmd.filter_starts_with_name(StudyGroupPriorityQueue,information.name);
        if (information.cmdtype.equalsIgnoreCase("filter_greater_than_students_count")) AllCmd.filter_greater_than_students_count(StudyGroupPriorityQueue, Long.parseLong(information.count));
        answer answer=new answer();
        answer.answer=AllCmd.answer;
    }

}

class information implements Serializable {
    String cmdtype;
    ArrayList arrayList = new ArrayList();
    PriorityQueue priorityQueue = new PriorityQueue();
    File file;
    String name;
    String count;
    String exp;
    String form;
    String semestr;
    String groupAdmin;
    String height;
    String weight;
    String eyeColor;
    String X;
    String Y;
    String idstr;

}
class answer implements Serializable{
    String answer;
}
