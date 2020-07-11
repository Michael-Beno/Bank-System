package ie.michael.bank.errors;

public class ErrorsImplementation implements Errors{
	
	private String errorName;

	public ErrorsImplementation() {
		super();
	}
	public ErrorsImplementation(String errorName) {
		super();
		this.errorName = errorName;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	@Override
	public String toString() {
		return errorName;
	}

}
