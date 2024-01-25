package com.kuber.medicapclassrooms.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Signup {
	@XmlElement
	String name;
	@XmlElement
	String password;
	@XmlElement
	String email;
	@XmlElement
	String role;
	public String getRole() {
		return this.role;
	}
	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}
	public String getEmail() {
		return this.email;
	}	

}
