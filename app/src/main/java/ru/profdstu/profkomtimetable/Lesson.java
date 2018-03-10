package ru.profdstu.profkomtimetable;

import java.util.UUID;

/**
 * Created by metal on 03.03.2018.
 */

/*
Класс для описания данных
 */

public class Lesson {

    private UUID mUUID;
    private String mLessonName;
    private String mTeacherName;
    private int mLessonNumber;
    private int mWeekNumber;
    private int mDayNumber;
    private String mAuditory;

    public Lesson(UUID UUID, String lessonName, String teacherName, int lessonNumber, int weekNumber, int dayNumber, String auditory) {
        mUUID = UUID;
        mLessonName = lessonName;
        mTeacherName = teacherName;
        mLessonNumber = lessonNumber;
        mWeekNumber = weekNumber;
        mDayNumber = dayNumber;
        mAuditory = auditory;
    }

    public Lesson(String lessonName, String teacherName, int lessonNumber, int weekNumber, int dayNumber, String auditory) {
        mUUID = UUID.randomUUID();
        mLessonName = lessonName;
        mTeacherName = teacherName;
        mLessonNumber = lessonNumber;
        mWeekNumber = weekNumber;
        mDayNumber = dayNumber;
        mAuditory = auditory;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getLessonName() {
        return mLessonName;
    }

    public void setLessonName(String lessonName) {
        mLessonName = lessonName;
    }

    public String getTeacherName() {
        return mTeacherName;
    }

    public void setTeacherName(String teacherName) {
        mTeacherName = teacherName;
    }

    public int getLessonNumber() {
        return mLessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        mLessonNumber = lessonNumber;
    }

    public int getWeekNumber() {
        return mWeekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        mWeekNumber = weekNumber;
    }

    public int getDayNumber() {
        return mDayNumber;
    }

    public void setDayNumber(int dayNumber) {
        mDayNumber = dayNumber;
    }

    public String getAuditory() {
        return mAuditory;
    }

    public void setAuditory(String auditory) {
        mAuditory = auditory;
    }
}
