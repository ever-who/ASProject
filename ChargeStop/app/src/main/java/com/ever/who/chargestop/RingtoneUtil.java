package com.ever.who.chargestop;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RingtoneUtil {

    public static void getSystemRingtone(Context context) {
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION);

        Cursor cursor = ringtoneManager.getCursor();
        //List<String> ringtoneTitles = new ArrayList<>();

        while (cursor.moveToNext()) {
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
//            if (title != null) {
//                ringtoneTitles.add(title);
//            }

            Uri uri = ringtoneManager.getRingtoneUri(cursor.getPosition());
            Log.d(Utils.TAG, "getSystemRingtones: title = " + title +" ,uri = " + uri);
            if("Fresh".equals(title)){
                Ringtone ringtone = ringtoneManager.getRingtone(cursor.getPosition());
                ringtone.setStreamType(AudioManager.STREAM_ALARM);
                ringtone.play();
                break;
            }
        }

        cursor.close();
    }
}
