package com.swkj.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * @PackageNmae: com.bs.blaufuchslive.receiver
 * @Author: kele
 * @createTime: 2019/11/30 10:03
 * @Description: 监听网络变化
 */
public class NetworkReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean internet = isConnectingToInternet(context);
        if (internet) {

        }else {
            Toast.makeText(context,"当前无网络，请检查你的网络",Toast.LENGTH_LONG).show();
        }
    }
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //新版本调用方法获取网络状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[]   networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo != null && networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            //否则调用旧版本方法
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
