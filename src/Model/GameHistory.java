package Model;

import java.time.LocalDate;

public class GameHistory extends Game{
	
	private LocalDate gamedate;


	public GameHistory(int level, User user, int finalscore) {
		super(level, user, finalscore);
		// TODO Auto-generated constructor stub
		this.gamedate = LocalDate.now();
	}
	
	
	
	

}
