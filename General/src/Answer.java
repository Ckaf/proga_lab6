import java.io.Serializable;

class Answer implements Serializable {
    String answer;
    String answer1;
    int SIZE=16384;
    byte[] file=new byte[SIZE];
    Integer wrong;
    public byte[] getFile(){return file;}
    public String getAnswer(){return answer;}
    public Integer getWrong(){return wrong;}
    public void setAnswer(String answer){
        this.answer=answer;
}
}
