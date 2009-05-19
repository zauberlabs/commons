/**
 *  Copyright (c) 2009 Clarin Global S.A.  -- All rights reserved
 */
// CHECKSTYLE:ALL:OFF
package org.springframework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Miscellaneous method for calculating MD5 hashes.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
public abstract class Md5HashUtils {

	private static final char[] HEX_CHARS =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * Calculate the MD5 hash of the given bytes.
	 * @param bytes the bytes to calculate the hash over
	 * @return the hash
	 */
	public static byte[] getHash(byte[] bytes) {
		try {
			// MessageDigest is not thread-safe
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			return messageDigest.digest(bytes);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Could not find MD5 MessageDigest instance", ex);
		}
	}

	private static char[] getHashChars(byte[] bytes) {
		byte[] hash = getHash(bytes);
		char chars[] = new char[32];
		for (int i = 0; i < chars.length; i = i + 2) {
			byte b = hash[i / 2];
			chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
			chars[i + 1] = HEX_CHARS[b & 0xf];
		}
		return chars;
	}

	/**
	 * Return a hex string representation of the MD5 hash of the given bytes.
	 * @param bytes the bytes to calculate the hash over
	 * @return a hexadecimal hash string
	 */
	public static String getHashString(byte[] bytes) {
		return new String(getHashChars(bytes));
	}

	/**
	 * Append a hex string representation of the MD5 hash of the given bytes to the given {@link StringBuilder}.
	 * @param bytes the bytes to calculate the hash over
	 * @param builder the string builder to append the hash to
	 * @return the given string builder
	 */
	public static StringBuilder appendHashString(byte[] bytes, StringBuilder builder) {
		builder.append(getHashChars(bytes));
		return builder;
	}

}