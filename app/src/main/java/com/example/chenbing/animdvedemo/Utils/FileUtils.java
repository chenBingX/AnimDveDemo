package com.example.chenbing.animdvedemo.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/24
 * Notes:
 */
public class FileUtils {
  private static final String APP_FILE_DIR =
      Environment.getExternalStorageDirectory().getAbsolutePath() + "/ice/";
  private static final String APP_CACHE_DIR = APP_FILE_DIR + "cache/";
  private static final String APP_PHOTO_DIR = APP_FILE_DIR + "photo/";
  private static final String APP_OBJECT_DIR = APP_CACHE_DIR + "object/";

  public static final String getAppFileDir() {
    File file = new File(APP_FILE_DIR);
    if (!file.exists()) {
      file.mkdirs();
    }
    return APP_FILE_DIR;
  }

  public static final String getAppCacheDir() {
    File file = new File(APP_CACHE_DIR);
    if (!file.exists()) {
      file.mkdirs();
    }
    return APP_CACHE_DIR;
  }

  public static final String getAppPhotoDir() {
    File file = new File(APP_PHOTO_DIR);
    if (!file.exists()) {
      file.mkdirs();
    }
    return APP_PHOTO_DIR;
  }

  public static final String getAppObjectDir() {
    File file = new File(APP_OBJECT_DIR);
    if (!file.exists()) {
      file.mkdirs();
    }
    return APP_OBJECT_DIR;
  }

  public static final void saveObject(String path, Object object) {
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    File file = new File(path);
    try {
      LogUtils.e(file.getAbsolutePath());
      fos = new FileOutputStream(file);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(object);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
        if (fos != null) {
          fos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static final <T> T restoreObject(String path, Class<T> clazz) throws FileNotFoundException {
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    T t = null;
    File file = new File(path);
    if (!file.exists()) {
      throw new FileNotFoundException("请求文件不存在，请检查路径是否正确。");
    }
    try {
      LogUtils.e(file.getAbsolutePath());
      fis = new FileInputStream(file);
      ois = new ObjectInputStream(fis);
      t = (T) ois.readObject();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (ois != null) {
          ois.close();
        }
        if (fis != null) {
          fis.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return t;
    }
  }

  public static final String scanDirectory(String path) {
    File file = new File(path);
    File[] files = file.listFiles();
    StringBuffer filesName = new StringBuffer();
    String format = "index:%d\nfileName:%s\nfileSize:%s\n最后修改时间:%s\n--------------\n";
    int count = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    for (File tempFile : files) {
      count++;
      if (tempFile.isFile()) {
        long fileLength = tempFile.length();
        String fileSize;
        if (fileLength >= 1024) {
          fileSize = String.valueOf(fileLength / 1024 + "K");
        } else
          fileSize = String.valueOf(fileLength + "B");

        long lastModify = tempFile.lastModified();
        String lastModifyString = dateFormat.format(new Date(lastModify));

        filesName.append(String.format(format, count, tempFile.getName(), fileSize,
            lastModifyString));
      }
    }
    filesName.append(String.format("共计文件数:%d\n", count));
    return filesName.toString();
  }
}
