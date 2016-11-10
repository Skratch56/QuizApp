package vut.mambane.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {

    MyDBHandler dbHandler;
    int _id;
    private Button btn1, btn2, btn3, btn4, btnSubmit;
    private RadioButton rdoTrue, rdoFalse;
    private CheckBox chk1, chk2, chk3, chk4;
    private TextView txtQuestion, txtTimer, txtStats;
    private ImageView imgQuestion;
    private ArrayList<Quiz> arQuestion;
    private ArrayList<Integer> arIndex;
    private LinearLayout layoutButtons1, layoutButtons2, layoutRadioButtons, layoutCheckBoxes1, layoutCheckBoxes2, layoutStats, gamePlayLayout;
    private AVLoadingIndicatorView avi;
    private String answer, selectedAnswer, answer2, checkedAnswer1, checkedAnswer2, checkedAnswer3, checkedAnswer4;
    private int score = 0;
    private Vibrator vibrator;
    private CountDownTimer countTime;
    private MenuItem scoreItem;
    private int questionCount = 0;
    Random randomIndex = new Random();
    public static String[] RandomizeArray(String[] array) {
        Random rgen = new Random();  // Random number generator

        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            String temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        dbHandler = new MyDBHandler(this);
        arIndex = new ArrayList<>();
        _id = getIntent().getIntExtra("_id", 0);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        chk1 = (CheckBox) findViewById(R.id.chk1);
        chk2 = (CheckBox) findViewById(R.id.chk2);
        chk3 = (CheckBox) findViewById(R.id.chk3);
        chk4 = (CheckBox) findViewById(R.id.chk4);

        rdoTrue = (RadioButton) findViewById(R.id.rdoTrue);
        rdoFalse = (RadioButton) findViewById(R.id.rdoFalse);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        layoutStats = (LinearLayout) findViewById(R.id.challengeStats);
        layoutButtons1 = (LinearLayout) findViewById(R.id.buttonsLayout1);
        layoutButtons2 = (LinearLayout) findViewById(R.id.buttonsLayout2);
        layoutRadioButtons = (LinearLayout) findViewById(R.id.radioLayout);
        layoutCheckBoxes1 = (LinearLayout) findViewById(R.id.checkboxLayout1);
        layoutCheckBoxes2 = (LinearLayout) findViewById(R.id.checkboxLayout2);

        gamePlayLayout = (LinearLayout) findViewById(R.id.gamePlayLayout);

        txtStats = (TextView) findViewById(R.id.txtStats);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        imgQuestion = (ImageView) findViewById(R.id.imgQuestion);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        Quiz.initialise();
        arQuestion = new ArrayList<>();
        arQuestion = Quiz.getAll();

        avi.setVisibility(View.GONE);
        txtQuestion.setText("Press Start game to begin");
        btnSubmit.setText("Start Game");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        scoreItem = menu.findItem(R.id.txtScore);
        scoreItem.setTitle(score + "");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method checks to see if the selected question hasn't already been selected because the getQuestion method randomly gets questions.
     */
    public void loopReturn(boolean exists) {
        if (questionCount != 8) {
            if (exists) {
                getQuestion();
            }
        } else {

            disableViews();
            avi.setVisibility(View.GONE);
            txtTimer.setVisibility(View.GONE);
            txtQuestion.setText("Congratulations you have completed the challenge\n");
            txtQuestion.append("View your results below");
            imgQuestion.setImageResource(R.drawable.plainback);
            layoutStats.setVisibility(View.VISIBLE);
            txtStats.setText("Score " + score + "\n");
            txtStats.append("Correct answers " + score + "\n");
            txtStats.append("Incorrect answers " + (arQuestion.size() + 1 - score));
            if (dbHandler.getScore(_id) < score) {
                dbHandler.updateScore(_id, score);
            }

        }
    }

    /**
     * This method determines the range at which the questions will be fetched from the Questions Array.
     * It then gets the question from the Array and checks if the question has already been asked.
     * If the question has been asked it gets runs the loop return method. If it hasn't it displays the question along with it's picture and set of widgets.
     */
    public void getQuestion() {

        Boolean exists;
        int index, maxi = 0, min = 0;
        if (_id == 1) {
            maxi = 8;
            min = 0;
        } else if (_id == 2) {
            maxi = 16;
            min = 8;

        } else if (_id == 3) {
            maxi = 24;
            min = 16;
        }
        index = randomIndex.nextInt(((maxi - min) + min));

        for (int x = 0; x < arIndex.size(); x++) {
            if (arIndex.get(x) == index) {
                exists = true;
                loopReturn(exists);
                return;
            }
        }

        questionCount += 1;
        arIndex.add(index);

        String[] arAnswers = {"a", "a", "a", "a"};

        String question = arQuestion.get(index).getQuestion(), wrongAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3, type;

        answer = arQuestion.get(index).getAnswer();
        answer2 = arQuestion.get(index).getFalseAnswer();

        enableControls();

        arAnswers[0] = answer;
        arAnswers[1] = arQuestion.get(index).getFalseAnswer();
        arAnswers[2] = arQuestion.get(index).getFalseAnswer2();
        arAnswers[3] = arQuestion.get(index).getFalseAnswer3();

        String[] arRanAnswers = RandomizeArray(arAnswers);

        type = arQuestion.get(index).getType();

        initialiseControls(arRanAnswers, type);

        if (arQuestion.get(index).getImage() == "none") {
            imgQuestion.setImageResource(R.drawable.plainback);
        } else {
            String imageName = arQuestion.get(index).getImage();
            int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());

            Picasso.with(this).load(resID).into(imgQuestion);

        }

        txtQuestion.setText(question);

        countTime = new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                avi.show();

                txtTimer.setTextColor(Color.WHITE);
                txtTimer.setText("seconds remaining: " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                txtTimer.setText("You Lose!");
                txtTimer.setTextColor(Color.RED);
                avi.smoothToHide();
                disableControls();

                new CountDownTimer(6000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        loopReturn(false);

                        if (questionCount != arQuestion.size()) {

                            txtTimer.setText("Time up\n");
                            txtTimer.append("Next question in: " + millisUntilFinished / 1000);

                        }
                    }

                    public void onFinish() {

                        getQuestion();

                    }
                }.start();
            }
        }.start();
    }

    /**
     * This method disables all the views(Linear Layouts) that contain the controls allowing the user to answer the questions
     */
    public void disableViews() {
        layoutCheckBoxes1.setVisibility(View.GONE);
        layoutCheckBoxes2.setVisibility(View.GONE);
        layoutButtons1.setVisibility(View.GONE);
        layoutButtons2.setVisibility(View.GONE);
        layoutRadioButtons.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        avi.setVisibility(View.GONE);
        //txtTimer.setVisibility(View.GONE);
    }

    /**
     * This method enables the views(Linear Layouts) that contain the controls allowing the user to answer the questions depending on what value the parameter type contains.
     * If parameter type has the value "button" this method displays the button layouts and sets the button text to the values in the arAnswers parameter.
     * If parameter type has the value "radiobuttons" it enables the radio button  controls which has the Values True or False
     * If parameter type has the value "checkboxes" it enables the check boc controls which allows the user to answer multiple choice questions
     */
    public void initialiseControls(String[] arAnswers, String type) {
        //All the controls are first disabled
        disableViews();
        //checks the type parameter then enables the required controls.
        if (type.equals("button")) {
            layoutButtons1.setVisibility(View.VISIBLE);
            layoutButtons2.setVisibility(View.VISIBLE);
            btn1.setText(arAnswers[0]);
            btn2.setText(arAnswers[1]);
            btn3.setText(arAnswers[2]);
            btn4.setText(arAnswers[3]);
            btn1.setActivated(true);
            btn2.setActivated(true);
            btn3.setActivated(true);
            btn4.setActivated(true);

        } else if (type.equals("radiobuttons")) {
            layoutRadioButtons.setVisibility(View.VISIBLE);
            rdoTrue.setActivated(true);
            rdoFalse.setActivated(true);


        } else if (type.equals("checkboxes")) {
            layoutCheckBoxes1.setVisibility(View.VISIBLE);
            layoutCheckBoxes2.setVisibility(View.VISIBLE);
            btnSubmit.setText("Submit");
            btnSubmit.setVisibility(View.VISIBLE);
            chk1.setText(arAnswers[0]);
            chk2.setText(arAnswers[1]);
            chk3.setText(arAnswers[2]);
            chk4.setText(arAnswers[3]);
            chk1.setActivated(true);
            chk2.setActivated(true);
            chk3.setActivated(true);
            chk4.setActivated(true);

        }
    }

    /**
     * This method is executed when a radio button is clicked. When it is clicked it checks which button is clicked and assigns true or false to the selected answer variable depending on the selected button
     */
    public void rdoOnClick(View view) {
        //checks which button was clicked
        if (view.getId() == rdoTrue.getId()) {
            selectedAnswer = "True";
        } else {
            selectedAnswer = "False";
        }
        //launches the gamemplay methid and passes in its parameters.
        gamePlay(selectedAnswer, answer, "radiobuttons");
    }

    /**
     * Is executed when a submit button is clicked
     * Since the check boxes allow the user to select multiple answers I couldn't allow this method to be excecuted when a checkbox button is clicked
     * Thus I added a "Submit" button which when clicked checks to see which CheckBox button is clicked.
     */
    public void btnSubmitOnClick(View view) {
        //If the submit button has the text start game it'll restart and get the question then change the button text to Submit
        if (btnSubmit.getText() == "Start Game") {
            getQuestion();
            btnSubmit.setText("Submit");
        } else {
            //Checks the checkbox buttons to see which one is clicked then assigns the text to the CheckedAnswer Variable if it is not selected it assigns an empty answer
            if (chk1.isChecked()) {
                checkedAnswer1 = chk1.getText().toString();
            } else {
                checkedAnswer1 = "";
            }
            if (chk2.isChecked()) {
                checkedAnswer2 = chk2.getText().toString();
            } else {
                checkedAnswer2 = "";
            }
            if (chk3.isChecked()) {
                checkedAnswer3 = chk3.getText().toString();
            } else {
                checkedAnswer3 = "";
            }
            if (chk4.isChecked()) {
                checkedAnswer4 = chk4.getText().toString();
            } else {
                checkedAnswer4 = "";
            }
            //launches the gamemplay method and passes in its parameters.
            gamePlay(this.answer, this.answer2, "checkboxes");
        }
    }

    /**
     * Is executed when a button is clicked. It assigns the selected buttons text to the selectedAnswer variable
     */
    public void btnOnClick(View view) {
        //Checks which button is selected then assigns the button text to the selectedAnswer variable
        if (view.getId() == btn1.getId()) {
            selectedAnswer = btn1.getText().toString();
        } else if (view.getId() == btn2.getId()) {
            selectedAnswer = btn2.getText().toString();
        } else if (view.getId() == btn3.getId()) {
            selectedAnswer = btn3.getText().toString();
        } else if (view.getId() == btn4.getId()) {
            selectedAnswer = btn4.getText().toString();
        }
        //launches the gamemplay method and passes in its parameters.
        gamePlay(selectedAnswer, null, "button");
    }

    /**
     * This method receives parameters then checks to see if the selected answer or answers are correct.
     * If they are correct the variable score is increased by 1.
     * If it is not correct the phone will vibrate then the button with the correct answer will start flashing green. After 5 seconds a new question will be called
     * A timer will also be counting down while
     *
     */
    public void gamePlay(final String answer, final String answer2, String type) {
        final String btnAnswer = this.answer;
        if (!type.equals("checkboxes")) {
            if (this.answer.equals(answer)) {
                score += 1;
                countTime.cancel();
                scoreItem.setTitle(score + "");
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
                getQuestion();
            } else {
                vibrator.vibrate(200);
                Toast.makeText(this, "Wrong", Toast.LENGTH_LONG).show();
                countTime.cancel();
                disableControls();
                if (type == "button") {
                    new CountDownTimer(4000, 500) {

                        public void onTick(long millisUntilFinished) {
                            if (btn1.getText() == btnAnswer) {
                                btn1.setActivated(false);
                            } else if (btn2.getText() == btnAnswer) {
                                btn2.setActivated(false);
                            } else if (btn3.getText() == btnAnswer) {
                                btn3.setActivated(false);
                            } else if (btn4.getText() == btnAnswer) {
                                btn4.setActivated(false);
                            }
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    btn1.setActivated(true);
                                    btn2.setActivated(true);
                                    btn3.setActivated(true);
                                    btn4.setActivated(true);
                                }
                            }, 300);

                        }

                        public void onFinish() {
                            btn1.setActivated(true);
                            btn2.setActivated(true);
                            btn3.setActivated(true);
                            btn4.setActivated(true);

                            getQuestion();

                        }
                    }.start();

                } else if (type == "radiobuttons") {
                    new CountDownTimer(4000, 500) {

                        public void onTick(long millisUntilFinished) {
                            if (answer2.equals("True")) {
                                rdoTrue.setActivated(false);
                            }
                            if (answer2.equals("False")) {
                                rdoFalse.setActivated(false);
                            }
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    rdoTrue.setActivated(true);
                                    rdoFalse.setActivated(true);

                                }
                            }, 300);

                        }

                        public void onFinish() {
                            rdoTrue.setActivated(true);
                            rdoFalse.setActivated(true);

                            getQuestion();

                        }
                    }.start();
                }
            }
        } else {
            int checkCorrect = 0;

            if (answer == checkedAnswer1 || answer2 == checkedAnswer1) {
                score += 1;
                checkCorrect += 1;
                countTime.cancel();
                scoreItem.setTitle(score + "");
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();

            }
            if (answer == checkedAnswer2 || answer2 == checkedAnswer2) {
                score += 1;
                checkCorrect += 1;
                countTime.cancel();
                scoreItem.setTitle(score + "");
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();

            }
            if (answer == checkedAnswer3 || answer2 == checkedAnswer3) {
                score += 1;
                checkCorrect += 1;
                countTime.cancel();
                scoreItem.setTitle(score + "");
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();

            }
            if (answer == checkedAnswer4 || answer2 == checkedAnswer4) {
                score += 1;
                checkCorrect += 1;
                countTime.cancel();
                scoreItem.setTitle(score + "");
                Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();

            }


            if (checkCorrect != 2) {
                //score -= 1;
                countTime.cancel();
                scoreItem.setTitle(score + "");
                Toast.makeText(this, "Wrong", Toast.LENGTH_LONG).show();
                vibrator.vibrate(200);
                //getQuestion();

                new CountDownTimer(4000, 500) {

                    public void onTick(long millisUntilFinished) {
                        if (chk1.getText() == answer || chk1.getText() == answer2) {
                            chk1.setActivated(false);
                        }
                        if (chk2.getText() == answer || chk2.getText() == answer2) {
                            chk2.setActivated(false);
                        }
                        if (chk3.getText() == answer || chk3.getText() == answer2) {
                            chk3.setActivated(false);
                        }
                        if (chk4.getText() == answer || chk4.getText() == answer2) {
                            chk4.setActivated(false);
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                chk1.setActivated(true);
                                chk2.setActivated(true);
                                chk3.setActivated(true);
                                chk4.setActivated(true);
                            }
                        }, 300);

                    }

                    public void onFinish() {
                        chk1.setActivated(true);
                        chk2.setActivated(true);
                        chk3.setActivated(true);
                        chk4.setActivated(true);
                        getQuestion();

                    }
                }.start();
            } else {
                getQuestion();
            }
        }

    }

    /**
     * Created by CE on 2016-10-10.
     */
    public void disableControls() {

        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        chk1.setEnabled(false);
        chk2.setEnabled(false);
        chk3.setEnabled(false);
        chk4.setEnabled(false);
        rdoTrue.setEnabled(false);
        rdoFalse.setEnabled(false);

    }

    /**
     * Created by CE on 2016-10-10.
     */
    public void enableControls() {

        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        chk1.setEnabled(true);
        chk2.setEnabled(true);
        chk3.setEnabled(true);
        chk4.setEnabled(true);
        rdoTrue.setEnabled(true);
        rdoFalse.setEnabled(true);

        chk1.setChecked(false);
        chk2.setChecked(false);
        chk3.setChecked(false);
        chk4.setChecked(false);
        rdoTrue.setChecked(false);
        rdoFalse.setChecked(false);

    }
}
