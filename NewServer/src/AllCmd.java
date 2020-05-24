import java.io.*;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;


/**
 * This class describes how commands work
 **/
public class AllCmd {
    private static int BUFFER_SIZE = 2048;
    static String answer;
    static Answer answerr = new Answer();

    public static void help() throws SocketException {
        answer = "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                " exit : завершить программу (без сохранения в файл)\n" +
                "head : вывести первый элемент коллекции\n" +
                "remove_head : вывести первый элемент коллекции и удалить его\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "remove_any_by_form_of_education formOfEducation : удалить из коллекции один элемент, значение поля formOfEducation которого эквивалентно заданному\n" +
                "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки\n" +
                "filter_greater_than_students_count studentsCount : вывести элементы, значение поля studentsCount которых больше заданного)";
        answerr.setAnswer(answer);
    }

    public static void info(Queue<StudyGroup> StudyGroupPriorityQueue) throws SocketException {
        int i = 0;
        for (StudyGroup student : StudyGroupPriorityQueue) i++;

        answer = "тип коллекции:PriorityQueue " + "кол-во элементов: " + i + " дата инициализации: " + StudyGroupPriorityQueue.peek().getCreationDate();
        answerr.setAnswer(answer);


    }

    public static void show(Queue<StudyGroup> StudyGroupPriorityQueue) throws SocketException {
        answer = "";
        for (StudyGroup student : StudyGroupPriorityQueue) {
            String answer1 = "Имя: " + student.getName() + " Номер:" + student.getStudentsCount() + " " + student.getexp() + " Форма обучения: " + student.getFormOfEducation() + " Id: " + student.getId() + " Имя админа: " + student.getAdminName()
                    + " Рост админа: " + student.getHeight() + " Вес админа: " + student.getWeight() + " Цвет глаз админа: " + student.getColor() + " Координата X: " + student.getCoordinatesX() + " Координата Y: " + student.getCoordinatesY() + "\n";
            answer = answer + answer1;
        }
        answerr.setAnswer(answer);
    }

    public static void add(String name, String count, String exp, String form, String semestr, String groupAdmin, String height, String weight, String eyeColor, String X, String Y, Queue<StudyGroup> StudyGroupPriorityQueue) throws Exception {
        StudyGroupPriorityQueue.add(new StudyGroup(StudyGroupPriorityQueue, name, count, exp, form, semestr, groupAdmin, height, weight, eyeColor, X, Y));
    answer="Элемент добавлен";
    answerr.setAnswer(answer);
    }

    public static void update(Queue<StudyGroup> StudyGroupPriorityQueue, String idstr, String name, String count, String exp, String form, String semestr, String groupAdmin, String height, String weight, String eyeColor, String X, String Y) throws Exception {
        Integer id = Integer.parseInt(idstr);
        Person person = new Person(groupAdmin, height, weight, eyeColor);
        Coordinates coordinates = new Coordinates(X, Y);
        for (StudyGroup student : StudyGroupPriorityQueue) {


            if (id.equals(student.getId())) {
                student.setName(name);
                student.setStudentsCount(Long.parseLong(count));
                student.setExp(exp);
                student.setFormOfEducation(form);
                student.setSemesterEnum(semestr);
                person.setAdminName(groupAdmin);
                person.setHeight(height);
                person.setWeight(weight);
                person.setEyeColor(eyeColor);
                coordinates.setX(X);
                coordinates.setY(Y);
                answer = "Данные обновлены";
                answerr.setAnswer(answer);
            }

            if (StudyGroupPriorityQueue == null) break;
        }
    }


    public static void remove_by_id(Queue<StudyGroup> StudyGroupPriorityQueue, String idstr) {
        idstr.trim();
        Integer id = Integer.valueOf(idstr);
        Iterator<StudyGroup> iterator = StudyGroupPriorityQueue.iterator();
        while (iterator.hasNext())
            if (id.equals(iterator.next().getId())) iterator.remove();
            answer="";
            answerr.setAnswer(answer);
    }

    public static void clear(Queue<StudyGroup> StudyGroupPriorityQueue) {
        int i = 0;
        for (StudyGroup student : StudyGroupPriorityQueue) i++;
        for (int i1 = 0; i1 < i; i1++)
            StudyGroupPriorityQueue.remove(StudyGroupPriorityQueue.iterator().next());
        answer="";
        answerr.setAnswer(answer);
    }

    public static void save(Queue<StudyGroup> StudyGroupPriorityQueue) throws FileNotFoundException {
        XMLWriter.write(StudyGroupPriorityQueue, XMLReader.FILENAME);
    }

    public static void execute_script(Queue<StudyGroup> StudyGroupPriorityQueue, String file_name) throws IOException {
        FileInputStream fileInputStream = null;
        while (true) {
            try {
                fileInputStream = new FileInputStream(file_name);
                break;
            } catch (FileNotFoundException e) {
                if (Files.exists(Paths.get(file_name)) && (Files.isReadable(Paths.get(file_name)) == false))
                    System.out.println("Ошибка доступа");
                else System.out.println("Файл не найден, введите имя еще раз");
                Scanner scanner = new Scanner(System.in);
                file_name = scanner.nextLine();
                file_name = file_name.trim();
                if (file_name.equalsIgnoreCase("exit")) System.exit(0);
            }
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String cmd = s;
            cmd = cmd.trim();
            if (cmd.equalsIgnoreCase("exit") == true) break;

            if (cmd.equalsIgnoreCase("help") == true) {
                AllCmd.help();
            }

            if (cmd.equalsIgnoreCase("info") == true) {
                AllCmd.info(StudyGroupPriorityQueue);
            }
            if (cmd.lastIndexOf("add") != -1) {
                cmd.replace(" ", "");
                String substr[] = new String[12];
                Scanner scanner = new Scanner(System.in);

                System.out.println("Введите имя");
                substr[0] = scanner.nextLine();
                System.out.println("Введите номер");

                substr[1] = scanner.nextLine();
                long check;
                while (true) {
                    try {
                        check = Long.parseLong(substr[1]);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("неправильный ввод,попробуем еще раз");
                        substr[1] = scanner.nextLine();
                    }
                }

                System.out.println("Введите отчислен ли студент(yes/no)");
                substr[2] = scanner.nextLine();
                substr[2] = substr[2].trim();
                while (true) {
                    if (substr[2].equalsIgnoreCase("yes")) break;
                    else {
                        if (substr[2].equalsIgnoreCase("no")) break;
                        else System.out.println("неправильный ввод,попробуем еще раз");
                        substr[2] = scanner.nextLine();
                    }
                }
                System.out.println("Введите форму обучения(full time, evening, distance)");
                substr[3] = scanner.nextLine();
                substr[3] = substr[3].trim();
                while (true) {
                    if (substr[3].equalsIgnoreCase("full time")) break;
                    else {
                        if (substr[3].equalsIgnoreCase("evening")) break;
                        else {
                            if (substr[3].equalsIgnoreCase("distance")) break;
                            else System.out.println("неправильный ввод,попробуем еще раз");
                            substr[3] = scanner.nextLine();
                        }
                    }
                }
                System.out.println("Введите номер семестра");
                substr[4] = scanner.nextLine();
                substr[4] = substr[4].trim();
                while (true) {
                    try {
                        int check1 = Integer.parseInt(substr[4]);
                        if (check1 >= 5 & check1 <= 8) break;
                        else {
                            System.out.println("неправильный ввод,попробуем еще раз");
                            substr[4] = scanner.nextLine();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("неправильный ввод,попробуем еще раз");
                        substr[4] = scanner.nextLine();
                    }
                }
                System.out.println("Введите имя админа группы");
                substr[5] = scanner.nextLine();
                System.out.println("Введите высоту");
                substr[6] = scanner.nextLine();
                substr[6] = substr[6].trim();
                while (true) {
                    try {
                        Double check2 = Double.parseDouble(substr[6]);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("неправильный ввод,попробуем еще раз");
                        substr[6] = scanner.nextLine();
                    }
                }
                System.out.println("Введите вес");
                substr[7] = scanner.nextLine();
                while (true) {
                    try {
                        Integer check3 = Integer.parseInt(substr[7]);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("неправильный ввод,попробуем еще раз");
                        substr[7] = scanner.nextLine();
                    }
                }
                System.out.println("Введите цвет глаз(red, black, brown, orange)");
                substr[8] = scanner.nextLine();
                substr[8].trim();
                while (true) {
                    if (substr[8].equalsIgnoreCase("red")) break;
                    else {
                        if (substr[8].equalsIgnoreCase("black")) break;
                        else {
                            if (substr[8].equalsIgnoreCase("brown")) break;
                            else {
                                if (substr[8].equalsIgnoreCase("orange")) break;
                                else System.out.println("неправильный ввод,попробуем еще раз");
                                substr[8] = scanner.nextLine();
                            }
                        }
                    }
                }
                System.out.println("Введите X");
                substr[9] = scanner.nextLine();
                substr[9] = substr[9].trim();
                while (true) {
                    try {
                        Float check4 = Float.parseFloat(substr[9]);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("неправильный ввод,попробуем еще раз");
                        substr[9] = scanner.nextLine();
                    }
                }
                System.out.println("Введите Y");
                substr[10] = scanner.nextLine();
                substr[10] = substr[10].trim();
                while (true) {
                    try {
                        long check5 = Long.valueOf(substr[10]);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("неправильный ввод,попробуем еще раз");
                        substr[10] = scanner.nextLine();
                    }
                }

                try {
                    AllCmd.add(substr[0], substr[1], substr[2], substr[3], substr[4], substr[5], substr[6], substr[7], substr[8], substr[9], substr[10], StudyGroupPriorityQueue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (cmd.equalsIgnoreCase("show") == true) AllCmd.show(StudyGroupPriorityQueue);

            if (cmd.lastIndexOf("remove_by_id") != -1) {
                String id;
                int index1 = cmd.lastIndexOf(" ");
                id = cmd.substring(index1);
                AllCmd.remove_by_id(StudyGroupPriorityQueue, id);
            }

            if (cmd.equalsIgnoreCase("clear") == true) AllCmd.clear(StudyGroupPriorityQueue);

            if (cmd.equalsIgnoreCase("save") == true) {
                AllCmd.save(StudyGroupPriorityQueue);
            }

            if (cmd.equalsIgnoreCase("head") == true) AllCmd.head(StudyGroupPriorityQueue);

            if (cmd.equalsIgnoreCase("remove_head") == true) AllCmd.remove_head(StudyGroupPriorityQueue);

            if (cmd.lastIndexOf("update") != -1) {
                String id;
                int index1 = cmd.lastIndexOf(" ");
                id = cmd.substring(index1);
                System.out.println("Введите через запятую в нужном порядке следующие данные: имя, номер, отчислен ли студент(yes/no), форма обучения(full time, evening, distance), номер сместра(5,6,7,8), имя админа группы" +
                        ", высоту, вес, цвет глаз(red, black brown, orange), X, Y");
                String substr[];
                Scanner scanner = new Scanner(System.in);
                String string = scanner.toString();
                substr = string.split(",");
                try {
                    AllCmd.update(StudyGroupPriorityQueue, id, substr[0], substr[1], substr[3], substr[4], substr[5], substr[6], substr[7], substr[8], substr[9], substr[10], substr[11]);
                } catch (Exception e) {
                    System.out.println("Неправильный ввод данных");
                }
            }

            if (cmd.equalsIgnoreCase("remove_lower") == true) {
                System.out.println("Введите номер в списке (count)");
                Scanner scanner = new Scanner(System.in);
                long count = scanner.nextLong();
                for (StudyGroup student : StudyGroupPriorityQueue) {
                    if (student.getStudentsCount() == count) {
                        System.out.println("Хотите удалить все элементы меньшие чем:");
                        System.out.println("Имя: " + student.getName() + " Номер:" + student.getStudentsCount() + " " + student.getexp() + " Форма обучения: " + student.getFormOfEducation() + " Id: " + student.getId()
                                + " Рост админа: " + student.getHeight() + " Вес админа: " + student.getWeight() + " Цвет глаз админа: " + student.getColor() + " Координата X: " + student.getCoordinatesX() + " Координата Y: " + student.getCoordinatesY());
                        System.out.println("Введите yes\\no");
                        Scanner scanner2 = new Scanner(System.in);
                        String answer = scanner2.nextLine();
                        answer = answer.trim();
                        if (answer.equalsIgnoreCase("yes") == true) AllCmd.remove_lover(StudyGroupPriorityQueue, count);
                        else {
                            if (answer.equalsIgnoreCase("no") == true)
                                System.out.println("Ничего не удаляем");
                            else System.out.println("Что-то пошло не так, введите следующую команду");
                        }
                    }
                }
            }


            if (cmd.lastIndexOf("remove_any_by_form_of_education") != -1) {
                String form;
                int index1 = cmd.lastIndexOf(" ");
                form = cmd.substring(index1 + 1);
                form = form.trim();
                AllCmd.remove_any_by_form_of_education(StudyGroupPriorityQueue, form);
            }

            if (cmd.lastIndexOf("filter_starts_with_name") != -1) {
                String name;
                int index1 = cmd.lastIndexOf(" ");
                name = cmd.substring(index1 + 1);
                name = name.trim();
                AllCmd.filter_starts_with_name(StudyGroupPriorityQueue, name);
            }

            if (cmd.lastIndexOf("filter_greater_than_students_count") != -1) {
                String count;
                int index1 = cmd.lastIndexOf(" ");
                count = cmd.substring(index1 + 1);
                count = count.trim();
                long cou = 0;
                while (true) {
                    try {
                        cou = Long.parseLong(count);
                        break;
                    } catch (Exception e) {
                        System.out.println("Номер введен неправильно, попробуем еще раз");
                        Scanner scanner = new Scanner(System.in);
                        count = scanner.nextLine();
                        count = count.trim();
                    }
                }
                AllCmd.filter_greater_than_students_count(StudyGroupPriorityQueue, cou);
            }

            if (cmd.lastIndexOf("execute_script") != -1) {
                //  String path;
                int index1 = cmd.lastIndexOf(" ");
                file_name = cmd.substring(index1);
                System.out.println("Мы рискуем уйти в рекурсию, вы действительно хотите продолжить?(yes\\no)");
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String answer = scanner.nextLine();
                    answer.trim();
                    if (answer.equalsIgnoreCase("yes") == true) {
                        AllCmd.execute_script(StudyGroupPriorityQueue, file_name);
                        break;
                    } else {
                        if (answer.equalsIgnoreCase("no") == true) {
                            System.out.println("Хороший выбор");
                            break;
                        } else System.out.println("Введите ответ еще раз");
                    }
                }
            }
        }

    }


    public static void head(Queue<StudyGroup> StudyGroupPriorityQueue) throws SocketException {
        try {
            StudyGroup studyGroup = StudyGroupPriorityQueue.peek();
            answer = "Имя: " + studyGroup.getName() + " Номер:" + studyGroup.getStudentsCount() + " " + studyGroup.getexp() + " Форма обучения: " + studyGroup.getFormOfEducation() + " Id: " + studyGroup.getId()
                    + " Рост админа: " + studyGroup.getHeight() + " Вес админа: " + studyGroup.getWeight() + " Цвет глаз админа: " + studyGroup.getColor() + " Координата X: " + studyGroup.getCoordinatesX() + " Координата Y: " + studyGroup.getCoordinatesY();
            answerr.setAnswer(answer);
        } catch (NullPointerException e) {
            answer = "Нет здесь никакого первого элемента";
            answerr.setAnswer(answer);
        }
    }

    public static void remove_head(Queue<StudyGroup> StudyGroupPriorityQueue) {
        StudyGroup studyGroup = StudyGroupPriorityQueue.poll();
        answer="";
        answerr.setAnswer(answer);
    }

    public static void remove_lover(Queue<StudyGroup> student, long count) {
        Iterator<StudyGroup> iterator = student.iterator();
        while (iterator.hasNext()) {
            if (count < iterator.next().getStudentsCount()) {
                iterator.remove();
            }
        }
        answer="";
        answerr.setAnswer(answer);
    }


    public static void remove_any_by_form_of_education(Queue<StudyGroup> StudyGroupPriorityQueue, String form) {

        Iterator<StudyGroup> iterator = StudyGroupPriorityQueue.iterator();
        form = form.trim();
        while (iterator.hasNext()) {


            if (form.equalsIgnoreCase("time")) {
                if (iterator.next().getFormOfEducation().equals(Enum.FormOfEducation.FULL_TIME_EDUCATION)) {
                    iterator.remove();
                    break;
                }
            }
            if (form.equalsIgnoreCase("distance")) {
                if (iterator.next().getFormOfEducation().equals(Enum.FormOfEducation.DISTANCE_EDUCATION)) {
                    iterator.remove();
                    break;
                }
            }
            if (form.equalsIgnoreCase("evening")) {
                if (iterator.next().getFormOfEducation().equals(Enum.FormOfEducation.EVENING_CLASSES)) {
                    iterator.remove();
                    break;
                }
            }

        }
        answer="";
        answerr.setAnswer(answer);
    }

    public static void filter_starts_with_name(Queue<StudyGroup> StudyGroupPriorityQueue, String name) throws SocketException {
        for (StudyGroup student : StudyGroupPriorityQueue) {
            if (student.getName().indexOf(name) == 0) {
                answer = "Имя: " + student.getName() + " Номер:" + student.getStudentsCount() + " " + student.getexp() + " Форма обучения: " + student.getFormOfEducation() + " Id: " + student.getId()
                        + " Рост админа: " + student.getHeight() + " Вес админа: " + student.getWeight() + " Цвет глаз админа: " + student.getColor() + " Координата X: " + student.getCoordinatesX() + " Координата Y: " + student.getCoordinatesY();
                answerr.setAnswer(answer);
            }
        }

    }

    public static void filter_greater_than_students_count(Queue<StudyGroup> StudyGroupPriorityQueue, long count) throws SocketException {
        for (StudyGroup student : StudyGroupPriorityQueue) {
            if (student.getStudentsCount() > count) {
                answer = "Имя: " + student.getName() + " Номер:" + student.getStudentsCount() + " " + student.getexp() + " Форма обучения: " + student.getFormOfEducation() + " Id: " + student.getId()
                        + " Рост админа: " + student.getHeight() + " Вес админа: " + student.getWeight() + " Цвет глаз админа: " + student.getColor() + " Координата X: " + student.getCoordinatesX() + " Координата Y: " + student.getCoordinatesY();
                answerr.setAnswer(answer);

            }
        }

    }

    public static void file() {
        answer = "Файл передан";
        answerr.setAnswer(answer);
    }

}




