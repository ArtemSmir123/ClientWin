package com.example.airport.objects;

import org.json.simple.JSONObject;

public class Autorit implements Objects{
    private String login;
    private String pass;
    public Autorit(String login, String pass){
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }
    public String getPass() {
        return pass;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        result.put("login", this.getLogin());
        result.put("pass", this.getPass());
        JSONObject result1 = new JSONObject();
        result1.put("Autorit", result);
        return result1;
    }
    public static Objects fromJSONObject(JSONObject object) {
        JSONObject res = (JSONObject) object.get("Autorit");
        return new Autorit((String) res.get("login"),
                (String) res.get("pass"));
    }
}
