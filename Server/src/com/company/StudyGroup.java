package com.company;

import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Queue;

/**
 * Stored and set the parameters of students
 */

public class StudyGroup {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;//Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates;//Поле не может быть null
    private ZonedDateTime creationDate;//Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount;//Значение поля должно быть больше 0
    private Long expelledStudents;//Значение поля должно быть больше 0, Поле не может быть null
    private com.company.Enum.FormOfEducation formOfEducation;//Поле не может быть null
    private com.company.Enum.Semester semesterEnum;//Поле не может быть null
    private Person groupAdmin;//Поле не может быть null

    private String exp;
    Integer checkid = 0;

    public String time;
    Integer i=0;
    public StudyGroup(Queue<StudyGroup> StudyGroupPriorityQueue, String name, String count, String exp, String form, String semestr, String groupAdmin, String height, String weight, String eyeColor, String X, String Y) throws Exception {
        //creationDate=creationDate.minusDays(0);
        creationDate=ZonedDateTime.now();
        Iterator<StudyGroup> it = StudyGroupPriorityQueue.iterator();
        this.id = 0;
        this.name = name.trim();
        if (this.name == null | this.name.equalsIgnoreCase("") ) {
            System.out.println("Ошибка в заполнении данных, программа прерывает работу");
            System.exit(0);
        }

        this.studentsCount = Long.parseLong(count);
        if (this.studentsCount <= 0) {
            System.out.println("Ошибка в заполнении данных, программа прерывает работу");
            System.exit(0);
        }

        this.exp = exp;
        if (this.exp == null ) {
            System.out.println("Ошибка в заполнении данных, программа прерывает работу");
            System.exit(0);
        }
        while (checkid == this.id) {
            this.id = (int) (Math.random() * 1000000);
        }
        checkid = this.id;

        if (exp.equals("yes") == true) {
            this.exp = "отчислен";
            expelledStudents=1L;
        } else {
            this.exp = "не отчислен";
            expelledStudents=2L;
        }
        form=form.trim();
        if (form.equals("full time") == true || form.equals("FULL_TIME_EDUCATION") == true) this.formOfEducation = com.company.Enum.FormOfEducation.FULL_TIME_EDUCATION;
        else
        if (form.equals("distance") == true|| form.equals("DISTANCE_EDUCATION") == true) this.formOfEducation = com.company.Enum.FormOfEducation.DISTANCE_EDUCATION;
        else
        if (form.equals("evening") == true|| form.equals("EVENING_CLASSES") == true) this.formOfEducation = com.company.Enum.FormOfEducation.EVENING_CLASSES;
        else  {
            System.out.println("Ошибка в заполнении данных, программа прерывает работу");
            System.exit(0);
        }
        semestr=semestr.trim();
        if (semestr.equals("FIFTH") == true || semestr.equals("5") == true ) this.semesterEnum = com.company.Enum.Semester.FIFTH;
        else
        if (semestr.equals("6") == true||semestr.equals("SIXTH") == true) this.semesterEnum = com.company.Enum.Semester.SIXTH;
        else
        if (semestr.equals("7") == true||semestr.equals("SEVENTH") == true) this.semesterEnum = com.company.Enum.Semester.SEVENTH;
        else
        if (semestr.equals("8") == true||semestr.equals("EIGHTH") == true) this.semesterEnum = com.company.Enum.Semester.EIGHTH;
        else  {
            System.out.println("Ошибка в заполнении данных, программа прерывает работу");
            System.exit(0);
        }
        this.groupAdmin = new Person(groupAdmin, height, weight, eyeColor);
        this.coordinates = new Coordinates(X, Y);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public String getexp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public com.company.Enum.FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public String getAdminName() {
        return groupAdmin.getAdminName();
    }

    public void setFormOfEducation(String form) {
        if (form.equals("full time") == true) this.formOfEducation = com.company.Enum.FormOfEducation.FULL_TIME_EDUCATION;
        if (form.equals("distance") == true) this.formOfEducation = com.company.Enum.FormOfEducation.DISTANCE_EDUCATION;
        if (form.equals("evening") == true) this.formOfEducation = com.company.Enum.FormOfEducation.EVENING_CLASSES;
    }

    public Double getHeight() {
        return groupAdmin.getHeight();
    }

    public com.company.Enum.Color getColor() {
        return groupAdmin.getColor();
    }

    public Integer getWeight() {
        return groupAdmin.getWeight();
    }

    public Float getCoordinatesX() {
        return coordinates.getX();
    }

    public Double getCoordinatesY() {
        return coordinates.getY();
    }

    public void setSemesterEnum(String semestr) {
        if (semestr.equals("5") == true) this.semesterEnum = com.company.Enum.Semester.FIFTH;
        if (semestr.equals("6") == true) this.semesterEnum = com.company.Enum.Semester.SIXTH;
        if (semestr.equals("7") == true) this.semesterEnum = com.company.Enum.Semester.SEVENTH;
        if (semestr.equals("8") == true) this.semesterEnum = com.company.Enum.Semester.EIGHTH;
    }

    public com.company.Enum.Semester getSemesterEnum() {
        return semesterEnum;
    }
    public ZonedDateTime getCreationDate(){
        return creationDate;
    }
    public Long getExpelledStudents(){
        return expelledStudents;
    }


}

