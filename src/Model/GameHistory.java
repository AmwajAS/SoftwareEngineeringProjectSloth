package Model;



public class GameHistory implements Comparable{
	
    private int level;
	private User user;
	private int finalScore;
	private String gameDate;
	
/*
 *this default Constructor for the JSON import and export use. 
 */
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
		return user.getUsername() +"               "+level +"                          " + finalScore +"                          "	
	+ gameDate + "                    " + user.getHighScore();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + finalScore;
		result = prime * result + ((gameDate == null) ? 0 : gameDate.hashCode());
		result = prime * result + level;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		GameHistory other = (GameHistory) obj;
		if (finalScore != other.finalScore)
			return false;
		if (gameDate == null) {
			if (other.gameDate != null)
				return false;
		} else if (!gameDate.equals(other.gameDate))
			return false;
		if (level != other.level)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		//	if(this.getUser().getHighScore().compateTo(o.user.getHighScore()))
		//	return this.getUser().getHighScore();
		return 0;
	}








	

}
