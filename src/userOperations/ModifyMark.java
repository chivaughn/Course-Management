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

public class ModifyMark implements Operations {

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
				String examOrAssignment = null;
				Double mark = 0.0;
		
				Scanner scan = Server.getInstance().getScanner();
				
				System.out.println("For which course do you want to modify the mark? Enter the course ID");
				String courseID = scan.nextLine();
				course = findCourse(instructor, courseID);
				if(course != null)
				{
					System.out.println("For which student do you want to modify the mark? Enter the student ID.");
					String studentID = scan.nextLine();
					
					student = findStudent(course, studentID);
					if(student != null)
					{
						System.out.println("For which evaluation strategy of this course do you want to modify the mark?");
						examOrAssignment = scan.nextLine();
				
						System.out.println("Enter the new mark for course "+ course.getCourseName()+" ,student " +student.getName() +" " + student.getSurname() + ", evaluation strategy "+examOrAssignment + ".");
						mark = Double.parseDouble(scan.nextLine());
				
						Map<ICourseOffering, Marks> currentMarks = student.getPerCourseMarks();
						Marks m = currentMarks.get(course);
						if(m == null)
						{
							System.out.println("No marks exist for this student for this course");
						}
						else
						{
							if(updateMark(m, examOrAssignment, mark))
							{
								currentMarks.put(course,m);
								student.setPerCourseMarks(currentMarks);
							}
							else
							{
								System.out.println("Evaluation strategy not found");
							}
							
						}
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
	
	private boolean updateMark(Marks m, String examOrAssignment, Double mark)
	{
		m.initializeIterator();
		while(m.hasNext()){
			m.next();
			if(m.getCurrentKey().equals(examOrAssignment)){
				m.setCurrentValue(mark);
				return true;
			}
		}
		return false;
	}
}