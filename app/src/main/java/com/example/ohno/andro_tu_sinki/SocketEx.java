package com.example.ohno.andro_tu_sinki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class SocketEx extends Activity {
	private final static String BR = System.getProperty("line.separator");
	private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int MP = LinearLayout.LayoutParams.MATCH_PARENT;

	private final static String IP = "10.13.53.128";

	private TextView lblReceive;
	private EditText edtSend;
	private Button btnSend;

	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private boolean error;

	private final Handler handler = new Handler();


	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

//		setContentView(R.layout.socket_ex);
		LinearLayout layout = new LinearLayout(this);
//		layout.setBackgroundColor(Color.WHITE);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);

		edtSend = new EditText(this);
		edtSend.setText("");
		edtSend.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
		layout.addView(edtSend);


		btnSend = new Button(this);
		btnSend.setText("送信");
//		btnSend.setOnClickListener(this);
		btnSend.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
		layout.addView(btnSend);

		lblReceive = new TextView(this);
		lblReceive.setText("");
		lblReceive.setTextSize(16.0f);
//		lblReceive.setTextColor(Color.BLACK);
		lblReceive.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
		layout.addView(lblReceive);
	}


	@Override
	public void onStart() {
		super.onStart();


		Thread thread = new Thread() {
			public void run() {
				try {
					connect(IP, 8081);
				} catch (Exception e) {
				}
			}
		};
		thread.start();


		Thread threadReal = new Thread(new Runnable() {
			public void run() {
				error = false;
				try {


					if (socket != null && socket.isConnected()) {            /////kokokokokokokoko
						byte[] w = "あばばばばばばば".getBytes(StandardCharsets.UTF_8);				//edtSend.getText().toString().getBytes("UTF8");
						out.write(w);
						out.flush();
					}
				} catch (Exception e) {
					error = true;
				}


				handler.post(new Runnable() {
					public void run() {
						if (!error) {
							edtSend.setText("");

						} else {

							addText("送信失敗しました");

						}
					}
				});
			}
		});

		threadReal.start();
	}

}


	@Override
	public void onStop() {
		super.onStop();
		disconnect();
	}


	private void addText(final String text) {

	//�n���h���̐���
		handler.post(new Runnable(){

			public void run() {
				lblReceive.setText(text + BR +
						lblReceive.getText());
			}
		});
	}


	private void connect(String ip, int port) {	//kokokokokokokokokokokoko
		int size;
		String str;
		byte[] w = new byte[1024];
		try {
			addText("接続中");
			socket = new Socket(ip, port);
			in  = socket.getInputStream();
			out = socket.getOutputStream();
			addText("接続完了");

			while (socket != null && socket.isConnected()) {
				size = in.read(w);
				if (size <= 0) continue;
				str = new String(w, 0, size, "UTF-8");
				addText(str);
			}
		} catch (Exception e) {
			addText("通信失敗しました");
		}
	}


	private void disconnect() {
		try {
			socket.close();
			socket = null;
		} catch (Exception e) {
		}
	}


//	public void onClick(View view) {


//		Thread thread = new Thread(new Runnable() {public void run() {
//			error = false;
//			try {
//
//
//				if (socket != null && socket.isConnected()) {			/////kokokokokokokoko
//					byte[] w = edtSend.getText().toString().getBytes("UTF8");
//					out.write(w);
//					out.flush();
//					}
//			} catch (Exception e) {
//				error = true;
//			}
//
//
//			handler.post(new Runnable() {public void run() {
//				if (!error) {
//					edtSend.setText("");
//
//				} else {
//
//					addText("送信失敗しました");
//
//				}
//			}});
//		}});
//
//		thread.start();
//		switch (view.getId()){
//			case R.id.backToTheFuture:
//				Intent intent = new Intent(this, BT_Info_get.class);
//				startActivity(intent);
//				break;
//		}
//	}
	}