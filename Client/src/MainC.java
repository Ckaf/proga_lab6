import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The class processes input data
 */
public class MainC {
    public static String FILENAME;

    public static void main() throws Exception {
        String host = "localhost";
        int port = 4000;
        Client client = new Client();
        client.connect(host, port);
        System.out.println("Введите имя файла");
        Scanner scanner = new Scanner(System.in);
        FILENAME = scanner.nextLine();
        FileInputStream fileInputStream = null;

        while (true) {
            try {

                if (Files.exists(Paths.get(FILENAME)) && Files.isReadable(Paths.get(FILENAME)) && Files.isWritable(Paths.get(FILENAME))) {
                    fileInputStream = new FileInputStream(FILENAME);
                    break;
                } else {
                    if (Files.exists(Paths.get(FILENAME)) && (Files.isReadable(Paths.get(FILENAME)) == false | Files.isWritable(Paths.get(FILENAME)) == false)) {
                        System.out.println("Кажется у нас ошибка доступа, попробуем еще раз");
                        FILENAME = scanner.nextLine();
                        if (FILENAME.equalsIgnoreCase("exit")) System.exit(0);
                    } else {
                        System.out.println("Файл не найден, введите имя заново");
                        FILENAME = scanner.nextLine();
                        if (FILENAME.equalsIgnoreCase("exit")) System.exit(0);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Кажется путь указыает на директрию, попробуем еще раз");
                FILENAME = scanner.nextLine();
                if (FILENAME.equalsIgnoreCase("exit")) System.exit(0);
            }
        }
        information file = new information();
        file.file = new File(FILENAME);
        file.cmdtype="file";

        Scanner scanner1 = new Scanner(System.in);
        String cmd = "";
        int flag = 0;
        while (cmd.equalsIgnoreCase("exit") != true) {
            cmd = scanner1.nextLine();
            cmd = cmd.trim();
            if (cmd.equalsIgnoreCase("help") == true) {
                information help = new information();
                help.cmdtype = "help";
                client.run(help);
            } else {
                if (cmd.equalsIgnoreCase("info") == true) {
                    information info = new information();
                    info.cmdtype = "info";
                    client.run(info);
                } else {
                    if (cmd.lastIndexOf("add") != -1) {
                        cmd.replace(" ", "");
                        String substr[] = new String[12];
                        scanner = new Scanner(System.in);

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
                                information add = new information();
                                add.cmdtype = "add";
                             //   add.arrayList.addAll(Arrays.asList(substr));
                                add.setParametrs(substr[0], substr[1], substr[2], substr[3], substr[4], substr[5], substr[6], substr[7], substr[8], substr[9], substr[10]);
                                client.run(add);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("неправильный ввод,попробуем еще раз");
                                substr[10] = scanner.nextLine();
                            }
                        }


                    } else {
                        if (cmd.equalsIgnoreCase("show") == true) {
                            information show = new information();
                            show.cmdtype = "show";
                            client.run(show);
                        } else {
                            if (cmd.lastIndexOf("remove_by_id") != -1) {
                                String id;
                                int index1 = cmd.lastIndexOf(" ");
                                id = cmd.substring(index1 + 1);
                                id = id.trim();
                                while (true) {
                                    try {
                                        Integer check = Integer.valueOf(id);
                                        information remove_by_id = new information();
                                        remove_by_id.cmdtype = "remove_by_id";
                                       // remove_by_id.arrayList.add(id);
                                        remove_by_id.idstr=id;
                                        client.run(remove_by_id);
                                        break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Введите id");
                                        id = scanner1.nextLine();
                                    }
                                }
                            } else {
                                if (cmd.equalsIgnoreCase("clear") == true) {
                                    information clear = new information();
                                    clear.cmdtype = "clear";
                                    client.run(clear);
                                } else {
                                    if (cmd.equalsIgnoreCase("head") == true) {
                                        information head = new information();
                                        head.cmdtype = "head";
                                        client.run(head);
                                    } else {
                                        if (cmd.equalsIgnoreCase("remove_head") == true) {
                                            information remove_head = new information();
                                            remove_head.cmdtype = "remove_head";
                                            client.run(remove_head);
                                        } else {
                                            if (cmd.lastIndexOf("update") != -1) {
                                                String id = null;
                                                int index1 = cmd.lastIndexOf(" ");
                                                try {
                                                    id = cmd.substring(index1);
                                                    id = id.trim();
                                                } catch (StringIndexOutOfBoundsException e) {
                                                    System.out.println("Введите id");
                                                    while (true) {
                                                        try {

                                                            id = scanner1.nextLine();
                                                            Integer check = Integer.parseInt(id);
                                                            break;
                                                        } catch (NumberFormatException ex) {
                                                            System.out.println("Неправильный ввод, попробуем еще раз");
                                                        }
                                                    }
                                                } catch (NumberFormatException e2) {
                                                    System.out.println("Неправильный ввод, попробуем еще раз");
                                                }

                                                scanner = new Scanner(System.in);
                                                //  String string = scanner.toString();
                                                String substr[] = new String[12];
                                                //  Scanner scanner = new Scanner(System.in);

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
                                                        else
                                                            System.out.println("неправильный ввод,попробуем еще раз");
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
                                                            else
                                                                System.out.println("неправильный ввод,попробуем еще раз");
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
                                                                else
                                                                    System.out.println("неправильный ввод,попробуем еще раз");
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
                                                information update = new information();
                                                update.cmdtype = "update";
                                                update.idstr = id;
                                                update.setParametrs(substr[0], substr[1], substr[2], substr[3], substr[4], substr[5], substr[6], substr[7], substr[8], substr[9], substr[10]);
                                                client.run(update);
                                            } else {
                                                if (cmd.equalsIgnoreCase("remove_lower") == true) {
                                                    System.out.println("Введите номер в списке (count)");
                                                    scanner = new Scanner(System.in);
                                                    String string = scanner.nextLine();
                                                    long count;
                                                    while (true) {
                                                        try {
                                                            count = Long.valueOf(string);
                                                            break;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Номер введен неправильно, попробуем еще раз");
                                                            string = scanner.nextLine();
                                                        }
                                                    }
                                                    information remove_lover = new information();
                                                    remove_lover.cmdtype = "remove_lover";
                                                   // remove_lover.arrayList.add(count);
                                                    remove_lover.count= String.valueOf(count);
                                                    client.run(remove_lover);
                                                } else {
                                                    if (cmd.lastIndexOf("remove_any_by_form_of_education") != -1) {
                                                        String form;
                                                        int index1 = cmd.lastIndexOf(" ");
                                                        form = cmd.substring(index1 + 1);
                                                        while (true) {
                                                            if (form.equalsIgnoreCase("time") == false && form.equalsIgnoreCase("distance") == false && form.equalsIgnoreCase("evening") == false) {
                                                                System.out.println("Введите форму обучения(full time,distance,evening)");
                                                                scanner = new Scanner(System.in);
                                                                form = scanner.nextLine();
                                                                form = form.trim();
                                                            } else break;
                                                        }
                                                        information remove_any_by_form_of_education = new information();
                                                        remove_any_by_form_of_education.cmdtype = "remove_any_by_form_of_education";
                                                       // remove_any_by_form_of_education.arrayList.add(form);
                                                        remove_any_by_form_of_education.form=form;
                                                        client.run(remove_any_by_form_of_education);
                                                    } else {
                                                        if (cmd.lastIndexOf("filter_starts_with_name") != -1) {
                                                            String name;
                                                            int index1 = cmd.lastIndexOf(" ");
                                                            name = cmd.substring(index1 + 1);
                                                            information filter_starts_with_name = new information();
                                                            filter_starts_with_name.cmdtype = "filter_starts_with_name";
                                                            filter_starts_with_name.name=name;
                                                           // SocketC.send(filter_starts_with_name);
                                                            client.run(filter_starts_with_name);
                                                        } else {
                                                            if (cmd.lastIndexOf("filter_greater_than_students_count") != -1) {
                                                                String count;
                                                                int index1 = cmd.lastIndexOf(" ");
                                                                count = cmd.substring(index1 + 1);
                                                                while (true) {
                                                                    try {
                                                                        long cou = Long.parseLong(count);
                                                                        information filter_greater_than_students_count = new information();
                                                                        filter_greater_than_students_count.cmdtype = "filter_greater_than_students_count";
                                                                        filter_greater_than_students_count.count= String.valueOf(cou);
                                                                        client.run(filter_greater_than_students_count);
                                                                        break;
                                                                    } catch (NumberFormatException e) {
                                                                        System.out.println("Введите номер");
                                                                        scanner = new Scanner(System.in);
                                                                        count = scanner.nextLine();
                                                                    }
                                                                }
                                                            } else {
                                                                if (cmd.lastIndexOf("execute_script") != -1) {
                                                                    String path;

                                                                    while (true)
                                                                        try {
                                                                            int index1 = cmd.lastIndexOf(" ");
                                                                            path = cmd.substring(index1);
                                                                            information execute_script = new information();
                                                                            execute_script.cmdtype = "execute_script";
                                                                            execute_script.arrayList.add(path);
                                                                            break;
                                                                        } catch (StringIndexOutOfBoundsException e) {
                                                                            System.out.println("Введите имя файла");
                                                                            cmd = " " + scanner1.nextLine();
                                                                        }

                                                                } else {
                                                                    if (cmd.equalsIgnoreCase("exit") != true)
                                                                        System.out.println("Команда введена неправильно");
                                                                }

                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (cmd.equalsIgnoreCase("exit")) System.exit(0);
    }


}

class information implements Serializable {
    String answer;
    File file;
    String cmdtype;
    ArrayList arrayList = new ArrayList();
    PriorityQueue priorityQueue = new PriorityQueue();
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

    public void setParametrs(String name, String count, String exp, String form, String semestr, String groupAdmin, String height, String weight, String eyeColor, String X, String Y) {
        this.count = count;
        this.name = name;
        this.exp = exp;
        this.form = form;
        this.eyeColor = eyeColor;
        this.semestr = semestr;
        this.groupAdmin = groupAdmin;
        this.height = height;
        this.weight = weight;
        this.X = X;
        this.Y = Y;
    }
    public String getAnswer(){return answer;}
}

