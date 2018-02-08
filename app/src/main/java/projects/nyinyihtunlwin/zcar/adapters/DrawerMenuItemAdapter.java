package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.DrawerMenuItemVO;
import projects.nyinyihtunlwin.zcar.delegates.DrawerMenuItemDelegate;
import projects.nyinyihtunlwin.zcar.viewholders.DrawerMenuItemViewHolder;

/**
 * Created by Dell on 2/7/2018.
 */

public class DrawerMenuItemAdapter extends BaseRecyclerAdapter<DrawerMenuItemViewHolder, DrawerMenuItemVO> {

    private DrawerMenuItemDelegate mDrawerMenuItemDelegate;

    public DrawerMenuItemAdapter(Context context,DrawerMenuItemDelegate drawerMenuItemDelegate) {
        super(context);
        this.mDrawerMenuItemDelegate=drawerMenuItemDelegate;
    }

    @Override
    public DrawerMenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_drawer_menu, parent, false);
        return new DrawerMenuItemViewHolder(view,mDrawerMenuItemDelegate);
    }

}
