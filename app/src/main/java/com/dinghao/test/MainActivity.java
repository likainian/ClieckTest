package com.dinghao.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivityttt";
    private TextView mText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

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
                MyApplication application = (MyApplication) getApplication();
                AccessibilityNodeInfo info = application.getInfo();
                if(info!=null){
                    Log.i(TAG, "onClick: "+info.toString());
                    recycle(application.getInfo());
                }
                break;
        }
    }
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            Log.i(TAG, "getRootInActiveWindow：" + info.toString());
        } else {
            Log.i(TAG, "getRootInActiveWindow：" + info.toString());
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    recycle(info.getChild(i));
                }
            }
        }
    }
}
