package com.alexsophia.alexsophiautils.share.views.zlistview.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alexsophia.alexsophiautils.share.views.zlistview.enums.Mode;
import com.alexsophia.alexsophiautils.share.views.zlistview.listener.OnSwipeLayoutListener;
import com.alexsophia.alexsophiautils.share.views.zlistview.listener.SimpleSwipeListener;
import com.alexsophia.alexsophiautils.share.views.zlistview.widget.ZSwipeItem;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 如果要使用ZSwipeItem，请继承这个Adapter
 * 
 * @date 2015-1-8 上午10:05:04
 * 
 */
public abstract class BaseSwipeAdapter extends BaseAdapter {

	public static final String TAG = "BaseSwipeAdapter";

	public final int INVALID_POSITION = -1;
	/**
	 * 显示模式，默认单开
	 */
	private Mode mode = Mode.Single;
	/**
	 * 当前打开的item的position
	 */
	protected int openPosition = INVALID_POSITION;
	/**
	 * 当前打开的所有item的position
	 */
	protected Set<Integer> openPositions = new HashSet<Integer>();
	/**
	 * 当前打开的所有ZSwipeItem对象
	 */
	protected Set<ZSwipeItem> mShownLayouts = new HashSet<ZSwipeItem>();

	public abstract int getSwipeLayoutResourceId(int position);

	public abstract View generateView(int position, ViewGroup parent);

	public abstract void fillValues(int position, View convertView);

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = generateView(position, parent);
			AutoUtils.auto(convertView);
			initialize(convertView, position);
		} else {
			updateConvertView(convertView, position);
		}
		fillValues(position, convertView);
		return convertView;

	}

	/**
	 * 复用item布局的时候调用
	 *
	 * @param target
	 * @param position
	 */
	public void updateConvertView(View target, int position) {

		int resId = getSwipeLayoutResourceId(position);
		ZSwipeItem swipeLayout = (ZSwipeItem) target.findViewById(resId);
		if (swipeLayout == null)
			throw new IllegalStateException(
					"can not find SwipeLayout in target view");

		ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
		valueBox.swipeMemory.setPosition(position);
		valueBox.onLayoutListener.setPosition(position);
		valueBox.position = position;
		Log.d(TAG, "updateConvertView=" + position);

	}

	/**
	 * 初始化item布局调用
	 *
	 * @param target
	 * @param position
	 */
	public void initialize(View target, int position) {

		int resId = getSwipeLayoutResourceId(position);

		OnLayoutListener onLayoutListener = new OnLayoutListener(position);
		ZSwipeItem swipeLayout = (ZSwipeItem) target.findViewById(resId);
		if (swipeLayout == null)
			throw new IllegalStateException(
					"can not find SwipeLayout in target view");

		SwipeMemory swipeMemory = new SwipeMemory(position);
		// 添加滑动监听器
		swipeLayout.addSwipeListener(swipeMemory);
		// 添加布局监听器
		swipeLayout.addOnLayoutListener(onLayoutListener);
		swipeLayout.setTag(resId, new ValueBox(position, swipeMemory,
				onLayoutListener));

		mShownLayouts.add(swipeLayout);
	}

	/**
	 * 关闭其他的item
	 * 
	 * @param layout
	 */
	private void closeAllExcept(ZSwipeItem layout) {
		for (ZSwipeItem s : mShownLayouts) {
			if (s != layout)
				s.close();
		}
	}

	/**
	 * 关闭所有条目
	 */
	public void closeAll() {
		for (ZSwipeItem s : mShownLayouts) {
			s.close();
		}

	}

	/**
	 * 是否有打开的条目
	 */
	public boolean isOpenItem() {
		if (getOpenItems().get(0) == INVALID_POSITION) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isOpenItem = false;

	public boolean getIsOpenItem() {
		if (isOpenItem) {
			return true;
		} else {
			if (getOpenItems().get(0) == INVALID_POSITION) {
				return false;
			} else {
				return true;
			}

		}
	}

	public void setIsOpenItem(boolean b) {
		this.isOpenItem = b;
	}

	/**
	 * 获取打开的所有的item的position信息
	 * 
	 * @return
	 */
	public List<Integer> getOpenItems() {

		if (mode == Mode.Multiple) {
			return new ArrayList<Integer>(openPositions);
		} else {
			return Arrays.asList(openPosition);
		}
	}

	/**
	 * position位置的item是否打开
	 * 
	 * @param position
	 * @return
	 */
	public boolean isOpen(int position) {
		if (mode == Mode.Multiple) {
			return openPositions.contains(position);
		} else {
			return openPosition == position;
		}
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		openPositions.clear();
		mShownLayouts.clear();
		openPosition = INVALID_POSITION;
	}

	class ValueBox {
		OnLayoutListener onLayoutListener;
		SwipeMemory swipeMemory;
		int position;

		ValueBox(int position, SwipeMemory swipeMemory,
				OnLayoutListener onLayoutListener) {
			this.swipeMemory = swipeMemory;
			this.onLayoutListener = onLayoutListener;
			this.position = position;
		}
	}

	class OnLayoutListener implements OnSwipeLayoutListener {

		private int position;

		OnLayoutListener(int position) {
			this.position = position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public void onLayout(ZSwipeItem v) {

			if (isOpen(position)) {
				v.open(false, false);
			} else {
				v.close(false, false);
			}
		}

	}

	class SwipeMemory extends SimpleSwipeListener {

		private int position;

		SwipeMemory(int position) {
			this.position = position;
		}

		@Override
		public void onClose(ZSwipeItem layout) {

			if (mode == Mode.Multiple) {
				openPositions.remove(position);
			} else {
				openPosition = INVALID_POSITION;
			}
		}

		@Override
		public void onStartOpen(ZSwipeItem layout) {

			if (mode == Mode.Single) {
				closeAllExcept(layout);
			}
		}

		@Override
		public void onOpen(ZSwipeItem layout) {

			if (mode == Mode.Multiple)
				openPositions.add(position);
			else {
				closeAllExcept(layout);
				openPosition = position;
			}
		}

		public void setPosition(int position) {
			this.position = position;
		}
	}

}
