package com.example.gusfc_000.freeex;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gusfc_000.freeex.PaqueteCliente.Usuario;
import com.example.gusfc_000.freeex.PaqueteEstructras.GrafMatPeso;
import com.example.gusfc_000.freeex.conexionBluetooth.BaseSThread;
import com.example.gusfc_000.freeex.conexionBluetooth.ClientThread;
import com.example.gusfc_000.freeex.conexionBluetooth.ConnectionThread;
import com.example.gusfc_000.freeex.conexionBluetooth.ShowDevices;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivityFreeEX extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int SELECT_SERVER = 1;
    public static final int DATA_RECEIVED = 3;
    public static final int SOCKET_CONNECTED = 4;

    public static final UUID APP_UUID = UUID.fromString("aeb9f938-a1a3-4947-ace2-9ebd0c67adf1");


    //mensajes
    EditText inputText;
    Button buttonSendM;
    TextView textoEnviado;

    //estado de la conexion
    TextView textEstado;

    //usuario
    TextView nombreUsuario;
    TextView correo;
    TextView contreseña;

    String correoAEnviar;
    String contraseñaDeCorreo;
    String stringNombreUsuario;

    //activar bluetooth mediante switch
    Switch buttonActivarBluetooth;

    //mostrar las coordenadas del dispositivo
    TextView textViewGPS;

    //mostrar el nombre del Bluetooth, el cual sera Base Station
    TextView textBaseStationInfo;

    //lista donde se mostraran los dispositivos
    public ListView listViewDevicesNew;
    //mostrar dispositivos asociados
    public ListView listViewPairedDevice;
    ArrayAdapter<String> arrayAdapterDevicesNew = null;
    ArrayList<String> devicesStrings = new ArrayList<>();
    ArrayAdapter<BluetoothDevice> arrayAdapterDevicesPaird;

    ArrayAdapter<String> arrayAdapter = null;


    //para las conexiones
    private BluetoothAdapter bluetoothAdapter = null;
    private ConnectionThread connectionThread = null;
    private BaseSThread baseStation;
    private ClientThread cliente;
    private String data;
    private boolean baseStationMode;


    //estados para cambiar de panatallas y salir de la aplicacion
    boolean enMenu = true;
    boolean enIngresar = false;
    boolean enConectando = false;


    //obtner el posicionamiento del dispositivo
    public GPSTracker gpsTracker;
    public double latitud = 0.0;
    public double longitud = 0.0;

    public GrafMatPeso getGrafMatPeso() {
        return grafMatPeso;
    }

    //grafo
    GrafMatPeso grafMatPeso = new GrafMatPeso(8);
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_free_ex);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth no es soportado", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {

        } else if (requestCode == SELECT_SERVER && resultCode == RESULT_OK) {
            BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            connectToBluetoothServer(device.getAddress());
        }
    }

    private void connectToBluetoothServer(String id) {
        cliente = new ClientThread(id, handler);
        cliente.start();
    }


    public void onClickEmpezar(View view) {
        setContentView(R.layout.activity_ingresar);
        enMenu = false;
        enIngresar = true;
        enConectando = false;

        nombreUsuario = (TextView) findViewById(R.id.textNombre);
        correo = (TextView) findViewById(R.id.textCorreo);
        contreseña = (TextView) findViewById(R.id.textContraseña);

    }

    public void onClickSiguiente(View view) {
        correoAEnviar = correo.getText().toString();
        contraseñaDeCorreo = contreseña.getText().toString();
        stringNombreUsuario = nombreUsuario.getText().toString();

        textEstado = (TextView) findViewById(R.id.textEstado);

        textBaseStationInfo = (TextView) findViewById(R.id.textViewInfo);

        setContentView(R.layout.activity_conectando);
        enMenu = false;
        enIngresar = false;
        enConectando = true;

        buttonActivarBluetooth = (Switch) findViewById(R.id.switchAB);
        buttonActivarBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        Intent visibleBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        visibleBluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivityForResult(visibleBluetooth, REQUEST_ENABLE_BT);
                        startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
                        Toast.makeText(getApplicationContext(), "Activando bluetooth", Toast.LENGTH_SHORT).show();

                        while (isChecked) {
                            if (bluetoothAdapter.isEnabled()) {
                                startBaseS();
                                baseStationMode = true;
                                break;
                            } else {
                                isChecked = true;
                            }

                        }


                    }
                } else {
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Desactivando bluetooth", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void onClickBuscar(View view) throws Exception {
        baseStationMode = false;
        selectBaseS();
    }



    private void startBaseS() {
        textEstado = (TextView) findViewById(R.id.textEstado);
        textEstado.setText("Esperando Dispositivo...");
        baseStation = new BaseSThread(handler);
        baseStation.start();

    }

    private void selectBaseS() throws Exception {
        textEstado = (TextView) findViewById(R.id.textEstado);
        textEstado.setText("Seleccionar Base Station...");

        baseStation.cancel();
        Intent showDevices = new Intent(this, ShowDevices.class);
        showDevices.putStringArrayListExtra("devices", devicesStrings);
        startActivityForResult(showDevices, SELECT_SERVER);


//        listViewPairedDevice = (ListView) findViewById(R.id.listViewPairedBlue);
//        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//        ArrayList<BluetoothDevice> pairedDevicesString = new ArrayList<BluetoothDevice>();
//        if (pairedDevices.size() > 0) {
//            for (BluetoothDevice device : pairedDevices) {
//                //usuario = new Usuario(correoAEnviar,stringNombreUsuario,getGrafMatPeso());
//                pairedDevicesString.add(device);
//            }
//        }
//        arrayAdapterDevicesPaird = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1, pairedDevicesString);
//        listViewPairedDevice.setAdapter(arrayAdapterDevicesPaird);
//
//        listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);
//                Toast.makeText(getApplicationContext(), "Nombre: " + device.getName(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }

    public Handler handler = new Handler(){

        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SOCKET_CONNECTED: {
                    connectionThread = (ConnectionThread) msg.obj;
                    if(!baseStationMode){
                        System.out.println("entrando baseStationMode false");
                        textEstado.setText("Conectado con Base Station");
                        connectionThread.write("Conversacion inicidada".getBytes());
                        //conversacionUnoAUno();
                    }
                    break;
                }
                case DATA_RECEIVED: {
                    data = (String) msg.obj;
                    if (textoEnviado != null){
                        textoEnviado.setText(data);
                    }
                    if(baseStationMode){
                        System.out.println("entrando baseStationMode true y el data es: " + data);
                        connectionThread.write(data.getBytes());
                        //connectionThread.write(data.getBytes());
                        //textoEnviado.setText(data);
                        //conversacionUnoAUno();

                    }
                }
                default:
                    break;
            }
        }
    };

    public void onClickUpdatePairedDevices(View view){
        listViewPairedDevice = (ListView) findViewById(R.id.listViewPairedBlue);
        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        ArrayList<BluetoothDevice> pairedDevicesString = new ArrayList<BluetoothDevice>();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                //usuario = new Usuario(correoAEnviar,stringNombreUsuario,getGrafMatPeso());
                pairedDevicesString.add(device);
            }
        }
        arrayAdapterDevicesPaird = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1, pairedDevicesString);
        listViewPairedDevice.setAdapter(arrayAdapterDevicesPaird);

        listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);

                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivityFreeEX.this);
                alert.setTitle("Mensajes");
                alert.setMessage("Realizar conversacion uno a uno con: " + device.getName());
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        conversacionUnoAUno();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
                //Toast.makeText(getApplicationContext(), "Nombre: " + device.getName(), Toast.LENGTH_SHORT).show();

            }
        });


        listViewPairedDevice.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Nombre: " + device.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


    public void conversacionUnoAUno(){
        setContentView(R.layout.activity_conversacion_uno_auno);
        textoEnviado = (TextView)findViewById(R.id.textViewMensajeRecivido);
        inputText = (EditText)findViewById(R.id.editTextMensaje);
        buttonSendM = (Button)findViewById(R.id.buttonEnviarMensaje);
        buttonSendM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionThread != null){
                    if(inputText.getText() == null) {
                        Toast.makeText(getApplicationContext(), "Ingrese un mensaje para enviar", Toast.LENGTH_SHORT).show();
                    }
                    byte[] bytesToSend = inputText.getText().toString().getBytes();
                    //textoEnviado.setText(data);
                    connectionThread.write(bytesToSend);
                    //textoEnviado.setText(data);
//                        try {
//                            usuario.mensajeUnoAUno(stringNombreUsuario, inputText.getText().toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


                }
            }
        });

    }


    public void onClickMostrarU(View view) {

        textViewGPS = (TextView) findViewById(R.id.textUbicacion);

        gpsTracker = new GPSTracker(MainActivityFreeEX.this);

        if (gpsTracker.isGPSEnable || gpsTracker.isNetworkEnable) {
            //ciclo hasta que encuentre una ubicacion
            while (latitud == 0.0 && longitud == 0.0) {

                // optener la ubicacion
                gpsTracker.getLocation();

                latitud = gpsTracker.getLatitude();
                longitud = gpsTracker.getLongitude();
                double total = Math.sqrt((latitud * latitud) + (longitud * longitud));

                System.out.println(latitud + ", " + longitud);

                if (latitud != 0.0 && longitud != 0.0) {
                    textViewGPS.setText(latitud + "\n" + longitud);
                    break;
                }
            }

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed(){
        if(enIngresar){
            setContentView(R.layout.activity_main_activity_free_ex);
        }
        if(enConectando){
            setContentView(R.layout.activity_ingresar);
            enIngresar = true;
            enConectando = false;
        }
        if(enMenu){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivityFreeEX.this);
            alertDialog.setTitle("Salir de la aplicacion");
            alertDialog.setMessage("Desea salir de FreeEX ?");
            alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_free_ex, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}



