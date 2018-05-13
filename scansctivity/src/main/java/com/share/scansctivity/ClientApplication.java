package com.share.scansctivity;

import android.app.Application;

import com.share.scansctivity.common.ClientGlobal;
import com.share.scansctivity.config.ClientConfig;
import com.share.scansctivity.utils.FileUtil;

/**
 * Created by ToaHanDong on 2018/2/24.
 */

public class ClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ClientConfig.init(getApplicationContext());
        initDefaultValue();
        initSDcard();
    }

    private void initSDcard() {
        FileUtil.createDirIfNotExist(ClientGlobal.Path.ClientDir);
    }


    private void initDefaultValue() {
        if(!ClientConfig.hasValue(ClientConfig.OPEN_SCAN)){
            ClientConfig.setValue(ClientConfig.OPEN_SCAN, true);
        }

        if(!ClientConfig.hasValue(ClientConfig.DATA_APPEND_ENTER)){
            ClientConfig.setValue(ClientConfig.DATA_APPEND_ENTER, true);
        }

        if(!ClientConfig.hasValue(ClientConfig.APPEND_RINGTONE)){
            ClientConfig.setValue(ClientConfig.APPEND_RINGTONE, true);
        }

        if(!ClientConfig.hasValue(ClientConfig.APPEND_VIBRATE)){
            ClientConfig.setValue(ClientConfig.APPEND_VIBRATE, true);
        }

        if(!ClientConfig.hasValue(ClientConfig.CONTINUE_SCAN)){
            ClientConfig.setValue(ClientConfig.CONTINUE_SCAN, false);
        }

        if(!ClientConfig.hasValue(ClientConfig.SCAN_REPEAT)){
            ClientConfig.setValue(ClientConfig.SCAN_REPEAT, false);
        }

        if(!ClientConfig.hasValue(ClientConfig.SCAN_REPEAT)){
            ClientConfig.setValue(ClientConfig.SCAN_REPEAT, false);
        }

    }
}
