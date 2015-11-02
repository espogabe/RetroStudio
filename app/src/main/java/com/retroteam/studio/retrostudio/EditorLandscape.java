package com.retroteam.studio.retrostudio;

import com.retroteam.studio.retrostudio.MainActivity;
import com.retroteam.studio.retrostudio.NavigationDrawerFragment;
import com.retroteam.studio.util.SystemUiHider;

import android.annotation.TargetApi;

import android.app.ActionBar;
import android.app.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;
import hollowsoft.slidingdrawer.SlidingDrawer;


public class EditorLandscape extends Activity {

    /**
     * The info that gets passed into the MeasureEditor activity.
     */
    public final static String MEASURE_INFO = "com.retroteam.studio.MEASUREINFO";

    public final static String MEASURE_TITLE = "com.retroteam.studio.MEASURETITLE";


    public String[] notes = {"A", "B", "C", "D", "E", "F", "G", "A", "B", "C", "D", "E", "F", "G"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editor_landscape);

        // get the song name from the intent

        setupActionBar("test song name"); //cannot use this if we have no action bar

        //final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        //toggleHideyBar(); //start immersive mode

        //check where we're coming from, if we have an intent
        if (getIntent().getStringExtra("SourceActivity") != null && getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getStringExtra("SourceActivity").equals("MainActivity")) {
                //we're making a new project or coming from the main menu
                // show a toast
                String message = intent.getStringExtra(NavigationDrawerFragment.SONG_NAME);
                Toast.makeText(this, "Created Project '" + message + "'", Toast.LENGTH_SHORT).show();
            }
        }
        //handling from a measure is done in the overloaded OnActivityResult

//        //dynamically fill up note drawer with notes
//        LinearLayout notecontainer = (LinearLayout) findViewById(R.id.notecontainer);
//        //HorizontalScrollView notecontainer = (HorizontalScrollView) findViewById(R.id.content);
//
//        for (int i = 0; i < notes.length; i++) {
//            TextView note = new TextView(getApplicationContext());
//            // use non-deprecated function if 5.0 or above
//            note.setId(i);
//            note.setGravity(1);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                note.setBackground(getDrawable(R.drawable.note_icon_small_alphad));
//            }else {
//                Resources resources = getResources();
//                note.setBackground(resources.getDrawable(R.drawable.note_icon_small_alphad));
//            }
//            note.setText(notes[i]);
//            note.setTextSize(50);
//            note.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //our implementation of "tap this to play the noise it makes goes here"
//                    //this.image1.playSound();
//                    MediaPlayer mp = MediaPlayer.create(EditorLandscape.this, R.raw.beep);
//                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            mp.release();
//                        }
//
//                    });
//                    mp.start();
//                }
//            });
//            notecontainer.addView(note);
//        }
    }


    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            ActionBar editorbar = getActionBar();
            editorbar.setDisplayHomeAsUpEnabled(true);
            editorbar.setTitle(title);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // make this a switch instead
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            // TODO: If Settings has multiple levels, Up should navigate up
            // that hierarchy.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        if (id == R.id.action_play || id == R.id.action_settings) {
            Toast.makeText(this, "Tell Gabe to implement this!", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.action_togglefs) {
            toggleHideyBar();
        }

        //toggleHideyBar(); // TODO: something better than this for toggling immersive mode back. For some reason, adding a overflow menu into the editor actionbar and clicking it makes the immersive mode turn off.
        return super.onOptionsItemSelected(item);
    }

    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = this.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("EditorLandscape", "Turning immersive mode mode off. ");
        } else {
            Log.i("EditorLandscape", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        this.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public void editMeasure(View view) {

        Intent intent = new Intent(this, MeasureEditor.class);

        //will need to be able to pass note and measure information.
        String msg = "Here's the measure data placeholder";
        intent.putExtra(MEASURE_INFO, msg);
        TextView viewt = (TextView) view;
        intent.putExtra(MEASURE_TITLE, viewt.getText().toString());
        intent.putExtra("SourceActivity", "EditorLandscape");
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 123) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Do something here
            }
        }
    }

}
