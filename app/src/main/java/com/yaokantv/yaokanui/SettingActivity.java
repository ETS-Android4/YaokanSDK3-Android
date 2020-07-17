package com.yaokantv.yaokanui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.yaokantv.sdkdemo.R;
import com.yaokantv.yaokansdk.manager.Yaokan;
import com.yaokantv.yaokansdk.model.YkMessage;
import com.yaokantv.yaokansdk.model.e.MsgType;
import com.yaokantv.yaokansdk.utils.DlgUtils;
import com.yaokantv.yaokanui.bean.UiRc;
import com.yaokantv.yaokanui.dlg.RenameDialog;
import com.yaokantv.yaokanui.utils.YKAppManager;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    View delete, rename;
    UiRc uiRc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiRc = getIntent().getExtras().getParcelable(Config.S_GID);
        setContentView(R.layout.activity_setting);
        setMTitle(R.string.setting, TITLE_LOCATION_CENTER);
    }

    @Override
    protected void initView() {
        delete = findViewById(R.id.rl_delete);
        delete.setOnClickListener(this);
        rename = findViewById(R.id.rl_update);
        rename.setOnClickListener(this);
        findViewById(R.id.rl_study).setOnClickListener(this);
        if (uiRc != null && uiRc.getBe_rc_type() == 7) {
            findViewById(R.id.rl_study).setVisibility(View.GONE);
        }
    }

    @Override
    public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {

    }

    @Override
    protected void reload() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_delete) {
            final String id = uiRc.getUuid();
            if (!TextUtils.isEmpty(id)) {
                DlgUtils.createDefDlg(activity, "", getString(R.string.is_delete_rc), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Yaokan.instance().deleteRcByUUID(id);
                        YKAppManager.getAppManager().finishActivity(RcActivity.class);
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        } else if (v.getId() == R.id.rl_update) {
            RenameDialog renameDialog = new RenameDialog(activity, uiRc);
            renameDialog.show();
        } else if (v.getId() == R.id.rl_study) {
            Intent intent = new Intent(this, RcActivity.class);
            setResult(100, intent);
            finish();
        }
    }
}
