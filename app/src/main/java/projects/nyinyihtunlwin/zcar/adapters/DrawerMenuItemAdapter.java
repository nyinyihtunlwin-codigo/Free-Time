package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.DrawerMenuItemVO;
import projects.nyinyihtunlwin.zcar.viewholders.DrawerMenuItemViewHolder;

/**
 * Created by Dell on 2/7/2018.
 */

public class DrawerMenuItemAdapter extends BaseRecyclerAdapter<DrawerMenuItemViewHolder, DrawerMenuItemVO> {

    public DrawerMenuItemAdapter(Context context) {
        super(context);
    }

    @Override
    public DrawerMenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_drawer_menu, parent, false);
        return new DrawerMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerMenuItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
