package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import Model.Question;

public class Sysdata {

	static ArrayList<Question> importedQuestions = new ArrayList<>();
	static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		importQuestionsFromJSON();
		System.out.println(importedQuestions);
	}

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
					importedQuestions.add(q);
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
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("QuestionsFormat2.json"), importedQuestions);

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

}
