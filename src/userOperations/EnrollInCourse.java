package userOperations;

import java.util.List;
import java.util.Scanner;
import authenticatedUsers.LoggedInAuthenticatedUser;
import offerings.ICourseOffering;
import systemUsers.StudentModel;
import server.Server;

public class EnrollInCourse implements Operations{

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
				System.out.println("What is the course ID of the course where you want to enroll for? ");
				Scanner scan = Server.getInstance().getScanner();
				String course = scan.nextLine();
				ICourseOffering c = findCourse(student, course);

				if(c == null)
				{
					System.out.println("You are not eligible to enroll in this course");
				}
				else
				{
					//add course to student list
					List<ICourseOffering> currentCourses = student.getCoursesEnrolled();
					currentCourses.add(c);
					student.setCoursesEnrolled(currentCourses);

					//add student to course list
					List<StudentModel> currentStudents = c.getStudentsEnrolled();
					currentStudents.add(student);
					c.setStudentsEnrolled(currentStudents);
					System.out.println("You are successfully enrolled in course " +c.getCourseID() + " " + c.getCourseName());
				}
			}
			else
			{
				System.out.println("The operation 'Enroll in course' is only available for students.");
			}
		}
	}
	
	private ICourseOffering findCourse(StudentModel student, String course) {
		for(ICourseOffering a: student.getCoursesAllowed()){
			if(a.getCourseID().equals(course)){
				return a;
			}
		}
		return null;
	}
}
