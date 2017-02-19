package dm.com.silentmusicparty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class EventsActivity extends AppCompatActivity implements EventsAdapter.ViewHolder.ClickListener
{
    private EventsAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.upcoming_events);

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
            }
        });

        DBHelper dbHelper = DBHelper.getInstance(this);

        List<Event> eventList = dbHelper.getAllEvents(this);
        adapter = new EventsAdapter(this, this, eventList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
    }

    @Override
    public void onItemClicked(int position)
    {

    }
}
