package com.face.mode.cluster;

import com.face.faceobject.FaceInfo;

public interface IQueryReturnHandler {
    //返回查询的结果
    //result == true 时，face 包含结果
    //result == false 时, face 为null
    void OnRtnQueryResult(FaceInfo face);
}
