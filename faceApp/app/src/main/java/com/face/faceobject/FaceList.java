package com.face.faceobject;

import java.util.ArrayList;
import java.util.List;

public final class FaceList {
    public static List<FaceInfo> allUsers = new ArrayList<FaceInfo>();

    public static void AddFaceInfo(FaceInfo obj)
    {
        allUsers.add(obj);
    }

    public static void CleanAll()
    {
        allUsers.clear();
    }

    public static String[] AllNames()
    {
        ArrayList<String> items = new ArrayList<String>();
        for (FaceInfo obj:allUsers)
        {
            items.add(obj.Name);
        }

        return (String[])items.toArray();
    }

    public static FaceInfo SearchInfo(String name)
    {
        for (FaceInfo obj:allUsers)
        {
            if(obj.Name.compareTo(name) == 0)
            {
                return obj;
            }
        }
        return null;
    }
}
