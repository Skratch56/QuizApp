package vut.mambane.quizapp;

/**
 * Created by CE on 2016-07-18.
 */
public class Highscore {
    private int score,_id;
    private String challenge;


    public Highscore(int score, int _id, String challenge) {
        this.score = score;
        this._id = _id;
        this.challenge = challenge;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public int getScore() {
        return score;
    }

    public int get_id() {
        return _id;
    }

    public String getChallenge() {
        return challenge;
    }
}
