package Controller;

import java.util.ArrayList;

import Model.Question;

public class Json {
	//helper Class - In order to export and import from a Json file using the Jackson Libraries
	private ArrayList<Question> questions;

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	

}
