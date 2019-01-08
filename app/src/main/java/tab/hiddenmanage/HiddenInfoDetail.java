package tab.hiddenmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adpter.CommonlyGridViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.yqh.dmdcspapp.R;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DateUtils;
import utils.MyGridView;
import utils.PermissionUtil;
import utils.PortIpAddress;
import utils.SDUtils;
import utils.ShowToast;

import static utils.PortIpAddress.CODE;
import static utils.PortIpAddress.SUCCESS_CODE;

public class HiddenInfoDetail extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.etv_fxyhdw)
    TextView etv_fxyhdw;
    @BindView(R.id.etv_yhbh)
    EditText etv_yhbh;
    @BindView(R.id.etv_yhmc)
    EditText etv_yhmc;
    @BindView(R.id.etv_yhczdw)
    EditText etv_yhczdw;
    @BindView(R.id.tv_fxsgyhrq)
    TextView tv_fxsgyhrq;
    @BindView(R.id.tv_djyhsj)
    TextView tv_djyhsj;
    @BindView(R.id.etv_sgyhfxr)
    EditText etv_sgyhfxr;
    @BindView(R.id.etv_sgyhxxtbr)
    EditText etv_sgyhxxtbr;
    @BindView(R.id.spinner_yhjb)
    TextView spinner_yhjb;
    @BindView(R.id.spinner_sgyhdly)
    TextView spinner_sgyhdly;
    @BindView(R.id.spinner_yhlx)
    TextView spinner_yhlx;
    @BindView(R.id.spinner_yhlb)
    TextView spinner_yhlb;
    @BindView(R.id.spinner_yhzt)
    TextView spinner_yhzt;
    @BindView(R.id.spinner_sgknzcdhg)
    TextView spinner_sgknzcdhg;
    @BindView(R.id.yhzp_gridview)
    MyGridView yhzp_gridview;
    @BindView(R.id.wczghzp_gridview)
    MyGridView wczghzp_gridview;
    @BindView(R.id.etv_fxsgyhdxxdd)
    EditText etv_fxsgyhdxxdd;
    @BindView(R.id.etv_fxsgyhxxbw)
    EditText etv_fxsgyhxxbw;
    @BindView(R.id.etv_dsgyhdxxxxjxdms)
    EditText etv_dsgyhdxxxxjxdms;
    @BindView(R.id.etv_dccs)
    EditText etv_dccs;
    @BindView(R.id.etv_yhjbqkjlnr)
    EditText etv_yhjbqkjlnr;
    @BindView(R.id.spinner_sfwcyhzg)
    TextView spinner_sfwcyhzg;
    @BindView(R.id.spinner_czzt)
    TextView spinner_czzt;

    private String tag = "";
    private String id = "";
    private String url = "";
    private Calendar calendar;
    private CustomDatePicker fxsgyhrq_customDatePicker;
    private CustomDatePicker djyhsj_customDatePicker;
    private Date fxsgyhrq_date;
    private Date djyhsj_date;
    private boolean fxsgyhrq_first = true;
    private boolean djyhsj_first = true;
    //隐患级别
    private String[] yhjb_arr;
    private Map yhjb_map;
    //隐患来源
    private String[] sgyhdly_arr;
    private Map sgyhdly_map;
    //隐患类型
    private String[] yhlx_arr;
    private Map yhlx_map;
    //隐患类别
    private String[] yhlb_arr;
    private Map yhlb_map;
    //隐患状态
    private String[] yhzt_arr;
    private Map yhzt_map;
    //是否整改
    private String[] sfwcyhzg_arr;
    private Map sfwcyhzg_map;
    //操作状态
    private String[] czzt_arr;
    private Map czzt_map;


    private String clickTag = "yhzp";

    //隐患照片
    private String yhzp_imgs;
    private int yhzp_clickPosition;
    private ArrayList<String> yhzp_imagePaths;
    private ArrayList<String> yhzp_list;

    //完成整改后的照片
    private String wczghzp_imgs;
    private int wczghzp_clickPosition;
    //选择的图片的集合
    private ArrayList<String> wczghzp_imagePaths;
    //拍照的照片集合
    private ArrayList<String> wczghzp_list;


    //默认字符
    private static final String myCode = "000000";
    private CommonlyGridViewAdapter yhzp_gridAdapter;
    private CommonlyGridViewAdapter wczghzp_gridAdapter;
    private static final int REQUEST_CAMERA_CODE = 10;// 相机
    private static final int REQUEST_PREVIEW_CODE = 20; //预览
    private ImageCaptureManager captureManager; // 相机拍照处理类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_info_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        id = intent.getStringExtra("yhjbxxid");
        title_name.setText(R.string.hidden_info);
        title_name_right.setText(R.string.submit);
        yhzp_imagePaths = new ArrayList<>();
        wczghzp_imagePaths = new ArrayList<>();
        calendar = Calendar.getInstance();
        yhjb_map = new HashMap();
        yhjb_arr = getResources().getStringArray(R.array.hidden_level);
        sgyhdly_map = new HashMap();
        sgyhdly_arr = getResources().getStringArray(R.array.hidden_yhly);
        yhlx_map = new HashMap();
        yhlx_arr = getResources().getStringArray(R.array.hidden_yhlx);
        yhlb_map = new HashMap();
        yhlb_arr = getResources().getStringArray(R.array.hidden_yhlb);
        yhzt_map = new HashMap();
        yhzt_arr = getResources().getStringArray(R.array.hidden_yhzt);
        sfwcyhzg_map = new HashMap();
        sfwcyhzg_arr = getResources().getStringArray(R.array.yes_no);
        czzt_map = new HashMap();
        czzt_arr = getResources().getStringArray(R.array.czzt);

        spinner_yhjb.setText(yhjb_arr[0]);
        yhjb_map.put(yhjb_arr[0], "YHJB001");
        yhjb_map.put(yhjb_arr[1], "YHJB002");
        yhjb_map.put(yhjb_arr[2], "YHJB003");
        yhjb_map.put(yhjb_arr[3], "YHJB004");

        spinner_sgyhdly.setText(sgyhdly_arr[0]);
        sgyhdly_map.put(sgyhdly_arr[0], "YHLY001");
        sgyhdly_map.put(sgyhdly_arr[1], "YHLY002");
        sgyhdly_map.put(sgyhdly_arr[2], "YHLY003");
        sgyhdly_map.put(sgyhdly_arr[3], "YHLY004");
        sgyhdly_map.put(sgyhdly_arr[4], "YHLY005");
        sgyhdly_map.put(sgyhdly_arr[5], "YHLY006");

        spinner_yhlx.setText(yhlx_arr[0]);
        yhlx_map.put(yhlx_arr[0], "YHLX001");
        yhlx_map.put(yhlx_arr[1], "YHLX002");
        yhlx_map.put(yhlx_arr[2], "YHLX003");

        spinner_yhlb.setText(yhlb_arr[0]);
        yhlb_map.put(yhlb_arr[0], "YHLB001");
        yhlb_map.put(yhlb_arr[1], "YHLB002");

        spinner_yhzt.setText(yhzt_arr[0]);
        yhzt_map.put(yhzt_arr[0], "YHZT001");
        yhzt_map.put(yhzt_arr[1], "YHZT002");
        yhzt_map.put(yhzt_arr[2], "YHZT003");
        yhzt_map.put(yhzt_arr[3], "YHZT004");
        yhzt_map.put(yhzt_arr[4], "YHZT005");
        yhzt_map.put(yhzt_arr[5], "YHZT006");
        yhzt_map.put(yhzt_arr[6], "YHZT007");
        yhzt_map.put(yhzt_arr[7], "YHZT008");
        yhzt_map.put(yhzt_arr[8], "YHZT009");
        yhzt_map.put(yhzt_arr[9], "YHZT010");

        spinner_sfwcyhzg.setText(sfwcyhzg_arr[0]);
        sfwcyhzg_map.put(sfwcyhzg_arr[0], "0");
        sfwcyhzg_map.put(sfwcyhzg_arr[1], "1");

        spinner_czzt.setText(czzt_arr[0]);
        czzt_map.put(czzt_arr[0], "0");
        czzt_map.put(czzt_arr[1], "1");

        //设置popwindow
        setPopWindowHalf(spinner_yhjb, yhjb_arr);
        setPopWindowHalf(spinner_sgyhdly, sgyhdly_arr);
        setPopWindowHalf(spinner_yhlx, yhlx_arr);
        setPopWindowHalf(spinner_yhlb, yhlb_arr);
        setPopWindowHalf(spinner_yhzt, yhzt_arr);
        setPopWindowHalf(spinner_sfwcyhzg, sfwcyhzg_arr);
        setPopWindowHalf(spinner_czzt, czzt_arr);

        //设置gridview一行多少个
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        yhzp_gridview.setNumColumns(cols);
        wczghzp_gridview.setNumColumns(cols);


        yhzp_imagePaths.add(myCode);
        yhzp_gridAdapter = new CommonlyGridViewAdapter(this, yhzp_imagePaths);
        yhzp_gridview.setAdapter(yhzp_gridAdapter);

        wczghzp_imagePaths.add(myCode);
        wczghzp_gridAdapter = new CommonlyGridViewAdapter(this, wczghzp_imagePaths);
        wczghzp_gridview.setAdapter(wczghzp_gridAdapter);
        //设置初始日期
        String nowdate = DateUtils.getStringDateShort();
//        tv_fxsgyhrq.setText(nowdate);
//        tv_djyhsj.setText(nowdate);
        if (tag.equals("add")) {
            url = "";
            fxsgyhrq_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            djyhsj_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            AddYhzpImage();
            AddWczghzpImage();

        } else {
            url = PortIpAddress.HiddenInfo();
            mConnect();
        }
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void Submit() {

    }

    /**
     * 隐患照片点击监听
     */
    private void AddYhzpImage() {
        //添加多张图片
        yhzp_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickTag = "yhzp";
                yhzp_imgs = (String) parent.getItemAtPosition(position);
                yhzp_clickPosition = position;
                AndPermission.with(HiddenInfoDetail.this)
                        .requestCode(300)
                        .permission(
                                // 申请多个权限组方式：
                                PermissionUtil.CameraPermission,
                                PermissionUtil.WriteFilePermission
                        ).send();
            }
        });
    }


    /**
     * 完成整改后照片点击监听
     */
    private void AddWczghzpImage() {
        //添加多张图片
        wczghzp_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickTag = "wczghzp";
                wczghzp_imgs = (String) parent.getItemAtPosition(position);
                wczghzp_clickPosition = position;
                AndPermission.with(HiddenInfoDetail.this)
                        .requestCode(200)
                        .permission(
                                // 申请多个权限组方式：
                                PermissionUtil.CameraPermission,
                                PermissionUtil.WriteFilePermission
                        ).send();
            }
        });
    }


    private void mConnect() {
        OkGo.<String>get(url)
                .params("bean.yhjbxxid", id)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray(PortIpAddress.JsonArrName);
                            if (jsonObject.getString(CODE).equals(SUCCESS_CODE)) {
                                etv_fxyhdw.setText(jsonArray.getJSONObject(0).getString("fxdw"));
                                etv_yhbh.setText(jsonArray.getJSONObject(0).getString("yhbh"));
                                etv_yhmc.setText(jsonArray.getJSONObject(0).getString("yhmc"));
                                etv_yhczdw.setText(jsonArray.getJSONObject(0).getString("yhczdw"));
                                tv_fxsgyhrq.setText(jsonArray.getJSONObject(0).getString("fxyhsj"));
                                tv_djyhsj.setText(jsonArray.getJSONObject(0).getString("djyhsj"));
                                etv_sgyhfxr.setText(jsonArray.getJSONObject(0).getString("fxr"));
                                etv_sgyhxxtbr.setText(jsonArray.getJSONObject(0).getString("tbr"));
                                etv_fxsgyhdxxdd.setText(jsonArray.getJSONObject(0).getString("yhdd"));
                                etv_fxsgyhxxbw.setText(jsonArray.getJSONObject(0).getString("yhbw"));
                                etv_dsgyhdxxxxjxdms.setText(jsonArray.getJSONObject(0).getString("yhms"));
                                etv_yhjbqkjlnr.setText(jsonArray.getJSONObject(0).getString("yhjbqkjlnr"));

                                fxsgyhrq_date = DateUtils.strToDate(tv_fxsgyhrq.getText().toString());
                                djyhsj_date = DateUtils.strToDate(tv_djyhsj.getText().toString());

                                AddYhzpImage();
                                AddWczghzpImage();
                                ArrayList<String> list = new ArrayList<>();


                            } else {
                                ShowToast.showShort(HiddenInfoDetail.this, R.string.getInfoErr);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ShowToast.showShort(HiddenInfoDetail.this, R.string.connect_err);
                    }
                });
    }


    @OnClick(R.id.tv_fxsgyhrq)
    void Fxsgyhrq() {
            if (tag.equals("modify")) {
                if (fxsgyhrq_first && fxsgyhrq_date != null) {
                    calendar.setTime(fxsgyhrq_date);
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    fxsgyhrq_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                    fxsgyhrq_first = false;
                }
            }
            fxsgyhrq_customDatePicker.CustomDatePickDialog(HiddenInfoDetail.this, tv_fxsgyhrq);
    }


    @OnClick(R.id.tv_djyhsj)
    void Djyhsj() {
            if (tag.equals("modify")) {
                if (djyhsj_first && djyhsj_date != null) {
                    calendar.setTime(djyhsj_date);
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    djyhsj_customDatePicker = new CustomDatePicker(mYear, mMonth, mDay);
                    djyhsj_first = false;
                }
            }
            djyhsj_customDatePicker.CustomDatePickDialog(HiddenInfoDetail.this, tv_djyhsj);
    }


    private void ClickAdd(int clickPosition, String imgs, ArrayList<String> imagePaths) {
        if (SDUtils.hasSdcard()) {
            if (myCode.equals(imgs)) {
                PhotoPickerIntent intent = new PhotoPickerIntent(HiddenInfoDetail.this);
                imagePaths.remove(imagePaths.size() - 1);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(false); // 是否显示拍照
                intent.setMaxTotal(6); // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(HiddenInfoDetail.this);
                imagePaths.remove(imagePaths.size() - 1);
                intent.setCurrentItem(clickPosition);
                intent.setPhotoPaths(imagePaths);
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        } else {
            ShowToast.showShort(HiddenInfoDetail.this, "没有SD卡");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    //权限回调
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == 200) {
                ClickAdd(wczghzp_clickPosition, wczghzp_imgs, wczghzp_imagePaths);
            } else if (requestCode == 300) {
                ClickAdd(yhzp_clickPosition, yhzp_imgs, yhzp_imagePaths);
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == 200) {
                ShowToast.showShort(HiddenInfoDetail.this, "需要开启权限");
            } else if (requestCode == 300) {
                ShowToast.showShort(HiddenInfoDetail.this, "需要开启权限");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    if (clickTag.equals("yhzp")) {
                        loadAdpater(list, yhzp_imagePaths, yhzp_gridAdapter);
                    } else {
                        loadAdpater(list, wczghzp_imagePaths, wczghzp_gridAdapter);
                    }

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    if (clickTag.equals("yhzp")) {
                        loadAdpater(ListExtra, yhzp_imagePaths, yhzp_gridAdapter);
                    } else {
                        loadAdpater(ListExtra, wczghzp_imagePaths, wczghzp_gridAdapter);
                    }

                    break;
            }
        } else {
            if (clickTag.equals("yhzp")) {
                if (data == null) {
                    if (!yhzp_imagePaths.contains(myCode)) {
                        yhzp_imagePaths.add(myCode);
                    }
                    yhzp_gridAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    if (!ListExtra.contains(myCode)) {
                        ListExtra.add(myCode);
                    }
                    yhzp_imagePaths = ListExtra;
                    yhzp_gridAdapter.DataNotify(yhzp_imagePaths);
                }
            } else {
                if (data == null) {
                    if (!wczghzp_imagePaths.contains(myCode)) {
                        wczghzp_imagePaths.add(myCode);
                    }
                    wczghzp_gridAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    if (!ListExtra.contains(myCode)) {
                        ListExtra.add(myCode);
                    }
                    wczghzp_imagePaths = ListExtra;
                    wczghzp_gridAdapter.DataNotify(wczghzp_imagePaths);
                }
            }

        }
    }


    //加载图片
    private void loadAdpater(List<String> paths, ArrayList<String> imagePaths, CommonlyGridViewAdapter adapter) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (imagePaths.contains(myCode)) {
            imagePaths.remove(myCode);
        }
        imagePaths.addAll(paths);
        imagePaths.add(myCode);
        adapter.DataNotify(imagePaths);
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
