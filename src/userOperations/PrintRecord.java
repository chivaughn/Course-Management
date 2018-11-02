package userOperations;

import java.util.Scanner;
import authenticatedUsers.LoggedInAuthenticatedUser;
import offerings.ICourseOffering;
import systemUsers.StudentModel;
import server.Server;

public class PrintRecord implements Operations{

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
				System.out.println("What is the course ID of the course where you want to print your records for?");
				Scanner scan = Server.getInstance().getScanner();
				String courseID = scan.nextLine();
				ICourseOffering course = enrolledInCourse(student, courseID);
				if(course != null)
				{
					if(student.getPerCourseMarks().get(course) != null)
					{
						System.out.println(student.getPerCourseMarks().get(course));
					}
					else
					{
						System.out.println("No marks have been entered for this course");
					}
				}
				else
				{
					System.out.println("You are not enrolled in that course");
				}
			}
			else
			{
				System.out.println("This operation is unavailable to your user type.");
			}
		}
	}
	
	private ICourseOffering enrolledInCourse(StudentModel student, String courseID) {
		for(ICourseOffering c: student.getCoursesEnrolled()){
			if(c.getCourseID().equals(courseID)){
				return c;
			}
		}
		return null;
	}
}
