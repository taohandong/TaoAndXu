package com.share.scansctivity.config;

import java.io.UnsupportedEncodingException;

import com.share.scansctivity.BaseActivity;
import com.share.scansctivity.R;
import com.smartdevice.aidl.IZKCService;
import com.zkc.baseLibrary.MessageType;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ScanSetActivity extends BaseActivity implements OnClickListener{
	
	private CheckBox checkBox_hex;
	private EditText editText_command;
	private Button button_sendcommand,btnRecoveryFactory;
	
	private ScrollView scrollView_setting;
	//初始化控件
	private CheckBox checkbox_openScan,checkbox_keyBordInput,checkbox_addEnter,checkbox_openSound,checkbox_openVibration,checkbox_continueScan,checkbox_repeatScanTip,checkbox_reset;

	private boolean runFlag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp_twopage);
		initView();
		setCheckBoxEnable(false);
	}

	@Override
	protected void handleStateMessage(Message message) {
		super.handleStateMessage(message);
		switch (message.what){
			//服务绑定成功 service bind success
			case MessageType.BaiscMessage.SEVICE_BIND_SUCCESS:
				Toast.makeText(this, getString(R.string.service_bind_success), Toast.LENGTH_SHORT).show();
				mIzkcService = (IZKCService) message.obj;
				try {
					mIzkcService.setModuleFlag(4);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			//服务绑定失败 service bind fail
			case MessageType.BaiscMessage.SEVICE_BIND_FAIL:
				Toast.makeText(this, getString(R.string.service_bind_fail), Toast.LENGTH_SHORT).show();
				break;
		}
	}

	private void setCheckBoxEnable(boolean enable) {
		checkbox_openScan.setEnabled(enable);
		
	}

	Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				setCheckBoxEnable(true);
				break;
			default:
				break;
			}
			return false;
		}
	});

	private void initView() {
		
		scrollView_setting=(ScrollView)findViewById(R.id.scrollView_setting);
		
		checkBox_hex=(CheckBox)findViewById(R.id.checkBox_hex);
		
		editText_command=(EditText)findViewById(R.id.editText_command);
		
		button_sendcommand=(Button)findViewById(R.id.button_sendcommand);
		button_sendcommand.setOnClickListener(this);
		
		checkbox_openScan=(CheckBox)findViewById(R.id.checkbox_openScan);
		checkbox_openScan.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_openScan.setChecked(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
		
		checkbox_keyBordInput=(CheckBox)findViewById(R.id.checkbox_keyBordInput);
		checkbox_keyBordInput.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_keyBordInput.setChecked(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
		
		checkbox_addEnter=(CheckBox)findViewById(R.id.checkbox_addEnter);
		checkbox_addEnter.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_addEnter.setChecked(ClientConfig.getBoolean(ClientConfig.DATA_APPEND_ENTER));
		
		checkbox_openSound=(CheckBox)findViewById(R.id.checkbox_openSound);
		checkbox_openSound.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_openSound.setChecked(ClientConfig.getBoolean(ClientConfig.APPEND_RINGTONE));
		
		checkbox_openVibration=(CheckBox)findViewById(R.id.checkbox_openVibration);
		checkbox_openVibration.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_openVibration.setChecked(ClientConfig.getBoolean(ClientConfig.APPEND_VIBRATE));
		
		checkbox_continueScan=(CheckBox)findViewById(R.id.checkbox_continueScan);
		checkbox_continueScan.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_continueScan.setChecked(ClientConfig.getBoolean(ClientConfig.CONTINUE_SCAN));
		
		checkbox_repeatScanTip=(CheckBox)findViewById(R.id.checkbox_repeatScanTip);
		checkbox_repeatScanTip.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_repeatScanTip.setChecked(ClientConfig.getBoolean(ClientConfig.SCAN_REPEAT));
		btnRecoveryFactory = (Button) findViewById(R.id.btnRecoveryFactory);
		btnRecoveryFactory.setOnClickListener(this);
		
	}

	private void sendCommand() {
		try{
            String str=editText_command.getText().toString();
            if(checkBox_hex.isChecked()){
                if(mIzkcService!=null){
                    mIzkcService.sendCommand(StringToByteArray(str));
                }
            }else{
                try {
                    if(mIzkcService!=null){
                        mIzkcService.sendCommand(str.getBytes("US-ASCII"));
                    }
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            }catch (Exception e) {
                Log.e("ScanSetActivity", e.getMessage());
                Toast.makeText(ScanSetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
	}

	static public byte[] StringToByteArray(String strInput)
	{
		int l = strInput.length() / 2;  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            ret[i] = (byte) Integer  
                    .valueOf(strInput.substring(i * 2, i * 2 + 2), 16).byteValue();  
        }  
        return ret;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btnRecoveryFactory:
				try {
					mIzkcService.recoveryFactorySet(true);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case R.id.button_sendcommand:
				sendCommand();
				break;
		}
	}

	class checkBoxCheckedChangeListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			try {
				switch (buttonView.getId()) {
				case R.id.checkbox_openScan:
					if(isChecked){
						scrollView_setting.setVisibility(View.VISIBLE);
					}else{
						scrollView_setting.setVisibility(View.GONE);
					}
					mIzkcService.openScan(isChecked);
					ClientConfig.setValue(ClientConfig.OPEN_SCAN, isChecked);
					break;
				case R.id.checkbox_keyBordInput:
					
					break;
				case R.id.checkbox_addEnter:
					mIzkcService.dataAppendEnter(isChecked);
					ClientConfig.setValue(ClientConfig.DATA_APPEND_ENTER, isChecked);
					break;
				case R.id.checkbox_openSound:
					mIzkcService.appendRingTone(isChecked);
					ClientConfig.setValue(ClientConfig.APPEND_RINGTONE, isChecked);
					break;
				case R.id.checkbox_openVibration:
					ClientConfig.setValue(ClientConfig.APPEND_VIBRATE, isChecked);
					break;
				case R.id.checkbox_continueScan:
					mIzkcService.continueScan(isChecked);
					ClientConfig.setValue(ClientConfig.CONTINUE_SCAN, isChecked);
					break;
				case R.id.checkbox_repeatScanTip:
					mIzkcService.scanRepeatHint(isChecked);
					ClientConfig.setValue(ClientConfig.SCAN_REPEAT, isChecked);
					break;

				default:
					break;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
