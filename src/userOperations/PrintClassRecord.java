package userOperations;

import java.util.Scanner;
import authenticatedUsers.LoggedInAuthenticatedUser;
import customDatatypes.Marks;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;
import server.Server;

public class PrintClassRecord implements Operations{

	public void execute(LoggedInAuthenticatedUser user)
	{
		if(Server.getInstance().getState().equals("OFF"))
		{
			System.out.println("Operation unavailable - server is stopped");
		}
		else
		{
			if(user.getAuthenticationToken().getUserType().equals("Instructor"))
			{
				InstructorModel instructor = (InstructorModel)user.getModel();
				ICourseOffering course = new CourseOffering();
				
				Scanner scan = Server.getInstance().getScanner();
				
				System.out.println("For which course do you want to print the class record? Enter the course ID");
				String courseID = scan.nextLine();
				course = findCourse(instructor, course, courseID);
				if(course != null){
					System.out.println("Class record for course "+ course.getCourseID()+" "+ course.getCourseName() + ":");
					for(StudentModel sm : course.getStudentsEnrolled()){
						System.out.println(sm.getID()+ "\t" + sm.getName() +" "+ sm.getSurname() + "\t"
								+sm.getEvaluationEntities().get(course));
						Marks m = sm.getPerCourseMarks().get(course);
						System.out.println(m.toString()+"\n");
					}
				}
				else{
					System.out.println("Invalid course");
				}
				
			}
			else
			{
				System.out.println("This operation is unavailable to your user type.");
			}
		}
	}

	private ICourseOffering findCourse(InstructorModel instructor, ICourseOffering course, String courseID) {
		for(ICourseOffering c : instructor.getIsTutorOf()){
			if(c.getCourseID().equals(courseID)){
				return c;
			}
		}
		return null;
	}
}
