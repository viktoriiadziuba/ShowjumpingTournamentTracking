package com.viktoriia.model;

public class Participant {

	private String fullName;
	
	private int age;

	
	public Participant() {
		
	}

	public Participant(String fullName, int age) {
		this.fullName = fullName;
		this.age = age;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("[Participant "
				+ "fullName=%s]"
				+ "age=%d", fullName, age);
	}

	
}
