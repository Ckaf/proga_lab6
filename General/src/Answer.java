import java.io.Serializable;

/**
 * Class that stores information received from the server
 */
class Answer implements Serializable {
    String answer;
    String answer1;
    String autorizatonflag;
    int SIZE=16384;
    byte[] file=new byte[SIZE];
    Integer wrong;
    public String getAnswer(){return answer;}
    public Integer getWrong(){return wrong;}
    public void setAnswer(String answer){
        this.answer=answer;
}
    public void setAutorizatonflag(String autorizatonflag){this.autorizatonflag=autorizatonflag;}
    public String getAutorizatonflag(){return autorizatonflag;}
}
