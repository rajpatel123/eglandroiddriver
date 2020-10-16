package com.eaglecabs.provider.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.data.network.model.LatLngPointModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SharedHelper {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static void putKey(Context context, String Key, String Value) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(Key, Value);
            editor.apply();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

    }

    public static String getKey(Context context, String Key) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

        return sharedPreferences.getString(Key, "");
    }

    public static void putKey(Context context, String Key, Boolean Value) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putBoolean(Key, Value);
            editor.apply();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

    }

    public static void putKey(Context context, String Key, Integer value) {
        try {


            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putInt(Key, value);
            editor.apply();

        } catch (NullPointerException n) {
            n.printStackTrace();
        }

    }


    public static void putKey(Context context, String Key, float value) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putFloat(Key, value);
            editor.apply();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

    }


    public static Integer getIntKey(Context context, String Key) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Key, -1);
    }


    public static float getDoubleKey(Context context, String Key) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

        return sharedPreferences.getFloat(Key, -1.0f);
    }

    public static boolean getBoolKey(Context context, String Key) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Key, false);
    }

    public static String getKey(Context context, String Key, String defaultValue) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        return sharedPreferences.getString(Key, defaultValue);
    }

    public static void clearSharedPreferences(Context context) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }


    public static void putKeyFCM(Context context, String Key, String Value) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID + ".fcm", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(Key, Value);
            editor.apply();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

    }

    public static String getKeyFCM(Context context, String Key) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID + ".fcm", Context.MODE_PRIVATE);
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

        return sharedPreferences.getString(Key, "");
    }

    public static List<LatLngPointModel> getLocation(Context context) {
        sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
//        return sharedPreferences.getString("location", "");
        return new Gson().fromJson(sharedPreferences.getString("location", ""),
                new TypeToken<List<LatLngPointModel>>() {
                }.getType());
    }

    public static void putLocation(Context context, String Value) {
        try {
            sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("location", Value);
            editor.apply();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }


}
