package de.mymiggi.r6.stats.wrapper.config;

public class WrapperConfig
{
	/*
	 * USEING EMAIL + PW
	 * OR CREDENTIAL
	 */
	private String eMail;
	private String password;
	private String credential;

	public String getEMail()
	{
		return eMail;
	}

	public void setEMail(String eMail)
	{
		this.eMail = eMail;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getCredential()
	{
		return credential;
	}

	public void setCredential(String credential)
	{
		this.credential = credential;
	}
}
