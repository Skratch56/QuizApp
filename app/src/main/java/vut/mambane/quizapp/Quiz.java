package vut.mambane.quizapp;

/**
 * Created by CE on 2016-10-10.
 */
public class Quiz {
    private String question,answer,falseAnswer,falseAnswer2,falseAnswer3,type,image;

    public Quiz(){
        this("","","","","","","");
    }

    public Quiz(String question, String answer, String falseAnswer, String falseAnswer2, String falseAnswer3,String type,String image) {
        this.question = question;
        this.answer = answer;
        this.falseAnswer = falseAnswer;
        this.falseAnswer2 = falseAnswer2;
        this.falseAnswer3 = falseAnswer3;
        this.type = type;
        this.image = image;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setFalseAnswer(String falseAnswer) {
        this.falseAnswer = falseAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setFalseAnswer2(String falseAnswer2) {
        this.falseAnswer2 = falseAnswer2;
    }

    public void setFalseAnswer3(String falseAnswer3) {
        this.falseAnswer3 = falseAnswer3;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getFalseAnswer() {
        return falseAnswer;
    }

    public String getFalseAnswer2() {
        return falseAnswer2;
    }

    public String getFalseAnswer3() {
        return falseAnswer3;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public static void initialise()
    {
        QuizDA.initialse();
    }

    public static java.util.ArrayList<Quiz> getAll()
    {
        return QuizDA.getAll();
    }
}
