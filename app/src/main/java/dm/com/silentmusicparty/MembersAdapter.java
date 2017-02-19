package dm.com.silentmusicparty;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SESA72162 on 2/18/2017.
 */

public class MembersAdapter extends SelectableAdapter<MembersAdapter.ViewHolder>
{
    private static final String TAG = MembersAdapter.class.getSimpleName();
    private List<Member> items;
    private Context context = null;
    private ViewHolder.ClickListener clickListener;

    public MembersAdapter(ViewHolder.ClickListener clickListener, Context context, List<Member> listOfMembers)
    {
        super();
        this.context = context;
        this.clickListener = clickListener;
        this.items = listOfMembers;
    }

    @Override
    public MembersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(MembersAdapter.ViewHolder holder, int position)
    {
        Member member = items.get(position);

        holder.name.setText(member.getName());
        holder.email.setText(member.getEmail());
        holder.phone.setText(member.getPhone());
        holder.profile_image.setImageBitmap(member.getProfile_photo());
    }

    public ArrayList<Member> getSelectedMembers()
    {
        ArrayList<Member> selectedMembers = new ArrayList<Member>();

        for (int i = 0; i < this.items.size(); i++)
        {
            if (isSelected(i))
            {
                selectedMembers.add(this.items.get(i));
            }
        }

        return selectedMembers;
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
        TextView email;
        TextView phone;
        CircleImageView profile_image;
        ImageView memberSelectedView;
        View separatorTop;
        View containerLayout;

        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener)
        {
            super(itemView);

            separatorTop = itemView.findViewById(R.id.separatorTop);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            phone = (TextView) itemView.findViewById(R.id.phone);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
            memberSelectedView = (ImageView) itemView.findViewById(R.id.memberSelectedView);
            containerLayout = itemView.findViewById(R.id.containerLayout);

            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (listener != null)
            {
                int isVisible = this.memberSelectedView.getVisibility();
                int position = getLayoutPosition();
                boolean selected;

                if (isVisible != View.VISIBLE)
                {
                    this.memberSelectedView.setVisibility(View.VISIBLE);
                    this.itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.selected_item_background));
                    selected = true;

                    if (position == 0)
                    {
                        separatorTop.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    this.memberSelectedView.setVisibility(View.GONE);
                    this.itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary));
                    selected = false;

                    if (position == 0)
                    {
                        separatorTop.setVisibility(View.GONE);
                    }
                }

                listener.onItemClicked(position, selected);
            }
        }

        public interface ClickListener
        {
            void onItemClicked(int position, boolean isSelected);
        }
    }
}
