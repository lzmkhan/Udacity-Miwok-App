package com.example.android.miwok;

/**
 * Created by Marcus Khan on 4/29/2017.
 */

public class Words {


    private String DefaultTranslation;
    private String MiwokTranslation;
    private int imageId;
    public static final int NO_IMAGE = -2;
    private int audioId;

    public Words(int audioId, String defaultTranslation, String miwokTranslation) {
        DefaultTranslation = defaultTranslation;
        MiwokTranslation = miwokTranslation;
        imageId = NO_IMAGE;
        this.audioId = audioId;
    }


    public String getMiwokTranslation() {
        return MiwokTranslation;
    }

    public void setMiwokTranslation(String miwokTranslation) {
        MiwokTranslation = miwokTranslation;
    }


    public String getDefaultTranslation() {
        return DefaultTranslation;
    }

    public void setDefaultTranslation(String mDefaultTranslation) {
        this.DefaultTranslation = mDefaultTranslation;
    }

    public int getImageId() {
        return imageId;
    }


    public int getAudioId(){
        return audioId;
    }

    public Words(String defaultTranslation, String miwokTranslation, int imageId, int audioId) {
        DefaultTranslation = defaultTranslation;
        MiwokTranslation = miwokTranslation;
        this.imageId = imageId;
        this.audioId = audioId;
    }

    @Override
    public String toString() {
        return "Words{" +
                "DefaultTranslation='" + DefaultTranslation + '\'' +
                ", MiwokTranslation='" + MiwokTranslation + '\'' +
                ", imageId=" + imageId +
                ", audioId=" + audioId +
                '}';
    }
}
