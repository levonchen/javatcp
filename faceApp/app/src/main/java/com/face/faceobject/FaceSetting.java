package com.face.faceobject;

import android.app.Activity;
import android.media.FaceDetector;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FaceSetting implements Serializable {

    private String IP;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    private static File mFolder;
    public void saveData(Activity pContext)
    {
        if(mFolder == null)
        {
            mFolder = pContext.getExternalFilesDir(null);
        }
        try{
            ObjectOutput out;

            File outFile = new File(mFolder,"faceSetting.data");

            out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(this);
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static FaceSetting loadData(Activity pContext)
    {
        if(mFolder == null)
        {
            mFolder = pContext.getExternalFilesDir(null);
        }

        FaceSetting setting = null;
        try{
            ObjectInput in;

            FileInputStream fileIn = new FileInputStream(mFolder.getPath() + File.separator + "faceSetting.data");
            in = new ObjectInputStream(fileIn);

            setting = (FaceSetting)in.readObject();
            in.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        if(setting != null)
        {
            Log.d("In IP :",setting.getIP() );
        }else
        {
            Log.d("Face Setting is null","");
        }

        return setting;
    }
}
