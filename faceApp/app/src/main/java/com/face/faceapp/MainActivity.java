package com.face.faceapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.face.faceobject.FaceInfo;
import com.face.faceobject.FaceList;
import com.face.faceobject.FaceSetting;
import com.face.mode.cluster.IQueryReturnHandler;
import com.face.mode.cluster.UdpQueryReqThread;
import com.face.mode.cluster.UdpQueryRspThread;
import com.face.mode.cluster.UdpQueryRtnThread;
import com.face.mode.cluster.UdpReturnHandler;
import com.face.network.AppClient;
import com.face.network.CTPReceiveHandler;
import com.face.network.ITCPReceiveHandler;
import com.face.services.ClusterService;
import com.face.util.DeviceHelper;
import com.face.util.PermisionUtils;
import com.face.util.StringUtils;

import org.appcommon.AppNetwork;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IQueryReturnHandler {

    private FaceSetting mFaceSetting = null;

    private ListView lvNames;
    private ArrayAdapter<String> lvAdapter;
    private ArrayList<String> allNames;

    private UdpReturnHandler returnHandler;
    private UdpQueryRtnThread queryRtnThread;

    private UdpQueryRspThread queryRspThread;

    WifiManager wifimanager;

    private String DeviceId = "-----";

    private AppClient tcpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFaceSetting = FaceSetting.loadData(this);
        if(mFaceSetting != null)
        {
            EditText txt = (EditText)findViewById(R.id.edit_ip_input);
            txt.setText(mFaceSetting.getIP());
        }else
        {
            mFaceSetting = new FaceSetting();
        }

        allNames = new ArrayList<String>();

        lvNames = (ListView)findViewById(R.id.lvNames);
        lvAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,allNames);
        lvNames.setAdapter(lvAdapter);


        wifimanager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        returnHandler = new UdpReturnHandler(this);
        queryRtnThread = new UdpQueryRtnThread(returnHandler);

        queryRspThread = new UdpQueryRspThread(wifimanager);


        Log.d("queryRspThread .start","=========================================");
       // queryRspThread.start();

        Log.d("queryRtnThread .start","*****************************************");
       // queryRtnThread.start();


        CTPReceiveHandler.getInstance().AddListener(this, AppNetwork.C_Rsp_Login, new ITCPReceiveHandler() {
            @Override
            public void OnReceived(AppNetwork.FaceObjBase objField) {
                if(objField instanceof AppNetwork.RspLogin) {
                    AppNetwork.RspLogin field = (AppNetwork.RspLogin)objField;
                    if (field != null) {
                        Toast.makeText(MainActivity.this, "received login:" + field.result, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "received  * NULL * RspLogin broadcast,", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CTPReceiveHandler.getInstance().AddListener(this, AppNetwork.C_Rtn_Login, new ITCPReceiveHandler() {
            @Override
            public void OnReceived(AppNetwork.FaceObjBase objField) {
                if(objField instanceof AppNetwork.RtnLogin) {
                    AppNetwork.RtnLogin field = (AppNetwork.RtnLogin)objField;
                    if (field != null) {

                        EditText txt = (EditText) findViewById(R.id.edit_Qry_Result);
                        txt.setText(field.msg);
                    } else {
                        Toast.makeText(MainActivity.this, "received  * NULL * RtnLogin broadcast,", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tcpClient = new AppClient();
        tcpClient.start();


        PermisionUtils.verifyStoragePermissions(this);


        String readDeviceID = DeviceHelper.getDeviceId(this);

        EditText txt = (EditText)findViewById(R.id.edit_Qry_Result);
        txt.setText(readDeviceID);

        if(!StringUtils.isBlank(readDeviceID))
        {
            DeviceId = readDeviceID;
        }

        String result = DeviceHelper.GenerateLicense(readDeviceID);

        Log.d("good",readDeviceID  + "  "  +  result);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        //call the stop the thread.
        tcpClient.Stop();
    }

    public void OnRtnQueryResult(FaceInfo face)
    {
        Log.d("MainActivity:",face.Name);

        EditText txt = (EditText)findViewById(R.id.edit_Qry_Result);
        txt.setText(face.Name + "," + face.CardId);
    }

    public void submitConfig(View view){
        EditText txt = (EditText)findViewById(R.id.edit_ip_input);

        String ip = txt.getText().toString();

        mFaceSetting.setIP(ip);

        mFaceSetting.saveData(this);
    }

    public void onStartClusterServiceClicked(View view)
    {
        Intent startIntent = new Intent(this, ClusterService.class);
        startService(startIntent);
    }

    public void onEndClusterServiceClicked(View view)
    {
        Intent stopIntent = new Intent(this,ClusterService.class);
        stopService(stopIntent);
    }

    public void onClickMakeNames(View view)
    {
        lvAdapter.clear();
        EditText txt = (EditText)findViewById(R.id.text_userId);
        String ip = txt.getText().toString();

        FaceList.CleanAll();

        for (int index = 1; index<=5;index++)
        {
            String name = ip + index;
            lvAdapter.add(name);

            FaceInfo info = new FaceInfo();
            info.valid = true;
            info.Name = name;
            info.CardId = String.valueOf(index);

            FaceList.AddFaceInfo(info);
        }

    }

    public void onClickQueryFace(View view)
    {
        EditText txt = (EditText)findViewById(R.id.text_userId);
        String ip = txt.getText().toString();

        UdpQueryReqThread reqThread = new UdpQueryReqThread( wifimanager,ip.getBytes());
        reqThread.start();

    }

    public void onClickSendLogin(View view)
    {

//        new Thread("runReqLogin") {
//            public void run () {
//                try {
//                    tcpClient.ReqLogin(DeviceId);
//                    // Server communication after connection can go here, or in Listener#connected().
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }.start();

        tcpClient.ReqLogin(DeviceId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
