package main;

import userOperations.*;
import authenticatedUsers.LoggedInAuthenticatedUser;

public class Main
{
	public static void main(String args[])
	{	
		Operations op;
		LoggedInAuthenticatedUser Admin, Instructor, Student;
		
		LoginOperation lo = new LoginOperation();
		
		//Start Server
		Admin = lo.login();
		op = new ServerStart();
		op.execute(Admin);

		/*
		//Enroll in course
		Student = lo.login();
		op = new EnrollInCourse();
		op.execute(Student);
		
		//Print record
		Student = lo.login();
		op = new PrintRecord();
		op.execute(Student);
		
		//Change notification type
		Student = lo.login();
		op = new ChangeNotificationPreferences();
		op.execute(Student);
		
		//Add mark
		Instructor = lo.login();
		op = new AddMark();
		op.execute(Instructor);
		
		//Modify mark
		Instructor = lo.login();
		op = new ModifyMark();
		op.execute(Instructor);
		*/
		
		//Stop Server
		Admin = lo.login();
		op = new ServerStop();
		op.execute(Admin);
		
		//Calculate final mark of 1 student
		Instructor = lo.login();
		op = new CalculateFinal();
		op.execute(Instructor);
	}
}