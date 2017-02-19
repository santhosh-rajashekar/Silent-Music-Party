package dm.com.silentmusicparty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class SignupActivity extends AppCompatActivity
{
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name)
    EditText nameText;

    @Bind(R.id.input_address)
    EditText addressText;

    @Bind(R.id.input_email)
    EditText emailText;

    @Bind(R.id.input_mobile)
    EditText mobileText;

    @Bind(R.id.input_password)
    EditText passwordText;

    @Bind(R.id.input_reEnterPassword)
    EditText reEnterPasswordText;

    @Bind(R.id.btn_signup)
    Button signupButton;

    @Bind(R.id.link_login)
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup()
    {
        Log.d(TAG, "Signup");

        if (!validate())
        {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = nameText.getText().toString();
        final String address = addressText.getText().toString();
        final String email = emailText.getText().toString();
        final String phone = mobileText.getText().toString();
        final String password = passwordText.getText().toString();
        final String reEnterPassword = reEnterPasswordText.getText().toString();
        final Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.login);

        final DBHelper dbHelper = DBHelper.getInstance(this);

        Thread signupThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                final boolean isSignupOkay = dbHelper.signUpNewMember(name, phone, email, password, address, photo);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressDialog.dismiss();

                        if (isSignupOkay)
                        {
                            onSignupSuccess();
                        }
                        else
                        {
                            onSignupFailed();
                        }
                    }
                });
            }
        });

        signupThread.start();
    }

    public void onSignupSuccess()
    {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast toast = Toast.makeText(getBaseContext(), "You have signedup successfully", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

        ActvityManager actvityManager = ActvityManager.getInstance();
        actvityManager.nagivateToLoginActivity(this);
    }

    public void onSignupFailed()
    {
        makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3)
        {
            nameText.setError("at least 3 characters");
            valid = false;
        }
        else
        {
            nameText.setError(null);
        }

        if (address.isEmpty())
        {
            addressText.setError("Enter Valid Address");
            valid = false;
        }
        else
        {
            addressText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailText.setError("enter a valid email address");
            valid = false;
        }
        else
        {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10)
        {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        }
        else
        {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15)
        {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }
        else
        {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password)))
        {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        }
        else
        {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
}