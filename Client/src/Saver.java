import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Saver {
    public static void save(String path, Answer answer) throws IOException {
      //  path = "C:\\Users\\dns\\Desktop\\list.txt";
        FileOutputStream fos = new FileOutputStream(new File(path));
        fos.write(answer.file);
        System.out.println("Файл сохранен");
    }
}
