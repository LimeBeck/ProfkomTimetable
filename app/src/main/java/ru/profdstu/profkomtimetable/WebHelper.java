package ru.profdstu.profkomtimetable;


import android.content.Context;
import android.media.MediaRouter;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metal on 19.03.2018.
 */

public class WebHelper {
    private static WebHelper sWebHelper;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static final String groupsUrl = "http://thisistest.ru/get_groups.php";
    private static final String teachersUrl = "http://thisistest.ru/get_teachers.php";
    private static final String sheduleUrl = "http://thisistest.ru/get_shed_group.php?nameGroup=";
    private static final String sheduleTeacherUrl = "http://thisistest.ru/get_shed_teacher.php?nameTeacher=";
    private List<Lesson> mLessonList;


    public static ArrayList<String> mGroups;


    public static synchronized WebHelper getInstance(Context context) {

        if (sWebHelper == null) {
            sWebHelper = new WebHelper(context);
        }
        return sWebHelper;

    }

    private WebHelper(Context context) {
        mGroups = new ArrayList<>();
        mLessonList = new ArrayList<>();
        mCtx = context;
        mRequestQueue = getRequestQueue();
        makeGroupRequest();

    }

    public RequestQueue getRequestQueue() {
        return Volley.newRequestQueue(mCtx);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public ArrayList<String> getGroups() {
        return mGroups;
    }

    public void makeGroupRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, groupsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("groups");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        mGroups.add(obj.getString("name_group"));
                    }
                    mGroups.size();

                } catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    Log.e("LOGE", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGE", error.toString());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }

    public void makeLessonsRequest(String group, final VolleyCallback callback, final TimeTableData timeTableData){
        mLessonList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, sheduleUrl + group, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray(LessonDbSchema.LessonJSON.JSON_ROOT_NODE_NAME);
                    for(int i=0; i<array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        Boolean duplicateWeek = false;
                        int lessonNumber = jsonObject.getInt(LessonDbSchema.LessonJSON.JsonCols.LESSON_NUMBER);
                        int dayNumber = jsonObject.getInt(LessonDbSchema.LessonJSON.JsonCols.DAY_NUMBER);
                        int weekNumber=1;
                        switch (jsonObject.getString(LessonDbSchema.LessonJSON.JsonCols.WEEK_NUMBER)){
                            case " ":
                                duplicateWeek=true;
                                break;
                            case "Ч":
                                weekNumber=2;
                                break;
                            case "Н":
                                weekNumber=1;
                                break;
                        }

                        String auditory = jsonObject.getString(LessonDbSchema.LessonJSON.JsonCols.AUDITORY);
                        String lessonType = jsonObject.getString(LessonDbSchema.LessonJSON.JsonCols.LESSON_TYPE);
                        String lessonName = jsonObject.getString(LessonDbSchema.LessonJSON.JsonCols.LESSON_NAME);
                        String teacherName = jsonObject.getString(LessonDbSchema.LessonJSON.JsonCols.TEACHER_NAME);
                        switch (lessonType){
                            case "Лабораторные":
                                lessonName = "лаб. "+lessonName;
                                break;
                            case "пр. зан.":
                                lessonName = lessonName = "пр. "+lessonName;
                                break;
                            case "лекция":
                                lessonName = lessonName = "лек. "+lessonName;
                                break;
                        }
                        Lesson lesson = new Lesson(lessonName,teacherName, lessonNumber, weekNumber,dayNumber, auditory);
                        mLessonList.add(lesson);
                        if(duplicateWeek){
                            lesson = new Lesson(lessonName,teacherName, lessonNumber, weekNumber+1,dayNumber, auditory);
                            mLessonList.add(lesson);
                        }
                        timeTableData.updateLessons(getLessonList());
                        callback.onSuccess();
                    }

                } catch (JSONException e) {
                    Log.e("LOGE", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGE", error.toString());
            }
        });
        addToRequestQueue(jsonObjectRequest);
    }

    public interface VolleyCallback{
        void onSuccess();
    }

    public List<Lesson> getLessonList() {
        return mLessonList;
    }
}
