package dm.com.silentmusicparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener
{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_music_event);

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
    }

    @Override
    public void onClick(View view)
    {

    }
}
