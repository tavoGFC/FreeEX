package com.example.gusfc_000.freeex.conexionBluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.util.Log;


import com.example.gusfc_000.freeex.MainActivityFreeEX;

import java.io.IOException;

/**
 * Created by gusfc_000 on 18/11/2015.
 */
public class BaseSThread extends Thread {
    private BluetoothServerSocket bluetoothServerSocket;
    private BluetoothSocket bluetoothSocket;
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private final Handler handler;


    public BaseSThread(Handler handler){
        this.handler = handler;
        try{

            bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Server Bluetooth", MainActivityFreeEX .APP_UUID);
            Log.d(this.toString(), "Inicializando servidor: " + bluetoothServerSocket.toString());


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            try {
                bluetoothSocket = bluetoothServerSocket.accept();
                Log.d(this.toString(), "ServerSocket es aceptado'");
                manageConnectedSocket();
                bluetoothServerSocket.close();
                Log.d(this.toString(), "se cerro el serverSocket");
                break;
            }catch (IOException e1){
                if (bluetoothSocket != null){
                    Log.d(this.toString(), "Socket != null, se cerrara ServerSocket");
                    try{
                        bluetoothServerSocket.close();
                    }catch (IOException e2){
                        e2.printStackTrace();
                    }
                }
            }
        }
    }


    private  void manageConnectedSocket(){
        ConnectionThread  connectionThread = new ConnectionThread(bluetoothSocket, handler);
        handler.obtainMessage(MainActivityFreeEX.SOCKET_CONNECTED, connectionThread).sendToTarget();

        connectionThread.start();
        Log.d(this.toString(), "manageConnectedSocket del Servidor comenzado");

    }


    public void cancel(){
        try {
            if(null != bluetoothServerSocket){
                bluetoothServerSocket.close();
                System.out.println("Cerrar ServerSockt por que empezo a buscar ServerSocket");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }






}
