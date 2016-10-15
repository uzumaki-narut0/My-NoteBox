package com.example.android.mytraveldiary.data;

import android.provider.BaseColumns;

/**
 * Created by Kumar Swapnil on 13-10-2016.
 */

public final class DiaryContract {
    private DiaryContract(){

    }

    public class DiaryEntry implements BaseColumns{
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_TIME_OF_ENTRY = "timeOfEntry";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_TYPE_OF_ENTRY = "typeOfEntry";

        public static final int TYPE_TEXT = 0;
        public static final int TYPE_IMAGE = 1;
    }


}
