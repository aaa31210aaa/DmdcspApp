package tab.homedetail.safetydetail.safetyplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adpter.DetailedListAdapter;
import bean.DetailedListBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.yqh.dmdcspapp.R;
import utils.BaseActivity;
import utils.DividerItemDecoration;
import utils.PortIpAddress;
import utils.ShowToast;

import static utils.PortIpAddress.CODE;
import static utils.PortIpAddress.SUCCESS_CODE;
import static utils.PortIpAddress.TOKEN_KEY;

/**
 * 计划清单
 */
public class DetailedList extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<DetailedListBean.CellsBean> searchDatas;
    private List<DetailedListBean.CellsBean> mDatas;
    private DetailedListAdapter adapter;
    private DetailedListBean.CellsBean bean;
    private String cpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_layout);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.pending_detail_list);
        Intent intent = getIntent();
        cpid = intent.getStringExtra("cpid");
        initRv();
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        if (adapter == null) {
            adapter = new DetailedListAdapter(R.layout.yjjy_item3, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(true);
            recyclerView.setAdapter(adapter);
        }

        mDatas = new ArrayList<>();
        isFirstEnter = true;
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
                    ShowToast.showShort(DetailedList.this, R.string.loadSuccess);
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
        OkGo.<String>get(PortIpAddress.Detaillist())
                .tag(this)
                .params(TOKEN_KEY, PortIpAddress.GetToken(this))
                .params("detailbean.cpid", cpid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            Log.e(TAG, response.body().toString());
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray(PortIpAddress.JsonArrName);

                            if (jsonObject.getString(CODE).equals(SUCCESS_CODE)) {
                                mDatas.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    bean = new DetailedListBean.CellsBean();
                                    bean.setScdid(jsonArray.optJSONObject(i).getString("scdid"));
                                    bean.setCpid(jsonArray.optJSONObject(i).getString("cpid"));
                                    bean.setRscdesc(jsonArray.optJSONObject(i).getString("rscdesc"));
                                    bean.setRsckyj(jsonArray.optJSONObject(i).getString("rsckyj"));
                                    bean.setPczq(jsonArray.optJSONObject(i).getString("pczqname"));
                                    bean.setPcdeptid(jsonArray.optJSONObject(i).getString("pcdeptname"));
                                    bean.setWgpcd(jsonArray.optJSONObject(i).getString("wgpcdname"));
                                    bean.setSfbc(jsonArray.optJSONObject(i).getString("sfbcname"));
                                    bean.setIsok(jsonArray.optJSONObject(i).getString("isokname"));
                                    bean.setJlly(jsonArray.optJSONObject(i).getString("jllyname"));
                                    bean.setBzorder(jsonArray.optJSONObject(i).getString("bzorder"));
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
                                        bean = (DetailedListBean.CellsBean) adapter.getData().get(position);
                                        Intent intent = new Intent(DetailedList.this, DetailedListDetail.class);
                                        intent.putExtra("scdid", bean.getScdid());
                                        intent.putExtra("cpid", bean.getCpid());
                                        intent.putExtra("rscdesc", bean.getRscdesc());
                                        intent.putExtra("rsckyj", bean.getRsckyj());
                                        intent.putExtra("pczq", bean.getPczq());
                                        intent.putExtra("pcdeptid", bean.getPcdeptid());
                                        intent.putExtra("wgpcd", bean.getWgpcd());
                                        intent.putExtra("sfbc", bean.getSfbc());
                                        intent.putExtra("isok", bean.getIsok());
                                        intent.putExtra("jlly", bean.getJlly());
                                        intent.putExtra("bzorder", bean.getBzorder());
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                ShowToast.showShort(DetailedList.this, R.string.getInfoErr);
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
                        ShowToast.showShort(DetailedList.this, R.string.connect_err);
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
            searchDatas = new ArrayList<DetailedListBean.CellsBean>();
            for (DetailedListBean.CellsBean entity : mDatas) {
                try {
                    if (entity.getRscdesc().contains(str) || entity.getRsckyj().contains(str) || entity.getPczq().contains(str)) {
                        searchDatas.add(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.setNewData(searchDatas);
            }
        }
    }
}
