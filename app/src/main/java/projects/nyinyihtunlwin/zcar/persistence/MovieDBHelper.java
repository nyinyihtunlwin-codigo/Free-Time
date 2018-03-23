package projects.nyinyihtunlwin.zcar.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "zcar.db";


    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
            MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MovieEntry.COLUMN_MOVIE_ID + " VARCHAR(256), " +
            MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
            MovieContract.MovieEntry.COLUMN_VIDEO + " INTEGER DEFAULT 0, " +
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
            MovieContract.MovieEntry.COLUMN_POPULARITY + " REAL, " +
            MovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_ADULT + " INTEGER DEFAULT 0, " +
            MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            " UNIQUE (" + MovieContract.MovieEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE" +
            " );";
    private static final String SQL_CREATE_GENRE_TABLE = "CREATE TABLE " + MovieContract.GenreEntry.TABLE_NAME + " (" +
            MovieContract.GenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.GenreEntry.COLUMN_GENRE_ID + " VARCHAR(256), " +
            MovieContract.GenreEntry.COLUMN_GENRE_NAME + " VARCHAR(256), " +
            " UNIQUE (" + MovieContract.GenreEntry.COLUMN_GENRE_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_GENRE_TABLE = "CREATE TABLE " + MovieContract.MovieGenreEntry.TABLE_NAME + " (" +
            MovieContract.MovieGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + " VARCHAR(256), " +
            MovieContract.MovieGenreEntry.COLUMN_GENRE_ID + " VARCHAR(256), " +
            " UNIQUE (" + MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + ", " +
            MovieContract.MovieGenreEntry.COLUMN_GENRE_ID
            + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_MOVIE_IN_SCREEN_TABLE = "CREATE TABLE " + MovieContract.MovieInScreenEntry.TABLE_NAME + " (" +
            MovieContract.MovieInScreenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + " VARCHAR(256), " +
            MovieContract.MovieInScreenEntry.COLUMN_SCREEN + " VARCHAR(256), " +
            " UNIQUE (" + MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + ", " +
            MovieContract.MovieInScreenEntry.COLUMN_SCREEN
            + ") ON CONFLICT REPLACE" +
            ");";

    private static final String SQL_CREATE_TV_SHOW_TABLE = "CREATE TABLE " + MovieContract.TvShowsEntry.TABLE_NAME + " (" +
            MovieContract.TvShowsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.TvShowsEntry.COLUMN_TV_SHOWS_ID + " VARCHAR(256), " +
            MovieContract.TvShowsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            MovieContract.TvShowsEntry.COLUMN_ORIGINAL_NAME + " TEXT, " +
            MovieContract.TvShowsEntry.COLUMN_FIRST_AIR_DATE + " TEXT, " +
            MovieContract.TvShowsEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
            MovieContract.TvShowsEntry.COLUMN_VIDEO + " INTEGER DEFAULT 0, " +
            MovieContract.TvShowsEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
            MovieContract.TvShowsEntry.COLUMN_POPULARITY + " REAL, " +
            MovieContract.TvShowsEntry.COLUMN_POSTER_PATH + " TEXT, " +
            MovieContract.TvShowsEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
            MovieContract.TvShowsEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            MovieContract.TvShowsEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            " UNIQUE (" + MovieContract.TvShowsEntry.COLUMN_NAME + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TV_SHOW_GENRE_TABLE = "CREATE TABLE " + MovieContract.TvShowGenreEntry.TABLE_NAME + " (" +
            MovieContract.TvShowGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.TvShowGenreEntry.COLUMN_TV_SHOW_ID + " VARCHAR(256), " +
            MovieContract.TvShowGenreEntry.COLUMN_GENRE_ID + " VARCHAR(256), " +
            " UNIQUE (" + MovieContract.TvShowGenreEntry.COLUMN_TV_SHOW_ID + ", " +
            MovieContract.TvShowGenreEntry.COLUMN_GENRE_ID
            + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_TV_SHOW_IN_SCREEN_TABLE = "CREATE TABLE " + MovieContract.TvShowInScreenEntry.TABLE_NAME + " (" +
            MovieContract.TvShowInScreenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.TvShowInScreenEntry.COLUMN_TV_SHOWS_ID + " VARCHAR(256), " +
            MovieContract.TvShowInScreenEntry.COLUMN_SCREEN + " VARCHAR(256), " +
            " UNIQUE (" + MovieContract.TvShowInScreenEntry.COLUMN_TV_SHOWS_ID + ", " +
            MovieContract.TvShowInScreenEntry.COLUMN_SCREEN
            + ") ON CONFLICT REPLACE" +
            ");";


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_GENRE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_IN_SCREEN_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_TV_SHOW_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TV_SHOW_GENRE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TV_SHOW_IN_SCREEN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieGenreEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.GenreEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieInScreenEntry.TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.TvShowGenreEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.TvShowsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.TvShowInScreenEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
