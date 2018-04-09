package org.geplanes.validators;

public interface ValidadorGeplanes<T> {
	
	public ValidationResult validate(T object);

}
