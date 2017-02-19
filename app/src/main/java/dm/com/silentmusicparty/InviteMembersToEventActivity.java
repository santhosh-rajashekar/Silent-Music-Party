package dm.com.silentmusicparty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class InviteMembersToEventActivity extends AppCompatActivity implements MembersAdapter.ViewHolder.ClickListener,
                                                                               View.OnClickListener
{
    private Toolbar toolbar;
    private MembersAdapter adapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_members_to_event);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.invite_members_to_event);

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

        List<Member> memberList = dbHelper.getAllMembers(this);
        adapter = new MembersAdapter(this, this, memberList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
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
    public void onItemClicked(int position, boolean isSelected)
    {
        if (toolbar != null && adapter != null)
        {
            adapter.updateItemAt(position, isSelected);

            int count = adapter.getSelectedItemCount();

            if (count > 0)
            {
                floatingActionButton.setVisibility(View.VISIBLE);
                toolbar.setTitle(Integer.toString(count));
            }
            else
            {
                floatingActionButton.setVisibility(View.INVISIBLE);
                toolbar.setTitle(getString(R.string.invite_members_to_event));
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fab:

                if (adapter != null)
                {
                    int count = adapter.getSelectedItemCount();

                    if (count > 0)
                    {
                        ArrayList<Member> selectedMembers = adapter.getSelectedMembers();

                        Intent intent = new Intent();
                        intent.putExtra("selectedMembers", selectedMembers);
                        setResult(RESULT_OK, intent);
                        finish();
                        overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
                    }
                }
                break;
        }
    }
}
