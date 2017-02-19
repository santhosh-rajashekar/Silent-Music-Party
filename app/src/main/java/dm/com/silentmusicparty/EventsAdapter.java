package dm.com.silentmusicparty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SESA72162 on 2/18/2017.
 */

public class EventsAdapter extends SelectableAdapter<EventsAdapter.ViewHolder>
{
    private static final String TAG = EventsAdapter.class.getSimpleName();
    private List<Event> items;
    private Context context = null;
    private ViewHolder.ClickListener clickListener;

    public EventsAdapter(ViewHolder.ClickListener clickListener, Context context, List<Event> listOfEvents)
    {
        super();
        this.context = context;
        this.clickListener = clickListener;
        this.items = listOfEvents;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position)
    {
        Event event = items.get(position);

        holder.name.setText(event.getName());
        holder.organizer.setText(event.getOrganizer());
        holder.place.setText(event.getPlace());
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());
        holder.duration.setText(event.getDuration());
    }

    @Override
    public int getItemCount()
    {
        return this.items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        private static final String TAG = ViewHolder.class.getSimpleName();

        TextView name;
        TextView organizer;
        TextView place;
        TextView date;
        TextView time;
        TextView duration;
        View containerLayout;

        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener)
        {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            organizer = (TextView) itemView.findViewById(R.id.organizer);
            place = (TextView) itemView.findViewById(R.id.place);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            duration = (TextView) itemView.findViewById(R.id.duration);
            containerLayout = itemView.findViewById(R.id.containerLayout);

            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (listener != null)
            {
                listener.onItemClicked(getLayoutPosition());
            }
        }

        public interface ClickListener
        {
            void onItemClicked(int position);
        }
    }
}
