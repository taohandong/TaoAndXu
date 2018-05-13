package com.share.renderscriptdemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ToaHanDong on 2018/1/25.
 */

public class HouTaiService extends Service{
    private Context context = null;
    ActivityManager activityManager=null;
    @Override
    public void onCreate() {
        context = this;
        Log.d("66666","countDownTimer="+countDownTimer);
        if (null != countDownTimer) countDownTimer.start();
        activityManager= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (null!=countDownTimer)countDownTimer.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    CountDownTimer countDownTimer = new CountDownTimer(Long.MAX_VALUE, 2000) {
        @Override
        public void onTick(long millisUntilFinished) {
            boolean isRunning=isAppRunning(context,"com.shunzhi.zuoye");
            if (isRunning){
                activityManager.restartPackage("com.shunzhi.zuoye");
                activityManager.killBackgroundProcesses("com.shunzhi.zuoye");
            }
//            if (!isAppRunning(context, SystemUtils.getPackeageName(context))) {//判断没在运行
//                Intent intent = new Intent(context, StartActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                return;
//            }
//            if (isAppBackgroundRunning(context, SystemUtils.getPackeageName(context))) {
//                Intent intent = new Intent(context, StartActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                return;
//            }
        }

        @Override
        public void onFinish() {

        }

    };

    //判断app是否在后台运行
    public boolean isAppBackgroundRunning(Context context,String packageName){
        ActivityManager activityManager= (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList=activityManager.getRunningAppProcesses();
        if (null!=runningAppProcessInfoList){
            for (ActivityManager.RunningAppProcessInfo run :runningAppProcessInfoList
                    ) {
//                Log.d("5555","backpackageName="+run.processName);
//                Log.d("5555","run.processName="+run.processName+(run.importance!= ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE));
                if (run.processName.equals("com.android.gallery3d"))return false;//拍照的时候不阻止
//                verifyIfHasToCloseAnyApp();
                if (run.processName.startsWith(packageName)){
//                    Timber.i("----==importance : %s",run.importance);
                    return run.importance!= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            ||run.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                }
            }
        }
        return false;
    }

    private void getAllPackageInfo(){
        List<PackageInfo> list=new ArrayList<>();
        PackageManager packageManager=getPackageManager();
        List<PackageInfo> packageInfos=packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo=packageInfos.get(i);
            //系统非预装的app
            if ((packageInfo.applicationInfo.flags&packageInfo.applicationInfo.FLAG_SYSTEM)<=0){
                list.add(packageInfo);
                if (packageInfo.packageName.startsWith("com.tencent.mobileqq")){
                    boolean isRunning=isAppRunning(this,packageInfo.packageName);
//                    Log.d("5555","isRunning="+isRunning+"packageName="+packageInfo.packageName+"isBack="+isAppBackgroundRunning());
                }
            }
        }
    }




   /* //close any app
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void verifyIfHasToCloseAnyApp(){
        ActivityManager activityManager= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos=activityManager.getRunningTasks(Integer.MAX_VALUE);
        Log.d("5555","processName="+taskInfos.get(0).topActivity.getPackageName());
        for (int i = 0; i < taskInfos.size(); i++) {
            String processName=taskInfos.get(i).topActivity.getPackageName();
            if (processName.equals("tencent")){
                try {
                    Process process=Runtime.getRuntime().exec("kill");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String printForegroundTask() {
        String currentApp = "NULL";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        Log.e("adapter", "Current App in foreground is: " + currentApp);
        return currentApp;
    }*/

    // &&run.importance!= ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
    //判断app是否正在运行
    public boolean isAppRunning(Context context,String packageName){
        ActivityManager activityManager= (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList=activityManager.getRunningAppProcesses();
        if (null!=runningAppProcessInfoList){
            for (int i = 0; i < runningAppProcessInfoList.size(); i++) {
//                Log.d("5555","processName="+runningAppProcessInfoList.get(i).processName);
                if (runningAppProcessInfoList.get(i).processName.startsWith(packageName))return true;
            }
        }
        return false;
    }

   /* Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    public class DetectCalendarLaunchRunnable implements Runnable {

        @Override
        public void run() {
            String[] activePackages;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                activePackages = getActivePackages();
            } else {
                activePackages = getActivePackagesCompat();
            }
            if (activePackages != null) {
                for (String activePackage : activePackages) {
                    if (activePackage.equals("com.google.android.calendar")) {
                        //Calendar app is launched, do something
                    }
                }
            }
            myHandler.postDelayed(this, 1000);
        }


        String[] getActivePackagesCompat() {
            ActivityManager mActivityManager= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
            final ComponentName componentName = taskInfo.get(0).topActivity;
            final String[] activePackages = new String[1];
            activePackages[0] = componentName.getPackageName();
            return activePackages;
        }

        String[] getActivePackages() {
            ActivityManager mActivityManager= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            final Set<String> activePackages = new HashSet<String>();
            final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    activePackages.addAll(Arrays.asList(processInfo.pkgList));
                }
            }
            return activePackages.toArray(new String[activePackages.size()]);
        }
    }*/

}
