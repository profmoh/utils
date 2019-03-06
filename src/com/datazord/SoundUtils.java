package com.datazord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;

public class SoundUtils {

	/**
	 * Accepts any String & converts in into Camel Case String
	 * 
	 * @param file
	 * @return
	 * @throws EncoderException 
	 * @throws InputFormatException 
	 * @throws IllegalArgumentException 
	 */
	public static File convertToWav(File file, String path, String fileName) throws IllegalArgumentException, InputFormatException, EncoderException {

		File target = new File(path + fileName);

		// Audio Attributes
		AudioAttributes audio = new AudioAttributes();
		// audio.setCodec("libmp3lame");
		audio.setBitRate(16000);
		audio.setChannels(1);
		audio.setSamplingRate(16000);

		// Encoding attributes
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("wav");
		attrs.setAudioAttributes(audio);

		Encoder encoder = new Encoder();
		encoder.encode(new MultimediaObject(file), target, attrs);

		return target;
	}

	public static File convertMultiPartToFile(MultipartFile file, String outputPath) throws IOException {
	    File convFile = new File(outputPath + file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}

	public static byte[] readContentIntoByteArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bFile;
	}
}
