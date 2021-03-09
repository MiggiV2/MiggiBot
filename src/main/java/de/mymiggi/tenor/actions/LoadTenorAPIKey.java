package de.mymiggi.tenor.actions;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoadTenorAPIKey
{
	public String run() throws IOException
	{
		File _file = new File("tenor.api_key");
		if (!_file.exists())
		{
			_file.createNewFile();
			System.err.println("Please first enter your school name from the WebUntis URL!");
			System.exit(0);
		}
		Scanner myReader = new Scanner(_file);
		String content = "";
		while (myReader.hasNextLine())
		{
			String data = myReader.nextLine();
			content += data;
		}
		myReader.close();
		return content;
	}
}
