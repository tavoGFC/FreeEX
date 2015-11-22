package com.example.gusfc_000.freeex.conexionBluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.example.gusfc_000.freeex.MainActivityFreeEX;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by gusfc_000 on 18/11/2015.
 */
public class ConnectionThread extends Thread {
    BluetoothSocket bluetoothSocket;
    private final Handler handler;
    private InputStream inputStream;
    private OutputStream outputStream;


    ConnectionThread(BluetoothSocket socket, Handler handler1){
        super();
        this.bluetoothSocket = socket;
        this.handler = handler1;
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
            Log.d(this.toString(), "Inicializando ConnectionThread: " + "Input: " + inputStream.toString() + ", " + "Outpu: " + outputStream.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    /**
     * Calls the <code>run()</code> method of the Runnable object the receiver
     * holds. If no Runnable is set, does nothing.
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;
        while (true) {
            try {

                bytes = inputStream.read(buffer);
                String data = new String(buffer, 0, bytes);
                handler.obtainMessage(MainActivityFreeEX.DATA_RECEIVED, data).sendToTarget();

                Log.i(this.toString(), "en el run de Connection: " + handler.obtainMessage(MainActivityFreeEX.DATA_RECEIVED, data).toString());


            } catch (IOException e) {
                break;
            }
        }
    }


    public void write(byte[] bytes){
        try {
            outputStream.write(bytes);
            System.out.println("El mensaje es: " + bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
