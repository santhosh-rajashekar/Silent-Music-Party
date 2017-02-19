package dm.com.silentmusicparty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class CurrentEventActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private MenuItem menuItemMembersInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.ongoing_event);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        TextView name = (TextView) findViewById(R.id.name);
        TextView organizer = (TextView) findViewById(R.id.organizer);
        TextView place = (TextView) findViewById(R.id.place);
        TextView datetime = (TextView) findViewById(R.id.datetime);
        TextView duration = (TextView) findViewById(R.id.duration);

        DBHelper dbHelper = DBHelper.getInstance(this);

        List<Event> eventList = dbHelper.getAllEvents(this);

        if (eventList != null && eventList.size() > 0)
        {
            Event event = eventList.get(0);

            name.setText(event.getName());
            organizer.setText(event.getOrganizer());
            place.setText(event.getPlace());
            datetime.setText(event.getDate() + ":" + event.getTime());
            duration.setText(event.getDuration());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
        }
        else if (item.getItemId() == R.id.menuMembersInfo)
        {
            nagivateToMembersInfoActivity(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void nagivateToMembersInfoActivity(Activity act)
    {
        Intent intent = new Intent(act, InviteMembersToEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        act.startActivity(intent);
        act.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.event_info, menu);
        menuItemMembersInfo = menu.findItem(R.id.menuMembersInfo);
        return true;
    }
}
