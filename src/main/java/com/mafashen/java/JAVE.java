package com.mafashen.java;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.FFMPEGLocator;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoSize;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class JAVE {
	public static void main(String[] args) throws EncoderException {
		//encode();
//		List list = new ArrayList();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//
//		List list1 = new ArrayList(null);
//		list1.remove(null);
//		System.out.println(list1);
		String str = "%E6%80%A7%E8%83%BD%E6%9D%83%E5%A8%81%E6%8C%87%E5%8D%97%E5%A5%A5%E5%85%8B%E6%96%AF%28";
		try {
			byte[] bytes = str.getBytes("utf8");
			System.out.println(new String(bytes , "gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void encode() throws EncoderException {
		File source = new File("/Users/mafashen/Downloads/test.flv");
		System.out.println(source.exists() );
		File target = new File("/Users/mafashen/Downloads/jave.3gp");
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libfaac");
		audio.setBitRate(new Integer(128000));
		audio.setSamplingRate(new Integer(44100));
		audio.setChannels(new Integer(2));
		VideoAttributes video = new VideoAttributes();
		video.setCodec("mpeg4");
		video.setBitRate(new Integer(160000));
		video.setFrameRate(new Integer(15));
		video.setSize(new VideoSize(176, 144));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("3gp");
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
		Encoder encoder = new Encoder(new MyFFMPEGLocator());
		encoder.encode(source, target, attrs);
		for (String s : encoder.getVideoDecoders()) {

			System.out.println(s);
		}
		for (String s : encoder.getVideoEncoders()) {
			System.out.println(s);
		}
	}

	static class MyFFMPEGLocator extends FFMPEGLocator{

		protected String getFFMPEGExecutablePath() {
			return "/usr/local/Cellar/ffmpeg/3.4/bin/ffmpeg";
		}
	}
}
