package userOperations;

import authenticatedUsers.LoggedInAuthenticatedUser;
import server.Server;

public class ServerStop implements Operations{

	public void execute(LoggedInAuthenticatedUser user)
	{
		Server serv = Server.getInstance();
		
		if(serv.getState().equals("ON"))
		{
			if(user.getAuthenticationToken().getUserType().equals("Admin"))
			{
				serv.turnOff();
				System.out.println("The server is stopped");
			}
			else
			{
				System.out.println("This operation is unavailable to your user type.");
			}
		}
		else
		{
			System.out.println("The server is already stopped");
		}
	}
}