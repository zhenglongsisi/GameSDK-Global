package com.xinkuai.globalsdk.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.xinkuai.globalsdk.OnRewardListener;
import com.xinkuai.globalsdk.PurchaseParams;
import com.xinkuai.globalsdk.RoleInfo;
import com.xinkuai.globalsdk.UserToken;
import com.xinkuai.globalsdk.XKGlobalSDK;
import com.xinkuai.globalsdk.XKSDKEventReceiver;
import com.xinkuai.globalsdk.internal.share.ShareCallback;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XKGlobalSDK.registerSDKEventReceiver(new XKSDKEventReceiver() {
            @Override
            public void onLoginSucceed(@NonNull UserToken userToken) {
                Log.d(TAG, "onLoginSucceed: " + userToken.toString());
            }

            @Override
            public void onLoginFailed() {
                Log.d(TAG, "onLoginFailed: 登录失败");
            }

            @Override
            public void onLogout() {
                Log.d(TAG, "onLogout: 退出登录");
            }

            @Override
            public void onPurchaseFailed(String debugMessage) {
                Log.d(TAG, "onPurchaseFailed: " + debugMessage);
            }

            @Override
            public void onPurchaseSucceed() {
                Log.d(TAG, "onPurchaseSucceed: 购买成功");
            }

            @Override
            public void onExitGame() {
                Log.d(TAG, "onExitGame: 退出游戏");
                finish();
                System.exit(0);
            }

        });

        XKGlobalSDK.onMainActivityCreated(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        XKGlobalSDK.onMainActivityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        XKGlobalSDK.onMainActivityPaused();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XKGlobalSDK.onMainActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //调用XKGlobalSDK处理相关权限申请结果
        XKGlobalSDK.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        XKGlobalSDK.handleExitGame();
    }

    public void startLogin(View view) {
        if (XKGlobalSDK.isLogged()) {
            Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show();
            return;
        }
        XKGlobalSDK.launchLogin();
    }

    public void logout(View view) {
        if (XKGlobalSDK.isLogged()) {
            XKGlobalSDK.logout();
        } else {
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
        }
    }

    public void pay(View view) {
        if (!XKGlobalSDK.isLogged()) {
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText edit = findViewById(R.id.product_id_edit);
        String productId = edit.getText().toString().trim();
        if (TextUtils.isEmpty(productId)) {
            Toast.makeText(this, "请输入商品ID！", Toast.LENGTH_SHORT).show();
            return;
        }

        PurchaseParams purchaseParams = new PurchaseParams.Build(productId)
                .setOrderId(String.valueOf(System.currentTimeMillis()))
                .setProductName("钻石礼包")
                .setServerName("电信一区")
                .setPayCallbackUrl("")
                .setPayload("")
                .build();

        XKGlobalSDK.launchPurchase(purchaseParams);
    }


    public void reportRole(View view) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setBehavior(2);
        roleInfo.setCpUid("1001");
        roleInfo.setRoleId("1002");
        roleInfo.setRoleName("*无敌大魔王");
        roleInfo.setRoleLevel(30);
        roleInfo.setServerId("1001");
        roleInfo.setServerName("艾欧 里亚");
        roleInfo.setCoinNum(0);

        XKGlobalSDK.reportRoleInfo(roleInfo);
    }

    public void crashTest(View view) {
        throw new IllegalStateException("崩溃日志测试");
    }

    public void shareToFacebook(View view) {
        XKGlobalSDK.shareToFacebook(new ShareCallback() {
            @Override
            public void onShareSuccess() {
                Log.d(TAG, "onShareSuccess: 分享成功");
            }

            @Override
            public void onShareFailed() {
                Log.d(TAG, "onShareFailed: 分享失败");
            }
        });
    }

    public void reportTutorialCompleted(View view) {
        XKGlobalSDK.reportTutorialCompleted();
    }

    public void showRewardVideoAd(View view) {
        if (!XKGlobalSDK.isRewardVideoAdReady()) {
            Toast.makeText(this, "广告未加载", Toast.LENGTH_SHORT).show();
            return;
        }

        XKGlobalSDK.showRewardVideoAd(new OnRewardListener() {
            @Override
            public void onReward() {
                Log.d(TAG, "onReward: 发放奖励");
            }
        });

    }


}