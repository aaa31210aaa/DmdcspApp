package tab.homedetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.yqh.dmdcspapp.R;
import tab.hiddenmanage.HiddenCase;
import tab.hiddenmanage.HiddenCheck;
import tab.hiddenmanage.HiddenDetail;
import tab.hiddenmanage.HiddenFlow;
import tab.hiddenmanage.HiddenGovernment;
import tab.hiddenmanage.HiddenInfo;
import tab.hiddenmanage.HiddenRectification;
import utils.BaseActivity;

public class Hidden extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.hidden_title);
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    /**
     * 隐患整改信息
     */
    @OnClick(R.id.hidden_rectification)
    void HiddenRectification() {
        startActivity(new Intent(this, HiddenRectification.class));
    }

    /**
     * 隐患验收管理
     */
    @OnClick(R.id.hidden_check)
    void HiddenCheck() {
        startActivity(new Intent(this, HiddenCheck.class));
    }

    /**
     * 隐患基本信息
     */
    @OnClick(R.id.hidden_info)
    void HiddenInfo() {
        startActivity(new Intent(this, HiddenInfo.class));
    }

    /**
     * 隐患整改详情
     */
    @OnClick(R.id.hidden_detail)
    void HiddenDetail() {
        startActivity(new Intent(this, HiddenDetail.class));
    }

    /**
     * 隐患整改流程
     */
    @OnClick(R.id.hidden_flow)
    void HiddenFlow() {
        startActivity(new Intent(this, HiddenFlow.class));
    }

    /**
     * 隐患销案管理
     */
    @OnClick(R.id.hidden_case)
    void HiddenCase() {
        startActivity(new Intent(this, HiddenCase.class));
    }


    /**
     * 重大隐患排查治理
     */
    @OnClick(R.id.hidden_government)
    void HiddenGovernment() {
        startActivity(new Intent(this, HiddenGovernment.class));
    }
}
