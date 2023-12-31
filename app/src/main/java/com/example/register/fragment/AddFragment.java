package com.example.register.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.register.R;
import com.example.register.RegiterActivity;
import com.example.register.util.data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    //    DocumentReference documentReference;
    DatabaseReference reference;

    private EditText TitleEdit, DecriptionEdit, QuestionEdit, LanguageEdit;
    FirebaseDatabase db;
    private Button AddQuestionSubmit;
    FirebaseAuth fauth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, NewEmail;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add, container, false);
        // Inflate the layout for this fragment

        //Edit Texts
        TitleEdit = view.findViewById(R.id.TitleEdit);
        QuestionEdit = view.findViewById(R.id.QuestionEdit);
        DecriptionEdit = view.findViewById(R.id.DescriptionEdit);
        LanguageEdit = view.findViewById(R.id.LanguageEdit);

        //Database Variable
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("User");

        // Submit Button
        AddQuestionSubmit = view.findViewById(R.id.SubmitQuestion);

        fauth = FirebaseAuth.getInstance();

        String Email = fauth.getCurrentUser().getEmail();


        Log.d("TAG100", "onCreate: encoded mail " + Email);

        AddQuestionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = TitleEdit.getText().toString();
                String question = QuestionEdit.getText().toString();
                String description = DecriptionEdit.getText().toString();
                String language = LanguageEdit.getText().toString();

                if (title.isEmpty() || question.isEmpty() || description.isEmpty() || language.isEmpty()) {
                    Toast.makeText(getContext(), "Pls All fille ", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> user = new HashMap<>();
                    user.put("Title", title);
                    user.put("Question", question);
                    user.put("Description", description);
                    user.put("Language", language);

                    String key = reference.push().getKey();

                    reference.child(Email.replace(".", ",")).child(key).child("Title").setValue(title);
                    reference.child(Email.replace(".", ",")).child(key).child("Question").setValue(question);
                    reference.child(Email.replace(".", ",")).child(key).child("Description").setValue(description);
                    reference.child(Email.replace(".", ",")).child(key).child("Language").setValue(language);
                    Toast.makeText(getContext(), "Submitted", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }
}