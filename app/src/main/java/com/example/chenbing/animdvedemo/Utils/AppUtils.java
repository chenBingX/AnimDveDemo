package com.example.chenbing.animdvedemo.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Base64;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/24
 * Notes:
 */
public class AppUtils {

  public static final String ObjectToBase64String(Object object) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(object);
    String base64String = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    if (oos != null){
      oos.close();
    }
    if (baos != null){
      baos.close();
    }
    return base64String;
  }

  public static final <T> T Base64StringToObject(String base64String, Class<T> clazz)
      throws IOException, ClassNotFoundException {
    byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(bais);
    T t = (T) ois.readObject();
    if (ois != null) {
      ois.close();
    }
    if (bais != null) {
      bais.close();
    }
    return t;
  }

}
