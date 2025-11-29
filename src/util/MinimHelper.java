package util;

import java.io.FileInputStream;
import java.io.InputStream;

/*
 * MinimHelper class is a simple utility to help load files (likely audio or other assets) from 
 *  assets folder by returning paths or creating an InputStream to read them.
 */
public class MinimHelper {

    public String sketchPath( String fileName ) {
        return "assets/"+fileName;
    }

    public InputStream createInput(String fileName) {
        InputStream is = null;
        try{
            is = new FileInputStream(sketchPath(fileName));
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return is;
    }
}