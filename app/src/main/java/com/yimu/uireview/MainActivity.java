package com.yimu.uireview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.yimu.uireview.library.UiReview;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.content_container)
    ViewGroup mContentContainer;
    @InjectView(R.id.text)
    TextView mFrodoTextView;
    @InjectView(R.id.seekBar)
    SeekBar mSeekBar;
    @InjectView(R.id.list_view)
    ListView mListView;

    private MyAdapter mAdapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mFrodoTextView.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mAdapter = new MyAdapter(this, R.layout.abc_action_bar_title_item);
        mListView.setAdapter(mAdapter);
        for(index =0;index < 20;index++){
            mAdapter.add(String.valueOf(index));
        }
    }

    @OnClick(R.id.add5)
    void onAddClick(View view){
        for(int i = index;i < index + 5;i++){
            mAdapter.add(String.valueOf(i));
        }
        index += 5;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        UiReview.getInstance().resume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        UiReview.getInstance().pause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view;
            if (convertView == null) {
                view = new TextView(parent.getContext());
            } else {
                view = (TextView) convertView;
            }
            view.setTextSize(12 + position);
            view.setText(String.format("%d : size=%d %s", position, 12 + position, getItem(position)));
            return view;
        }
    }
}
