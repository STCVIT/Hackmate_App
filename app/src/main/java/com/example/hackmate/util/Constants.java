package com.example.hackmate.util;

import java.util.HashMap;
import java.util.stream.Stream;

public class Constants {
    public static final String BASE_URL = "https://hackportalbackend.herokuapp.com/";
    public static HashMap<String, String> skills = new HashMap<String,String>(){{
        put("ml","Machine Learning");
        put("Machine Learning","ml");
        put("frontend","Frontend");
        put("Frontend","frontend");
        put("backend","Backend");
        put("Backend","backend");
        put("ui/ux","UI/UX Design");
        put("UI/UX Design","ui/ux");
        put("management","Management");
        put("Management","management");
        put("appdev","App Development");
        put("App Development","appdev");
    }};

}
