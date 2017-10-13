package sdk.hhyk.com.libhhyk_sdk.Util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NImsiUtil {
	
private static final String LOCAL_NIMSI = "local_nImsi";
	
	public static String getNImsiValue(Context context){
		LogUtil.i("nImsi", "getNImsiValue start");
		String nImsi = "";
		//从本地SharedPreferences中拿数据
		LocalStorage storage = LocalStorage.getInstance(context);
		nImsi = storage.getString(LOCAL_NIMSI, "");
		LogUtil.i("nImsi", "nImsi-->" + nImsi);
		if("".equals(nImsi)){
			//从SD卡上读取nImsi
			if(CommonUtil.hasSDCard()){
				LogUtil.i("nImsi", "have sd card");
				String filePath = CommonUtil.getRootFilePath() + "vs/value.key";
				if(FileHelper.fileIsExist(filePath)){//文件存在
					File file = new File(filePath);
					String str = FileHelper.readFileData(file);
					if(StringUtil.isEmpty(str)){
						nImsi = getTimeValue();
						FileHelper.writeFile(filePath, nImsi);
					}else{
						nImsi = str;
					}
					storage.putString(LOCAL_NIMSI, nImsi);
				}else{ //文件不存在
					String dirpath = CommonUtil.getRootFilePath() + "vs";
					if(FileHelper.createDirectory(dirpath)){
						File keyFile = new File(filePath);
						keyFile.delete();
						try {
							keyFile.createNewFile();
							nImsi = getTimeValue();
							FileHelper.writeFile(filePath, nImsi);
						} catch (IOException e) {
							nImsi = getTimeValue();
							storage.putString(LOCAL_NIMSI, nImsi);
							e.printStackTrace();
						}
						
					}else{
						nImsi = getTimeValue();
						storage.putString(LOCAL_NIMSI, nImsi);
					}
				}
			}else{
				LogUtil.i("nImsi", "no sd card");
				nImsi = getTimeValue();
				storage.putString(LOCAL_NIMSI, nImsi);
			}
		}
		return nImsi;
	} 
	
	public static String getTimeValue(){
		SimpleDateFormat sf =new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sf.format(new Date());
	}

}
