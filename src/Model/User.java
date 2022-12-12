package Model;
public class User {
	
	private String username;
	private String password;
	private String name;
	private int pointsInGame;
	private int highScore;
	
	public User(String username, String password, String name) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	
}
