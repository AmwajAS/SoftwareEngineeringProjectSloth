package Model;

import java.time.LocalDate;

import javafx.scene.layout.GridPane;

public class GameHistory {
	
    private int level;
	private User user;
	private int finalScore;
	private String gameDate;

	public GameHistory() {
		super();
	}
	
	
	public GameHistory(int level, User user, int finalScore, String gameDate) {
		super();
		this.level = level;
		this.user = user;
		this.finalScore = finalScore;
		this.gameDate = gameDate;
	}



	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getFinalScore() {
		return finalScore;
	}


	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}




	public String getGameDate() {
		return gameDate;
	}


	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}


	@Override
	public String toString() {
		return "GameHistory [level=" + level + ", user=" + user + ", finalScore=" + finalScore + ", gameDate="
				+ gameDate + "]";
	}








	
	
	
	
	
	
	

}
