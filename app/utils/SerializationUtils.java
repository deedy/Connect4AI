package utils;

import java.io.*;
import java.util.*;

import biz.source_code.base64Coder.*;

public class SerializationUtils {

    public static String encode(Serializable o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( o );
            oos.close();
            return new String( Base64Coder.encode( baos.toByteArray() ) );
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Object decode(String s) {
        try {
            byte [] data = Base64Coder.decode( s );
            ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
            Object o  = ois.readObject();
            ois.close();
            return o;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
