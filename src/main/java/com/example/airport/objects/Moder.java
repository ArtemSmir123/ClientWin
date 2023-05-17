package com.example.airport.objects;

import org.json.simple.JSONObject;

public class Moder extends Users implements Objects{
    public Moder(String login, String password, String role, String name, String lastname) {
        super(login, password, role, name, lastname);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("login", this.getLogin());
        result.put("password", this.getPassword());
        result.put("role", this.getRole());
        result.put("name", this.getName());
        result.put("lastname", this.getLastname());
        JSONObject result1 = new JSONObject();
        result1.put("Moder", result);
        return result1;
    }
    public static Objects fromJSONObject(JSONObject object) {
        JSONObject res = (JSONObject) object.get("Moder");
        return new Moder((String) res.get("login"),
                (String) res.get("password"),
                (String) res.get("role"),
                (String) res.get("name"),
                (String) res.get("lastname"));
    }
}
