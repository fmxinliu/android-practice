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
public class Fragment3 extends Fragment {
    private String page = "页面3";

    public Fragment3() {
        // Required empty public constructor
    }

    public String getPage() {
        return page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        MainActivity activity = (MainActivity) getActivity();
        int image = activity.getImage(2);
        ImageView ivThree = (ImageView) view.findViewById(R.id.iv_three);
        ivThree.setImageResource(image);
        return view;
    }

}
