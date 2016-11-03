package vut.mambane.quizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

public class SelectTopic extends AppCompatActivity {
    LinearLayout layoutWorld, layoutAnimal, layoutFamous;
    CircularFillableLoaders circularFillableLoaders, circularFillableLoaders2, circularFillableLoaders3;
    Intent intent;
    MyDBHandler dbHandler;
    TextView txtScoreTotal1,txtScoreTotal2,txtScoreTotal3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);
        dbHandler = new MyDBHandler(this);
        circularFillableLoaders = (CircularFillableLoaders) findViewById(R.id.circularFillableLoaders);
        circularFillableLoaders2 = (CircularFillableLoaders) findViewById(R.id.circularFillableLoaders2);
        circularFillableLoaders3 = (CircularFillableLoaders) findViewById(R.id.circularFillableLoaders3);
        circularFillableLoaders.setAmplitudeRatio(0.02f);
        circularFillableLoaders2.setAmplitudeRatio(0.02f);
        circularFillableLoaders3.setAmplitudeRatio(0.02f);

        txtScoreTotal1 = (TextView) findViewById(R.id.txtScoreTotal);
        txtScoreTotal2 = (TextView) findViewById(R.id.txtScoreTotal2);
        txtScoreTotal3 = (TextView) findViewById(R.id.txtScoreTotal3);

        txtScoreTotal1.setText(dbHandler.getScore(1) + "/ 10");
        txtScoreTotal2.setText(dbHandler.getScore(2) + "/ 10");
        txtScoreTotal3.setText(dbHandler.getScore(3) + "/ 10");

        layoutWorld = (LinearLayout) findViewById(R.id.layoutWorld);
        layoutAnimal = (LinearLayout) findViewById(R.id.layoutAnimal);
        layoutFamous = (LinearLayout) findViewById(R.id.layoutFamous);

        layoutWorld.setVisibility(View.GONE);
        layoutAnimal.setVisibility(View.GONE);
        layoutFamous.setVisibility(View.GONE);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.titleWorld) {
            if (layoutWorld.isShown()) {
                animation.slide_up(this, layoutWorld);
                layoutWorld.setVisibility(View.GONE);
                circularFillableLoaders.setProgress(100);
            } else {
                layoutWorld.setVisibility(View.VISIBLE);
                new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        circularFillableLoaders.setProgress(100 - (dbHandler.getScore(1)*100));
                    }

                }.start();


                animation.slide_down(this, layoutWorld);
            }
        } else if (v.getId() == R.id.titleAnimal) {
            if (layoutAnimal.isShown()) {
                animation.slide_up(this, layoutAnimal);
                layoutAnimal.setVisibility(View.GONE);
                circularFillableLoaders2.setProgress(100);

            } else {
                layoutAnimal.setVisibility(View.VISIBLE);
                new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        circularFillableLoaders2.setProgress(100 - (dbHandler.getScore(2)*100));
                    }

                }.start();


                animation.slide_down(this, layoutAnimal);
            }
        } else if (v.getId() == R.id.titleFamous) {
            if (layoutFamous.isShown()) {
                animation.slide_up(this, layoutFamous);
                layoutFamous.setVisibility(View.GONE);
                circularFillableLoaders3.setProgress(100);
            } else {
                layoutFamous.setVisibility(View.VISIBLE);
                new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        circularFillableLoaders3.setProgress(100 - (dbHandler.getScore(3)*100));
                    }

                }.start();


                animation.slide_down(this, layoutFamous);
            }
        }
    }

    public void btnStartGameOnclick(View view) {
        intent = new Intent(this, GamePlayActivity.class);
        if (view.getId() == R.id.btnSubItem) {
            intent.putExtra("_id", 1);
        } else if (view.getId() == R.id.btnSubItem2) {
            intent.putExtra("_id", 2);
        } else if (view.getId() == R.id.btnSubItem3) {
            intent.putExtra("_id", 3);
        }
        startActivity(intent);
    }


}
