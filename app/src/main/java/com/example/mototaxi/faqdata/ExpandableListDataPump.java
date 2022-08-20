package com.example.mototaxi.faqdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Good");
        cricket.add("Medium");
        cricket.add("bad");


        List<String> football = new ArrayList<String>();
        football.add("Phone");
        football.add("Email");
        football.add("SMS");
        football.add("Whatsapp");

        List<String> basketball = new ArrayList<String>();
        basketball.add("Privacy policy ");
        basketball.add("Details of Services");


        expandableListDetail.put("Feed back about this app", cricket);
        expandableListDetail.put("Need to contact Developer?", football);
        expandableListDetail.put("Need to Know about this product", basketball);
        return expandableListDetail;
    }
}
