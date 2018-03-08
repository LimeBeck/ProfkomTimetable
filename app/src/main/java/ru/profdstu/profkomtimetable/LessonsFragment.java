package ru.profdstu.profkomtimetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by metal on 03.03.2018.
 */

/*
В классе реализована работа с основной активностью, вывод пар на экран

 */

public class LessonsFragment extends Fragment {

    private RecyclerView mLessonRecyclerView;
    private LessonAdapter mAdapter;
    private Boolean mWeekEven = IsWeekEven();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_day_list, container, false);
        mLessonRecyclerView = (RecyclerView) view.findViewById(R.id.day_recycler_view);
        mLessonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_day_list, menu);

        MenuItem weekButton = menu.findItem(R.id.change_week);
        if (mWeekEven){
            weekButton.setTitle(R.string.to_nor_even_week);
        }
        else{
            weekButton.setTitle(R.string.to_even_week);
        }

        MenuItem settingsButton = menu.findItem(R.id.settings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.change_week:
                mWeekEven = !mWeekEven;
                getActivity().invalidateOptionsMenu();
                updateUI();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void updateUI(){
        TimeTableData timeTable = TimeTableData.get(getActivity());
        List<Lesson> lessonList = timeTable.getLessonsList();

        //Тестовый набор данных!!!
        Lesson first = new Lesson("Первая", "Препод 1", 1,1,1,"8-143");
        lessonList.add(first);
        Lesson second = new Lesson("Вторая", "Препод 2", 2,1,1,"8-143");
        lessonList.add(second);


        if(mAdapter == null){
            mAdapter = new LessonAdapter(lessonList);
            mLessonRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setLessonList(lessonList);
            mAdapter.notifyDataSetChanged();
        }

    }

    private Boolean IsWeekEven(){
        int days;
        int firtsSeptemberDay;
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        Calendar firstSeptember = new GregorianCalendar();
        calendar.setTime(today);
        int todayDay = calendar.get(Calendar.DAY_OF_YEAR);
        if(calendar.get(Calendar.MONTH)>=9){
            firstSeptember.set(calendar.get(Calendar.YEAR),9,1);
            firtsSeptemberDay = firstSeptember.get(Calendar.DAY_OF_YEAR);
            days = todayDay - firtsSeptemberDay;
        }
        else {
            firstSeptember.set(calendar.get(Calendar.YEAR)-1,9,1);
            Calendar lastDayOfYear = new GregorianCalendar(calendar.get(Calendar.YEAR)-1,12,1);
            int lastDay = lastDayOfYear.get(Calendar.DAY_OF_YEAR);
            firtsSeptemberDay = firstSeptember.get(Calendar.DAY_OF_YEAR);

            days = firtsSeptemberDay-lastDay+todayDay;
        }

        int weeks = days/7;


        if(weeks%2!=0){
            return false;
        }
        else{
            return true;
        }

    }

    private class LessonHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Lesson mLesson;

        private TextView mLessonName;
        private TextView mAuditory;
        private TextView mTeacher;
        private TextView mLessonNumber;

        public LessonHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_lesson, parent, false));
            //Добавить реакцию на клики: itemView.SetOnClockListener(this);

            mAuditory = (TextView) itemView.findViewById(R.id.auditory);
            mLessonName = (TextView) itemView.findViewById(R.id.lesson_name);
            mTeacher = (TextView) itemView.findViewById(R.id.teacher_name);
            mLessonNumber = (TextView) itemView.findViewById(R.id.lesson_number);
        }

        public void bind(Lesson lesson){
            mLessonNumber.setText(String.valueOf(lesson.getLessonNumber()));
            mLessonName.setText(lesson.getLessonName());
            mTeacher.setText(lesson.getTeacherName());
            mAuditory.setText(lesson.getAuditory());
        }

        @Override
        public void onClick(View view){

        }
    }

    private  class LessonAdapter extends RecyclerView.Adapter<LessonHolder>{
        private List<Lesson> mLessonList;

        public LessonAdapter (List<Lesson> lessonsList) {
            mLessonList = lessonsList;
        }

        @Override
        public LessonHolder onCreateViewHolder (ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LessonHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(LessonHolder holder, int position){
            Lesson lesson = mLessonList.get(position);
            holder.bind(lesson);
        }

        @Override
        public int getItemCount(){return mLessonList.size();}

        public void setLessonList(List<Lesson> lessonList){mLessonList = lessonList;}
    }
}
