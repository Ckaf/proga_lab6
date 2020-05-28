import java.io.Serializable;

class Answer implements Serializable {
    String answer;
    Integer wrong;
    public String getAnswer(){return answer;}
    public Integer getWrong(){return wrong;}
    public void setAnswer(String answer){
        this.answer=answer;
}
}
