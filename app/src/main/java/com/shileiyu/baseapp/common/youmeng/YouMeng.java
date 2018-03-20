package com.shileiyu.baseapp.common.youmeng;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public class YouMeng {
    public static void main(String[] args) {
        File file = new File("d:/ym.txt");
        File target = new File("d:/target.txt");
        BufferedReader reader = null;
        FileOutputStream fos = null;
        if (file.exists()) {
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                fos = new FileOutputStream(target);
                String temp;
                while ((temp = reader.readLine()) != null) {
                    temp = temp.trim();
                    String[] split = temp.split("\\s+");
                    System.out.println(Arrays.toString(split));
                    if (split.length == 2) {
                        String line = split[0] + " = " + "\"" + split[0] + "\"" + " ,//" + split[1] + "\n";
                        fos.write(line.getBytes(Charset.forName("UTF-8")));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
