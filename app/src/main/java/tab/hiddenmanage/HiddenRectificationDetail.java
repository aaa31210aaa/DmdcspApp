package tab.hiddenmanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.yqh.dmdcspapp.R;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DateUtils;
import utils.DialogUtil;
import utils.PortIpAddress;
import utils.ShowToast;

import static utils.PortIpAddress.CODE;
import static utils.PortIpAddress.SUCCESS_CODE;

/**
 * 隐患整改信息
 */
public class HiddenRectificationDetail extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.spinner_fsdw)
    TextView spinner_fsdw;
    @BindView(R.id.etv_tzdbh)
    EditText etv_tzdbh;
    @BindView(R.id.etv_zgdwzrr)
    EditText etv_zgdwzrr;
    @BindView(R.id.tv_zgqx)
    TextView tv_zgqx;
    @BindView(R.id.tv_zgwcrq)
    TextView tv_zgwcrq;
    @BindView(R.id.tv_djsj)
    TextView tv_djsj;
    @BindView(R.id.etv_zgdw)
    EditText etv_zgdw;
    @BindView(R.id.etv_zgzrr)
    EditText etv_zgzrr;
    @BindView(R.id.etv_zgqk)
    EditText etv_zgqk;
    @BindView(R.id.etv_yszrdw)
    EditText etv_yszrdw;
    @BindView(R.id.etv_zglx)
    EditText etv_zglx;
    @BindView(R.id.etv_xadw)
    EditText etv_xadw;
    @BindView(R.id.etv_zgcs)
    EditText etv_zgcs;

    private String tag = "";
    private String url = "";
    private String id;
    private CustomDatePicker zgqx_customDatePicker;
    private CustomDatePicker zgwcrq_customDatePicker;
    private CustomDatePicker djsj_customDatePicker;

    private Date zgqx_date;
    private Date zgwcrq_date;
    private Date djsj_date;
    private Calendar calendar;
    private boolean zgqx_first = true;
    private boolean zgwcrq_first = true;
    private boolean djsj_first = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_rectification_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        id = intent.getStringExtra("yhzgtzxxid");
        title_name.setText(R.string.hidden_rectification);
        title_name_right.setText(R.string.submit);
        calendar = Calendar.getInstance();
        //设置初始日期
        String nowdate = DateUtils.getStringDateShort();
        tv_zgqx.setText(nowdate);
        tv_zgwcrq.setText(nowdate);
        tv_djsj.setText(nowdate);

        if (tag.equals("add")) {
            url = "";
            zgqx_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            zgwcrq_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            djsj_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            url = PortIpAddress.HiddenRectification();
            mConnect();
        }
    }

    private void mConnect() {
        OkGo.<String>get(url)
                .params("bean.yhzgtzxxid", id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray(PortIpAddress.JsonArrName);
                            if (jsonObject.getString(CODE).equals(SUCCESS_CODE)) {
                                //发送单位没数据
                                etv_tzdbh.setText(jsonArray.getJSONObject(0).getString("tzdbh"));
                                etv_zgdwzrr.setText(jsonArray.getJSONObject(0).getString("zgzrdwfzr"));

                                tv_zgqx.setText(jsonArray.getJSONObject(0).getString("zgqx"));
                                tv_zgwcrq.setText(jsonArray.getJSONObject(0).getString("zgwcrq"));
                                tv_djsj.setText(jsonArray.getJSONObject(0).getString("djsj"));

                                etv_zgdw.setText(jsonArray.getJSONObject(0).getString("zgzrdw"));
                                etv_zgzrr.setText(jsonArray.getJSONObject(0).getString("zgzrr"));
                                etv_zgqk.setText(jsonArray.getJSONObject(0).getString("zgqknm"));
                                etv_yszrdw.setText(jsonArray.getJSONObject(0).getString("zrysdw"));
                                etv_zglx.setText(jsonArray.getJSONObject(0).getString("zglxnm"));
                                etv_xadw.setText(jsonArray.getJSONObject(0).getString("xadw"));
                                etv_zgcs.setText(jsonArray.getJSONObject(0).getString("jycs"));

                                zgqx_date = DateUtils.strToDate(tv_zgqx.getText().toString());
                                zgwcrq_date = DateUtils.strToDate(tv_zgwcrq.getText().toString());
                                djsj_date = DateUtils.strToDate(tv_djsj.getText().toString());
                            } else {
                                ShowToast.showShort(HiddenRectificationDetail.this, R.string.getInfoErr);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ShowToast.showShort(HiddenRectificationDetail.this, R.string.connect_err);
                    }
                });
    }


    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void Submit() {
        dialog = DialogUtil.createLoadingDialog(HiddenRectificationDetail.this, R.string.loading_write);
        OkGo.<String>get(url)
                .params("bean.yhzgtzxxid", id)
                .params("", "")
                .params("", "")
                .params("", "")
                .params("", "")
                .params("", "")
                .params("", "")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            if (jsonObject.getString(CODE).equals(SUCCESS_CODE)) {
                                ShowToast.showShort(HiddenRectificationDetail.this, R.string.submit_success);
                                setResult(Activity.RESULT_OK);
                                finish();
                            } else {
                                ShowToast.showShort(HiddenRectificationDetail.this, R.string.getInfoErr);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dialog.dismiss();
                        ShowToast.showShort(HiddenRectificationDetail.this, R.string.connect_err);
                    }
                });
    }


    @OnClick(R.id.tv_zgqx)
    void Zgqx() {
        if (!tv_zgqx.getText().toString().equals("")) {
            if (tag.equals("modify")) {
                if (zgqx_first && zgqx_date != null) {
                    calendar.setTime(zgqx_date);
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    zgqx_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                    zgqx_first = false;
                }
            }
            zgqx_customDatePicker.CustomDatePickDialog(HiddenRectificationDetail.this, tv_zgqx);
        }
    }

    @OnClick(R.id.tv_zgwcrq)
    void Zgwcrq() {
        if (!tv_zgwcrq.getText().toString().equals("")) {
            if (tag.equals("modify")) {
                if (zgwcrq_first && zgwcrq_date != null) {
                    calendar.setTime(zgwcrq_date);
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    zgwcrq_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                    zgwcrq_first = false;
                }
            }
            zgwcrq_customDatePicker.CustomDatePickDialog(HiddenRectificationDetail.this, tv_zgwcrq);
        }
    }

    @OnClick(R.id.tv_djsj)
    void Djsj() {
        if (!tv_djsj.getText().toString().equals("")) {
            if (tag.equals("modify")) {
                if (djsj_first && djsj_date != null) {
                    calendar.setTime(djsj_date);
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    djsj_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                    djsj_first = false;
                }
            }
            djsj_customDatePicker.CustomDatePickDialog(HiddenRectificationDetail.this, tv_djsj);
        }
    }
}
