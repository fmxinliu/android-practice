package com.example.xinliu.showfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Fragment 包含在 Activity 中，切换图片并不创建新的 Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int[] image = { R.drawable.fragment1, R.drawable.fragment2, R.drawable.fragment3 };
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_show).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                changeFragment(new Fragment1());
                break;
            case R.id.btn_two:
                changeFragment(new Fragment2());
                break;
            case R.id.btn_three:
                changeFragment(new Fragment3());
                break;
            case R.id.btn_show:
                Fragment fragment = getDataFromFragment();
                if (fragment instanceof Fragment1) {
                    Toast.makeText(this, ((Fragment1) fragment).getPage(), Toast.LENGTH_SHORT).show();
                } else if (fragment instanceof Fragment2) {
                    Toast.makeText(this, ((Fragment2) fragment).getPage(), Toast.LENGTH_SHORT).show();
                } else if (fragment instanceof Fragment3) {
                    Toast.makeText(this, ((Fragment3) fragment).getPage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public int getImage(int id) {
        return image[id];
    }

    /**
     * 切换 Fragment
     * @param fragment
     */
    private void changeFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fm, fragment);
        transaction.commit();
    }

    /**
     * Activity 获取 Fragment 中的数据
     */
    private Fragment getDataFromFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fm);
        return fragment;
    }
}
