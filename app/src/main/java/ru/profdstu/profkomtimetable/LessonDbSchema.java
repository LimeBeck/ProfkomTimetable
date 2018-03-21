package ru.profdstu.profkomtimetable;

/**
 * Created by metal on 03.03.2018.
 */

/*
Схема БД для удобства работы
 */

public class LessonDbSchema {
    public static final class LessonTable {
        public static final String NAME = "lessons";

        public static final class Cols {
            public static final String LESSON_ID = "uuid";
            public static final String LESSON_NAME = "lesson_name";
            public static final String TEACHER_NAME = "teacher";
            public static final String AUDITORY = "auditory";
            public static final String LESSON_NUMBER = "lesson_number";
            public static final String WEEK_NUMBER = "week_number";
            public static final String DAY_NUMBER = "day_number";
        }

    }

    public static final class SettingsTable {
        public static final String NAME = "settings";

        public static final class Cols {
            public static final String PARAMETER = "parameters";
            public static final String VALUES = "set_values";
        }
    }

    public static final class LessonJSON{
        public static final String JSON_ROOT_NODE_NAME = "shed_group";
        public static final class JsonCols{
            public static final String LESSON_ID = "ID";
            public static final String LESSON_NAME = "Subject";
            public static final String TEACHER_NAME = "Teacher";
            public static final String AUDITORY = "Aud";
            public static final String LESSON_NUMBER = "Les";
            public static final String WEEK_NUMBER = "Week";
            public static final String DAY_NUMBER = "Day";
            public static final String LESSON_TYPE = "Subj_type";
            public static final String TEACHER_COUNT = "Subg";
        }

        public static final class JsonGroups{
            public static final String JSON_ROOT_NODE_NAME = "groups";
            public static final String GROUP_NODE_NAME = "name_group";
        }
    }
}
