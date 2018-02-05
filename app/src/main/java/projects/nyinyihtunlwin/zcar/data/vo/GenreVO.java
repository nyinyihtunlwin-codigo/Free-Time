package projects.nyinyihtunlwin.zcar.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import projects.nyinyihtunlwin.zcar.persistence.MovieContract;

/**
 * Created by Dell on 2/5/2018.
 */

public class GenreVO {


    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String genreName;

    public GenreVO() {
    }

    public GenreVO(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public ContentValues parseToContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.GenreEntry.COLUMN_GENRE_ID, id);
        contentValues.put(MovieContract.GenreEntry.COLUMN_GENRE_NAME, genreName);
        return contentValues;
    }

    public static GenreVO parseFromCursor(Cursor cursor) {
        GenreVO genre = new GenreVO();
        genre.id = cursor.getInt(cursor.getColumnIndex(MovieContract.GenreEntry.COLUMN_GENRE_ID));
        genre.genreName = cursor.getString(cursor.getColumnIndex(MovieContract.GenreEntry.COLUMN_GENRE_NAME));
        return genre;
    }
}
