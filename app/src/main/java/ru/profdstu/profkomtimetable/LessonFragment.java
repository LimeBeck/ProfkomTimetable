package ru.profdstu.profkomtimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by metal on 09.03.2018.
 */

public class LessonFragment extends Fragment {

    private static String LESSON_ARGUMENT_KEY = "lesson_arg";

    private Lesson mLesson;
    private TextView mLessonName;
    private TextView mAuditory;
    private TextView mTeacher;
    private TextView mLessonNumber;
    private TextView mTime;

    public static LessonFragment newInstance(String lesson_uuid) {

        Bundle args = new Bundle();

        args.putString(LESSON_ARGUMENT_KEY, lesson_uuid);
        LessonFragment fragment = new LessonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public String getLessonArgumentKey(){
        return getArguments().getString(LESSON_ARGUMENT_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID uuid = UUID.fromString(getLessonArgumentKey());
        TimeTableData timeTableData = TimeTableData.get(getActivity());
        mLesson = timeTableData.getLessonByUUID(uuid);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_lesson, container, false);

        mAuditory = (TextView) v.findViewById(R.id.auditory);
        mLessonName = (TextView) v.findViewById(R.id.lesson_name);
        mLessonNumber = (TextView) v.findViewById(R.id.lesson_number);
        mTeacher = (TextView) v.findViewById(R.id.teacher_name);
        mTime = (TextView) v.findViewById(R.id.time);


        mLessonNumber.setText(String.valueOf(mLesson.getLessonNumber()));
        mTeacher.setText(mLesson.getTeacherName());
        mAuditory.setText(mLesson.getAuditory());
        mLessonName.setText(mLesson.getLessonName());

        switch (mLesson.getLessonNumber()){
            case 1:
                mTime.setText(R.string.time_lesson_1);
                break;
            case 2:
                mTime.setText(R.string.time_lesson_2);
                break;
            case 3:
                mTime.setText(R.string.time_lesson_3);
                break;
            case 4:
                mTime.setText(R.string.time_lesson_4);
                break;
            case 5:
                mTime.setText(R.string.time_lesson_5);
                break;
            case 6:
                mTime.setText(R.string.time_lesson_6);
                break;
            default:
                mTime.setText(null);
        }

        return v;
    }


}
