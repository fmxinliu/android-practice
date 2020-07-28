package com.example.xinliu.showfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    private String page = "页面1";

    public Fragment1() {
        // Required empty public constructor
    }

    public String getPage() {
        return page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Fragment 获取 Activity 中的数据
         */
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        ImageView ivOne = (ImageView) view.findViewById(R.id.iv_one);
        ivOne.setImageResource(getDataFromActivity());
        return view;
    }

    /**
     * Fragment 获取 Activity 中的数据
     */
    private int getDataFromActivity() {
        MainActivity activity = (MainActivity) getActivity();
        int image = activity.getImage(0);
        return image;
    }
}
