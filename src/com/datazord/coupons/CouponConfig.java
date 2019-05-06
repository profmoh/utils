package com.datazord.coupons;

import java.util.Arrays;

public class CouponConfig {

	public final static char PATTERN_PLACEHOLDER = '#';

	public static class Charset {
		public static final String NUMBERS = "0123456789";

		public static final String ALPHABETIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String ALPHABETIC_SMALL = "abcdefghijklmnopqrstuvwxyz";
		public static final String ALPHABETIC_CAPITAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		public static final String ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String ALPHANUMERIC_SMALL = "0123456789abcdefghijklmnopqrstuvwxyz";
		public static final String ALPHANUMERIC_CAPITAL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}

	private final int length;
	private final String charset;
	private final String prefix;
	private final String postfix;
	private final String pattern;

	public CouponConfig(Integer length, String charset, String prefix, String postfix, String pattern) {
		if (length == null)
			length = 8;

		if (charset == null)
			charset = Charset.ALPHANUMERIC_CAPITAL;

		if (pattern == null) {
			char[] chars = new char[length];

			Arrays.fill(chars, PATTERN_PLACEHOLDER);

			pattern = new String(chars);
		}

		this.length = length;
		this.charset = charset;
		this.prefix = prefix;
		this.postfix = postfix;
		this.pattern = pattern;
	}

	public static CouponConfig length(int length) {
		return new CouponConfig(length, null, null, null, null);
	}

	public static CouponConfig pattern(String pattern) {
		return new CouponConfig(null, null, null, null, pattern);
	}

	public CouponConfig withCharset(String charset) {
		return new CouponConfig(length, charset, prefix, postfix, pattern);
	}

	public CouponConfig withPrefix(String prefix) {
		return new CouponConfig(length, charset, prefix, postfix, pattern);
	}

	public CouponConfig withPostfix(String postfix) {
		return new CouponConfig(length, charset, prefix, postfix, pattern);
	}

	public int getLength() {
		return length;
	}

	public String getCharset() {
		return charset;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getPostfix() {
		return postfix;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return "CodeConfig ["
				+ "length = " + length + ", "
				+ "charset = " + charset + ", "
				+ "prefix = "  + prefix  + ", "
				+ "postfix = " + postfix + ", "
				+ "pattern = " + pattern
				+ "]";
	}
}
