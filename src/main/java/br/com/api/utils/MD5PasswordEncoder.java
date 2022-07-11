package br.com.api.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MD5PasswordEncoder implements PasswordEncoder {

	private static final String ALGORITHM_MD5 = "MD5";

	@Override
	public String encode(CharSequence password) {
		return md5DigestAsHex(password.toString());
	}

	@Override
	public boolean matches(CharSequence password, String encodedPassword) {
		return encode(password.toString()).equals(encodedPassword); // QUANDO A SENHA VEM SEM ESTAR CRIPTOGRAFADA
//		return password.toString().equals(encodedPassword); // SENHA JÁ VEM CRIPTOGRAFADA
	}

	private String md5DigestAsHex(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_MD5);
			byte[] hashInBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hashInBytes);
		} catch (Exception e) {

		}
		return password;
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString().toUpperCase();
	}
}
