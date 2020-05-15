import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Queue;

/**
 * Writes the result to a file
 */
public class XMLWriter {


    public static void write(Queue<StudyGroup> StudyGroupPriorityQueue, String path) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            // создавать xml-файл
            Document doc = builder.newDocument();
            // создаем корневой элемент
            Element rootElement =
                    doc.createElement("StudyGroup");
            // добавляем корневой элемент в объект Document
            doc.appendChild(rootElement);

            // добавляем первый дочерний элемент к корневому
            for (StudyGroup student : StudyGroupPriorityQueue)
                rootElement.appendChild(getStudent(doc, student.getId(), student.getName(), student.getStudentsCount(), student.getCoordinatesX(), student.getCoordinatesY(), student.getexp(), student.getFormOfEducation()
                        , student.getName(), student.getSemesterEnum(), student.getHeight(), student.getWeight(), student.getColor()));


            //создаем объект TransformerFactory для печати
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //печатаем в файл
            OutputStream out=new FileOutputStream(path);

            try {
                Writer outputStreamWriter = new OutputStreamWriter(out);
                StreamResult file = new StreamResult(outputStreamWriter);

                //записываем данные
                transformer.transform(source, file);
                out.close();
                System.out.println("Создание XML файла закончено");
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e){
            System.out.println("Сохранение не удалось");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // метод для создания нового узла XML-файла
    private static Node getStudent(Document doc, Integer id, String name, long count, Float X, Double Y, String exp, Enum.FormOfEducation form, String groupAdmin, Enum.Semester semester, Double height, Integer weight, Enum.Color eyeColor) {
        Element StudyGroup = doc.createElement("Student");

        // устанавливаем атрибуты
        StudyGroup.setAttribute("id", String.valueOf(id));
        StudyGroup.setAttribute("count", String.valueOf(count));
        StudyGroup.setAttribute("name", name);
        StudyGroup.setAttribute("exp", exp);
        StudyGroup.setAttribute("form", String.valueOf(form));
        StudyGroup.setAttribute("semester", String.valueOf(semester));
        StudyGroup.setAttribute("groupAdmin", groupAdmin);
        StudyGroup.setAttribute("height", String.valueOf(height));
        StudyGroup.setAttribute("weight", String.valueOf(weight));
        StudyGroup.setAttribute("eyeColor", String.valueOf(eyeColor));
        StudyGroup.setAttribute("X", String.valueOf(X));
        StudyGroup.setAttribute("Y", String.valueOf(Y));

        return StudyGroup;
    }

}
