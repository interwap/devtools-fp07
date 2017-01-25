package com.praescient.components.fgtit_fp07;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class ActivityList {

    private List<Activity> activityList = new LinkedList<Activity>();
    @SuppressLint("StaticFieldLeak")
    private static ActivityList instance;
    @SuppressLint("StaticFieldLeak")
    private Context pcontext;
    @SuppressLint("StaticFieldLeak")
    private Context ccontext;

    public boolean 	IsUseNFC=false;
    public String  	PassWord="";
    public String	DeviceSN="";
    public String	WebAddr="";
    public String	UpdateUrl="";
    public String	WebService="";
    public boolean	IsOnline=false;
    public boolean	IsUpImage=false;
    public float    MapZoom=18.0f;
    public double 	MapLat=0.0;
    public double 	MapLng=0.0;

    private ActivityList(){
    }

    @SuppressLint("StaticFieldLeak")
    public static ActivityList getInstance() {
        if(null == instance) {
            instance = new ActivityList();
        }
        return instance;
    }

    public void setMainContext(Context context){
        pcontext=context;
    }

    public void setCurrContext(Context context){
        ccontext=context;
    }

    public void Relogon(){
        for(Activity activity:activityList) {
            activity.finish();
        }
    }

    public Context getCurrContext(){
        return ccontext;
    }

    public void addActivity(Activity activity){
        ccontext=activity;
        activityList.add(activity);
    }

    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public void exit(){
        for(Activity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }

}
