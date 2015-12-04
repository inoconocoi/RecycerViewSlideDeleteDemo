package tielizi.com.recycerviewslidedeletedemo;

/**
 * Created by 10840 on 2015/10/31.
 */
public class ItemBean {
    private String name;
    private boolean isExtend;
    private boolean isItemSlide;
    private MyItemView myItemView;

    public ItemBean(String name, boolean isExtend, boolean isIremSlide) {
        this.name = name;
        this.isExtend = isExtend;
        this.isItemSlide = isIremSlide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExtend() {
        return isExtend;
    }

    public void setIsExtend(boolean isExtend) {
        this.isExtend = isExtend;
    }

    public boolean isItemSlide() {
        return isItemSlide;
    }

    public void setIsItemSlide(boolean isIremSlide) {
        this.isItemSlide = isIremSlide;
    }

    public MyItemView getMyItemView() {
        return myItemView;
    }

    public void setMyItemView(MyItemView myItemView) {
        this.myItemView = myItemView;
    }
}
