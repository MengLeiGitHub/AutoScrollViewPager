package com.autoviewpager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.cicleprogrossbar.R;

 
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

//
public class AutoScrollViewPager extends ViewPager {
	/** ������µ����� **/
	PointF downP = new PointF();
	/** ��ǰ���µ����� **/
	PointF curP = new PointF();
	OnSingleTouchListener onSingleTouchListener;
	public MyPagerAdapter adapter;
	public ArrayList<View> listViews;
	private Activity acitvity;
	private boolean isTouch = false;
	AutoScrollViewPager viewpager;
	AutoScrollViewPagerStateChange stateChange;

	public AutoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		listViews = new ArrayList<View>();
		viewpager = this;
		adapter = new MyPagerAdapter();
		viewpager.setAdapter(adapter);
		initOntouch();
	}

	public AutoScrollViewPagerStateChange getStateChange() {
		return stateChange;
	}

	public void setStateChange(AutoScrollViewPagerStateChange stateChange) {
		this.stateChange = stateChange;
	}

	public void setAcitvity(Activity acitvity) {
		this.acitvity = acitvity;
	}

	public void setListinfo() {
		listViews = new ArrayList<View>();
		int image[] = { R.drawable.image1, R.drawable.image2,
				R.drawable.image3, R.drawable.image4 };

		if (acitvity == null)
			new Exception("activity  should not be null");

		for (int i = 0; i < image.length; i++) {
			ImageView img = new ImageView(acitvity);
			Drawable draw = acitvity.getResources().getDrawable(image[i]);
			img.setScaleType(ScaleType.FIT_XY);
			img.setImageDrawable(draw);
			listViews.add(img);
		}
		setChangeState();

	}

	private void setChangeState() {
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (stateChange != null)
					stateChange.drawCicle(listViews.size(),
							arg0 % listViews.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		adapter.notifyDataSetChanged();
		viewpager.setCurrentItem(0);
	}

	public void setListViews(ArrayList<View> listViews2) {
		this.listViews = listViews2;
		adapter.notifyDataSetChanged();
	}

	private void initOntouch() {
		// TODO Auto-generated method stub
		viewpager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg1, MotionEvent arg0) {
				// TODO Auto-generated method stub
				// ����ǰ���긳ֵ
				curP.x = arg0.getX();
				curP.y = arg0.getY();

				if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
					//
					// ����ǰ���¸�ֵ
					downP.x = arg0.getX();
					downP.y = arg0.getY();
					// ���� ��ȡ��ǰ�¼���֪ͨ���ؼ������¼��ڽ��зַ�
					getParent().requestDisallowInterceptTouchEvent(true);
					isTouch = true;
				}

				if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
					//
					getParent().requestDisallowInterceptTouchEvent(true);
					isTouch = true;
				}

				if (arg0.getAction() == MotionEvent.ACTION_UP) {
					//
					//
					if (downP.x == curP.x && downP.y == curP.y) {
						onSingleTouch(viewpager.getCurrentItem()
								% listViews.size());
					}
					isTouch = false;

				} else if (arg0.getAction() == MotionEvent.ACTION_CANCEL) {
					getParent().requestDisallowInterceptTouchEvent(false);

				}

				return false;
			}
		});
	}

	public AutoScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Override public boolean onInterceptTouchEvent(MotionEvent arg0) { //
	 * TODO Auto-generated method stub // ��ǰ�¼����ٽ��зַ�
	 * 
	 * return true; }
	 */

	/**
	 * �����¼�
	 */
	public void onSingleTouch(Object obj) {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch(obj);
		}
	}

	/**
	 * �����¼��ӿ�
	 */
	public interface OnSingleTouchListener {
		public void onSingleTouch(Object obj);
	}

	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}

	/**
	 * ��ʱ��
	 */
	private Timer time;
	boolean isScroll = false;

	public void startScroll() {
		if (isScroll)
			return;

		if (time == null)
			time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				isScroll = true;
				Runnable run = new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub

						if (isTouch) {

						} else {
							int current = viewpager.getCurrentItem();

							if (current < adapter.getCount() - 1) {
								viewpager.setCurrentItem(current + 1);
							} else {
								viewpager.setCurrentItem(0);
							}
						}
					}

				};

				viewpager.post(run);
			}

		}, 500, 2000);

	}

	public void stopScroll() {
		if (time != null) {
			time.cancel();
			time = null;
		}
	}

	// viewpager�� adapter
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			// return listViews.size();
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(listViews.get(position % listViews.size()));// ɾ���ϸ�ͼƬ
		}

		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(listViews.get(position % listViews.size()), 0);//
			return listViews.get(position % listViews.size());
		}
	}

}