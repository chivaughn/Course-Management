package userOperations;

import java.util.Map;
import java.util.Scanner;
import authenticatedUsers.LoggedInAuthenticatedUser;
import customDatatypes.Marks;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;
import server.Server;

public class AddMark implements Operations{

	public void execute(LoggedInAuthenticatedUser user) {
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
				String examOrAssignment = null;
				Double mark = 0.0;
		
				Scanner scan = Server.getInstance().getScanner();
				
				System.out.println("For which course do you want to add the mark? Enter the course ID");
				String courseID = scan.nextLine();
				course = findCourse(instructor, courseID);
				if(course != null)
				{
					System.out.println("For which student do you want to add the mark? Enter the student ID.");
					String studentID = scan.nextLine();
					student = findStudent(course, studentID);
					if(student != null)
					{
						System.out.println("For which evaluation strategy of this course do you want to add a mark?");
						examOrAssignment = scan.nextLine();
				
						System.out.println("What is the mark?");
						mark = Double.parseDouble(scan.nextLine());
						
						Map<ICourseOffering, Marks> currentMarks = student.getPerCourseMarks();
						if(currentMarks.get(course) == null)
						{
							Marks m = new Marks();
							m.addToEvalStrategy(examOrAssignment, mark);
							currentMarks.put(course, m);
						}
						else
						{
							currentMarks.get(course).addToEvalStrategy(examOrAssignment, mark);
						}
						student.setPerCourseMarks(currentMarks);
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

	private StudentModel findStudent(ICourseOffering course, String studentID) {
		for(StudentModel s : course.getStudentsEnrolled()){
			if(s.getID().equals(studentID)){
				return s;
			}
		}
		return null;
	}

	private ICourseOffering findCourse(InstructorModel instructor, String courseID) {
		for(ICourseOffering c : instructor.getIsTutorOf()){
			if(c.getCourseID().equals(courseID)){
				return c;
			}
		}
		return null;
	}
}
