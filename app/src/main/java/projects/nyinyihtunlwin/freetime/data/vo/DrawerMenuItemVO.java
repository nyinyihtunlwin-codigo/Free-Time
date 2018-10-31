package projects.nyinyihtunlwin.freetime.data.vo;

/**
 * Created by Dell on 2/7/2018.
 */

public class DrawerMenuItemVO {
    private int image;
    private String name;

    public DrawerMenuItemVO(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
