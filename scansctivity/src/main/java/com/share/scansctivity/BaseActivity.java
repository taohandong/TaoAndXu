package com.share.scansctivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.inputmethod.InputMethodManager;

import com.share.scansctivity.common.MessageCenter;
import com.smartdevice.aidl.IZKCService;
import com.zkc.baseLibrary.ZkcManager;

public class BaseActivity extends Activity {
	public static int DEVICE_MODEL = 0;
	private Handler mhanlder;
	public IZKCService mIzkcService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MessageCenter.getInstance().addHandler(getHandler());
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		//创建接口管理者对象 create the object of interface manager
		ZkcManager.getInstance().onCreate(this, mhanlder);
	}

	protected void handleStateMessage(Message message) {}

	/** handler */
	protected Handler getHandler() {
		if (mhanlder == null) {
			mhanlder = new Handler() {
				public void handleMessage(Message msg) {
					handleStateMessage(msg);
				}
			};
		}
		return mhanlder;
	}

	protected void sendMessage(Message message) {
		getHandler().sendMessage(message);
	}

	protected void sendMessage(int what, Object obj) {
		Message message = new Message();
		message.what = what;
		message.obj = obj;
		getHandler().sendMessage(message);
	}

	protected void sendEmptyMessage(int what) {
		getHandler().sendEmptyMessage(what);
	}

	@Override
	protected void onDestroy() {
		//销毁接口管理者对象 destroy the object of interface manager
		ZkcManager.getInstance().onDestroy();
		super.onDestroy();
	}
}
