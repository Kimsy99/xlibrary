package com.example.xlibrary.ui.account.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.UserSession;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {


    DatabaseHelper db;
    UserSession userSession;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();

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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        Button changePwdBtn = view.findViewById(R.id.change_password_btn);
        changePwdBtn.setOnClickListener(v -> {
            TextInputEditText newPwd = view.findViewById(R.id.new_password);
            TextInputEditText oldPwd = view.findViewById(R.id.old_password);
            if(oldPwd.getText().toString().equals(userSession.password.toString())){
                Boolean res = db.editPassword(userSession.uid, newPwd.getText().toString());
                if(res==true){
                    Toast.makeText(getContext(), "You had change your password. Congraz", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getActivity().getSharedPreferences("user", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", newPwd.getText().toString());
                    editor.apply();
                }else{
                    Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }else{
//                Toast.makeText(getContext(), "oldpwd: " +oldPwd.getText().toString() + "|||| sesspwd: " +userSession.password , Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Wrong Old password, Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}