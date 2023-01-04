package Controller;

import java.util.ArrayList;

import Model.User;

public class UserJson {
	/* helper Class - In order to export and import from a Users Json file using the
	 *  Jackson Libraries (users.json file)
	 */
	private ArrayList<User> users;
	

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
}
