package projects.nyinyihtunlwin.zcar.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import projects.nyinyihtunlwin.zcar.persistence.MovieDBHelper;

/**
 * Created by Dell on 2/11/2018.
 */

public class CacheManager extends IntentService {

    private static String name = "";
    private MovieDBHelper mDbHelper;

    public CacheManager() {
        super(name);
    }


    protected void onHandleIntent(@Nullable Intent intent) {

        //   mDbHelper = new MovieDBHelper(getApplicationContext());
        //   SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //   int rowDeleted;
        //  rowDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, null, null);
        //  Log.e(FreeTimeApp.LOG_TAG, String.valueOf(rowDeleted));
    }
}
