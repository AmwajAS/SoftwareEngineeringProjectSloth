package Controller;

import java.util.ArrayList;
import Model.GameHistory;

public class HistoryJson {
	
	/* helper Class - In order to export and import from a Games history Json file using the
	 *  Jackson Libraries (history.json file)
	 */

	private ArrayList<GameHistory> gamesHistory;

	public ArrayList<GameHistory> getGamesHistory() {
		return gamesHistory;
	}

	public void setGamesHistory(ArrayList<GameHistory> gamesHistory) {
		this.gamesHistory = gamesHistory;
	}


	
}
