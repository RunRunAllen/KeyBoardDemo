package com.comjia.keyboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 *
 */
public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> msgList = new ArrayList<>();
    private KeyboardHelper keyboardHelper;
    private MsgListAdapter msgListAdapter;
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ConstraintLayout mRootlayout = findViewById(R.id.layout_main);
        mRecycleView = findViewById(R.id.recycler_view);
        CInputPanel mInputPanel = findViewById(R.id.chat_input_panel);
        CMorePanel mMorePanel = findViewById(R.id.more_panel);
        for (int i = 0; i < 20; i++) {
            msgList.add(i + "=======aaaa");
        }
        int height = 0;
        if (MyApplication.keyboardHeight == 0) {
            height = DensityUtil.getScreenHeight(this) / 5 * 2;
        } else {
            height = MyApplication.keyboardHeight;
        }
        KeyboardHelper keyboardHelper = new KeyboardHelper.Builder(this)
                .bindRootLayout(mRootlayout)
                .bindRecyclerView(mRecycleView)
                .bindInputPanel(mInputPanel)
                .bindMorePanel(mMorePanel)
                .setScrollBodyLayout(msgList.size() > 15)
                .setKeyboardHeight(height)
                .setSoftKeyboardListener(new ISoftKeyboardStateListener() {
                    @Override
                    public void onOpened(int keyboardHeight) {
                        MyApplication.keyboardHeight = keyboardHeight;
                    }
                    @Override
                    public void onClosed() {

                    }
                })
                .create();

        mRecycleView.setHasFixedSize(true);
        msgListAdapter = new MsgListAdapter(this);
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(msgListAdapter);
        mRecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    keyboardHelper.reset();
                }
                return false;
            }
        });
    }


    public class MsgListAdapter extends RecyclerView.Adapter {
        private Context context;

        public MsgListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_msg, parent, false);
            return new MsgListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView msg = holder.itemView.findViewById(R.id.tv_msg);
            msg.setText(msgList.get(position));

        }

        @Override
        public int getItemCount() {
            return msgList == null ? 0 : msgList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                TextView msgTextView = itemView.findViewById(R.id.tv_msg);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboardHelper != null) {
            keyboardHelper.release();
        }
    }
}
