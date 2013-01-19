package de.entropia.rimshot;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RimshotActivity extends Activity {
	@Override
	protected void onStart() {
		super.onStart();

		DoEffect runner = new DoEffect();
		runner.execute();

		finish();
	}

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private class DoEffect extends AsyncTask<Void, Void, Void> {
		private Exception exception;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				DatagramSocket ds = new DatagramSocket();

				byte[] cmd = "rimshot\n".getBytes();
				DatagramPacket packet = new DatagramPacket(cmd, cmd.length,
						InetAddress.getByName("192.168.23.42"), 23421);

				ds.send(packet);
			} catch(IOException e) {
				exception = e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if(exception != null) {
				Toast t = Toast.makeText(getApplicationContext(),
						"Error: " + exception, Toast.LENGTH_SHORT);

				t.show();
			}
		}
	}
}
