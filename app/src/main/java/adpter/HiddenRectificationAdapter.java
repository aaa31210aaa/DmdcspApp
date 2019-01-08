package adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.HiddenRectificationBean;
import demo.yqh.dmdcspapp.R;

public class HiddenRectificationAdapter extends BaseQuickAdapter<HiddenRectificationBean.CellsBean, BaseViewHolder> {

    public HiddenRectificationAdapter(int layoutResId, @Nullable List<HiddenRectificationBean.CellsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HiddenRectificationBean.CellsBean item) {
        helper.setText(R.id.hiddeninfo_item1, "整改发出单位：" + item.getFczgdw());
        helper.setText(R.id.hiddeninfo_item2, "整改类型：" + item.getZglx());
        helper.setText(R.id.hiddeninfo_item3, "核查的日期：" + item.getZgqx());
    }
}
