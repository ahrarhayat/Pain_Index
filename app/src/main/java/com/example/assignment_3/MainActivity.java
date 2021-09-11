package com.example.assignment_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.assignment_3.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        View view = binding.getRoot();
        setContentView(view);


        TextView signUpView = binding.signUp;
        SpannableString content = new SpannableString("Sign Up here!");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signUpView.setText(content);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean logOut = getIntent().getBooleanExtra("Logout",false);

        if (logOut)
        {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("Email");
            editor.apply();
        }

        boolean CheckExists = sharedPref.contains("Email");

        if (CheckExists)
        {
            Log.d("Login", ""+ CheckExists);
            String email = sharedPref.getString("Email",null);
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("Email", email);
            startActivity(intent);
        }



        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Replace with moving to another fragment
                binding.emailEditText.setVisibility(View.GONE);
                binding.signUp.setVisibility(View.GONE);
                binding.passEditText.setVisibility(View.GONE);
                binding.welcomeText.setVisibility(View.GONE);
                binding.startButton.setVisibility(View.GONE);
                binding.showHidePass.setVisibility(View.GONE);
                replaceFragment(new SignUp());


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



        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String pass = binding.passEditText.getText().toString();


                if (email.isEmpty() || pass.isEmpty()) {
                    Snackbar.make(v, "You need to enter both login and password to login", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            SharedPreferences sharedPref =  getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Email", email);
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("Email", email);
                            startActivity(intent);
                        }
                        else {
                            Snackbar.make(v, "Email or Password is incorrect", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }



                });


                }

            }

        });

    }
    private void replaceFragment(Fragment nextFragment) {
        //this creates a fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //this creates a new transaction for the manager to begin
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //this replaces the previous one with a new fragment, which is passed as a parameter to this method
        fragmentTransaction.replace(R.id.fragment_container_view, nextFragment);
        //the transaction is committed by this line
        fragmentTransaction.commit();
    }
}