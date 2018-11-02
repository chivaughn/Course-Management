package authenticatedUsers;

import authenticationServer.AuthenticationToken;
import systemUsers.SystemUser;
import systemUsers.SystemUserModel;

public interface LoggedInAuthenticatedUser extends SystemUser {

	void setAuthenticationToken(AuthenticationToken authenticationToken);
	AuthenticationToken getAuthenticationToken();
	
	void setModel(SystemUserModel model);
	SystemUserModel getModel();
}
