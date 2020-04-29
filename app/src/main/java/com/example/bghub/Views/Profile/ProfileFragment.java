package com.example.bghub.Views.Profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bghub.R;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment implements ProfileContract.View {

    @Inject
    ProfileContract.Presenter mPresenter;

    private static final String TAG = "ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        return root;
    }


}
