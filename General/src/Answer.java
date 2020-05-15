import java.io.Serializable;

class Answer implements Serializable {
    String answer;
    public String getAnswer(){return answer;}
    public void setAnswer(String answer){
        this.answer=answer;
    }
}
