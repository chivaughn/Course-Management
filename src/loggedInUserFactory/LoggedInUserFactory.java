package loggedInUserFactory;

import authenticatedUsers.LoggedInAdmin;
import authenticatedUsers.LoggedInAuthenticatedUser;
import authenticatedUsers.LoggedInInstructor;
import authenticatedUsers.LoggedInStudent;
import authenticationServer.AuthenticationToken;
import customDatatypes.NotificationTypes;
import registrar.ModelRegister;
import systemUsers.SystemUserModel;
import systemUsers.StudentModel;

public class LoggedInUserFactory {

	ModelRegister register;
	
	public LoggedInUserFactory(){
		register = ModelRegister.getInstance();
	}
	
	public LoggedInAuthenticatedUser createAuthenticatedUser(String name, String surname, String id,
			AuthenticationToken authenticationToken){
		SystemUserModel model = register.getRegisteredUser(id);
		switch(authenticationToken.getUserType()){
		case "Admin":
			return createLoggedInAdmin(name, surname, id, authenticationToken, model);
		case "Student":
			return createLoggedInStudent(name, surname, id, authenticationToken, model);
		case "Instructor":
			return createLoggedInInstructor(name, surname, id, authenticationToken, model);
		default:
			return null;
		}
	}
	
	private LoggedInStudent createLoggedInStudent(String name, String surname, String id, 
			AuthenticationToken authenticationToken, SystemUserModel model){
		LoggedInStudent student = new LoggedInStudent();
		student.setName(name);
		student.setSurname(surname);
		student.setID(id);
		student.setAuthenticationToken(authenticationToken);
		StudentModel stu = ((StudentModel)model);
		if(stu.getNotificationType() == null)
		{
			stu.setNotificationType(NotificationTypes.EMAIL);
		}
		student.setModel(model);
		return student;
	}
	
	private LoggedInAdmin createLoggedInAdmin(String name, String surname, String id,
			AuthenticationToken authenticationToken, SystemUserModel model){
		LoggedInAdmin admin = new LoggedInAdmin();
		admin.setName(name);
		admin.setSurname(surname);
		admin.setID(id);
		admin.setAuthenticationToken(authenticationToken);
		admin.setModel(null);
		return admin;
	}
	
	private LoggedInInstructor createLoggedInInstructor(String name, String surname, String id,
			AuthenticationToken authenticationToken, SystemUserModel model){
		LoggedInInstructor instructor = new LoggedInInstructor();
		instructor.setName(name);
		instructor.setSurname(surname);
		instructor.setID(id);
		instructor.setAuthenticationToken(authenticationToken);
		instructor.setModel(model);
		return instructor;
	}
}
