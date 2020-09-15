package com.example.macaddressfinder

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_pared_devices.*

class ParedDevices : Fragment() {
    private var lstvw: ListView? = null
    private var aAdapter: AdapterBluetooth?=null
    var bluetoothDevice: ArrayList<BluetoothDevice> = ArrayList()
    private var bAdapter = BluetoothAdapter.getDefaultAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pared_devices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (bAdapter == null) {
            Toast.makeText(
                context!!,
                "Bluetooth Not Supported So Cannot get Pared Device",
                Toast.LENGTH_SHORT
            )
                .show();
        } else {
            var pairedDevices: Set<BluetoothDevice> = bAdapter!!.getBondedDevices();
            var list = ArrayList<Any>();
            if (pairedDevices.size > 0) {
                for (device: BluetoothDevice in pairedDevices) {
                    var devicename: String = device.getName();
                    var macAddress: String = device.getAddress();
                    list.add("Name: " + devicename + "MAC Address: " + macAddress);
                    bluetoothDevice.add(device)
                }
                aAdapter = AdapterBluetooth(context!!, R.layout.listview_item, bluetoothDevice)
                pared_devices_listview!!.setAdapter(aAdapter);
            }
        }

    }
}