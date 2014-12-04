package com.openwudi.common.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Setting的一个实现，使用SharedPreference作为底层存储.
 */
public class SharedPreferencesUtils {

    private String mFileName;
    private Context mContext;


    public SharedPreferencesUtils(Context context, String fileName) {
        mContext = context;
        mFileName = fileName;
    }

    
    public String getString(String field, String defValue) {
        return mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).getString(field, defValue);
    }

    
    public void setString(String field, String value) {
        Editor edit = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).edit();
        edit.putString(field, value);
        edit.commit();
    }


    public int getInt(String field, int defValue) {
        return mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).getInt(field, defValue);
    }


    public void setInt(String field, int value) {
        Editor edit = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).edit();
        edit.putInt(field, value);
        edit.commit();
    }


    public long getLong(String field, long defValue) {
        return mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).getLong(field, defValue);
    }


    public void setLong(String field, long value) {
        Editor edit = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).edit();
        edit.putLong(field, value);
        edit.commit();
    }


    public boolean getBoolean(String field, boolean defValue) {
        return mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).getBoolean(field, defValue);
    }


    public void setBoolean(String field, boolean value) {
        Editor edit = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).edit();
        edit.putBoolean(field, value);
        edit.commit();
    }


    public ArrayList<String> getStringList(String field, ArrayList<String> defValue) {
        int size = getInt(field + "_list_count", 0);
        ArrayList<String> array = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            String value = getString(field + "_list_" + i, null);
            if (!TextUtils.isEmpty(value)) {
                array.add(value);
            }
        }
        return array;
    }


    public void setStringList(String field, ArrayList<String> value) {
        setInt(field + "_list_count", value.size());
        for (int i = 0; i < value.size(); i++) {
            setString(field + "_list_" + i, value.get(i));
        }
    }


    public void clearStringList(String field) {
        int size = getInt(field + "_list_count", 0);
        setInt(field + "_list_count", 0);
        for (int i = 0; i < size; i++) {
            setString(field + "_list_" + i, null);
        }
    }


    public Map<String, ?> getAll() {
        return mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE).getAll();
    }

}
