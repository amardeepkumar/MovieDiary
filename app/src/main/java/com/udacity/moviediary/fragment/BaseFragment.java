package com.udacity.moviediary.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
