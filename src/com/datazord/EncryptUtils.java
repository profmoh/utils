package com.datazord;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EncryptUtils {
	public static final String DEFAULT_ENCODING = "UTF-8";

	private static Encoder enc = Base64.getEncoder();
	private static Decoder dec = Base64.getDecoder();

//	public static void main(String[] args) {
//		String txt = "mohamed";
//		String key = "keykey";
//
//		System.out.println(txt);
//		System.out.println(key);
//
//		txt = xorMessage(txt, key);
//
//		System.err.println(txt);
//
//		String encoded = base64encode(txt);
//
//		System.out.println(encoded);
//
//		String decoded = base64decode(encoded);
//
//		System.out.println(decoded);
//
//		txt = xorMessage(decoded, key);
//
//		System.err.println(txt);
//	}

	private static String base64encode(String text) {
		try {
			return new String(enc.encode(text.getBytes(DEFAULT_ENCODING)));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	private static String base64decode(String text) {
		try {
			return new String(dec.decode(text), DEFAULT_ENCODING);
		} catch (IOException e) {
			return null;
		}
	}

	public static String encript(String txt, String key) {
		txt = xorMessage(txt, key);

		return base64encode(txt);
	}

	public static String decode(String txt, String key) {
		txt = base64decode(txt);

		return xorMessage(txt, key);
	}

	private static String xorMessage(String message, String key) {
		try {
			if (message == null || key == null)
				return null;

			char[] keys = key.toCharArray();
			char[] mesg = message.toCharArray();

			int ml = mesg.length;
			int kl = keys.length;
			char[] newmsg = new char[ml];

			for (int i = 0; i < ml; i++) {
				newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
			} // for i

			return new String(newmsg);
		} catch (Exception e) {
			return null;
		}
	}
}
