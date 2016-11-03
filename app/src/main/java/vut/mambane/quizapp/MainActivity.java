package vut.mambane.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.co.barbuzz.beerprogressview.BeerProgressView;

import static vut.mambane.quizapp.R.id.beerProgressView;

public class MainActivity extends AppCompatActivity {
    BeerProgressView beerProgressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
