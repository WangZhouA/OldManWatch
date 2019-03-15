package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.entity.Relation;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.MultiItemTypeAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/21
 * @Describe 关系页面
 */
public class RelationActivity extends BaseAppCompatActivity {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.tv_relation)
    TextView tvrelation;
    @BindView(R.id.rv_relation)
    RecyclerView mRecyclerView;

    private Context mContext;
    private List<Relation> mData = new ArrayList<>();
    private static final int SELECT = 2;
    private CommonAdapter<Relation> mAdapter;
    private int[] imgIDs = {R.mipmap.call1, R.mipmap.call2, R.mipmap.call3, R.mipmap.call4, R.mipmap.call5,
            R.mipmap.call6, R.mipmap.call7, R.mipmap.call8, R.mipmap.call9, R.mipmap.call10,
            R.mipmap.call11, R.mipmap.call12, R.mipmap.call13, R.mipmap.call14, R.mipmap.call15, R.mipmap.call16};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_relation;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.user_relation);
        tvBarRight.setText(R.string.save);
    }

    @Override
    protected void initData() {
        List<String> familys = new ArrayList<>();
        familys.addAll(Arrays.asList(getResources().getStringArray(R.array.family)));
        for (int i = 0; i < imgIDs.length; i++) {
            Relation relation = new Relation();
            relation.setName(familys.get(i));
            relation.setPic(imgIDs[i]);
            relation.setSelecked(false);
            mData.add(relation);
        }

        mAdapter = new CommonAdapter<Relation>(mContext, R.layout.item_select_pic, mData) {
            @Override
            protected void convert(ViewHolder holder, Relation relation, int position) {
                holder.setText(R.id.tv_text, relation.getName());
                ImageView pic = holder.getView(R.id.iv_pic);
                Glide.with(mContext).load(relation.getPic()).into(pic);
                holder.setVisible(R.id.iv_selected, relation.isSelecked());
            }
        };
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));//设置Item有4个
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                if (position == mData.size() - 1) {
                    showDialog();
                } else {
                    for (int i = 0; i < mData.size(); i++) {
                        if (position == i) {
                            mData.get(i).setSelecked(true);
                            tvrelation.setText(mData.get(position).getName());
                        } else mData.get(i).setSelecked(false);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View textEntryView = inflater
                .inflate(
                        R.layout.dialog_relation,
                        null);
        final EditText etCustom = (EditText) textEntryView.findViewById(R.id.et_custom);
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(
                this, android.app.AlertDialog.THEME_HOLO_LIGHT);
        dialog.setView(textEntryView);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if ("".equals(etCustom.getText().toString())) {
                    ToastUtils.show(mContext, " 请输入你们的关系！", ToastUtils.LENGTH_LONG);
                    return;
                }
                addData(etCustom.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        android.app.AlertDialog ad = dialog.create();
        ad.show();
    }

    public void addData(String name) {
        Relation relation = new Relation();
        relation.setName(name);
        relation.setPic(R.mipmap.call16);
        relation.setSelecked(false);
        mData.add(mData.size() - 1, relation);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                Intent intent = new Intent();
                intent.putExtra("name", tvrelation.getText().toString());
                setResult(SELECT, intent);
                finish();
                break;
        }
    }

}
