package ru.profdstu.profkomtimetable;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import static ru.profdstu.profkomtimetable.LessonDbSchema.*;

/**
 * Created by metal on 03.03.2018.
 */

/*
Оболочка для работы с курсором БД
 */

public class LessonCursorWrapper extends CursorWrapper {

    public LessonCursorWrapper(Cursor cursor) {super(cursor);}

    public Lesson getLesson(){
        String uuidString = getString(getColumnIndex(LessonTable.Cols.LESSON_ID));
        String lessonName = getString(getColumnIndex(LessonTable.Cols.LESSON_NAME));
        String teacherName = getString(getColumnIndex(LessonTable.Cols.TEACHER_NAME));
        String auditory = getString(getColumnIndex(LessonTable.Cols.AUDITORY));
        int day = getInt(getColumnIndex(LessonTable.Cols.DAY_NUMBER));
        int week = getInt(getColumnIndex(LessonTable.Cols.WEEK_NUMBER));
        int lessonNumber = getInt(getColumnIndex(LessonTable.Cols.LESSON_NUMBER));

        Lesson lesson = new Lesson(UUID.fromString(uuidString), lessonName, teacherName, lessonNumber, week,day,auditory);

        return lesson;
    }
}
