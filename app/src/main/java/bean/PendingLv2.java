package bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import adpter.ExpandableItemAdapter;

public class PendingLv2 implements MultiItemEntity {
    public String title1;
    public String title2;
    public String title3;

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_2;
    }
}
