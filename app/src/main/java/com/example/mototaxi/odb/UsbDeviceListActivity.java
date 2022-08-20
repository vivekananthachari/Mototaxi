/*
 * (C) Copyright 2015 by fr3ts0n <erwin.scheuch-heilig@gmx.at>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307 USA
 *
 */

package com.example.mototaxi.odb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.example.mototaxi.R;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Shows a {@link ListView} of available USB devices.
 *
 * @author mike wakerly (opensource@hoho.com)
 */
public final class UsbDeviceListActivity extends Activity
{
	private static final String TAG = UsbDeviceListActivity.class.getSimpleName();
	private static final Logger log = Logger.getLogger(TAG);
	
	/** selected USB port */
	public static UsbSerialPort selectedPort = null;

	private UsbManager mUsbManager;
	private static final int MESSAGE_REFRESH = 101;
	private static final long REFRESH_TIMEOUT_MILLIS = 5000;

	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case MESSAGE_REFRESH:
					refreshDeviceList();
					mHandler.sendEmptyMessageDelayed(MESSAGE_REFRESH, REFRESH_TIMEOUT_MILLIS);
					break;
				default:
					super.handleMessage(msg);
					break;
			}
		}

	};

	private final List<UsbSerialPort> mEntries = new ArrayList<>();
	private ArrayAdapter<UsbSerialPort> mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usb_list);

		mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		ListView mListView = findViewById(R.id.deviceList);
		mAdapter = new ArrayAdapter<UsbSerialPort>(this,
		                                           android.R.layout.simple_expandable_list_item_2,
		                                           mEntries)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				final TwoLineListItem row;
				if (convertView == null)
				{
					final LayoutInflater inflater =
						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = (TwoLineListItem) (inflater != null ? inflater.inflate(
						android.R.layout.simple_list_item_2, null) : null);
				} else
				{
					row = (TwoLineListItem) convertView;
				}

				if (row != null)
				{
					final UsbSerialPort port = mEntries.get(position);
					final UsbSerialDriver driver = port.getDriver();
					final UsbDevice device = driver.getDevice();
	
					final String title = String.format("USB: 0x%04x/0x%04x",
					                                   device.getVendorId(),
					                                   device.getProductId());
					final String subtitle = driver.getClass().getSimpleName();

					row.getText1().setText(title);
					row.getText2().setText(subtitle);
				}
				
				//noinspection ConstantConditions
				return row;
			}

		};
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new ListView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				log.fine("Pressed item " + position);
				if (position >= mEntries.size())
				{
					log.warning("Illegal position.");
					return;
				}

				selectedPort = mEntries.get(position);

				// Create the result Intent and include the MAC address
				Intent intent = new Intent();
				// Set result and finish this Activity
				setResult(Activity.RESULT_OK, intent);
				log.fine("Sending Result...");
				finish();
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mHandler.sendEmptyMessage(MESSAGE_REFRESH);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mHandler.removeMessages(MESSAGE_REFRESH);
	}

	@SuppressLint("StaticFieldLeak")
	private void refreshDeviceList()
	{
		new AsyncTask<Void, Void, List<UsbSerialPort>>()
		{
			@Override
			protected List<UsbSerialPort> doInBackground(Void... params)
			{
				log.fine("Refreshing device list ...");
				final List<UsbSerialDriver> drivers =
					UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
				final List<UsbSerialPort> result = new ArrayList<>();

				for (final UsbSerialDriver driver : drivers)
				{
					final List<UsbSerialPort> ports = driver.getPorts();
					log.fine(String.format("+ %s: %s selectedPort%s",
					                         driver, ports.size(),
					                         ports.size() == 1 ? "" : "s"));
					result.addAll(ports);
				}

				return result;
			}

			@SuppressLint("StringFormatInvalid")
			@Override
			protected void onPostExecute(List<UsbSerialPort> result)
			{
				mEntries.clear();
				mEntries.addAll(result);
				TextView numFound = findViewById(R.id.num_found);
				numFound.setText(getString(R.string.devices_found, result.size()));
				mAdapter.notifyDataSetChanged();
				log.fine("Done refreshing, " + mEntries.size() + " entries found.");
			}

		}.execute();
	}
}
