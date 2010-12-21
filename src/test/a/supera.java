package test.a;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
//import android.view.View.OnClickListener;
import android.widget.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;

public class supera extends Activity{
	
	
    private String getContent(String url) throws Exception{
		StringBuilder sb = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,3000);
		HttpConnectionParams.setSoTimeout(httpParams,5000);
		HttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		if(entity != null){
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"),8192);
		    String line = null;
		    while((line = reader.readLine())!=null)
		    	sb.append(line+ "\n");
		    reader.close();
		}
		return sb.toString();
	}
    

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		try{
			StringBuffer sb = new StringBuffer();
			String url = "http://bandonsuper.appspot.com/api/updatedb";
			String body = getContent(url);
			JSONArray array = new JSONArray(body);
			for(int i=0;i<array.length();i++){
				JSONObject obj = array.getJSONObject(i);
				sb.append("最後更新日期:").append(obj.getString("update")).append("\t");
			}
			TextView textView = (TextView)findViewById(R.id.textView);
			textView.setText(sb.toString());
				
		}catch(Exception e){}
	}
	

}
