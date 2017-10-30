package com.example.android.Miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    ListView list;
    ArrayList<Word> words = new ArrayList<>();
    MediaPlayer audio = null;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener AudioChanged = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK == i) {
                audio.pause();
                audio.seekTo(0);
            } else if (AudioManager.AUDIOFOCUS_GAIN == i) {
                audio.start();
            } else if (AudioManager.AUDIOFOCUS_LOSS == i) {
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        list = (ListView) findViewById(R.id.numbersList);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //ArrayList of Word class
        words.add(new Word(getString(R.string.number_one), getString(R.string.miwok_number_one),
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word(getString(R.string.number_two), getString(R.string.miwok_number_two),
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word(getString(R.string.number_three), getString(R.string.miwok_number_three),
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word(getString(R.string.number_four), getString(R.string.miwok_number_four),
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word(getString(R.string.number_five), getString(R.string.miwok_number_five),
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word(getString(R.string.number_six), getString(R.string.miwok_number_six),
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word(getString(R.string.number_seven), getString(R.string.miwok_number_seven),
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(getString(R.string.number_eight), getString(R.string.miwok_number_eight),
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(getString(R.string.number_nine), getString(R.string.miwok_number_nine),
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(getString(R.string.number_ten), getString(R.string.miwok_number_ten),
                R.drawable.number_ten, R.raw.number_ten));
        //Adapter of the words ArrayList
        wordsAdapter numbersArray = new wordsAdapter(this, words, R.color.category_numbers);
        list.setAdapter(numbersArray);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                int focus = audioManager.requestAudioFocus(AudioChanged
                        , AudioManager.STREAM_MUSIC
                        , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                );
                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    audio = MediaPlayer.create(NumbersActivity.this, words.get(i).getAudio());
                    audio.start();
                }
                audio.setOnCompletionListener(mCompletionListener);

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (audio != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            audio.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            audio = null;
            audioManager.abandonAudioFocus(AudioChanged);

        }
    }
}
