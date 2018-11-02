package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import offerings.OfferingFactory;

public class Server {
	private static Server serverInstance = null;
	private static String state;
	private static Scanner input;
	
	private Server()
	{
		state = "OFF";
	}
	
	public static Server getInstance()
	{
		if(serverInstance == null)
		{
			serverInstance = new Server();
		}
		return serverInstance;
	}
	
	public String getState()
	{
		return state;
	}
	
	public void turnOn()
	{
		state = "ON";
		System.out.println("Building objects...");
		OfferingFactory factory = new OfferingFactory();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("note_1.txt")));
			factory.createCourseOffering(br);
			br.close();
			br = new BufferedReader(new FileReader(new File("note_2.txt")));
			factory.createCourseOffering(br);
			br.close();
		}
		catch(IOException ioe)
		{
			System.out.println("Error reading files");
		}
		System.out.println("The server is now started");
	}
	
	public void turnOff()
	{
		state = "OFF";
	}
	
	public void setScanner (Scanner scan)
	{
		input = scan;
	}
	
	public Scanner getScanner() {
		return input;
	}
}
