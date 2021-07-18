package com.example.xlibrary.ui.account;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.UserSession;

import org.w3c.dom.Text;

import java.awt.font.TextAttribute;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    NavController navController;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseHelper db;
    UserSession userSession;
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        userSession = db.getCurrentUserCreds();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        TextView profileTextView = view.findViewById(R.id.profile);
        TextView signOutTextView = view.findViewById(R.id.log_out);
        TextView accountUsernameTextView = view.findViewById(R.id.account_username);
        TextView changePwdTextView = view.findViewById(R.id.change_password);
        accountUsernameTextView.setText(userSession.username);
        profileTextView.setOnClickListener(v -> {
            navController.navigate(AccountFragmentDirections.actionNavigationAccountToNavigationProfile());
        });
        TextView editProfileTextView = view.findViewById(R.id.edit_info);
        editProfileTextView.setOnClickListener(v -> {
            navController.navigate(AccountFragmentDirections.actionNavigationAccountToNavigationEditProfile());
        });
        changePwdTextView.setOnClickListener(v -> {
            navController.navigate(AccountFragmentDirections.actionNavigationAccountToNavigationChangePassword());
        });
        signOutTextView.setOnClickListener(v -> {
            SharedPreferences info = getContext().getSharedPreferences("user", 0);
            info.edit().clear().apply();

            getActivity().finish();
        });
        return view;
    }
}