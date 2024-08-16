package com.androindian.vollry

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.androindian.vollry.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding?=null
    var url="https://androindian.com/test/Register_raw.php"
    var requestQueue:RequestQueue?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestQueue=Volley.newRequestQueue(this@MainActivity)
        binding?.submit?.setOnClickListener {
            var jsonObject=JSONObject()
            jsonObject.put("username",binding?.username?.text.toString().trim())
            jsonObject.put("email",binding?.email?.text.toString().trim())
            jsonObject.put("password",binding?.password?.text.toString().trim())

            var jsonObjectRequest=JsonObjectRequest(
                Request.Method.POST,url,jsonObject,{response->
                    var res=response.getString("status")
                    if(res.equals("failed")){
                        var res1=response.getString("key")
                        Toast.makeText(this@MainActivity,res1,Toast.LENGTH_LONG).show()
                    }else{
                        var res1=response.getString("key")
                        Toast.makeText(this@MainActivity,res1,Toast.LENGTH_LONG).show()
                    }
                }
            ){error->
Toast.makeText(this@MainActivity,error.toString(),Toast.LENGTH_LONG).show()
            }
            requestQueue?.add(jsonObjectRequest)
        }
    }
}