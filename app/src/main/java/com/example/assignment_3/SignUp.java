package com.example.assignment_3;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment_3.databinding.SignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends Fragment {

    private SignUpBinding binding;
    private FirebaseAuth auth;

   public SignUp()
   {

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        View view = binding.getRoot();

        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        binding.signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String pass = binding.passEditText.getText().toString();
                if(!email.isEmpty() && !pass.isEmpty())
                {


                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override public void onComplete(@NonNull Task<AuthResult> task) { if(task.isSuccessful()) {
                            Log.d("Firebase", "Success");
                            // do something, e.g. start the other activity
                            Snackbar.make(v, "Sign up successful!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                        else { //do something, e.g. give a message
                            Log.d("Firebase", "Fail");
                            Snackbar.make(v, "User already exists", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        } });
                }
                else
                {
                    Snackbar.make(v, "You must enter the Email and password to sign up", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }

        });

        binding.showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Show Pass","BTN Pressed");
                if (binding.showHidePass.getText().toString().equals("Show Password"))
                {
                    Log.d("Show Pass","Show pass Pressed");
                    binding.passEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.showHidePass.setText("Hide Password");
                }
                else if(binding.showHidePass.getText().toString().equals("Hide Password"))
                {
                    Log.d("Show Pass","Hide pass Pressed");
                    binding.passEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.showHidePass.setText("Show Password");
                }
            }
        });

        binding.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
        model.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
