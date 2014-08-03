package xymz.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class DigestUtils {

	public static String md5Hex(String value) {

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] md5ValueByte = messageDigest.digest(value.getBytes());

			BigInteger bigInteger = new BigInteger(1, md5ValueByte);
			return bigInteger.toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(md5Hex("1234"));
	}
}
