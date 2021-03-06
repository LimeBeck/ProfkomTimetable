package ru.profdstu.profkomtimetable;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


/**
 * Created by metal on 08.03.2018.
 */

public class DaysFragment extends Fragment {

    private static String DAY_ARGUMENT_KEY = "day_arg";
    private static String WEEK_ARGUMENT_KEY = "week_arg";
    TimeTableData mTimeTableData;
    List<Lesson> mLessonList;
    private TextView mFreeday;
    private TextView mWeekday;
    private boolean mWeekEven = WeekCompute.IsWeekEven();

    public static DaysFragment newInstance(int day, int week) {

        Bundle args = new Bundle();
        args.putInt(DAY_ARGUMENT_KEY,day);
        args.putInt(WEEK_ARGUMENT_KEY, week);
        DaysFragment fragment = new DaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownDay(){
        return getArguments().getInt(DAY_ARGUMENT_KEY);
    }
    public int getShownWeek(){
        return getArguments().getInt(WEEK_ARGUMENT_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        mTimeTableData = TimeTableData.get(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_day, container, false);

        mWeekday = (TextView) v.findViewById(R.id.day_of_week_text);
        mFreeday = (TextView)v.findViewById(R.id.freeday);


        switch (getShownDay()){
            case 1:
                mWeekday.setText(R.string.monday);
                break;
            case 2:
                mWeekday.setText(R.string.tuesday);
                break;
            case 3:
                mWeekday.setText(R.string.wednesday);
                break;
            case 4:
                mWeekday.setText(R.string.thursday);
                break;
            case 5:
                mWeekday.setText(R.string.friday);
                break;
            case 6:
                mWeekday.setText(R.string.saturday);
                break;
                default:
                    mWeekday.setText(R.string.default_day);
        }

        if((getShownDay()==(WeekCompute.Today()-1))&&(getShownWeek()==WeekCompute.WeekEven())){
            v.setBackgroundColor(Color.CYAN);
        }
        FragmentManager fm = getChildFragmentManager();
        int week = getShownWeek();
        int day = getShownDay();
        mLessonList = mTimeTableData.getLessonByWeekDay(week,day);
        if(mLessonList != null){
            mFreeday.setVisibility(View.GONE);
            for (Lesson lesson: mLessonList
                    ) {
                Fragment fragment = LessonFragment.newInstance(lesson.getUUID().toString());
                fm.beginTransaction().add(R.id.day_container,fragment).commit();
            }

        }
        return v;
    }


}
