package Model;

import java.util.Arrays;
import java.util.Iterator;

import javafx.scene.control.Button;

public class Question { 

	private static int quesidCounter = 1;    //counter++
	private String question;
	private String[] answers;
	private int correct_ans;
	private int level;
	private String team;

	

	public Question() {
		// TODO Auto-generated constructor stub
	}

	public Question(String question, String[] answers, int correct_ans, int level, String team) {
		super();
		this.quesidCounter = quesidCounter++;
		this.question = question;
		this.answers = answers;
		this.correct_ans = correct_ans;
		this.level = level;
		this.team = team;
	}
	
	public static int getQuesidCounter() {
		return quesidCounter;
	}

	public static void setQuesidCounter(int quesidCounter) {
		Question.quesidCounter = quesidCounter;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public int getCorrect_ans() {
		return correct_ans;
	}

	public void setCorrect_ans(int correct_ans) {
		this.correct_ans = correct_ans;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	

	@Override
	public String toString() {
		return "Question [question=" + question + ", answers=" + Arrays.toString(answers) + ", correct_ans="
				+ correct_ans + ", level=" + level + ", team=" + team + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(answers);
		result = prime * result + correct_ans;
		result = prime * result + level;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (!Arrays.equals(answers, other.answers))
			return false;
		if (correct_ans != other.correct_ans)
			return false;
		if (level != other.level)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	
	


	

	
}
