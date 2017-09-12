package com.migu.game.ddz.base.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by msg on 2017/7/28.
 */

public class ActivityCompmentControl {

    public static final Map<String,String> extActivityList = new HashMap<>();

    static{
        extActivityList.put("com.cmcc.migusso.sdk.activity.UpgradeUserActivity","com.cmcc.migusso.sdk.activity.UpgradeUserActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.LoginActivity","com.cmcc.migusso.sdk.activity.LoginActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.SmsLoginActivity","com.cmcc.migusso.sdk.activity.SmsLoginActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.RegisterActivity","com.cmcc.migusso.sdk.activity.RegisterActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.FindPasswordActivity","com.cmcc.migusso.sdk.activity.FindPasswordActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.UserProtocolActivity","com.cmcc.migusso.sdk.activity.UserProtocolActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.UserManageActivity","com.cmcc.migusso.sdk.activity.UserManageActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.ChangeNickNameActivity","com.cmcc.migusso.sdk.activity.ChangeNickNameActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.BindPhoneNumActivity","com.cmcc.migusso.sdk.activity.BindPhoneNumActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.ChangePasswordActivity","com.cmcc.migusso.sdk.activity.ChangePasswordActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.SetPasswordActivity","com.cmcc.migusso.sdk.activity.SetPasswordActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.VerifyOldPhoneActivity","com.cmcc.migusso.sdk.activity.VerifyOldPhoneActivityImpl");
        extActivityList.put("com.cmcc.migusso.sdk.activity.BindNewPhoneActivity","com.cmcc.migusso.sdk.activity.BindNewPhoneActivityImpl");
        extActivityList.put("com.mob.tools.MobUIShell","com.mob.tools.MobUIShellImpl");

    }

    public static final List<String> extActivityWhite=new ArrayList<>();

    static {
        extActivityWhite.add("com.tencent.mm.plugin.base.stub.WXEntryActivity");
    }

    public static Intent getStartActivityIntent(Context ctx, Intent intent) {
        // TODO Auto-generated method stub
        ComponentName componentName = intent.getComponent();
        if(componentName == null){
            return null;
        }
        String nextClassName = componentName.getClassName();
        if(extActivityWhite.contains(nextClassName)){
            return intent;
        }
        String nextClassNameImpl = extActivityList.get(nextClassName);
        if(!TextUtils.isEmpty(nextClassNameImpl)){
            nextClassName = nextClassNameImpl;
        }
        if(TextUtils.isEmpty(nextClassName)){
            return null;
        }
        try {
            Class<?> clz = Class.forName(nextClassName);
            List<Class<?>> interfaces = getAllInterfaces(clz);
            if(interfaces != null && interfaces.size() > 0){
                if(interfaces.contains(ISelfActivity.class)){
                    intent.setClass(ctx,clz);
                    return intent;
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private static List<Class<?>> getAllInterfaces(Class<?> clz){
        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        if(clz != Object.class){
            Class<?>[] in = clz.getInterfaces();
            if(in != null && in.length > 0){
                for(Class<?> c : in ){
                    interfaces.add(c);
                }
            }
            Class<?> superCls = clz.getSuperclass();
            List<Class<?>> superInterfaces = getAllInterfaces(superCls);
            if(superInterfaces != null && superInterfaces.size() > 0){
                interfaces.addAll(superInterfaces);
            }
            return interfaces;
        }else{
            return null;
        }
    }
}
