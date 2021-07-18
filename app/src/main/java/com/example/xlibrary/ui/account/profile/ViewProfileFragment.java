package com.example.xlibrary.ui.account.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.UserSession;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends Fragment {

   DatabaseHelper db;
    UserSession userSession;

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewProfileFragment newInstance(String param1, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        userSession = db.getCurrentUserCreds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_profile, container, false);
        TextView usernameView = root.findViewById(R.id.profile_username);
        TextView emailView = root.findViewById(R.id.profile_email);
        TextView uidView = root.findViewById(R.id.profile_uid);
        usernameView.setText(userSession.username);
        emailView.setText(userSession.email);
        uidView.setText(Integer.toString(userSession.uid));
        return root;
    }
}