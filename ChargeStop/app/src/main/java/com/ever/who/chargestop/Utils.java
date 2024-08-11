package com.ever.who.chargestop;

import android.util.Log;

import java.util.Calendar;

public class Utils {
    public static final String TAG = "ChargeStop";

    // 午睡
    public static final int[] NOON_SLEEP_PERIOD = {12, 14};

    // 夜里
    public static final int[] NIGHT_SLEEP_PERIOD_1 = {22, 24};
    public static final int[] NIGHT_SLEEP_PERIOD_2 = {0, 8};

    public static final int[][] NO_RING_PERIOD
            = {NOON_SLEEP_PERIOD, NIGHT_SLEEP_PERIOD_1, NIGHT_SLEEP_PERIOD_2};

    public static boolean ringTimeCheck (){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        for (int i = 0; i < NO_RING_PERIOD.length; i++){
            int startHour = NO_RING_PERIOD[i][0];
            int endHour = NO_RING_PERIOD[i][1];
            if ( hour >= startHour && hour < endHour ){
                Log.e(TAG, "ringTimeCheck Hour: " + hour + ",locate in sleep period, ignore Ring");
                return false;
            }
        }
        Log.i(TAG, "ringTimeCheck Hour: " + hour + ", Ring!");
        return true;
    }
}
