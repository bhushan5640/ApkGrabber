package com.apkgrabber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView processingText = (TextView) findViewById(R.id.textView2);
		processingText.setText("please wait while processing...");
		Button grabButton = (Button) findViewById(R.id.button1);
		grabButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				grabApk();
				Toast toast = Toast.makeText(getApplicationContext(), R.string.complete_message, Toast.LENGTH_SHORT);
				toast.show();
				processingText.setText("Completed Successfully..!!");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void grabApk() {

		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);
		int z = 0;
		
		for (Object object : pkgAppsList) {

			ResolveInfo info = (ResolveInfo) object;

			File f1 =new File( info.activityInfo.applicationInfo.publicSourceDir);

			Log.v("APPS", "Filename: "+f1.getName().toString()+"----"+info.loadLabel(getPackageManager()));

			
			// APPLICATION NAME WHICH YOU WANT TO EXTRACT APK OF
//			if(f1.getName().toString().contains("mybrowser")){
			if(true){
				try{

					String file_name = info.loadLabel(getPackageManager()).toString();
					Log.i("file_name--", " "+file_name);

					// File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/Folder/"+file_name+".apk");
					// f2.createNewFile();

					File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/APK");   
					f2.mkdirs();            
					f2 = new File(f2.getPath()+"/"+file_name+".apk");
					f2.createNewFile();

					InputStream in = new FileInputStream(f1);

					OutputStream out = new FileOutputStream(f2);

					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0){
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
					Log.i("APPS",f2.getName().toString() + " File copied.");
				}
				catch(FileNotFoundException ex){
					Log.i("Error", ex.getMessage() + " in the specified directory.");
				}
				catch(IOException e){
					Log.i("Error", e.getMessage());      
				}
			}
		}
	}
}
