package com.ripanhalder.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ripanhalder.validation.MatchingFields;
import com.ripanhalder.validation.CheckForValidEmail;

@MatchingFields.List({
    @MatchingFields(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class RegisterationUser {

	@NotNull(message = "is compulsory")
	@Size(min = 1, message = "is compulsory")
	private String userName;

	@NotNull(message = "is compulsory")
	@Size(min = 1, message = "is compulsory")
	private String firstName;

	@NotNull(message = "is compulsory")
	@Size(min = 1, message = "is compulsory")
	private String lastName;
	
	@NotNull(message = "is compulsory")
	@Size(min = 1, message = "is compulsory")
	private String password;
	
	@NotNull(message = "is compulsory")
	@Size(min = 1, message = "is compulsory")
	private String matchingPassword;

	@CheckForValidEmail
	@NotNull(message = "is compulsory")
	@Size(min = 1, message = "is compulsory")
	private String email;

	public RegisterationUser() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
