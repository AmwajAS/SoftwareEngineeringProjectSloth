package Model;

public class User {
	private String username;                      //the player or the user name
	private String password;                      //the player or the user password - for security
	private int pointsInGame;                     
	private int highScore;                        //the player highest score all time

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.pointsInGame = 0;
		this.highScore = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPointsInGame() {
		return pointsInGame;
	}

	public void setPointsInGame(int pointsInGame) {
		this.pointsInGame = pointsInGame;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + highScore;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + pointsInGame;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (highScore != other.highScore)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pointsInGame != other.pointsInGame)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", pointsInGame=" + pointsInGame
				+ ", highScore=" + highScore + "]";
	}

}
