package de.mymiggi.discordbot.tools.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA_512
{
	public static String hash(String toHash)
	{
		String generatedPassword = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bytes = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
}
