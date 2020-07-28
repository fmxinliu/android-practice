package com.example.xinliu.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

public class VideoChooseActivity extends Activity {
    private LinkedList<MovieInfo> mLinkedList;
    private LayoutInflater mInflater;
    View root;
    private EditText urlInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog);

        mLinkedList = VideoPlayerActivity.playList;

        mInflater = getLayoutInflater();
        ImageButton iButton = (ImageButton) findViewById(R.id.cancel);
        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoChooseActivity.this.finish();
            }
        });

        ListView myListView = (ListView) findViewById(R.id.list);
        myListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mLinkedList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list, null);
                }
                TextView text = (TextView) convertView.findViewById(R.id.text);
                text.setText(mLinkedList.get(position).getDisplayName());

                return convertView;
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("CHOOSE", position);
                VideoChooseActivity.this.setResult(Activity.RESULT_OK, intent);
                VideoChooseActivity.this.finish();
            }
        });

        initUri();

        myListView.requestFocus();
    }

    private void initUri() {
        urlInput = (EditText) findViewById(R.id.url_input);
        urlInput.setText("http://");
        urlInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("actionId", "" + actionId);
                Editable url = ((EditText) v).getEditableText();
                Intent intent = new Intent();
                intent.putExtra("CHOOSE_URL", url.toString());
                VideoChooseActivity.this.setResult(Activity.RESULT_OK, intent);
                VideoChooseActivity.this.finish();

                return false;
            }
        });
    }
}
