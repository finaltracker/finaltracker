package com.zdn.util;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static String mainPath="";
	static{
		if( isSDCardReady() ){
			mainPath=Environment.getExternalStorageDirectory()+"/zhaodaoni";
		}else{
			mainPath=Environment.getDataDirectory().getAbsolutePath()+"/zhaodaoni";
		}
	}
	
	public static String getRecentChatPath(){
		File file=new File(mainPath+"/RecentChat/");
		if(!file.exists()){
			file.mkdirs();
		}
		return mainPath+"/RecentChat/";
	}
	
	public static void createBasePath()
	{
		makeFile( "" );
	}
	
	////////////////////////////////////////////////////////////
	private static String ANDROID_SECURE = "/mnt/sdcard/.android_secure";

    private static final String LOG_TAG = "Util";

    public static boolean isSDCardReady() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static File makeFile( String fileName )
    {
    	String targetDir = makePath( mainPath , fileName);
    	File destDir = new File( targetDir );
    	  if (!destDir.exists()) {
    	   destDir.mkdirs();
    	  }
    	  Process p;
    	  int status;
    	  try {
              p = Runtime.getRuntime().exec("chmod 777 " +  destDir );
              status = p.waitFor();  
          }
    	  catch( Exception e1 )
    	  {
    		  Log.d( FileUtil.class.getSimpleName(),  destDir+": chomd 777 fail");
    	  }
    	  
    	  return destDir;
    	
    }
    public static String makePath(String path1, String path2) {
        if (path1.endsWith(File.separator))
            return path1 + path2;

        return path1 + File.separator + path2;
    }
    
    public static String getBaseDirector()
    {
    	return mainPath;
    }
    
    
    public static boolean fileExist( String absoultePath )
    {
    	File destDir = new File( absoultePath );
    
    	if (destDir.exists()) 
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public static String getSdDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static boolean isNormalFile(String fullName) {
        return !fullName.equals(ANDROID_SECURE);
    }

    /*
     * 采用了新的办法获取APK图标，之前的失败是因为android中存在的�?��BUG,通过
     * appInfo.publicSourceDir = apkPath;来修正这个问题，详情参见:
     * http://code.google.com/p/android/issues/detail?id=9151
     */
    public static Drawable getApkIcon(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                Log.e(LOG_TAG, e.toString());
            }
        }
        return null;
    }

    public static String getExtFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }

    public static String getNameFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(0, dotPosition);
        }
        return "";
    }

    public static String getPathFromFilepath(String filepath) {
        int pos = filepath.lastIndexOf('/');
        if (pos != -1) {
            return filepath.substring(0, pos);
        }
        return "";
    }

    public static String getNameFromFilepath(String filepath) {
        int pos = filepath.lastIndexOf('/');
        if (pos != -1) {
            return filepath.substring(pos + 1);
        }
        return "";
    }

    // does not include sd card folder
    private static String[] SysFileDirs = new String[] {
        "miren_browser/imagecaches"
    };



   

    public static class SDCardInfo {
        public long total;

        public long free;
    }

    public static SDCardInfo getSDCardInfo() {
        String sDcString = android.os.Environment.getExternalStorageState();

        if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            File pathFile = android.os.Environment.getExternalStorageDirectory();

            try {
                android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

                // 获取SDCard上BLOCK总数
                long nTotalBlocks = statfs.getBlockCount();

                // 获取SDCard上每个block的SIZE
                long nBlocSize = statfs.getBlockSize();

                // 获取可供程序使用的Block的数�?
                long nAvailaBlock = statfs.getAvailableBlocks();

                // 获取剩下的所有Block的数�?包括预留的一般程序无法使用的�?
                long nFreeBlock = statfs.getFreeBlocks();

                SDCardInfo info = new SDCardInfo();
                // 计算SDCard 总容量大小MB
                info.total = nTotalBlocks * nBlocSize;

                // 计算 SDCard 剩余大小MB
                info.free = nAvailaBlock * nBlocSize;

                return info;
            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, e.toString());
            }
        }

        return null;
    }
   
}
