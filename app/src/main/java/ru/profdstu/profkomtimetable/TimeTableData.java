package ru.profdstu.profkomtimetable;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import static ru.profdstu.profkomtimetable.LessonDbSchema.*;
import static ru.profdstu.profkomtimetable.SettingSchema.*;

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
    private ArrayList<String> mGroups;
    private WebHelper mWebHelper;

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
        mWebHelper = WebHelper.getInstance(context);
        mGroups = mWebHelper.getGroups();
    }

    public List<Lesson> getLessonsList(){
        List<Lesson> lessons = new ArrayList<>();
        LessonCursorWrapper cursorWrapper = queryLessons(null, null);
        try{
                cursorWrapper.moveToFirst();
                if(cursorWrapper.getCount()<1){
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

    public List<Lesson> getLessonByWeekDay (int week, int day){
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM "+LessonTable.NAME+" WHERE "+LessonTable.Cols.WEEK_NUMBER+"= "+ String.valueOf(week)
                +" AND "+LessonTable.Cols.DAY_NUMBER+" = "+String.valueOf(day)+" ORDER BY "+LessonTable.Cols.DAY_NUMBER+", "+LessonTable.Cols.LESSON_NUMBER;
        Cursor raw = mDatabase.rawQuery(sql,null);
        LessonCursorWrapper cursorWrapper = new LessonCursorWrapper(raw);

        try{
            cursorWrapper.moveToFirst();
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



    public Lesson getLessonByUUID(UUID uuid){
        LessonCursorWrapper cursorWrapper = queryLessons(LessonTable.Cols.LESSON_ID + " = ?", new String[]{uuid.toString()});
        try {
            cursorWrapper.moveToFirst();
            if(cursorWrapper.getCount()==0){
                return null;
            }
            return cursorWrapper.getLesson();
        }
        finally {
            cursorWrapper.close();
        }
    }

    private LessonCursorWrapper queryLessons(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(LessonTable.NAME,
                null, whereClause, whereArgs, null, null,
                LessonTable.Cols.LESSON_NUMBER
        );


        return new LessonCursorWrapper(cursor);
    }

    public void setLessonsList(List<Lesson> lessonsList){
        mLessonsList = lessonsList;
    }

    private static ContentValues getContentValues(Lesson lesson){
        ContentValues values = new ContentValues();
        values.put(LessonTable.Cols.LESSON_ID, lesson.getUUID().toString());
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

    public void clearOldLessons(){
        mDatabase.delete(LessonTable.NAME,null,null);
    }

    public String getParameterFromSettings(String parameter){
        Cursor cursor = mDatabase.query(SettingsTable.NAME, null,SettingsTable.Cols.PARAMETER + " = ? ", new String[]{parameter}, null, null, null);
        try {
            cursor.moveToFirst();
            if(cursor.getCount()==0){
                return null;
            }
            return cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.VALUES));
        }
        finally {
            cursor.close();
        }
    }

    public void setParameterToSettings(String parameter, String group){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SettingsTable.Cols.PARAMETER,parameter);
        contentValues.put(SettingsTable.Cols.VALUES,group);
        mDatabase.delete(SettingsTable.NAME, SettingsTable.Cols.PARAMETER+"= ?",new String[]{parameter});
        mDatabase.insert(SettingsTable.NAME, null, contentValues);
    }

    public void updateLessons(List<Lesson> lessonsList){
        clearOldLessons();
        mLessonsList = lessonsList;
        for (Lesson lesson: mLessonsList
             ) {
            addLesson(lesson);
        }

    }

}
