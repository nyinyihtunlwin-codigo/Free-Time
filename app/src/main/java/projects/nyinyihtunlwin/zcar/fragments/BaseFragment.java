package projects.nyinyihtunlwin.zcar.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import projects.nyinyihtunlwin.zcar.delegates.MovieItemDelegate;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public class BaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,MovieItemDelegate{
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClickMovie(String movieId) {

    }
}
