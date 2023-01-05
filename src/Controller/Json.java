package Controller;

import java.util.ArrayList;

import Model.Question;

public class Json {
	// helper Class - In order to export and import from a Question Json file using the
	// Jackson Libraries (Questionformat.json file)
	private ArrayList<Question> questions;


	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}



}
