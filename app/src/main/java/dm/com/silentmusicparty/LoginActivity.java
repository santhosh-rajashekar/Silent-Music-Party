package dm.com.silentmusicparty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email)
    EditText emailText;

    @Bind(R.id.input_password)
    EditText passwordText;

    @Bind(R.id.btn_login)
    Button loginButton;

    @Bind(R.id.link_signup)
    TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login()
    {
        Log.d(TAG, "Login");

        if (!validate())
        {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

        final DBHelper dbHelper = DBHelper.getInstance(this);

        Thread loginThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                final boolean isValidLogin = dbHelper.isValidLogin(email, password);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressDialog.dismiss();

                        if (isValidLogin)
                        {
                            onLoginSuccess();
                        }
                        else
                        {
                            onLoginFailed();
                        }
                    }
                });
            }
        });

        loginThread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SIGNUP)
        {
            if (resultCode == RESULT_OK)
            {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {
        loginButton.setEnabled(true);

        ActvityManager actvityManager = ActvityManager.getInstance();
        actvityManager.nagivateToOngoingEventActivity(this);
    }

    public void onLoginFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailText.setError("enter a valid email address");
            valid = false;
        }
        else
        {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15)
        {
            passwordText.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        }
        else
        {
            passwordText.setError(null);
        }

        return valid;
    }
}
