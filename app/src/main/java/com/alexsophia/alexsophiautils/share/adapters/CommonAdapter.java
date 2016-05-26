package com.alexsophia.alexsophiautils.share.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * ListView，GridView的万能适配器
 * @author liuweiping
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	protected int mItemLayoutId;

	public CommonAdapter(Context context, int itemLayoutId, List<T> datas) {
		this.mContext = context;
		this.mDatas = datas;
		this.mItemLayoutId = itemLayoutId;
	}
	/**
	 * 使用数据集刷新adapter
	 * @param datas
	 */
	public void reflushAdapter(List<T> datas) {
		if (datas != null && !datas.isEmpty()) {
			this.mDatas = datas;
		} else {
			if (this.mDatas != null)
				this.mDatas.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.get(convertView, mContext,
				mItemLayoutId, parent, position);
		covertView(viewHolder, getItem(position));
		return viewHolder.getConVertView();
	}
	/**
	 * 暴露给外面实现对item的view进行操作的接口
	 * @param viewholder
	 * @param t
	 */
	public abstract void covertView(ViewHolder viewholder, T t);
}
