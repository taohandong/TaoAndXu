package com.share.scansctivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.share.scansctivity.config.ClientConfig;
import com.share.scansctivity.config.ScanSetActivity;
import com.share.scansctivity.utils.FileUtil;
import com.smartdevice.aidl.ICallBack;
import com.smartdevice.aidl.IZKCService;
import com.zkc.baseLibrary.MessageType;

public class ScannerActivity extends BaseActivity implements OnClickListener {

	private EditText et_code;
	private Button btn_scan, btn_clear, btn_set;
	public String text = "";
	MediaPlayer player;
	Vibrator vibrator;
	private String firstCodeStr = "";
	TextView tv_send,tv_receiver;
	int send = 0,receiver=0;

	ICallBack.Stub mCallback = new ICallBack.Stub() {

		@Override
		public void onReturnValue(byte[] buffer, int size)
				throws RemoteException {
			String codeStr = new String(buffer, 0, size);
			if(ClientConfig.getBoolean(ClientConfig.SCAN_REPEAT)){
				if(firstCodeStr.equals(codeStr)){
					vibrator.vibrate(100);
				}
			}
			if(ClientConfig.getBoolean(ClientConfig.APPEND_RINGTONE)){
				player.start();
			}
			if(ClientConfig.getBoolean(ClientConfig.APPEND_VIBRATE)){					
				vibrator.vibrate(100);
			}
			firstCodeStr = codeStr;
			text += codeStr+"\n";
			sendMessage(MessageType.BaiscMessage.SCAN_RESULT_GET_SUCCESS, text);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner_two);
		initView();
		initEvent();
		btn_scan.setEnabled(false);
		player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void registerCallbackAndInitScan() {
		try {
			//设置扫描模块 set up scan module
			mIzkcService.setModuleFlag(4);
			//注册扫描数据回调接口
            mIzkcService.registerCallBack("Scanner", mCallback);
			//打开扫描 open scan
			mIzkcService.openScan(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
			btn_scan.setEnabled(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		switch (message.what){
			//服务绑定成功 service bind success
			case MessageType.BaiscMessage.SEVICE_BIND_SUCCESS:
				Toast.makeText(this, getString(R.string.service_bind_success), Toast.LENGTH_SHORT).show();
				mIzkcService = (IZKCService) message.obj;
				registerCallbackAndInitScan();
				break;
			//服务绑定失败 service bind fail
			case MessageType.BaiscMessage.SEVICE_BIND_FAIL:
				Toast.makeText(this, getString(R.string.service_bind_fail), Toast.LENGTH_SHORT).show();
				break;
			case MessageType.BaiscMessage.SCAN_RESULT_GET_SUCCESS:
				et_code.setText((String) message.obj);
				tv_receiver.setText("R:"+ et_code.getText().length());
				break;
		}
	}

	private void initEvent() {
		btn_scan.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_set.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		et_code = (EditText) findViewById(R.id.et_code);
		btn_scan = (Button) findViewById(R.id.btn_scan);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_set = (Button) findViewById(R.id.btn_set);
		tv_receiver = (TextView) findViewById(R.id.tv_receiver);
		tv_send = (TextView) findViewById(R.id.tv_send);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_scan:
			try {
				mIzkcService.scan();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.btn_clear:
			et_code.setText("");
			text = "";
			tv_receiver.setText("R:"+ "  ");
			tv_send.setText("S:"+ "  ");
			send = 0;
			receiver = 0;
			break;
		case R.id.btn_set:
			try {
				intent = new Intent(this, ScanSetActivity.class);
			}catch (Exception e){
			}
			break;

		default:
			break;
		}
		if (intent != null)
			startActivity(intent);

	}

	@Override
	protected void onDestroy() {
		try {
			mIzkcService.unregisterCallBack("Scanner", mCallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}
}
