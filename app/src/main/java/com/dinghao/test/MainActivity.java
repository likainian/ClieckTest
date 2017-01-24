package com.dinghao.test;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivityttt";
    private TextView mText;
    private int num;
    private Button mButton;
    private List<AccessibilityNodeInfo> list = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            findText(30,150,"Button");
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };
//    private IChimpDevice device;
//    private AdbBackend adb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        if(adb==null){
//            adb = new AdbBackend();
//        }
//        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);//取得相关系统服务
//        device = (AdbChimpDevice) adb.waitForConnection(8000, tm.getDeviceId());

//        ChimpChat chimpChat = ChimpChat.getInstance();
//        device = chimpChat.waitForConnection();
        handler.sendEmptyMessage(0);
        startService(new Intent(MainActivity.this, MyAccessibility.class));
    }

    private void initView() {
        mText = (TextView) findViewById(R.id.text);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                num++;
                mText.setText(""+num);
                break;
        }
    }
    public boolean findText(int x, int y, String name) {
        MyApplication application = (MyApplication) getApplication();
        AccessibilityNodeInfo info = application.getInfo();
        if(info!=null){
            recycle(info);
            for (int i = 0; i < list.size(); i++) {
                Rect rect = new Rect();
                list.get(i).getBoundsInScreen(rect);
                if(list.get(i).getText()!=null&&list.get(i).getText().toString().contains(name)&&rect.contains(x,y)){
                    Log.i(TAG, "lookFor: "+list.get(i).getText().toString().contains(name)+rect.contains(x,y)+rect.toString());
                    if(list.get(i).isClickable()){
                        list.get(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    return true;
                }
            }

        }
        return false;
    }
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            Log.i(TAG, "getRootInActiveWindow：" + info.toString());
            list.add(info);
        } else {
            Log.i(TAG, "getRootInActiveWindow：" + info.toString());
            list.add(info);
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    recycle(info.getChild(i));
                }
            }
        }
    }
}
