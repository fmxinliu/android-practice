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
public class Fragment2 extends Fragment {
    private String page = "页面2";

    public Fragment2() {
        // Required empty public constructor
    }

    public String getPage() {
        return page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        MainActivity activity = (MainActivity) getActivity();
        int image = activity.getImage(1);
        ImageView ivTwo = (ImageView) view.findViewById(R.id.iv_two);
        ivTwo.setImageResource(image);
        return view;
    }

}
