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

/**
 * Created by SESA72162 on 2/18/2017.
 */

public class AudioMusicSelectAdapter extends SelectableAdapter<AudioMusicSelectAdapter.ViewHolder>
{
    private ArrayList<String> musicList;
    private Context mContext;
    public boolean onBind = false;
    private ViewHolder.ClickListener clickListener;

    public AudioMusicSelectAdapter(ViewHolder.ClickListener clickListener, Context context, ArrayList<String> audiosList, ArrayList<String> selectedMusicsUrls)
    {
        super();
        this.mContext = context;
        this.clickListener = clickListener;
        musicList = new ArrayList<String>();
        this.musicList.addAll(audiosList);

        if (selectedMusicsUrls != null && this.musicList != null)
        {
            for (int i = 0; i < selectedMusicsUrls.size(); i++)
            {
                String selectedMusicPath = selectedMusicsUrls.get(i);

                for (int j = 0; j < this.musicList.size(); j++)
                {
                    String musicPath = this.musicList.get(j).toString();

                    if (selectedMusicPath.equalsIgnoreCase(musicPath))
                    {
                        updateItemAt(j, true);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public AudioMusicSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_audio_item, parent, false);

        return new ViewHolder(itemView, this.clickListener);
    }

    @Override
    public void onBindViewHolder(AudioMusicSelectAdapter.ViewHolder holder, int position)
    {
        String audioUrl = this.musicList.get(position);
        int index = audioUrl.lastIndexOf("/");

        if (index != -1)
        {
            audioUrl = audioUrl.substring(index + 1, audioUrl.length());
        }
        holder.filePath.setText(audioUrl);

        boolean isSelected = isSelected(position);
        holder.filePath.setTag(position);

        onBind = true;
        if (isSelected)
        {
            holder.fileSelectedView.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.fileSelectedView.setVisibility(View.GONE);
        }
        onBind = false;
    }

    public ArrayList<String> getSelectedAudioItems()
    {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for (int i = 0; i < this.musicList.size(); i++)
        {
            if (isSelected(i))
            {
                mTempArry.add(this.musicList.get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public int getItemCount()
    {
        return this.musicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView filePath;
        public ImageView fileSelectedView;
        View separatorTop;
        View containerLayout;
        private ClickListener listener;

        public ViewHolder(View itemView, ClickListener listener)
        {
            super(itemView);

            separatorTop = itemView.findViewById(R.id.separatorTop);
            filePath = (TextView) itemView.findViewById(R.id.filePath);
            fileSelectedView = (ImageView) itemView.findViewById(R.id.fileSelectedView);
            containerLayout = itemView.findViewById(R.id.containerLayout);

            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (listener != null)
            {
                int isVisible = this.fileSelectedView.getVisibility();
                int position = getLayoutPosition();
                boolean selected;

                if (isVisible != View.VISIBLE)
                {
                    this.fileSelectedView.setVisibility(View.VISIBLE);
                    this.itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.selected_item_background));
                    selected = true;

                    if (position == 0)
                    {
                        separatorTop.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    this.fileSelectedView.setVisibility(View.GONE);
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
