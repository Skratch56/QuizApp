package vut.mambane.quizapp;

/**
 * Created by CE on 2016-07-18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Quiz.db";
    public static final String TABLE_NAME = "highscore";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CHALLENGE = "challenge";
    public static final String COLUMN_SCORE = "score";
    private static final int DATABASE_VERSION = 5;


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_CHALLENGE + " TEXT, " +
                        COLUMN_SCORE + " INTEGER)"
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, 1);
        contentValues.put(COLUMN_CHALLENGE, "Places around the world");
        contentValues.put(COLUMN_SCORE, 0);


        db.insert(TABLE_NAME, null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COLUMN_ID, 2);
        contentValues1.put(COLUMN_CHALLENGE, "Animal Kingdom");
        contentValues1.put(COLUMN_SCORE, 0);


        db.insert(TABLE_NAME, null, contentValues1);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COLUMN_ID, 3);
        contentValues2.put(COLUMN_CHALLENGE, "Famous People");
        contentValues2.put(COLUMN_SCORE, 0);


        db.insert(TABLE_NAME, null, contentValues2);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean updateScore(int _id, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SCORE, score);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{Integer.toString(_id)});
        return true;
    }


    public int getScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        res.moveToFirst();
        int score = res.getInt(res.getColumnIndex("score"));
        return score;
    }

    public ArrayList<Highscore> getAllScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Highscore> arScore = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        res.moveToFirst();
        while (!res.isAfterLast()) {

            arScore.add(new Highscore(res.getInt(res.getColumnIndex("score")), res.getInt(res.getColumnIndex("_id")), res.getString(res.getColumnIndex("challenge"))));
            res.moveToNext();
        }
        res.close();
        db.close();

        return arScore;
    }


}
