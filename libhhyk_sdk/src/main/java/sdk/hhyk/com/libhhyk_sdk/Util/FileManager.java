package sdk.hhyk.com.libhhyk_sdk.Util;


public class FileManager {
	
	//游戏首页json缓存
	public static final int GAME_HOME_CACHE = 1;
    //apk 信息缓存
	public static final int APK_INFOR_CACHE = 2;

	public static String getSaveFilePath(int cacheType, long gameId) {
		if (CommonUtil.hasSDCard()) {
			if(cacheType == GAME_HOME_CACHE){
				return CommonUtil.getRootFilePath() + "vsoyou/ad/";
			}else if(cacheType == APK_INFOR_CACHE){
				return CommonUtil.getRootFilePath() + "vsoyou/apk/" + gameId +"/";
			}
		}
		return "";
	}
}
