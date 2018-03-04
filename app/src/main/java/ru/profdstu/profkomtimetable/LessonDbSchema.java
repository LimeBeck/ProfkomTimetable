package ru.profdstu.profkomtimetable;

/**
 * Created by metal on 03.03.2018.
 */

public class LessonDbSchema {
    public static final class LessonTable{
        public static final String NAME="lessons";

        public static final class Cols{
            public static final String LESSON_NAME = "lesson_name";
            public static final String TEACHER_NAME = "teacher";
            public static final String AUDITORY = "auditory";
            public static final String LESSON_NUMBER = "lesson_number";
            public static final String WEEK_NUMBER = "week_number";
            public static final String DAY_NUMBER = "day_number";
        }
    }
}
