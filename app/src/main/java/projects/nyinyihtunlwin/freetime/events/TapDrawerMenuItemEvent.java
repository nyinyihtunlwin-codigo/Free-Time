package projects.nyinyihtunlwin.freetime.events;

import projects.nyinyihtunlwin.freetime.data.vo.DrawerMenuItemVO;

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
