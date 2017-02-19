package dm.com.silentmusicparty;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class AudioMusicSelectActivity extends AppCompatActivity implements AudioMusicSelectAdapter.ViewHolder.ClickListener,
                                                                           View.OnClickListener

{
    private static final int REQUEST_FOR_STORAGE_PERMISSION = 123;
    private AudioMusicSelectAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private ArrayList<String> selectedAudioFiles = null;
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_music_select);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.select_audios);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
            }
        });

        if (getIntent() != null)
        {
            this.selectedAudioFiles = getIntent().getStringArrayListExtra("selectedAudioFiles");
        }

        populateAudiosFromDevice();
    }

    private void populateAudiosFromDevice()
    {
        if (!mayRequestGalleryAudios())
        {
            return;
        }
        ArrayList<String> audiosUrls = loadAudiosFromNativeGallery();
        initializeRecyclerView(audiosUrls, this.selectedAudioFiles);
    }

    private void initializeRecyclerView(ArrayList<String> musicUrls, ArrayList<String> selectedMusicUrls)
    {
        adapter = new AudioMusicSelectAdapter(this, this, musicUrls, selectedMusicUrls);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // (Context context, int spanCount)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        updateTitle();
        updateSelectButtonStatus();
    }

    private void showPermissionRationaleSnackBar()
    {
        Snackbar.make(findViewById(R.id.recycler_view), getString(R.string.grant_permission_for_file_access),
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.OK, new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Request the permission
                ActivityCompat.requestPermissions(AudioMusicSelectActivity.this,
                        new String[]{READ_EXTERNAL_STORAGE},
                        REQUEST_FOR_STORAGE_PERMISSION);
            }
        }).show();
    }

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
    }

    private void updateSelectButtonStatus()
    {
        if (floatingActionButton != null && adapter != null)
        {
            int count = adapter.getSelectedItemCount();

            if (count > 0)
            {
                floatingActionButton.setVisibility(View.VISIBLE);
            }
            else
            {
                floatingActionButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void updateTitle()
    {
        if (toolbar != null && adapter != null)
        {
            int count = adapter.getSelectedItemCount();

            if (count > 0)
            {
                toolbar.setTitle(Integer.toString(count));
            }
            else
            {
                toolbar.setTitle(getString(R.string.select_audios));
            }
        }
    }

    private boolean mayRequestGalleryAudios()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }

        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }

        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE))
        {
            showPermissionRationaleSnackBar();
        }
        else
        {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_FOR_STORAGE_PERMISSION);
        }

        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {

        switch (requestCode)
        {

            case REQUEST_FOR_STORAGE_PERMISSION:
            {

                if (grantResults.length > 0)
                {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        populateAudiosFromDevice();
                    }
                    else
                    {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE))
                        {
                            showPermissionRationaleSnackBar();
                        }
                        else
                        {
                            Toast.makeText(this, R.string.permission_rationale_audio_select, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;
            }
        }
    }

    private ArrayList<String> loadAudiosFromNativeGallery()
    {
        final String[] columnsAudio = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID};
        final String audiosOrderBy = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        Cursor audiocursor = managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columnsAudio, null,
                null, audiosOrderBy + " DESC");

        ArrayList<String> audioUrls = new ArrayList<String>();

        for (int i = 0; i < audiocursor.getCount(); i++)
        {
            audiocursor.moveToPosition(i);
            int dataColumnIndex = audiocursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            audioUrls.add(audiocursor.getString(dataColumnIndex));

            System.out.println("=====> Array path => " + audioUrls.get(i));
        }

        return audioUrls;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fab:

                if (adapter != null)
                {
                    int count = adapter.getSelectedItemCount();

                    if (count > 0)
                    {
                        ArrayList<String> selectedItems = adapter.getSelectedAudioItems();

                        Intent intent = new Intent();
                        intent.putExtra("selectedItems", selectedItems);
                        setResult(RESULT_OK, intent);
                        finish();
                        overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
                    }
                }
                break;
        }
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
                toolbar.setTitle(getString(R.string.select_audios));
            }
        }
    }
}
