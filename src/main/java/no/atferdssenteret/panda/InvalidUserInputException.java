package no.atferdssenteret.panda;

public class InvalidUserInputException extends RuntimeException {

	public InvalidUserInputException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
