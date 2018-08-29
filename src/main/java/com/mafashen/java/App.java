package com.mafashen.java;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<String> commend = new ArrayList<String>();
        commend.add("ffmpeg -i /Users/mafashen/Downloads/VID_20171106_105416.mp4 -b:v 640k /Users/mafashen/Downloads/new1.mp4  ");

        try {
            //调用线程命令启动转码
//            ProcessBuilder builder = new ProcessBuilder();
//            builder.command(commend);
//            builder.start();
            //return "【存放转码后视频的路径，记住一定是.avi后缀的文件名】";
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
        // -r 10 -b:a 32k 采用H.264视频压缩算法和AAC音频压缩算法，视频帧率10fps，音频码率32k
        String command = "ffmpeg -i /Users/mafashen/Downloads/VID_20171108_175941.mp4  -r 10 -b:a 32k /Users/mafashen/Downloads/new720k.mp4";
        String screenshot = "ffmpeg -ss 00:00:06 -i /Users/mafashen/Downloads/VID_20171108_175941.mp4 -f image2 -y /Users/mafashen/Downloads/screenshot.jpg";
        runCmd(command);
    }

    public static void runCmd(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<ERROR>");
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
            System.out.println("</ERROR>");
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            System.out.println(t);
            t.printStackTrace();
        }

    }
}
