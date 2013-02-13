package no.atferdssenteret.panda.model;

import no.atferdssenteret.panda.model.entity.User;

public class Session {
	private User user;
	
	public Session(User user) {
		this.user = user;
	}
	
	public User user() {
		return user;
	}
	
	public static Session createTestSession() {
		User user = new User();
		user.setUserName("erkebisk");
		user.setFirstName("Erkebiskop");
		user.setLastName("Jacobsen");
		user.setAccessLevel(User.AccessLevel.SUPER_USER);
		return new Session(user);
	}
}
