package com.fengchi.TimeTravel.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengchi.TimeTravel.R;


public class UpdateDialog extends Dialog implements View.OnClickListener{
    private TextView contentTxt;
    private Button bt_update;
    private TextView cancelTxt;
    private Context mContext;
    private String content;
    private String content1;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private ImageView deleteDialog;

    public UpdateDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    public UpdateDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public UpdateDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public UpdateDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected UpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public UpdateDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public UpdateDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public UpdateDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        contentTxt = (TextView)findViewById(R.id.update_content);
        bt_update = (Button)findViewById(R.id.bt_update);
        deleteDialog =(ImageView)findViewById(R.id.deleteDialog);
        bt_update.setOnClickListener(this);
        deleteDialog.setOnClickListener(this);
        contentTxt.setText(content);
        setCanceledOnTouchOutside(true);
//        cancelTxt = (TextView)findViewById(R.id.cancel);
//        cancelTxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_update:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
            case R.id.deleteDialog:
                if(listener != null){
                    listener.onHoldClick(this);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
        void onHoldClick(Dialog dialog);
    }
}
