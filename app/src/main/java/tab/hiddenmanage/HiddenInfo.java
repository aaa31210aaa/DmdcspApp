package tab.hiddenmanage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adpter.HiddenInfoAdapter;
import bean.HiddenInfoBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.yqh.dmdcspapp.R;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DividerItemDecoration;
import utils.PortIpAddress;
import utils.ShowToast;
import utils.SwipeMenu;

import static utils.PortIpAddress.CODE;
import static utils.PortIpAddress.SUCCESS_CODE;

/**
 * 隐患基本信息
 */
public class HiddenInfo extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    ImageView title_name_right;
    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private HiddenInfoAdapter adapter;
    private List<HiddenInfoBean.CellsBean> mDatas;
    private List<HiddenInfoBean.CellsBean> searchDatas;
    private HiddenInfoBean.CellsBean bean;
    private String tag = "";
    private int requestCode = 10;

    private Dialog assignDialog;
    private EditText etv_zdzgdw;
    private EditText etv_zgzrr;
    private TextView tv_zgqx;
    private Button assign_btn;
    private Date zgqx_date;
    private Calendar calendar;
    private CustomDatePicker zgqx_customDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_info);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.hidden_info);
        title_name_right.setBackgroundResource(R.drawable.add);
        initRv();
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void Add() {
        Intent intent = new Intent(HiddenInfo.this, HiddenInfoDetail.class);
        intent.putExtra("yhzgtzxxid", bean.getYhjbxxid());
        tag = "add";
        intent.putExtra("tag", tag);
        startActivityForResult(intent, requestCode);
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
//        recyclerView.setSwipeItemClickListener(this);
        recyclerView.setSwipeMenuCreator(SwipeMenu.CreateSwipeMenu(this, R.drawable.selector_red, R.drawable.selector_gray, R.drawable.modify, R.drawable.delete, Color.WHITE));
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

        if (adapter == null) {
            adapter = new HiddenInfoAdapter(R.layout.hiddeninfo_item, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(true);
            recyclerView.setAdapter(adapter);
        }
        mDatas = new ArrayList<>();
        isFirstEnter = true;

//        assignDialog = new AlertDialog.Builder(HiddenInfo.this,R.style.AppTheme).create();
        assignDialog = new Dialog(HiddenInfo.this, R.style.style_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.assign_dialog_item, null);

        etv_zdzgdw = (EditText) root.findViewById(R.id.etv_zdzgdw);
        etv_zgzrr = (EditText) root.findViewById(R.id.etv_zgzrr);
        tv_zgqx = (TextView) root.findViewById(R.id.tv_zgqx);
        assign_btn = (Button) root.findViewById(R.id.assign_btn);


        tv_zgqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zgqx_customDatePicker == null || calendar == null){
                    calendar = Calendar.getInstance();
                    zgqx_customDatePicker = new CustomDatePicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                zgqx_customDatePicker.CustomDatePickDialog(HiddenInfo.this, tv_zgqx);
            }

        });

        assign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.showShort(HiddenInfo.this, "指派");
            }
        });

        assignDialog.setCanceledOnTouchOutside(true);//点击dialog外部消失
        Window window = assignDialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.dialogstyle);
        window.setContentView(root);

        //获得window窗口的属性
        WindowManager.LayoutParams params = window.getAttributes();
        //设置窗口宽度为充满全屏
        params.width = WindowManager.LayoutParams.MATCH_PARENT;//如果不设置,可能部分机型出现左右有空隙,也就是产生margin的感觉
        //设置窗口高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;//就是这个属性导致window后所有的东西都成暗淡
        params.dimAmount = 0.5f;//设置对话框的透明程度背景(非布局的透明度)

        //将设置好的属性set回去
        window.setAttributes(params);

        initRefresh();
        MonitorEditext();
    }


    /**
     * 设置下拉刷新
     */
    private void initRefresh() {
        if (isFirstEnter) {
            isFirstEnter = false;
            refreshLayout.autoRefresh();//第一次进入触发自动刷新
        }

        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                handler.sendEmptyMessageDelayed(1, ShowToast.refreshTime);
            }
        });

        //加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                handler.sendEmptyMessageDelayed(0, ShowToast.refreshTime);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    ShowToast.showShort(HiddenInfo.this, R.string.loadSuccess);
                    refreshLayout.finishLoadmore();
                    break;
                case 1:
                    mConnect();
                    break;
                default:
                    break;
            }
        }
    };


    private void mConnect() {
        OkGo.<String>get(PortIpAddress.HiddenInfo())
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray(PortIpAddress.JsonArrName);
                            if (jsonObject.getString(CODE).equals(SUCCESS_CODE)) {
                                mDatas.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    bean = new HiddenInfoBean.CellsBean();
                                    bean.setYhjbxxid(jsonArray.optJSONObject(i).getString("yhjbxxid"));
                                    bean.setYhmc(jsonArray.optJSONObject(i).getString("yhmc"));
                                    bean.setFxdw(jsonArray.optJSONObject(i).getString("fxdw"));
                                    bean.setFxyhsj(jsonArray.optJSONObject(i).getString("fxyhsj"));
                                    mDatas.add(bean);
                                }

                                adapter.setNewData(mDatas);

                                //如果无数据设置空布局
                                if (mDatas.size() == 0) {
                                    adapter.setEmptyView(R.layout.nodata_layout, (ViewGroup) recyclerView.getParent());
                                }

                                //子项点击事件
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        bean = (HiddenInfoBean.CellsBean) adapter.getData().get(position);
                                        Intent intent = new Intent(HiddenInfo.this, HiddenInfoDetail.class);
                                        intent.putExtra("yhjbxxid", bean.getYhjbxxid());
                                        tag = "modify";
                                        intent.putExtra("tag", tag);
                                        startActivityForResult(intent, requestCode);
                                    }
                                });
                            } else {
                                ShowToast.showShort(HiddenInfo.this, R.string.getInfoErr);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        refreshLayout.finishRefresh();
                        ShowToast.showShort(HiddenInfo.this, R.string.connect_err);
                    }
                });
    }


    /**
     * 监听搜索框
     */
    private void MonitorEditext() {
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, count + "----");
                if (mDatas != null) {
                    if (search_edittext.length() > 0) {
                        refreshLayout.setEnableRefresh(false);
                        search_clear.setVisibility(View.VISIBLE);
                        search(search_edittext.getText().toString().trim());
                    } else {
                        refreshLayout.setEnableRefresh(true);
                        search_clear.setVisibility(View.GONE);
                        if (adapter != null) {
                            adapter.setNewData(mDatas);
                        }
                    }
                } else {
                    if (search_edittext.length() > 0) {
                        search_clear.setVisibility(View.VISIBLE);
                    } else {
                        search_clear.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 清除搜索框内容
     */
    @OnClick(R.id.search_clear)
    public void ClearSearch() {
        search_edittext.setText("");
        search_clear.setVisibility(View.GONE);
    }

    //搜索框
    private void search(String str) {
        if (mDatas != null) {
            searchDatas = new ArrayList<HiddenInfoBean.CellsBean>();
            for (HiddenInfoBean.CellsBean entity : mDatas) {
                try {
                    if (entity.getYhmc().contains(str) || entity.getFxdw().contains(str) || entity.getFxyhsj().contains(str)) {
                        searchDatas.add(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.setNewData(searchDatas);
            }
        }
    }


    /**
     * 列表侧滑菜单子项点击监听
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//            bean = adapter.getData().get(adapterPosition);

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//              Toast.makeText(TruckLedger.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                switch (menuPosition) {
                    case 0:
                        ShowToast.showShort(HiddenInfo.this, "弹出指派dialog");
                        assignDialog.show();
                        break;
                    case 1:
                        ShowToast.showShort(HiddenInfo.this, "弹出删除dialog");
                        break;
                }
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(TruckLedger.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

}
