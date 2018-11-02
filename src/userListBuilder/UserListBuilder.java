package userListBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserListBuilder {

	public ArrayList<String[]> buildList()
	{		
		ArrayList<String[]> users = new ArrayList<String[]>();
		try {
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(new File("Login.txt")));
			while((line = br.readLine()) != null)
			{
				users.add(line.split("\t"));
			}
			br.close();
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		return users;
	}
}
