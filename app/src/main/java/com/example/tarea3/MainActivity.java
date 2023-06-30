package com.example.tarea3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.SectionResponse;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText Correo;
    EditText Clave;
    TextView tex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btnLogin(View view){
        Correo=findViewById(R.id.txtCorreo);
        Clave=findViewById(R.id.txtClave);
        tex=findViewById(R.id.txt);
        Intent intent=new Intent(MainActivity.this, MainActivity2.class);
        Bundle b=new Bundle();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res=new JSONObject(response);
                            b.putString("Token",res.getString("access_token"));
                            intent.putExtras(b);
                            startActivity(intent);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error detectado", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            public Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("correo",Correo.getText().toString());
                params.put("clave",Clave.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}