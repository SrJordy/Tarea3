package com.example.tarea3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle b=this.getIntent().getExtras();
        TextView txt=(TextView) findViewById(R.id.txt);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/productos/search";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     String v1="";
                     String dat="";
                        try {
                            JSONObject res=new JSONObject(response);
                            v1=res.getString("productos");
                            JSONArray JSONlista = new JSONArray(v1);
                            for(int i=0; i< JSONlista.length();i++){
                                JSONObject dats= JSONlista.getJSONObject(i);
                                dat=dat+ "\n"+"ID: "+
                                        dats.getString("id").toString()+"\n"
                                        +"DESCRIPCIÓN: "+dats.getString("descripcion")+"\n"+
                                        "BARCODE: "+dats.getString("barcode")+"\n"+
                                        "COSTO: "+dats.getString("costo")+"\n\n";
                            }
                            txt.setText(dat);
                            Log.i("INFO",v1);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Error detectado", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            public Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("fuente","1");
                return params;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","Bearer "+ b.getString("Token"));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}