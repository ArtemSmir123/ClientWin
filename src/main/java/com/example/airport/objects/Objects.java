package com.example.airport.objects;

import org.json.simple.JSONObject;

public interface Objects {
    public JSONObject toJSONObject();

    public static Objects fromJSONObject(JSONObject object) {
        System.out.println("нереализованный метод");
        return null;
    }
}