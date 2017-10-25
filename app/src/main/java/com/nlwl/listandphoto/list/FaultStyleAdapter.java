package com.nlwl.listandphoto.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nlwl.listandphoto.R;

import java.util.ArrayList;

/**
 * 选择故障类型适配器
 * @author iceforg
 *         <p>
 *         Created by Administrator on 2016-12-19.
 */

public class FaultStyleAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> mData = new ArrayList<>();

	public FaultStyleAdapter(Context mContext, ArrayList<String> mData) {
		this.mContext = mContext;
		this.mData=mData;
	}

	/**
	 * 获取数据的大小
	 * <p>
	 * created at 2016-12-19 下午 9:03
	 */
	@Override
	public int getCount() {
		return mData.size();
	}

	/**
	 * 获取某一条数据
	 * <p>
	 * created at 2016-12-19 下午 9:04
	 */
	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	/**
	 * 返回位置
	 * <p>
	 * created at 2016-12-19 下午 9:04
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 视图
	 * <p>
	 * created at 2016-12-19 下午 9:04
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder=null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.fault_style_item, null);
			viewHolder=new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder= (ViewHolder) convertView.getTag();
		}
		viewHolder.tvFaultStyle.setText(mData.get(position));
		return convertView;
	}

	/**
	 * 添加数据
	 * <p>
	 * created at 2016-12-19 下午 9:06
	 */
	public void addAll(ArrayList<String> mData) {
		this.mData = mData;
		notifyDataSetChanged();
	}

	static class ViewHolder{
//		@Bind(R.id.tv_fault_style)
		TextView tvFaultStyle;
		ViewHolder(View view) {
			tvFaultStyle = (TextView) view.findViewById(R.id.tv_fault_style);
//			ButterKnife.bind(this, view);
		}
	}
}
