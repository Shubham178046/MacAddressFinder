package com.example.macaddressfinder

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_discover.*


class Discover : Fragment() {
    var bAdapter: BluetoothAdapter? = null
    var intentFilter: IntentFilter? = null
    var bluetoothDevice: ArrayList<BluetoothDevice> = ArrayList()
    var f3600ab = false
    var adapterBluetooth: AdapterBluetooth? = null

    val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {
            val action: String? = intent.getAction()
            if ("android.bluetooth.adapter.action.DISCOVERY_STARTED".equals(action)) {
                Log.d("Fragment1Discovery", "onReceive: ACTION_DISCOVERY_STARTED");
                discover_btn.setText("Stop Search");
                f3600ab = true;
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                Log.d("Fragment1Discovery", "onReceive: ACTION_DISCOVERY_FINISHED");
                discover_btn.setText("Start Search");
                f3600ab = false;
                try {
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                var bluetoothDevices: BluetoothDevice =
                    intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")!!
                Log.d("Fragment1Discovery", "onReceive: "+"Found")
                if (!bluetoothDevice.contains(bluetoothDevices)) {
                    Log.d(
                        "Fragment1Discovery",
                        "onReceive: Found Device: " + bluetoothDevices.getName() + "  " + bluetoothDevices.getAddress()
                    );
                    bluetoothDevice.add(bluetoothDevices);
                    adapterBluetooth =
                        AdapterBluetooth(context!!, R.layout.listview_item, bluetoothDevice)
                    discovered_devices_listview.adapter =
                        adapterBluetooth
                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_discover, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        discover_btn.setOnClickListener {
            if (!(f3600ab)) {
                stopDiscovery()
                startDiscovery()
            }
            stopDiscovery()
        }
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        startDiscovery()
        registerReceiver()
        updateData()
    }

    fun updateData() {
        if (adapterBluetooth != null) {
            adapterBluetooth!!.clear()
            adapterBluetooth!!.notifyDataSetChanged()
        }
        adapterBluetooth = AdapterBluetooth(context!!, R.layout.listview_item, bluetoothDevice)
        discovered_devices_listview.adapter =
            adapterBluetooth
    }

    fun registerReceiver() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.bluetooth.device.action.FOUND");
        intentFilter!!.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter!!.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        context!!.registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        stopDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(receiver)
    }

    private fun startDiscovery() {
        bAdapter!!.startDiscovery()
    }

    private fun stopDiscovery() {
        bAdapter!!.cancelDiscovery()
        discover_btn.setText("Start Search")
        f3600ab = false
    }
}