package userOperations;

import authenticatedUsers.LoggedInAuthenticatedUser;

public interface Operations {
	public void execute(LoggedInAuthenticatedUser user);
}
