package com.rrajath.orange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rrajath on 3/29/15.
 */
public class CategoryFragment extends Fragment {

    public static Fragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.top_stories_text);
        textView.setText("Yay!!");
        Toast.makeText(getActivity(), "Yup", Toast.LENGTH_SHORT).show();
        return view;
    }

}
