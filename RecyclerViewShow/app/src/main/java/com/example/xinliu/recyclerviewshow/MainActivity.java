package com.example.xinliu.recyclerviewshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView idRecyclerView;
    private List<Integer> datas;
    private int[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        idRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        idRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        idRecyclerView.setAdapter(new MyAdapter());
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            datas.add(i);
        }
        img = new int[] {
                R.drawable.iv1, R.drawable.iv2,
                R.drawable.iv3, R.drawable.iv4,
                R.drawable.iv5, R.drawable.iv6,
                R.drawable.iv7, R.drawable.iv8,
                R.drawable.iv9, R.drawable.iv10
        };
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(
                    LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tvNum.setText("这是第" + datas.get(position) + "个精灵");
            holder.ivNum.setImageResource(img[position]);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvNum;
            ImageView ivNum;

            public MyViewHolder(View view) {
                super(view);
                tvNum = (TextView) view.findViewById(R.id.tv_num);
                ivNum = (ImageView) view.findViewById(R.id.iv_num);
            }
        }
    }
}
