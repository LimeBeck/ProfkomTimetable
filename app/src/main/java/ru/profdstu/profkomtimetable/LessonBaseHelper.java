package ru.profdstu.profkomtimetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ru.profdstu.profkomtimetable.LessonDbSchema.*;

/**
 * Created by metal on 03.03.2018.
 */

public class LessonBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "lessonBase.db";

    public LessonBaseHelper(Context context) {super(context, DATABASE_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+ LessonTable.NAME + "("+
        "_id integer primary key autoincrement, " +
                LessonTable.Cols.LESSON_NAME + "," +
                LessonTable.Cols.TEACHER_NAME + "," +
                LessonTable.Cols.AUDITORY + "," +
                LessonTable.Cols.LESSON_NUMBER + "," +
                LessonTable.Cols.WEEK_NUMBER + "," +
                LessonTable.Cols.DAY_NUMBER + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
