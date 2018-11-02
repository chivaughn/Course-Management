package userOperations;

import authenticatedUsers.LoggedInAuthenticatedUser;
import loggedInUserFactory.LoggedInUserFactory;
import server.Server;
import userListBuilder.UserListBuilder;
import authenticationServer.AuthenticationToken;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginOperation{
	
	private ArrayList<String[]> users;
	
	public LoginOperation()
	{
		UserListBuilder ulb = new UserListBuilder();
		users = ulb.buildList();
		Server.getInstance().setScanner(new Scanner(System.in));
	}
	
	public LoggedInAuthenticatedUser login()
	{
		String[] response = null;
		Scanner input = Server.getInstance().getScanner();
		do
		{
			System.out.print("User ID: ");
			String id = input.nextLine();
			System.out.print("Surname: ");
			String surname = input.nextLine();
			response = scanForUser(id, surname);
			if(response == null)
			{
				System.out.println("User not found.");
			}
		} while(response == null);
		
		LoggedInUserFactory factory = new LoggedInUserFactory();
		AuthenticationToken token = new AuthenticationToken();
		token.setUserType(response[3]);
		return factory.createAuthenticatedUser(response[0], response[1], response[2], token);
	}
	
	private String[] scanForUser(String id, String surname)
	{
		for(String[] user : users)
		{
			if(user[1].equals(surname) && user[2].equals(id))
			{
				return user;
			}
		}
		return null;
	}
}