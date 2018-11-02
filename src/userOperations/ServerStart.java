package userOperations;

import authenticatedUsers.LoggedInAuthenticatedUser;
import server.Server;

public class ServerStart implements Operations{

	public void execute(LoggedInAuthenticatedUser user)
	{
		Server serv = Server.getInstance();
		
		if(serv.getState().equals("OFF"))
		{
			if(user.getAuthenticationToken().getUserType().equals("Admin"))
			{
				serv.turnOn();
			}
			else
			{
				System.out.println("This operation is unavailable to your user type.");
			}
		}
		else
		{
			System.out.println("The server is already started");
		}
	}
}