package com.slieer.commons.Codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Base64Test {
	@Test
	public void test() {
		// 加密
		String str = "abc"; // abc为要加密的字符串
		byte[] b = Base64.encodeBase64(str.getBytes(), true);
		System.out.println(new String(b));

		// 解密
		str = "YWJj"; // YWJj为要解密的字符串
		b = Base64.decodeBase64(str.getBytes());
		System.out.println(new String(b));
	}

	@Test
	public void testMD5() {
		String str = "abc";
		String md5code = DigestUtils.md5Hex(str);

		System.out.println(md5code);
	}

	@Test
	public void testSh1() {
		String str = "abc";
		String sha1 = DigestUtils.sha1Hex(str);
		System.out.println(sha1);
	}

	@Test
	public void encodeHexTest() {
		String str = "啊啊";
		try {
			str = Hex.encodeHexString(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println("Hex 编码后：" + str);
	}

	@Test
	public void decodeHexTest() {
		String str = "e5958ae5958a";
		Hex hex = new Hex();
		try {
			str = new String((byte[]) hex.decode(str));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		System.out.println("Hex 编码后：" + str);
	}
}
