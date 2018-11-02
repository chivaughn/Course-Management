package userOperations;

import java.util.Scanner;
import authenticatedUsers.LoggedInAuthenticatedUser;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;
import server.Server;

public class CalculateFinal implements Operations{
	
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
				StudentModel student = new StudentModel();
		
				Scanner scan = Server.getInstance().getScanner();
				
				System.out.println("For which course do you want to calculate the final mark? Enter the course ID");
				String courseID = scan.nextLine();
				
				course = findCourse(instructor, courseID);
				if(course != null)
				{
					System.out.println("For which student do you want to calculate the final mark? Enter the student ID.");
					String studentID = scan.nextLine();
					student = findStudent(course, studentID);
					if(student != null)
					{
						System.out.println(((CourseOffering)course).calculateFinalGrade(student.getID()));
					}
					else
					{
						System.out.println("Invalid student");
					}
				}
				else
				{
					System.out.println("You are not a valid instructor for this course");
				}
			}
			else
			{
				System.out.println("This operation is unavailable to your user type.");
			}
		}
	}
	
	private ICourseOffering findCourse(InstructorModel instructor, String courseID) {
		for(ICourseOffering c : instructor.getIsTutorOf()){
			if(c.getCourseID().equals(courseID)){
				return c;
			}
		}
		return null;
	}
	
	private StudentModel findStudent(ICourseOffering course, String studentID) {
		for(StudentModel s : course.getStudentsEnrolled()){
			if(s.getID().equals(studentID)){
				return s;
			}
		}
		return null;
	}
}
