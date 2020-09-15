package com.example.macaddressfinder

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.listview_item.view.*


class AdapterBluetooth(context: Context, var i: Int, arrayList: ArrayList<BluetoothDevice>) :
    ArrayAdapter<BluetoothDevice>(
        context,
        i,
        arrayList
    ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val name = (getItem(position) as BluetoothDevice?)!!.name
        val address = (getItem(position) as BluetoothDevice?)!!.address
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(i, parent, false)
        inflate!!.device_name_tv.setText(name)
        inflate!!.mac_address_tv.setText(address)
        return inflate
    }

}