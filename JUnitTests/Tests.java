import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import org.junit.Test;
import Controller.BoardController;
import Controller.Sysdata;
import Model.Question;
import Model.User;

public class Tests {


	//First Test - checks when creating a new user the high score is equal to 0.
	@Test   
	public void initialHighScoreTest() {  		
		User u=new User("arwad", "1234");
		int expectedResult = 0 ;
		int actualResult = u.getHighScore();
		assertEquals(expectedResult, actualResult);
	}
	//Second Test - checks if the inserts questions to JSON is done successfully.
	@Test
	public void insertQuesToJsonTest(){
		String[] answers=new String [] {"Forging","Molding","Machining","all answers"};
		Question ques = new Question("What manufacturing processes did the engineers need to understand?", 
				answers, 4, 1, "Sloth");
		try {
			Sysdata.importQuestionsFromJSON();
			Sysdata.getImportedQuestions().add(ques);
			Sysdata.exportQuestionsToJSON();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Boolean actualResult = Sysdata.getImportedQuestions().contains(ques);
		Boolean expectedResult = true;
		assertEquals(expectedResult, actualResult);
	}

	//Third Test - checks if all the questions in our JSON questions file have 4 answers.
	@Test
	public void answersNumOfQuestionsTest(){
		for (Question q : Sysdata.getImportedQuestions()) {
			int expectedResult = 4 ;
			int actualResult = q.getAnswers().length;
			assertEquals(expectedResult, actualResult);
		}
	}
	//Forth Test - checks if the start time in the games begins from 60 seconds.
	@Test
	public void startTimeInGameTest() {
		int actualResult = BoardController.startTime;
		int expectedResult = 60;
		assertEquals(expectedResult, actualResult);
	}
	//Fifth Test - Checks if the high score of a certain user is updated successfully.
	@Test
	public void setHighSoreTest() {

		User u=new User("dc", "dd");
		try {	
			Sysdata.importUsersFromJSON();
			Sysdata.getThPlayers().remove(new User("dc", "dd"));
			u.setHighScore(10);
			Sysdata.getThPlayers().add(u);
			Sysdata.exportUsersToJSON();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int actualResult = u.getHighScore();
		int expectedResult = 10;
		assertEquals(expectedResult, actualResult);
	}
	//Extra Test - checks if a certain user can removed successfully from the users JSON file.
	@Test
	public void check() {
		try {	
			Sysdata.importUsersFromJSON();
			Sysdata.getThPlayers().remove(new User("zaher", "123"));
			Sysdata.exportUsersToJSON();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Boolean actualResult = Sysdata.getThPlayers().contains(new User("zaher", "123"));
		Boolean expectedResult = false;
		assertEquals(expectedResult, actualResult);
	}


}
