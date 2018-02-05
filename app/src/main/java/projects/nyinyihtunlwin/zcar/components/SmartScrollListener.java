package projects.nyinyihtunlwin.zcar.components;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Nyi Nyi Htun Lwin on 11/26/2017.
 */

public class SmartScrollListener extends RecyclerView.OnScrollListener {

    public interface OnSmartScrollListener {
        void onListEndReached();
    }

    private int visibleItemCount, pastVisibleItems, totalItemCount;
    private OnSmartScrollListener controller;
    private boolean isListEndReached = false;
    private int previousDy, currentDy;

    public SmartScrollListener(OnSmartScrollListener controller) {
        this.controller = controller;
    }

    @Override
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        super.onScrolled(rv, dx, dy);

        currentDy = dy;
        if (currentDy > previousDy) {
        } else if (currentDy < previousDy) {
            isListEndReached = false;
        }

        visibleItemCount = rv.getLayoutManager().getChildCount();
        totalItemCount = rv.getLayoutManager().getItemCount();
        pastVisibleItems = ((GridLayoutManager) rv.getLayoutManager()).findFirstVisibleItemPosition();

        previousDy = currentDy;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        super.onScrollStateChanged(recyclerView, scrollState);
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !isListEndReached) {
                isListEndReached = true;
                controller.onListEndReached();
            }
        }
    }
}
