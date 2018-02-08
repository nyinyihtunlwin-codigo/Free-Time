package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.DrawerMenuItemVO;
import projects.nyinyihtunlwin.zcar.delegates.DrawerMenuItemDelegate;

/**
 * Created by Dell on 2/7/2018.
 */

public class DrawerMenuItemViewHolder extends BaseViewHolder<DrawerMenuItemVO> {

    @BindView(R.id.iv_drawer_menu_item)
    ImageView ivDrawerMenuItem;

    @BindView(R.id.tv_drawer_menu_item)
    TextView tvDrawerMenuItem;

    DrawerMenuItemVO mData;

    DrawerMenuItemDelegate mDrawerMenuItemDelegate;

    public DrawerMenuItemViewHolder(View itemView, DrawerMenuItemDelegate drawerMenuItemDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mDrawerMenuItemDelegate = drawerMenuItemDelegate;
    }

    @Override
    public void setData(DrawerMenuItemVO mData) {
        this.mData = mData;
        ivDrawerMenuItem.setImageResource(mData.getImage());
        tvDrawerMenuItem.setText(mData.getName());

    }

    @Override
    public void onClick(View view) {
        mDrawerMenuItemDelegate.clickDrawerMenuItem(mData);
    }
}
