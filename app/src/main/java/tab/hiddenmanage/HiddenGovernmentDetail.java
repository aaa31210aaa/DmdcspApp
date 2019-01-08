package tab.hiddenmanage;

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
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.yqh.dmdcspapp.R;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DateUtils;
import utils.PortIpAddress;
import utils.ShowToast;

import static utils.PortIpAddress.CODE;
import static utils.PortIpAddress.SUCCESS_CODE;

public class HiddenGovernmentDetail extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;

    @BindView(R.id.spinner_jbxx)
    TextView spinner_jbxx;
    @BindView(R.id.etv_jgzrdw)
    EditText etv_jgzrdw;
    @BindView(R.id.tv_zgwcrq)
    TextView tv_zgwcrq;
    @BindView(R.id.etv_hcdw)
    EditText etv_hcdw;
    @BindView(R.id.spinner_sfgpdb)
    TextView spinner_sfgpdb;
    @BindView(R.id.etv_gpdbjb)
    EditText etv_gpdbjb;
    @BindView(R.id.etv_gpdbdw)
    EditText etv_gpdbdw;
    @BindView(R.id.etv_zlmbrw)
    EditText etv_zlmbrw;
    @BindView(R.id.etv_jfwzls)
    EditText etv_jfwzls;
    @BindView(R.id.etv_jgzrr)
    EditText etv_jgzrr;
    @BindView(R.id.tv_hcrq)
    TextView tv_hcrq;
    @BindView(R.id.etv_hcqk)
    EditText etv_hcqk;
    @BindView(R.id.etv_gpdbwh)
    EditText etv_gpdbwh;
    @BindView(R.id.tv_gpdbsj)
    TextView tv_gpdbsj;
    @BindView(R.id.etv_jfhwzdlsqk)
    EditText etv_jfhwzdlsqk;
    @BindView(R.id.etv_zlyq)
    EditText etv_zlyq;

    private String tag = "";
    private String id = "";
    private String url = "";
    private CustomDatePicker zgwcrq_customDatePicker;
    private CustomDatePicker hcrq_customDatePicker;
    private CustomDatePicker gpdbsj_customDatePicker;

    private Date zgwcrq_date;
    private Date hcrq_date;
    private Date gpdbsj_date;
    private Calendar calendar;
    private boolean zgwcrq_first = true;
    private boolean hcrq_first = true;
    private boolean gpdbsj_first = true;
    private String[] sfgpdb_arr;
    private Map sfgpdb_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_government_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        id = intent.getStringExtra("zdyhpczlid");
        title_name.setText(R.string.hidden_government);
        title_name_right.setText(R.string.submit);
        calendar = Calendar.getInstance();
        sfgpdb_map = new HashMap();
        sfgpdb_arr = getResources().getStringArray(R.array.yes_no);
        spinner_sfgpdb.setText(sfgpdb_arr[0]);
        sfgpdb_map.put(sfgpdb_arr[0], "0");
        sfgpdb_map.put(sfgpdb_arr[1], "1");
        //设置popwindow
        setPopWindowHalf(spinner_sfgpdb, sfgpdb_arr);

        if (tag.equals("add")) {
            url = "";
            //设置初始日期
            String nowdate = DateUtils.getStringDateShort();
            tv_zgwcrq.setText(nowdate);
            tv_hcrq.setText(nowdate);
            tv_gpdbsj.setText(nowdate);
            zgwcrq_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            hcrq_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            gpdbsj_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            url = PortIpAddress.HiddenGovernment();
            mConnect();
        }
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void Add() {

    }

    private void mConnect() {
        OkGo.<String>get(url)
                .params("bean.zdyhpczlid", id)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray(PortIpAddress.JsonArrName);
                            if (jsonObject.getString(CODE).equals(SUCCESS_CODE)) {
                                //基本信息没写
                                etv_jgzrdw.setText(jsonArray.getJSONObject(0).getString("zdyhzljgzrdw"));

                                tv_zgwcrq.setText(jsonArray.getJSONObject(0).getString("zdyhzljgzrr")); //有问题

                                etv_hcdw.setText(jsonArray.getJSONObject(0).getString("zdyhhcdw"));
                                etv_gpdbjb.setText(jsonArray.getJSONObject(0).getString("gpdbjb"));
                                etv_gpdbdw.setText(jsonArray.getJSONObject(0).getString("gpdbdw"));
                                etv_zlmbrw.setText(jsonArray.getJSONObject(0).getString("zlmbrw"));
                                etv_jfwzls.setText(jsonArray.getJSONObject(0).getString("zlmbrw"));

                                etv_jgzrr.setText(jsonArray.getJSONObject(0).getString("zdyhzljgzrr")); //有问题

                                tv_hcrq.setText(jsonArray.getJSONObject(0).getString("zdyhhcrq"));
                                etv_hcqk.setText(jsonArray.getJSONObject(0).getString("zdyhhcqk"));
                                etv_gpdbwh.setText(jsonArray.getJSONObject(0).getString("gpdbwh"));
                                tv_gpdbsj.setText(jsonArray.getJSONObject(0).getString("gpdbsj"));
                                etv_jfhwzdlsqk.setText(jsonArray.getJSONObject(0).getString("jfhwzdlsqk"));
                                etv_zlyq.setText(jsonArray.getJSONObject(0).getString("zldsxhyq"));

                                zgwcrq_date = DateUtils.strToDate(tv_zgwcrq.getText().toString());
                                hcrq_date = DateUtils.strToDate(tv_hcrq.getText().toString());
                                gpdbsj_date = DateUtils.strToDate(tv_gpdbsj.getText().toString());
                            } else {
                                ShowToast.showShort(HiddenGovernmentDetail.this, R.string.getInfoErr);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ShowToast.showShort(HiddenGovernmentDetail.this, R.string.connect_err);
                    }
                });
    }


    @OnClick(R.id.tv_zgwcrq)
    void Zgwcrq() {
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
        zgwcrq_customDatePicker.CustomDatePickDialog(HiddenGovernmentDetail.this, tv_zgwcrq);
    }

    @OnClick(R.id.tv_hcrq)
    void Hcrq() {
        if (tag.equals("modify")) {
            if (hcrq_first && hcrq_date != null) {
                calendar.setTime(hcrq_date);
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                hcrq_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                hcrq_first = false;
            }
        }
        hcrq_customDatePicker.CustomDatePickDialog(HiddenGovernmentDetail.this, tv_hcrq);
    }

    @OnClick(R.id.tv_gpdbsj)
    void Gpdbsj() {
        if (tag.equals("modify")) {
            if (gpdbsj_first && gpdbsj_date != null) {
                calendar.setTime(gpdbsj_date);
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                gpdbsj_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                gpdbsj_first = false;
            }
        }
        gpdbsj_customDatePicker.CustomDatePickDialog(HiddenGovernmentDetail.this, tv_gpdbsj);
    }
}
