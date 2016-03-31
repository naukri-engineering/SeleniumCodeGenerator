package input;

import java.io.IOException;

import by.By;





import generator.FieldLevelValidationGenerator;
import generator.PageObjectGenerator;



public class Input {

	//change name of the page for which you want to generate code	
	public static final String pageName = "RegPage";
	
	//Paste locators below for which the Page Object Functions need to be generated	
	public By name_Txt=By.name("Login to Apply");	
	public By country_DD=By.name("Login to Apply");	
	public By gender_Rd=By.name("Login to Apply");
	public By tnc_Chk=By.name("Login to Apply");
	public By registerMe_Btn=By.name("Login to Apply");
	public By signIn_Lnk=By.name("Login to Apply");
	public By errorRegPage_WE=By.name("Login to Apply");
	public By WelcomeMsg_Lbl=By.name("Login to Apply");
	

	public static void main(String[] args) {	


		PageObjectGenerator pageObjectGenerator = new PageObjectGenerator();
		FieldLevelValidationGenerator fieldValidationGenerator = new FieldLevelValidationGenerator();

		Input inputPage =new Input();

		try {
			pageObjectGenerator.generatePageMethods(inputPage.getClass().getDeclaredFields());
			fieldValidationGenerator.generateAdditionalMethods(inputPage.getClass().getDeclaredFields(), pageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
