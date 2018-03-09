package ru.profdstu.profkomtimetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
        mLessonsList = getLessonsList();
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

    public List<Lesson> getLessonByWeekDay (int week, int day){
        List<Lesson> lessons = new ArrayList<>();
        LessonCursorWrapper cursorWrapper = queryLessons(LessonTable.Cols.DAY_NUMBER+ "= ?"+
                LessonTable.Cols.WEEK_NUMBER+"= ?",
                new String[]{String.valueOf(day),String.valueOf(week)});
        try{
            if (cursorWrapper.getCount()==0){
                return null;
            }
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

    public int getDaysInWeek(int week){
       int days =2;



       return days;
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

    public void setLessonsList(List<Lesson> lessonsList){
        mLessonsList = lessonsList;
    }

    private static ContentValues getContentValues(Lesson lesson){
        ContentValues values = new ContentValues();
        values.put(LessonTable.Cols.AUDITORY, lesson.getAuditory());
        values.put(LessonTable.Cols.DAY_NUMBER, lesson.getDayNumber());
        values.put(LessonTable.Cols.LESSON_NAME, lesson.getLessonName());
        values.put(LessonTable.Cols.LESSON_NUMBER,lesson.getLessonNumber());
        values.put(LessonTable.Cols.TEACHER_NAME, lesson.getTeacherName());
        values.put(LessonTable.Cols.WEEK_NUMBER, lesson.getWeekNumber());
        return values;
    }

    public void addLesson(Lesson lesson){
        ContentValues contentValues = getContentValues(lesson);
        mDatabase.insert(LessonTable.NAME, null, contentValues);
    }
}
