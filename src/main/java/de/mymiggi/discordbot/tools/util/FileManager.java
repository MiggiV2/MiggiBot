package de.mymiggi.discordbot.tools.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager
{
	private String root = System.getProperty("user.dir") + "/Commands/";
	private String defaltRoot = System.getProperty("user.dir") + "/";
	private Logger logger = LoggerFactory.getLogger("FileManager");

	public String getDefaltRoot()
	{
		return defaltRoot;
	}

	public String read(String fileName)
	{
		return read(fileName, null);
	}

	public String read(String fileName, String path)
	{
		String json = "";
		String absolutePath;

		try
		{
			if (path == null)
			{
				absolutePath = defaltRoot + "/" + fileName;
			}
			else
			{
				absolutePath = defaltRoot + "/" + path + "/" + fileName;
			}
			File myObj = new File(absolutePath);
			Scanner myReader = new Scanner(myObj);
			// myReader.use
			while (myReader.hasNextLine())
			{
				String data = myReader.nextLine();
				json += data;
			}
			myReader.close();
		}
		catch (FileNotFoundException e)
		{
			logger.error("An error occurred!");
			e.printStackTrace();
		}

		return json;
	}

	public String read(File file)
	{
		String json = "";

		try
		{
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine())
			{
				String data = myReader.nextLine();
				json += data;
			}
			myReader.close();
		}
		catch (FileNotFoundException e)
		{
			logger.error("An error occurred!");
			e.printStackTrace();
		}

		return json;
	}

	public String[] getFiles(String dir)
	{
		File file = new File(defaltRoot + dir);

		if (!file.exists())
		{
			file.mkdir();
		}
		return file.list();
	}

	public void save(File file, String json)
	{
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdir();
		}
		try
		{
			FileWriter myWriter = new FileWriter(file.getAbsolutePath());
			myWriter.write(json);
			myWriter.close();
			logger.info("Successfully wrote to the file.");
		}
		catch (IOException e)
		{
			logger.error("An error occurred!");
			e.printStackTrace();
		}
	}

	public void save(String fileName, String json)
	{
		try
		{
			FileWriter myWriter = new FileWriter(root + fileName + ".json");
			myWriter.write(json);
			myWriter.close();
			logger.info("Successfully wrote to the file.");
		}
		catch (IOException e)
		{
			logger.error("An error occurred!");
			e.printStackTrace();
		}
	}

	public void save(String fileName, String json, String dir)
	{
		File testFile = new File(defaltRoot + "/" + dir);

		if (!testFile.isDirectory())
		{
			testFile.mkdir();
		}
		try
		{
			FileWriter myWriter = new FileWriter(defaltRoot + "/" + dir + "/" + fileName + ".json");
			myWriter.write(json);
			myWriter.close();
			logger.info("Successfully wrote to the file.");
		}
		catch (IOException e)
		{
			logger.error("An error occurred!");
			e.printStackTrace();
		}
	}

	public void copy(File input, String output)
	{
		String inputData = read(input);
		File outputFile = new File(output);

		save(outputFile, inputData);
	}

	public List<String> readUTF8(String fileName) throws IOException
	{
		Path path = Paths.get(fileName);
		List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
		return list;
	}

	public void saveUTF8(String fileName, String dataStr) throws IOException
	{
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
		out.write(dataStr);
		out.close();
	}
}
