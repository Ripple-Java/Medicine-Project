package com.rippletec.medicine.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * �����ҵ�MD5���ܹ�����
 * MD5���ܽ��ܼ��ַ�Աȹ�����
 * 
 * @author jacob_ye http://www.blog.csdn.net/zranye
 */
public class MD5Util {
	
	/**
	 * ������ת����16�����ַ�
	 * 
	 * @return String
	 * @author jacob
	 *
	 * */
	public static String byteToHexString(byte[] salt) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			String hex = Integer.toHexString(salt[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}
	/**
	 * ���md5֮���16�����ַ�
	 * 
	 * @param passwd
	 *            �û����������ַ�
	 * @return String md5���ܺ������ַ�
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getEncryptedPwd(String passwd)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// �õ�һ��������飬��Ϊ��
		byte[] pwd = null;
		SecureRandom sc = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		sc.nextBytes(salt);

		// ����ժҪ���󣬲����
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(salt);
		md.update(passwd.getBytes("UTF-8"));
		byte[] digest = md.digest();

		pwd = new byte[salt.length + digest.length];
		System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
		System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
		return byteToHexString(pwd);
	}

	/**
	 * ��16�����ַ�ת��������
	 * 
	 * @return byte[]
	 * @author jacob
	 * */
	public static byte[] hexStringToByte(String hex) {
		/*
		 * lenΪʲô��hex.length() / 2 ? ���ȣ�hex��һ���ַ��������������16���������char����
		 * ��2��16�������ֿ��Ա�ʾ1��byte
		 * ������Ҫ�����Щchar[]����ת����ʲô���byte[]�����ȿ���ȷ���ľ��ǳ���Ϊ���char[]��һ��
		 */
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
					.indexOf(hexChars[pos + 1]));
		}
		return result;
	}

	/**
	 * ������֤
	 * 
	 * @param passwd
	 *            �û���������
	 * @param dbPasswd
	 *            ��ݿⱣ�������
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean validPasswd(String passwd, String dbPasswd)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] pwIndb = hexStringToByte(dbPasswd);
		// ����salt
		byte[] salt = new byte[SALT_LENGTH];
		System.arraycopy(pwIndb, 0, salt, 0, SALT_LENGTH);
		// ������ϢժҪ����
		MessageDigest md = MessageDigest.getInstance("MD5");
		// ������ݴ�����ϢժҪ����
		md.update(salt);
		md.update(passwd.getBytes("UTF-8"));
		byte[] digest = md.digest();
		// ����һ�����������ݿ��еĿ�����ϢժҪ
		byte[] digestIndb = new byte[pwIndb.length - SALT_LENGTH];
		// �����ݿ��п����ժҪ
		System.arraycopy(pwIndb, SALT_LENGTH, digestIndb, 0, digestIndb.length);
		// �Ƚϸ�����������ɵ���ϢժҪ����ݿ��еĿ���ժҪ�Ƿ���ͬ
		if (Arrays.equals(digest, digestIndb)) {
			// ����ƥ����ͬ
			return true;
		} else {
			return false;
		}
	}

	private final static String HEX_NUMS_STR = "0123456789ABCDEF";

	private final static Integer SALT_LENGTH = 12;
}
