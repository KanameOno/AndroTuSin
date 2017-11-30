package com.example.ohno.andro_tu_sinki;

/**
 * Created by OHNO on 2017/09/08.
 */


// Sample.java
import java.io.PrintWriter;
        import java.io.StringWriter;

public class Sample
{
    public static void main( String[] args ){
        try{
            connect();
        } catch (Exception e) {
            System.out.println("例外処理でしたね");
            e.printStackTrace();
        }
    }
    private static void connect() throws Exception{
        throw new Exception();
    }
}
