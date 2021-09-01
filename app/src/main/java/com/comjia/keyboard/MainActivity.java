package com.comjia.keyboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout mActivityMain = findViewById(R.id.cl_activity_main);

//        SoftKeyboardStateHelper stateHelper = new SoftKeyboardStateHelper(mActivityMain);
//        stateHelper.addSoftKeyboardStateListener(new ISoftKeyboardStateListener() {
//            @Override
//            public void onOpened(int keyboardHeight) {
//                MyApplication.keyboardHeight = keyboardHeight;
//                Log.i("haha", "======open=====" + keyboardHeight);
//            }
//
//            @Override
//            public void onClosed() {
//                Log.i("haha", "=====close======" + MyApplication.keyboardHeight);
//            }
//        });
        mActivityMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("haha", "=====onClick======" + MyApplication.keyboardHeight);
                Log.i("haha","测试代码==========");
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });


    }
}