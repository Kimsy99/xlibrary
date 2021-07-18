package com.example.xlibrary.ui.account.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.UserSession;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    DatabaseHelper db;
    UserSession userSession;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();

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
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        TextInputEditText usernameInput = root.findViewById(R.id.edit_profile_username);
        usernameInput.setText(userSession.username);
        TextInputEditText emailInput = root.findViewById(R.id.edit_profile_email);
        emailInput.setText(userSession.email);
        TextView uidView = root.findViewById(R.id.edit_profile_uid);
        uidView.setText(Integer.toString(userSession.uid));
        Button editProfileButton = root.findViewById(R.id.edit_info_btn);
        editProfileButton.setOnClickListener(v -> {
            TextInputEditText usernameInputNew = root.findViewById(R.id.edit_profile_username);
            TextInputEditText emailInputNew = root.findViewById(R.id.edit_profile_email);
            boolean res = db.editProfile(userSession.uid, usernameInputNew.getText().toString(), emailInputNew.getText().toString());
            if(res == true ){
                Toast.makeText(getContext(), "Successfully Update Profile", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getActivity().getSharedPreferences("user", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", usernameInputNew.getText().toString());
                editor.putString("email", emailInputNew.getText().toString());
                editor.apply();
            }else{
                Toast.makeText(getContext(), "Something went wrong.. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}