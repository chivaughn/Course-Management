package userOperations;

import java.util.Scanner;
import authenticatedUsers.LoggedInAuthenticatedUser;
import systemUsers.StudentModel;
import server.Server;

public class ChangeNotificationPreferences implements Operations{
	
	public void execute(LoggedInAuthenticatedUser user)
	{
		if(Server.getInstance().getState().equals("OFF"))
		{
			System.out.println("Operation unavailable - server is stopped");
		}
		else
		{
			if(user.getAuthenticationToken().getUserType().equals("Student"))
			{
				StudentModel student = (StudentModel)user.getModel();
				System.out.println("Your current notification method is " + student.getNotificationType());
				System.out.println("Choose your new notification method. You can choose from the following options: \n"
						+ "\t\t 1) Email"
						+ "\t\t 2) Cellphone"
						+ "\t\t 3) Pigeon Post"
						+ "\nType the number of the method you want to select.");
				Scanner scan = Server.getInstance().getScanner();
				
				int method = Integer.parseInt(scan.nextLine());
				switch(method){
				case 1:
					student.setNotificationType(customDatatypes.NotificationTypes.EMAIL);
					break;
				case 2:
					student.setNotificationType(customDatatypes.NotificationTypes.CELLPHONE);
					break;
				case 3:
					student.setNotificationType(customDatatypes.NotificationTypes.PIGEON_POST);
					break;
				default:
					System.out.println("Your input is unreadable, please try again.");		
				}
				System.out.println("Your notification method is now " + student.getNotificationType());
			}
			else
			{
				System.out.println("This operation is unavailable to your user type.");
			}
		}
	}
}