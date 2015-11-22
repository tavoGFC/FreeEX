package com.example.gusfc_000.freeex.conexionBluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.example.gusfc_000.freeex.MainActivityFreeEX;


import java.io.IOException;

/**
 * Created by gusfc_000 on 18/11/2015.
 */
public class ClientThread extends Thread{
    private BluetoothSocket bluetoothSocket;
    private final BluetoothDevice device;
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private final Handler handler;


    public ClientThread(String deviceID, Handler handler1){
        this.device = bluetoothAdapter.getRemoteDevice(deviceID);
        this.handler = handler1;
        try {

            bluetoothSocket = device.createRfcommSocketToServiceRecord(MainActivityFreeEX.APP_UUID);
            Log.d(this.toString(), "Inicializando Cliente: " + bluetoothSocket.toString());

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void run(){
        bluetoothAdapter.cancelDiscovery();
        try {
            Log.d(this.toString(), "Conectando socket cliente");
            bluetoothSocket.connect();
            manageConnectedSocket();
        }catch (IOException connectException){
            Log.d("", "Error " + connectException.getMessage());
            try {
                Log.d(this.toString(), "Cerrando socket de clase cliente: " + bluetoothSocket.getRemoteDevice());
                bluetoothSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    private void manageConnectedSocket(){
        ConnectionThread connectionThread = new ConnectionThread(bluetoothSocket, handler);
        handler.obtainMessage(MainActivityFreeEX.SOCKET_CONNECTED, connectionThread).sendToTarget();

        connectionThread.start();
        Log.d(this.toString(), "manageConnectedSocket del Cliente comenzado");
    }


    public void cancel(){
        try {
            bluetoothSocket.close();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }


}
