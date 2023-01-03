package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import Model.Game;
import Model.GameHistory;
import Model.Question;
import Model.User;

public class Sysdata {

	static ArrayList<User> thPlayers = new ArrayList<>(); // saves the game players
	static ArrayList<Question> importedQuestions = new ArrayList<>(); // saves the imported questions from the JSON
																		// file.
	static ObjectMapper mapper = new ObjectMapper(); // We can use the mapper to parse or deserialize JSON content into
														// a Java object.
	static ObjectMapper usermapper = new ObjectMapper();
	static ObjectMapper historymapper = new ObjectMapper();
	public static ArrayList<GameHistory> gamesHistoryList = new ArrayList<>();

	/*
	 * This method read the Json file and creates a Questions Object. and returns an
	 * array with all the imported questions -importedQuestions- after validating
	 * the Json schema. In Order to do this we Used the Jackson Libraries, we also
	 * created a helper class to convert the Object into a Question Object ( import
	 * and the export methods)
	 */
	public static ArrayList<Question> importQuestionsFromJSON() throws FileNotFoundException {

		try {
			InputStream inputStream = new FileInputStream("QuestionsFormat.json");
			JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
			InputStream schemaStream = new FileInputStream("jsonschema.json");

			//
			JsonNode json = mapper.readTree(inputStream);
			JsonSchema schema = schemaFactory.getSchema(schemaStream);
			Set<ValidationMessage> validationResult = schema.validate(json);

			if (validationResult.isEmpty()) {
				System.out.println("no validation errors :-)");
				Json input = mapper.treeToValue(json, Json.class);
				for (Question q : input.getQuestions()) {
					// System.out.println(input.getQuestions());
					importedQuestions.add(q);
					// imported.put("questions", importedQuestions);

				}

			} else {
				validationResult.forEach(vm -> System.out.println(vm.getMessage()));
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return importedQuestions;
	}

	// This method updating the Json file as in the array importedQuestions.
	public static void exportQuestionsToJSON() throws FileNotFoundException {

		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			HashMap<String, ArrayList<Question>> outHashMap = new HashMap<>();
			outHashMap.put("questions", importedQuestions);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("QuestionsFormat.json"), outHashMap);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static ArrayList<User> importUsersFromJSON() throws FileNotFoundException {

		try {
			InputStream inputStream = new FileInputStream("users.json");
			JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
			InputStream schemaStream = new FileInputStream("userjsonschema.json");

			//
			JsonNode json = usermapper.readTree(inputStream);
			JsonSchema schema = schemaFactory.getSchema(schemaStream);
			Set<ValidationMessage> validationResult = schema.validate(json);

			if (validationResult.isEmpty()) {
				System.out.println("no validation errors :-)");
				UserJson input = usermapper.treeToValue(json, UserJson.class);
				for (User u : input.getUsers()) {
					thPlayers.add(u);
				}

			} else {
				validationResult.forEach(vm -> System.out.println(vm.getMessage()));
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thPlayers;
	}

	public static void exportUsersToJSON() throws FileNotFoundException {

		usermapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			HashMap<String, ArrayList<User>> outHashMap = new HashMap<>();
			outHashMap.put("users", thPlayers);
			usermapper.writerWithDefaultPrettyPrinter().writeValue(new File("users.json"), outHashMap);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static ArrayList<GameHistory> importGameHistorysFromJSON() throws FileNotFoundException {

		try {
			InputStream inputStream = new FileInputStream("history.json");
			JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
			InputStream schemaStream = new FileInputStream("historyschema.json");

			
			JsonNode json = usermapper.readTree(inputStream);
			JsonSchema schema = schemaFactory.getSchema(schemaStream);
			Set<ValidationMessage> validationResult = schema.validate(json);

			if (validationResult.isEmpty()) {
				System.out.println("no validation errors :-)");
				HistoryJson input = usermapper.treeToValue(json, HistoryJson.class);
				for (GameHistory g : input.getGamesHistory()) {
					gamesHistoryList.add(g);
				}

			} else {
				validationResult.forEach(vm -> System.out.println(vm.getMessage()));
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gamesHistoryList;
	}

	public static void exportGamesHistoryToJSON() throws FileNotFoundException {

		usermapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			HashMap<String, ArrayList<GameHistory>> outHashMap = new HashMap<>();
			outHashMap.put("gamesHistory", gamesHistoryList);
			usermapper.writerWithDefaultPrettyPrinter().writeValue(new File("history.json"), outHashMap);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	public static ArrayList<Question> getImportedQuestions() {
		return importedQuestions;
	}

	public static void setImportedQuestions(ArrayList<Question> importedQuestions) {
		Sysdata.importedQuestions = importedQuestions;
	}

	public static ArrayList<User> getThPlayers() {
		return thPlayers;
	}

	public static void setThPlayers(ArrayList<User> thPlayers) {
		Sysdata.thPlayers = thPlayers;
	}

	public static ArrayList<GameHistory> getGamesHistoryList() {
		return gamesHistoryList;
	}

	public static void setGamesHistoryList(ArrayList<GameHistory> gamesHistoryList) {
		Sysdata.gamesHistoryList = gamesHistoryList;
	}

//	//methods with test
//	public boolean addUser(User user) {
//		if(user==null || getThPlayers().contains(user)) 
//			return false;
//		return 	getThPlayers().add(user);
//	}
//	//methods with test
//	public boolean removeUser(User user) {
//		if(user == null || !getThPlayers().contains(user))   
//			return false;
//		return getThPlayers().remove(user);
//	}

}
