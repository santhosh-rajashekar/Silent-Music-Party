package dm.com.silentmusicparty;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by SESA72162 on 2/20/2017.
 */

public class ActvityManager
{
    private static ActvityManager actvityManager;

    public static synchronized ActvityManager getInstance()
    {
        if (actvityManager == null)
        {
            actvityManager = new ActvityManager();
        }

        return actvityManager;
    }

    private ActvityManager()
    {
        super();
    }

    public void nagivateToMembersInfoActivity(Activity act)
    {
        Intent intent = new Intent(act, InviteMembersToEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        act.startActivity(intent);
        act.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    public void nagivateToInviteMembersToEventActivity(Activity act)
    {
        Intent intent = new Intent(act, InviteMembersToEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        act.startActivity(intent);
        act.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    public void nagivateToUpcomingEventsInfoActivity(Activity act)
    {
        Intent intent = new Intent(act, EventsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        act.startActivity(intent);
        act.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    public void nagivateToOngoingEventActivity(Activity act)
    {
        Intent intent = new Intent(act, CurrentEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        act.startActivity(intent);
        act.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    public void nagivateToUpcomingAudiosInfoActivity(Activity act)
    {
        Intent intent = new Intent(act, CurrentEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        act.startActivity(intent);
        act.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }
}
