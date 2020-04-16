package id.co.myproject.gozakat.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.gozakat.view.MainActivity;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    //    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiRequest apiRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        frameLayout = findViewById(R.id.frame_login);
    }

    private void updateUI(final boolean isSignedIn) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        int idUser = sharedPreferences.getInt("id_user", 0);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSignedIn) {
                    if (firebaseUser != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int idUser = sharedPreferences.getInt("id_user", 0);
        if (idUser != 0 ){
            updateUI(true);
        }else {
            setFragment(new SignInFragment());
//            if (setSignUpFragment) {
//                setSignUpFragment = false;
//                setFragment(new SignUpFragment());
//            } else {
//                setFragment(new SignInFragment());
//            }
        }
    }
}
