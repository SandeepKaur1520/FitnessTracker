package com.fitness.tracker.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.tracker.R;


public class MeFragment extends Fragment {
    public View onCreateView(LayoutInflater inflator,ViewGroup container,Bundle SavedInstanceState){
        View view =inflator.inflate(R.layout.mefragment,container,false);
        return view;
    }

    public interface  onFragmentListener
    {
        void onFragment(Uri uri);
    }
}

