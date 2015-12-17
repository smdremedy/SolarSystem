package com.soldiersofmobile.solarsystem;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SolarObject implements Serializable {

    private String name;
    private String image;
    private String text;
    private String video;

    private SolarObject[] moons;

    public static boolean hasMoons(SolarObject solarObject) {
        SolarObject[] moons = solarObject.getMoons();
        return moons != null && moons.length > 0;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public SolarObject[] getMoons() {
        return moons;
    }

    public void setMoons(SolarObject[] moons) {
        this.moons = moons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static SolarObject[] getPlanetsFromJson(Context context) {
        return arrayFromJSON(context, "planets");
    }

    public static SolarObject[] getOthersFromJson(Context context) {
        return arrayFromJSON(context, "others");
    }

    public static SolarObject[] arrayFromJSON(Context context, String type) {
        String json = null;
        try {
            json = loadStringFromAssets(context, "solar.json");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(type);
            return getSolarObjectFromJsonArray(jsonArray);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        return new SolarObject[0];
    }

    @NonNull
    public static String loadStringFromAssets(Context context, String filename) throws IOException {
        String body;
        InputStream is = context.getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        body = new String(buffer, "UTF-8");
        return body;
    }

    private static SolarObject[] getSolarObjectFromJsonArray(JSONArray jsonArray) throws JSONException {
        SolarObject[] solarObjects = new SolarObject[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            solarObjects[i] = SolarObject.fromJSON(jsonArray.getJSONObject(i));
        }
        return solarObjects;
    }

    private static SolarObject fromJSON(JSONObject jsonObject) throws JSONException {
        SolarObject solarObject = new SolarObject();
        String name = jsonObject.getString("name");
        solarObject.setName(name);
        solarObject.setImage(String.format("images/%s.jpg", name.toLowerCase()));
        solarObject.setText(String.format("texts/%s.txt", name.toLowerCase()));
        solarObject.setVideo(jsonObject.optString("video"));
        JSONArray moons = jsonObject.optJSONArray("moons");
        if(moons != null) {
            solarObject.setMoons(getSolarObjectFromJsonArray(moons));
        }

        return solarObject;
    }
}
