package com.share.renderscriptdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1=new Intent(this,HouTaiService.class);
        startService(intent1);
        ActivityCompat.requestPermissions(this,new String[]{"android.permission.FORCE_STOP_PACKAGES",
                "android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"},1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1) Toast.makeText(this,"权限获取成功",Toast.LENGTH_SHORT).show();
        getSpecificTypeOfFile(this,new String[]{".ppt",".pptx"});
        getContentResolver().registerContentObserver(MediaStore.Files.getContentUri("external"),true,new MyContentObserver(new Handler()));
    }


    public static void getSpecificTypeOfFile(Context context, String[] extension)
    {
        //从外存中获取
        Uri fileUri= MediaStore.Files.getContentUri("external");
        Log.d("tagtag", "fileUri="+fileUri.toString());
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection=new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };
        //构造筛选语句
        String selection="";
        for(int i=0;i<extension.length;i++)
        {
            if(i!=0)
            {
                selection=selection+" OR ";
            }
            selection=selection+ MediaStore.Files.FileColumns.DATA+" LIKE '%"+extension[i]+"'";
        }
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder= MediaStore.Files.FileColumns.DATE_MODIFIED;
        //获取内容解析器对象
        ContentResolver resolver=context.getContentResolver();
        //获取游标
        Cursor cursor=resolver.query(fileUri, projection, selection, null, sortOrder);
        if(cursor==null)
            return;
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件，优先显示）
        if(cursor.moveToLast())
        {
            do{
                //输出文件的完整路径
                String data=cursor.getString(0);
                Log.d("tagtag", data);
            }while(cursor.moveToPrevious());
        }
        cursor.close();

    }

    private class MyContentObserver extends ContentObserver{

        public MyContentObserver(Handler handler) {
            super(handler);
            Log.d("tagtag","handler="+handler+"Media="+MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("tagtag","selfChange="+selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.d("tagtag","uri="+uri.toString());
        }
    }

}
