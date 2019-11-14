package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.DropBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zoubeiwen on 2018/6/20.
 */

public class DropdownButton extends RelativeLayout implements Checkable, View.OnClickListener, PopWinDownUtil.OnDismissLisener, AdapterView.OnItemClickListener {
    /**
     * 菜单按钮文字内容
     */
    private TextView text;
    private ImageView ivtabbutton;
    /**
     * 菜单按钮底部的提示条
     */
    private View bLine;
    private boolean isCheced;
    private PopWinDownUtil popWinDownUtil;
    private Context mContext;
    private DropDownAdapter adapter;
    /**
     * 传入的数据
     */
    private List<DropBean> drops = new ArrayList<DropBean>();
    /**
     * 当前被选择的item位置
     */
    private int selectPosition;
    private OnDropItemSelectListener onDropItemSelectListener;

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //菜单按钮的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_dropdown_tabbutton, this, true);

        text = view.findViewById(R.id.tvtabbutton);
        ivtabbutton = view.findViewById(R.id.ivtabbutton);
        bLine = view.findViewById(R.id.bottomLine);
        //点击事件，点击外部区域隐藏popupWindow
        setOnClickListener(this);
    }

    /**
     * 添加数据，默认选择第一项
     *
     * @param dropBeans
     */
    public void setData(List<DropBean> dropBeans, View v_h) {
//        if(dropBeans == null || dropBeans.isEmpty()){
//            return;
//        }

        drops.addAll(dropBeans);
        drops.get(0).setChoiced(true);
        text.setText(drops.get(0).getName());
        selectPosition = 0;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_dropdown_content, null);
        view.findViewById(R.id.content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWinDownUtil.hide();
            }
        });
        ListView listView = view.findViewById(R.id.lvcontent);
        listView.setOnItemClickListener(this);

        adapter = new DropDownAdapter(drops, mContext);
        listView.setAdapter(adapter);

        popWinDownUtil = new PopWinDownUtil(mContext, view, v_h);
        popWinDownUtil.setOnDismissListener(this);
        setStyle(false);
    }

    public void setText(CharSequence content) {
        text.setText(content);
    }

    public void setStyle(boolean checked) {
        Drawable icon;
        if (checked) {
//            icon = getResources().getDrawable(R.mipmap.icon_jiantoujindu);

            text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            ivtabbutton.setImageResource(R.mipmap.icon_jiantoujindu);
            bLine.setVisibility(VISIBLE);
            popWinDownUtil.show();
        } else {
//            icon = getResources().getDrawable(R.mipmap.icon_dropdown);
            text.setTextColor(getResources().getColor(R.color.black));
            ivtabbutton.setImageResource(R.mipmap.ico_shaixuan);
            bLine.setVisibility(GONE);
            popWinDownUtil.hide();
        }
        //把箭头画到textView右边
//        text.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    @Override
    public boolean isChecked() {
        return isCheced;
    }

    /**
     * 根据传过来的参数改变状态
     *
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        isCheced = checked;
        setStyle(isCheced);
    }

    @Override
    public void toggle() {
        setChecked(!isCheced);
    }

    @Override
    public void onClick(View v) {
        setChecked(!isCheced);
    }

    @Override
    public void onDismiss() {
        setChecked(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        notifyData(position);
        if (onDropItemSelectListener != null) {
            onDropItemSelectListener.onDropItemSelect(position, drops.get(position).getTab());
        }
    }

    public void notifyData(int position) {
        popWinDownUtil.hide();
        if (selectPosition == position) {
            return;
        }
        drops.get(selectPosition).setChoiced(false);
        adapter.notifyDataSetChanged();
        drops.get(position).setChoiced(true);
        text.setText(drops.get(position).getName());
        adapter.notifyDataSetChanged();
        selectPosition = position;


    }

    public void setOnDropItemSelectListener(OnDropItemSelectListener onDropItemSelectListener) {
        this.onDropItemSelectListener = onDropItemSelectListener;
    }

    public interface OnDropItemSelectListener {
        void onDropItemSelect(int Postion, int tab);
    }


    class DropDownAdapter extends BaseAdapter {
        private List<DropBean> drops;
        private Context context;

        public DropDownAdapter(List<DropBean> drops, Context context) {
            this.drops = drops;
            this.context = context;
        }

        @Override
        public int getCount() {
            return drops.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.view_dropdown_item, parent, false);
                holder.tv = convertView.findViewById(R.id.tvname);
                holder.tig = convertView.findViewById(R.id.ivcheck);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(drops.get(position).getName());
            if (drops.get(position).isChoiced()) {
                holder.tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                holder.tig.setVisibility(VISIBLE);
            } else {
                holder.tv.setTextColor(getResources().getColor(R.color.color_text_333333));
                holder.tig.setVisibility(GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView tv;
            ImageView tig;
        }
    }
}