package vis.study.datagramsocket4android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private final String TAG = Datagram4Socket.class.getSimpleName();
	private final Datagram4Socket.Listener mListener = new Datagram4Socket.Listener() {

		@Override
		public void onRunError(Exception e) {
			Log.d(TAG, "Runner stopped.");
		}

		@Override
		public void onNewMsg(final String msg) {
			// TODO 自动生成的方法存根
			MainActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// PlaceholderFragment.this.updateReceivedMsg(msg);
					Toast.makeText(getApplicationContext(), msg,
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onNewData(final String data) {
			// TODO 自动生成的方法存根
			MainActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// pf.addMsg(data);
					// MainActivity.this.updateReceivedData(data);
					tvMsgbox.append(data + "\n");
					mScrollView.smoothScrollTo(0, tvMsgbox.getBottom());
				}
			});
		}
	};
	private Datagram4Socket ds;
	private TextView tvIP;
	private Button btnSend;
	private EditText etSend;
	private EditText etTargetIP;
	private EditText etPort;
	private TextView tvMsgbox;
	private Button btnAction;
	private ScrollView mScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment()).commit();
		// }
		tvIP = (TextView) findViewById(R.id.tvIP);
		tvIP.setText(NetServer.getLocalIpAddress(this));
		etTargetIP = (EditText) findViewById(R.id.etTargetIP);
		etPort = (EditText) findViewById(R.id.etPort);
		btnAction = (Button) findViewById(R.id.btnAction);
		btnAction.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ds.setTarget(etTargetIP.getText().toString(),
						Integer.parseInt(etPort.getText().toString()));
				// btnSend.setEnabled(true);
			}
		});
		tvMsgbox = (TextView) findViewById(R.id.tvMsgbox);
		mScrollView = (ScrollView) findViewById(R.id.scrollView);
		etSend = (EditText) findViewById(R.id.etSend);
		btnSend = (Button) findViewById(R.id.btnSend);
		// btnSend.setEnabled(false);
		btnSend.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ds.send(etSend.getText().toString());
			}
		});

		ds = new Datagram4Socket(mListener);
		ds.beginReceive(5550);
	}

	@Override
	protected void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();

		ds.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
