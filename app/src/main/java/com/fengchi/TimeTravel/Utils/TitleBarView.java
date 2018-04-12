package com.fengchi.TimeTravel.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengchi.TimeTravel.R;


/**
 * 自定义的TitleBar控件
 * 
 * @author baron 提供监听器接口，调用者可以实现监听器接口包含左右两个按钮的点击事件方法 然后绑定监听器
 */
public class TitleBarView extends LinearLayout {
	// 右侧按钮属性
	Button btnRight;
	Drawable rightDrawable;
	String rightTxt;
	boolean rightable;
	//右侧图片显示
	boolean rightDrwablehow;
	boolean rightDrawablerefresh;
	// 左侧按钮属性
	Button btnLeft;
	Drawable leftDrawable;
	String leftTxt;
	boolean leftable;
	// 标题
	TextView tvTitle;
	ImageView iv_search;
	ImageView iv_refresh;
	String titleTxt;
	BtnClickListener listener = null;

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getStyle(context, attrs);
		init(context);
	}
	public TitleBarView(Context context) {
		super(context);
		init(context);
	}
	@SuppressLint("Recycle")
	public void getStyle(Context context, AttributeSet attrs) {
		// 获取在attrs中设置的属性组
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.TitleBarView);
		// 右侧按钮属性
		rightTxt = array.getString(R.styleable.TitleBarView_rightText);
		rightDrawable = array
				.getDrawable(R.styleable.TitleBarView_rightDrawable);
		rightable = array.getBoolean(R.styleable.TitleBarView_rightable, true);
		rightDrwablehow =array.getBoolean(R.styleable.TitleBarView_rightDrawableShow, false);
		rightDrawablerefresh =array.getBoolean(R.styleable.TitleBarView_rightDrawableRefreshShow,false);
		// 左侧按钮属性
		leftTxt = array.getString(R.styleable.TitleBarView_leftText);
		leftDrawable = array.getDrawable(R.styleable.TitleBarView_leftDrawable);
		leftable = array.getBoolean(R.styleable.TitleBarView_leftable, true);
		// 标题
		titleTxt = array.getString(R.styleable.TitleBarView_titleText);
	}
	/**
	 * 初始化组件
	 * 
	 * @param context
	 */
	private void init(Context context) {
		final Context mycontext = context;
		LayoutInflater.from(context).inflate(R.layout.titlebar, this);
		btnLeft = (Button) findViewById(R.id.btn_left);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
		btnRight = (Button) findViewById(R.id.btn_right);
		tvTitle = (TextView) findViewById(R.id.title);
		tvTitle.setText(titleTxt);
		if (leftTxt != null) {
			btnLeft.setText(leftTxt);
		}
		btnRight.setText(rightTxt);
		if (leftable == false) {
			btnLeft.setVisibility(View.INVISIBLE);
		}
		if (rightable == false) {
			btnRight.setVisibility(View.INVISIBLE);
		}
		if(rightDrwablehow==true){
			btnRight.setVisibility(View.GONE);
			iv_search.setVisibility(View.VISIBLE);
		}else{
			iv_search.setVisibility(View.GONE);
		}
		if(rightDrawablerefresh==true){
			btnRight.setVisibility(View.GONE);
			iv_refresh.setVisibility(VISIBLE);
			iv_search.setVisibility(View.GONE);
		}else {
			iv_refresh.setVisibility(GONE);
		}
		btnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.leftClick();
				} else {
					((Activity) mycontext).finish();
				}
			}
		});
		btnRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.rightClick();
				}
			}
		});
		iv_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.searchClick();
				}
			}
		});

	}

	/**
	 * 提供加载自定义监听器方法
	 * 
	 * @param listener
	 */
	public void setTitleBarListener(BtnClickListener listener) {
		this.listener = listener;
	}

	/**
	 * 按钮点击接口,调用者实现
	 */
	public interface BtnClickListener {
		void leftClick();
		void rightClick();
		void searchClick();
	}

	/**
	 * 设置Titlebar内容（Java代码中设置）
	 * 
	 * @param leftDrabable
	 *            左侧背景
	 * @param leftTxt
	 *            左侧文字
	 * @param titleTxt
	 *            中间标题
	 * @param rightDrawable
	 *            右侧文字
	 */
	public void setTitleBarView(int leftDrabable, String leftTxt,
			String titleTxt, int rightDrawable, String rightTxt) {
		tvTitle.setText(titleTxt);
		btnLeft.setText(leftTxt);
	}
	
	/**
	 * 设置Titlebar内容（Java代码中设置）
	 * 
	 */
	public void setTitle( String txt) {
		titleTxt=txt;
		tvTitle.setText(titleTxt);
	}

	public void setrightTitle( String txt) {
		titleTxt=txt;
		btnRight.setText(titleTxt);
	}
	public void setRightDrawable(Drawable drawable) {

	}
}
