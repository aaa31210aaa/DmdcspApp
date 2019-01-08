package adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.HiddenGovernmentBean;
import demo.yqh.dmdcspapp.R;

public class HiddenGovernmentAdapter extends BaseQuickAdapter<HiddenGovernmentBean.CellsBean, BaseViewHolder> {

    public HiddenGovernmentAdapter(int layoutResId, @Nullable List<HiddenGovernmentBean.CellsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HiddenGovernmentBean.CellsBean item) {
        helper.setText(R.id.hiddeninfo_item1, "单位名称：" + item.getZdyhzljgzrdw());
        helper.setText(R.id.hiddeninfo_item2, "责任人姓名：" + item.getZdyhzljgzrr());
        helper.setText(R.id.hiddeninfo_item3, "核查的日期：" + item.getZdyhhcrq());
    }
}
