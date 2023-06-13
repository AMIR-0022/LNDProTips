package com.company.lndprotips.DbHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.company.lndprotips.QuestionModel.DbModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Quizzes";
    public static final int DATABASE_VERSION = 2;

    private static DbHelper instance;

    public static DbHelper getInstance(Context context) {
        if (instance == null){
            instance = new DbHelper(context);
        }
        return instance;
    }

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbModel.CREATE_QUIZ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL(DbModel.DROP_QUIZ_TABLE);
            db.execSQL(DbModel.CREATE_QUIZ_TABLE);
        }
    }

    // CRUD Operation of Sqlite Table
    // insert the data in sqlite table
    public boolean insertRecentQuiz(String quizTitle, String correct, String incorrect, String totalQuestion, String percentage){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbModel.KEY_QUIZ_TITLE, quizTitle);
        values.put(DbModel.KEY_CORRECT, correct);
        values.put(DbModel.KEY_INCORRECT, incorrect);
        values.put(DbModel.KEY_TOTAL_QUESTION, totalQuestion);
        values.put(DbModel.KEY_PERCENTAGE, percentage);

        long effectRows = 0;
        try {
            effectRows = database.insert(DbModel.TABLE_QUIZ, null, values);
        }
        catch (Exception ex){
            return false;
        }
        return effectRows == 1;
    }

    // delete the data from sqlite table
    public boolean deleteRecentQuiz(String id){
        SQLiteDatabase database = getWritableDatabase();

        long effectRows = 0;
        try {
            effectRows = database.delete(DbModel.TABLE_QUIZ, DbModel.KEY_ID+" = ?", new String[]{id});
        }
        catch (Exception ex){
            return false;
        }
        return effectRows==1;
    }

    // fetch data from sqlite table
    @SuppressLint("Range")
    public ArrayList<DbModel> fetchRecentQuiz(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(DbModel.SELECT_QUIZ_TABLE, null);

        ArrayList<DbModel> recentQuizList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                DbModel model = new DbModel();
                model.setId(cursor.getString(cursor.getColumnIndex(DbModel.KEY_ID)));
                model.setQuizTitle(cursor.getString(cursor.getColumnIndex(DbModel.KEY_QUIZ_TITLE)));
                model.setCorrect(cursor.getString(cursor.getColumnIndex(DbModel.KEY_CORRECT)));
                model.setIncorrect(cursor.getString(cursor.getColumnIndex(DbModel.KEY_INCORRECT)));
                model.setTotalQuestion(cursor.getString(cursor.getColumnIndex(DbModel.KEY_TOTAL_QUESTION)));
                model.setPercentage(cursor.getString(cursor.getColumnIndex(DbModel.KEY_PERCENTAGE)));
                recentQuizList.add(model);
            } while (cursor.moveToNext());
        }
        return recentQuizList;
    }

}
