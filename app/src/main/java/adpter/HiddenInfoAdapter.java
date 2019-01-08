package adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.HiddenInfoBean;
import demo.yqh.dmdcspapp.R;

public class HiddenInfoAdapter extends BaseQuickAdapter<HiddenInfoBean.CellsBean, BaseViewHolder> {

    public HiddenInfoAdapter(int layoutResId, @Nullable List<HiddenInfoBean.CellsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HiddenInfoBean.CellsBean item) {
        helper.setText(R.id.hiddeninfo_item1, "隐患名称：" + item.getYhmc());
        helper.setText(R.id.hiddeninfo_item2, "发现单位：" + item.getFxdw());
        helper.setText(R.id.hiddeninfo_item3, "发现时间：" + item.getFxyhsj());
    }
}
