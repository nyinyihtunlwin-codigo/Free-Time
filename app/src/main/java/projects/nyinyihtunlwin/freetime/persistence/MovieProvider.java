package projects.nyinyihtunlwin.freetime.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MovieProvider extends ContentProvider {

    public static final int MOVIE = 100;
    public static final int GENRE = 200;
    public static final int MOVIE_GENRE = 300;
    public static final int MOVIE_IN_SCREEN = 400;

    public static final int TV_SHOW = 500;
    public static final int TV_SHOW_GENRE = 600;
    public static final int TV_SHOW_IN_SCREEN = 700;


    private MovieDBHelper mDbHelper;

    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sMovieWithMovieInScreen_IJ, sMovieGenreWithGenre_IJ,
            sTvShowWithTvShowInScreen_IJ, sTvShowGenreWithGenre_IJ;

    static {
        sMovieWithMovieInScreen_IJ = new SQLiteQueryBuilder();
        sMovieWithMovieInScreen_IJ.setTables(
                MovieContract.MovieInScreenEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.MovieEntry.TABLE_NAME +
                        " ON " +
                        MovieContract.MovieInScreenEntry.TABLE_NAME + "." + MovieContract.MovieInScreenEntry.COLUMN_MOVIE_ID + " = " +
                        MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUMN_MOVIE_ID
        );
        sMovieGenreWithGenre_IJ = new SQLiteQueryBuilder();
        sMovieGenreWithGenre_IJ.setTables(
                MovieContract.MovieGenreEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.GenreEntry.TABLE_NAME +
                        " ON " +
                        MovieContract.MovieGenreEntry.TABLE_NAME + "." + MovieContract.MovieGenreEntry.COLUMN_GENRE_ID + " = " +
                        MovieContract.GenreEntry.TABLE_NAME + "." + MovieContract.GenreEntry.COLUMN_GENRE_ID
        );
        // for tv shows
        sTvShowWithTvShowInScreen_IJ = new SQLiteQueryBuilder();
        sTvShowWithTvShowInScreen_IJ.setTables(
                MovieContract.TvShowInScreenEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.TvShowsEntry.TABLE_NAME +
                        " ON " +
                        MovieContract.TvShowInScreenEntry.TABLE_NAME + "." + MovieContract.TvShowInScreenEntry.COLUMN_TV_SHOWS_ID + " = " +
                        MovieContract.TvShowsEntry.TABLE_NAME + "." + MovieContract.TvShowsEntry.COLUMN_TV_SHOWS_ID
        );
        sTvShowGenreWithGenre_IJ = new SQLiteQueryBuilder();
        sTvShowGenreWithGenre_IJ.setTables(
                MovieContract.TvShowGenreEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.GenreEntry.TABLE_NAME +
                        " ON " +
                        MovieContract.TvShowGenreEntry.TABLE_NAME + "." + MovieContract.TvShowGenreEntry.COLUMN_GENRE_ID + " = " +
                        MovieContract.GenreEntry.TABLE_NAME + "." + MovieContract.GenreEntry.COLUMN_GENRE_ID
        );
    }

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_GENRE, GENRE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_GENRE, MOVIE_GENRE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_IN_SCREEN, MOVIE_IN_SCREEN);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SHOWS, TV_SHOW);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SHOWS_GENRE, TV_SHOW_GENRE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SHOWS_IN_SCREEN, TV_SHOW_IN_SCREEN);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return MovieContract.MovieEntry.TABLE_NAME;
            case GENRE:
                return MovieContract.GenreEntry.TABLE_NAME;
            case MOVIE_GENRE:
                return MovieContract.MovieGenreEntry.TABLE_NAME;
            case MOVIE_IN_SCREEN:
                return MovieContract.MovieInScreenEntry.TABLE_NAME;
            case TV_SHOW:
                return MovieContract.TvShowsEntry.TABLE_NAME;
            case TV_SHOW_GENRE:
                return MovieContract.TvShowGenreEntry.TABLE_NAME;
            case TV_SHOW_IN_SCREEN:
                return MovieContract.TvShowInScreenEntry.TABLE_NAME;
        }
        return null;
    }

    private Uri getContentUri(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_URI;
            case GENRE:
                return MovieContract.GenreEntry.CONTENT_URI;
            case MOVIE_GENRE:
                return MovieContract.MovieGenreEntry.CONTENT_URI;
            case MOVIE_IN_SCREEN:
                return MovieContract.MovieInScreenEntry.CONTENT_URI;
            case TV_SHOW:
                return MovieContract.TvShowsEntry.CONTENT_URI;
            case TV_SHOW_GENRE:
                return MovieContract.TvShowGenreEntry.CONTENT_URI;
            case TV_SHOW_IN_SCREEN:
                return MovieContract.TvShowInScreenEntry.CONTENT_URI;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor queryCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_IN_SCREEN:
                queryCursor = sMovieWithMovieInScreen_IJ.query(mDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TV_SHOW_IN_SCREEN:
                queryCursor = sTvShowWithTvShowInScreen_IJ.query(mDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_GENRE:
                queryCursor = sMovieGenreWithGenre_IJ.query(mDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TV_SHOW_GENRE:
                queryCursor = sTvShowGenreWithGenre_IJ.query(mDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                queryCursor = mDbHelper.getReadableDatabase().query(getTableName(uri),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
        }

        if (getContext() != null) {
            queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return MovieContract.MovieEntry.DIR_TYPE;
            case GENRE:
                return MovieContract.GenreEntry.DIR_TYPE;
            case MOVIE_GENRE:
                return MovieContract.MovieGenreEntry.DIR_TYPE;
            case MOVIE_IN_SCREEN:
                return MovieContract.MovieInScreenEntry.DIR_TYPE;
            case TV_SHOW:
                return MovieContract.TvShowsEntry.DIR_TYPE;
            case TV_SHOW_GENRE:
                return MovieContract.TvShowGenreEntry.DIR_TYPE;
            case TV_SHOW_IN_SCREEN:
                return MovieContract.TvShowInScreenEntry.DIR_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long _id = db.insert(tableName, null, contentValues);
        if (_id > 0) {
            Uri tableContentUri = getContentUri(uri);
            Uri insertedUri = ContentUris.withAppendedId(tableContentUri, _id);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return insertedUri;
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);
        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
