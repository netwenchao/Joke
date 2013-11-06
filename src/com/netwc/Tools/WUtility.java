package com.netwc.Tools;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

public class WUtility {
	public static class ExternalStorageState{
		public ExternalStorageState(){
			Available=false;
			Writeable=false;
		}		
		public boolean Available;
		public boolean Writeable;
	}

	/*
	 * Get External StorageState
	 * */
	public static ExternalStorageState GetExternalStorageState(){
		ExternalStorageState state=new ExternalStorageState();
		String stateString=Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(stateString)){
			state.Available=state.Writeable=true;
		}else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(stateString)){
			state.Available=true;
		}
		return state;
	}
	
	/*
	 * Return SD Card Folder
	 * */
	public static File GetExternalStorageFolder(){
		ExternalStorageState state=GetExternalStorageState();
		if(state.Writeable){
			return Environment.getExternalStorageDirectory();			
		}
		return null;
	}

	/*
	 * */
	public static File GetDataFoder(){
		return Environment.getDataDirectory();
	}
}
