package ru.profdstu.profkomtimetable;


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


/*
В классе реализована работа с основной активностью, вывод пар на экран

 */

public class LessonsFragment extends Fragment {

    private RecyclerView mDayRecyclerView;
    private DayAdapter mAdapter;
    //private LessonAdapter mAdapter;
    private Boolean mWeekEven = IsWeekEven();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_day_list, container, false);
        mDayRecyclerView = (RecyclerView) view.findViewById(R.id.day_recycler_view);
        mDayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        /*List<Lesson> lessonList = timeTable.getLessonsList();

        //Тестовый набор данных!!!
        Lesson first = new Lesson("Первая", "Препод 1", 1,2,1,"8-143");
        timeTable.addLesson(first);
        Lesson second = new Lesson("Вторая", "Препод 2", 2,2,1,"8-143");
        timeTable.addLesson(second);
        Lesson third = new Lesson("Третья", "Препод 2", 2,2,2,"8-143");
        timeTable.addLesson(third);*/


        if(mAdapter == null){
            mAdapter = new DayAdapter(timeTable);
            mDayRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setTimeTableData(timeTable);
            mAdapter.notifyDataSetChanged();
        }

        /*if(mAdapter == null){
            mAdapter = new LessonAdapter(lessonList);
            mLessonRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setLessonList(lessonList);
            mAdapter.notifyDataSetChanged();
        }*/

    }

    private int Weeks(){
        int days;
        int firstSeptemberDay;
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        Calendar firstSeptember = new GregorianCalendar();
        calendar.setTime(today);
        int todayDay = calendar.get(Calendar.DAY_OF_YEAR);
        if(calendar.get(Calendar.MONTH)>=9){
            firstSeptember.set(calendar.get(Calendar.YEAR),9,1);
            firstSeptemberDay = firstSeptember.get(Calendar.DAY_OF_YEAR);
            days = todayDay - firstSeptemberDay;
        }
        else {
            firstSeptember.set(calendar.get(Calendar.YEAR)-1,9,1);
            Calendar lastDayOfYear = new GregorianCalendar(calendar.get(Calendar.YEAR)-1,12,1);
            int lastDay = lastDayOfYear.get(Calendar.DAY_OF_YEAR);
            firstSeptemberDay = firstSeptember.get(Calendar.DAY_OF_YEAR);

            days = firstSeptemberDay-lastDay+todayDay;
        }

        return days/7;
    }

    private Boolean IsWeekEven(){
        return Weeks() % 2 == 0;
    }

    private int WeekEven(){
        if(Weeks()%2!=0){
            return 1;
        }
        else{
            return 2;
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
            mLesson = lesson;

            mLessonNumber.setText(String.valueOf(mLesson.getLessonNumber()));
            mLessonName.setText(mLesson.getLessonName());
            mTeacher.setText(mLesson.getTeacherName());
            mAuditory.setText(mLesson.getAuditory());
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

    private class DayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LessonAdapter mAdapter;
        private List<Lesson> mLessonsList;
        private TextView mWeekday;
        private RecyclerView mLessonRecyclerView;

        public DayHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_day, parent,false));
            View view = inflater.inflate(R.layout.fragment_day, parent, false);
            mLessonRecyclerView = (RecyclerView) view.findViewById(R.id.day_container) ;
            mDayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mWeekday = (TextView) itemView.findViewById(R.id.day_of_week_text);
        }

        public void bind(List<Lesson> lessonsList){
            mLessonsList = lessonsList;

            switch (lessonsList.get(0).getDayNumber()) {
                case 1:
                    mWeekday.setText(R.string.monday);
                case 2:
                    mWeekday.setText(R.string.tuesday);
                case 3:
                    mWeekday.setText(R.string.wednesday);
                case 4:
                    mWeekday.setText(R.string.thursday);
                case 5:
                    mWeekday.setText(R.string.friday);
                case 6:
                    mWeekday.setText(R.string.saturday);
                default:
                    mWeekday.setText("Error");

            }

            if(mAdapter == null){
                mAdapter = new LessonAdapter(mLessonsList);
                mLessonRecyclerView.setAdapter(mAdapter);
            }
            else{
                mAdapter.setLessonList(mLessonsList);
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onClick(View view){

        }
    }

    private class DayAdapter extends RecyclerView.Adapter<DayHolder>{

        private TimeTableData mTimeTableData;

        public DayAdapter(TimeTableData timeTableData){
            mTimeTableData = timeTableData;
        }

        @Override
        public DayHolder onCreateViewHolder (ViewGroup parent, int ViewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DayHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DayHolder holder, int position){
            List<Lesson> lessonList = mTimeTableData.getLessonByWeekDay(WeekEven(),position+1);
            holder.bind(lessonList);
        }

        @Override
        public int getItemCount(){
            int count = mTimeTableData.getDaysInWeek(WeekEven());
            return count;}

        public void setTimeTableData(TimeTableData timeTableData) {
            mTimeTableData = timeTableData;
        }

    }

}
