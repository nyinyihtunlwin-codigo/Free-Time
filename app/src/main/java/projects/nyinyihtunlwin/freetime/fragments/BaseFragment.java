package projects.nyinyihtunlwin.freetime.fragments;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import projects.nyinyihtunlwin.freetime.delegates.MovieItemDelegate;

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
