package com.zdn.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;


public class FileUtil {
	private static String mainPath="";
    public static final String UTF_8 = "UTF-8";


    public static final String TAG = FileUtil.class.getSimpleName();

    private static final int MAX_BUFFER_LENGTH = 4096;
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


    public static String getAppRelatePath( String path )
    {
        return makePath( mainPath , path);
    }

    public static String generateA_FileNameAccordingTime()
    {
        return (String.valueOf(System.currentTimeMillis()));
    }

    public static String generateA_FileNameAccordingTimeInSpecifyPath( String path  )
    {
        return makePath( makePath( mainPath , path)  , generateA_FileNameAccordingTime() );
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
     * 采用了新的办法获取APK图标，之前的失败是因为android中存在的BUG,通过
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

                // 获取可供程序使用的Block的数
                long nAvailaBlock = statfs.getAvailableBlocks();

                // 获取剩下的所有Block的数包括预留的一般程序无法使用的
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
    public static StringBuffer readText(String filePath, String decoder) {
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.canRead())
                return null;

            return readText(filePath, decoder, 0, (int) file.length());

        } catch (Exception e) {
            Log.e(TAG,
                    String.format("readText Error! message:%s", e.getMessage()));
            return null;
        }
    }

    /**
     * 根据编码读取文本
     *
     * @param filePath
     *            文件路径
     * @param decoder
     *            字符集名称 例：GBK UTF-8
     * @param offset
     *            偏移量
     * @param length
     *            长度
     * @return 读取的文本
     */
    public static StringBuffer readText(String filePath, String decoder,
                                        int offset, int length) {
        FileInputStream fileInputStream = null;
        BufferedInputStream buffReader = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            buffReader = new BufferedInputStream(fileInputStream);

            StringBuffer buffer = new StringBuffer();

            byte[] bytesBuf = new byte[length];
            buffReader.skip(offset);
            buffReader.read(bytesBuf, 0, length);

            return buffer.append(new String(bytesBuf, decoder));
        } catch (Exception e) {
            Log.e(TAG,
                    String.format("readText Error!\te.getMessage:%s",
                            e.getMessage()));
        } finally {
            closeCloseable(fileInputStream);
            closeCloseable(buffReader);
        }

        return null;
    }

    public static byte[] getBuffer(String path, int off, int length) {
        byte[] cover = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            cover = new byte[length];
            fis.skip(off);
            fis.read(cover, 0, length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeCloseable(fis);
        }

        return cover;
    }

    public static byte[] getBuffer(String path) {
        File file = null;
        FileInputStream fis = null;
        byte[] cover = null;
        try {
            file = new File(path);
            int length = (int) file.length();
            fis = new FileInputStream(file);
            cover = new byte[length];
            fis.read(cover, 0, length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeCloseable(fis);
        }
        return cover;
    }

    /**
     * 关闭stream or reader
     *
     * @param closeObj
     */
    public static void closeCloseable(Closeable closeObj) {
        try {
            if (null != closeObj)
                closeObj.close();
        } catch (IOException e) {
            Log.e("ReadFileUtils Error",
                    "Method:readFile, Action:closeReader\t" + e.getMessage());
        }
    }

    public static String getContent(AssetManager assets, String fileName) {
        String ret = "";
        InputStream stream = null;
        try {
            stream = assets.open(fileName);
            ret = getContent(stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭文件
            closeCloseable(stream);
        }
        return ret;
    }

    // 从流中, 获取数据
    private static String getContent(InputStream stream) {
        String ret = "";
        try {
            int len = stream.available();
            byte[] buffer = new byte[len];
            stream.read(buffer);

            ret = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 以 <b> UTF-8 </b>格式从文件开始处写入字符串,如果文件存在，则会被重写
     *
     * @param path
     *            文件路径
     * @param content
     *            待写入的字符串
     * @return 成功时返回true，失败返回false
     */
    public static boolean writeString(String path, String content) {
        String encoding = "UTF-8";
        File file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return writeString(path, content, encoding);
    }

    /**
     * 从文件开始处写入字符串,如果文件存在，则会被重写
     *
     * @param path
     *            文件路径
     * @param content
     *            待写入的字符串
     * @param encoding
     *            String转换为byte[]编码
     * @return 成功时返回true，失败返回false
     */
    public static boolean writeString(String path, String content,
                                      String encoding) {
        FileOutputStream fos = null;
        boolean result = false;
        try {
            fos = new FileOutputStream(path);
            byte[] cover = content.getBytes(encoding);
            fos.write(cover, 0, cover.length);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeCloseable(fos);
        }
        return result;
    }

    /**
     * 创建目标文件写入字节数组
     *
     * @param fileData
     *            文件字节数组
     * @return 写入成功，返回true，否则返回false
     */
    public static boolean writeBytes(String targetFilePath, byte[] fileData) {
        boolean result = false;
        File targetFile = new File(targetFilePath);
        File parentFile = targetFile.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (targetFile.exists()) {
            targetFile.delete();
        }
        ByteArrayInputStream fosfrom = null;
        FileOutputStream fosto = null;
        try {
            fosfrom = new ByteArrayInputStream(fileData);
            fosto = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = fosfrom.read(buffer)) != -1) {
                fosto.write(buffer, 0, length);
            }
            fosto.flush();
            result = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeCloseable(fosto);
            closeCloseable(fosfrom);
        }
        return result;
    }

    public static File byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }
    /**
     * 复制文件
     *
     * @param oldPath
     *            String 原文件路径
     * @param newPath
     *            String 复制后路径
     * @return
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean result = false;
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return result;
        }

        File parentFile = newFile.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            newFile.getParentFile().mkdirs();
        }
        if (newFile.exists()) {
            newFile.delete();
        }
        FileInputStream fosfrom = null;
        FileOutputStream fosto = null;
        try {
            fosfrom = new FileInputStream(oldFile);
            fosto = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = fosfrom.read(buffer)) != -1) {
                fosto.write(buffer, 0, length);
            }
            fosto.flush();
            result = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeCloseable(fosto);
            closeCloseable(fosfrom);
        }
        return result;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file
     *            要删除的根目录
     */
    public static void deleteAllFiles(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteAllFiles(f);
            }
        }
    }
}
