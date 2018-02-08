package projects.nyinyihtunlwin.zcar.events;

import projects.nyinyihtunlwin.zcar.data.vo.DrawerMenuItemVO;

/**
 * Created by Dell on 2/8/2018.
 */

public class TapDrawerMenuItemEvent {
    DrawerMenuItemVO drawerMenuItemVO;

    public TapDrawerMenuItemEvent(DrawerMenuItemVO drawerMenuItemVO) {
        this.drawerMenuItemVO = drawerMenuItemVO;
    }

    public DrawerMenuItemVO getDrawerMenuItemVO() {
        return drawerMenuItemVO;
    }
}
