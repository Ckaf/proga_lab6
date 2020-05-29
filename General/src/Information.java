import java.io.Serializable;

class Information implements Serializable {
    String cmdtype;
    int number;
    int port;
    int SIZE=16384;
    byte[] file=new byte[SIZE];
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

}
