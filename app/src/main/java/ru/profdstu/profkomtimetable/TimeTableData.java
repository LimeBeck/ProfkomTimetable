package ru.profdstu.profkomtimetable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static ru.profdstu.profkomtimetable.LessonDbSchema.*;

/**
 * Created by metal on 03.03.2018.
 */

/*
Чтение данных из БД
 */

public class TimeTableData {

    private static TimeTableData sTimeTableData;
    private List<Lesson> mLessonsList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TimeTableData get(Context context){
        if (sTimeTableData==null){
            sTimeTableData= new TimeTableData(context);
        }
        return sTimeTableData;
    }

    private TimeTableData (Context context){
        mContext = context.getApplicationContext();
        mDatabase = new LessonBaseHelper(context).getWritableDatabase();
    }

    public List<Lesson> getLessonsList(){
        List<Lesson> lessons = new ArrayList<>();
        LessonCursorWrapper cursorWrapper = queryLessons(null, null);
        try{
                cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast())
            {
                    lessons.add(cursorWrapper.getLesson());
                    cursorWrapper.moveToNext();
            }

        } finally {
            cursorWrapper.close();
        }
        return lessons;
    }

    public Lesson getLessonByWeekDayNumber (int week, int day, int number){
        LessonCursorWrapper cursorWrapper = queryLessons(LessonTable.Cols.DAY_NUMBER+ "= ?"+
                LessonTable.Cols.WEEK_NUMBER+"= ?"+
                LessonTable.Cols.LESSON_NUMBER+"= ?",
                new String[]{(String.valueOf(day)),String.valueOf(week),String.valueOf(number)});
        try{
            if (cursorWrapper.getCount()==0){
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getLesson();
        } finally {
            cursorWrapper.close();
        }
    }

    private LessonCursorWrapper queryLessons(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(LessonTable.NAME,
                null, whereClause, whereArgs, null, null,
                LessonTable.Cols.WEEK_NUMBER + ", " +
                        LessonTable.Cols.DAY_NUMBER + ", " +
                        LessonTable.Cols.LESSON_NUMBER
        );
        return new LessonCursorWrapper(cursor);
    }

}
