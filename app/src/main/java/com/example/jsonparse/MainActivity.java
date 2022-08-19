package com.example.jsonparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
    }
    public void clickBtn(View view) {
        // json 파일 읽어와서 분석하기
        // assets 폴더의 파일을 가져오기 위해
        // 창고관리자 (AssetManager) 얻어오기
        AssetManager assetManager = getAssets();

        // assets/test.json 파일 읽기 위한 InputStream
        try {
            InputStream is = assetManager.open("test.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            String jsonData = buffer.toString();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonData);

            // features 가져오기
            JSONArray parse_features = (JSONArray) obj.get("features");

            // features 각각 요소 출력
            String s = "";
            for (int i = 0; i < parse_features.size(); i++) {
                JSONObject jsonObject = (JSONObject) parse_features.get(i);

                // properties 가져오기
                JSONObject parse_properties = (JSONObject) jsonObject.get("properties");

                // geometry 가져오기
                JSONObject parse_geometry = (JSONObject) jsonObject.get("geometry");

                // coordinates 각각 요소 가져오기
                JSONArray parse_coordinates = (JSONArray) parse_geometry.get("coordinates");

                String name = (String) parse_properties.get("NODE_NAME");

                s += name + " : " + parse_coordinates.get(1) + ", " + parse_coordinates.get(0) + "\n";
            }
            tv.setText(s);  // 신하리434 : 37.34234234234, 127.343434243242
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}