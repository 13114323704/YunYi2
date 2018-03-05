package com.hq.yunyi2.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GsonTools {
    // 使用Gson解析List<PrivateInfo>对象
    public static <T> List<T> getDataList(String jsonstring, Class<T[]> cls) {
       /* List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonstring, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;*/
        Gson gson = new Gson();
        T[] list  = gson.fromJson(jsonstring, cls);

        return Arrays.asList(list);
    }
}
