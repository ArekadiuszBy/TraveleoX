package com.example.traveleoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login, containter, false);

        ImageButton lgnButton = (ImageButton) view.findViewById(R.id.imageButtonLogin);
        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Login.class);
                in.putExtra("Hello!", "Login here");
                startActivity(in);
            }
        });
        return view;
   }
}
