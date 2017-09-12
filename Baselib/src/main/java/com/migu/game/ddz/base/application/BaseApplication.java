package com.migu.game.ddz.base.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.migu.game.ddz.base.activity.ActivityCompmentControl;

import java.util.ArrayList;

/**
 * Created by msg on 2017/7/28.
 */

public class BaseApplication extends MultiDexApplication {

    public static ArrayList<Activity> activityList = new ArrayList<Activity>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public void startActivity(Intent intent, boolean check){
        if(!check){
            super.startActivity(intent);
        }else{
            startActivity(intent);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        intent = ActivityCompmentControl.getStartActivityIntent(this,intent);
        if(intent != null){
            super.startActivity(intent);
        }
    }

    public static void addActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void finishAllActivity(){
        for(Activity activity: activityList){
            activity.finish();
        }
    }

    public static void exitApp(){
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        System.gc();
    }

}
