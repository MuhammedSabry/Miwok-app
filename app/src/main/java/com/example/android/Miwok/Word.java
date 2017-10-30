package com.example.android.Miwok;

public class Word {
    private String Native, Miwok;
    private int image, audio;

    public Word(String MiwokWord, String nativeWord, int z) {
        Native = nativeWord;
        Miwok = MiwokWord;
        audio = z;
    }

    public Word(String MiwokWord, String nativeWord, int z, int aud) {
        Native = nativeWord;
        Miwok = MiwokWord;
        image = z;
        audio = aud;
    }

    public String getNative() {
        return Native;
    }

    public String getMiwok() {
        return Miwok;
    }

    public int getImage() {
        return image;
    }

    public int getAudio() {
        return audio;
    }
}
