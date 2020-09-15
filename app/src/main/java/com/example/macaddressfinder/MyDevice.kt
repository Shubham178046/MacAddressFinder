package com.example.macaddressfinder

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_my_device.*


class MyDevice : Fragment() {
    var bluetoothAdapter: BluetoothAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_device, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        device_name_tv_myDevice_Actual.setText(bluetoothAdapter!!.name)
        device_macAddress_tv_myDevice_Actual.setText(
            Settings.Secure.getString(
                context!!.getContentResolver(),
                "bluetooth_address"
            )
        )
        device_blStatus_tv_myDevice_Actual.setText(if (bluetoothAdapter!!.isEnabled) "Enabled" else "Disabled")
        device_BLE_tv_myDevice.setText(if (activity!!.packageManager.hasSystemFeature("android.hardware.bluetooth_le")) "Yes" else "No")
    }
}