package com.example.ohno.andro_tu_sinki;

/**
 * Created by OHNO on 2017/10/18.
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import java.io.IOException;

public class BT_Info_get extends Activity {

    SocketEx Sox = new SocketEx();

    private BluetoothAdapter mBtAdapter;
    private TextView mScanResult;
    private String mResult = "";
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                mResult += "Name : " + device.getName() + "\n";
                mResult += "MAC Address : " + device.getAddress() + "\n";
                mScanResult.setText(mResult);
            }

            if("C4:4D:25:65:CA:A1".equals(device.getAddress())){
                mResult += "やったぜ。"+"\n";
                mScanResult.setText(mResult);

                    Intent intentActivity = new Intent(BT_Info_get.this, SocketEx.class);
                    //intent.putExtra("foo", someData);
                    startActivity(intentActivity);

            }
        }
    };


/** Called when the activity is first created. */
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mScanResult = (TextView)findViewById(R.id.bt_text);
        // インテントフィルタの作成
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // ブロードキャストレシーバの登録
        registerReceiver(mReceiver, filter);

        // BluetoothAdapterのインスタンス取得
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // Bluetooth有効
        if (!mBtAdapter.isEnabled()) {
            mBtAdapter.enable();
        }
        // 周辺デバイスの検索開始
        mBtAdapter.startDiscovery();
    }

//    public void Onclick(View view){
//        switch (view.getId()){
//            case R.id.metamorphose:
//                Intent intent = new Intent(this, SocketEx.class);
//                startActivity(intent);
//                break;
//        }
//    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 検索中止
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        unregisterReceiver(mReceiver);
    }
}