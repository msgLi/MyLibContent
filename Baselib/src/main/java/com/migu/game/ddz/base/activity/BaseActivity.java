package com.migu.game.ddz.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.migu.game.ddz.base.application.BaseApplication;

/**
 * Created by msg on 2017/7/28.
 */

public abstract class BaseActivity extends Activity implements ISelfActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BaseApplication.addActivity(this);
        super.onCreate(savedInstanceState);
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
            BaseApplication.addActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.removeActivity(this);
    }
}
