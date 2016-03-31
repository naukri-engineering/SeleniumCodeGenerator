package generator;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import csvReader.CSVReader;




public class FieldLevelValidationGenerator {

	private static final String fillFormFilePath = "./generatedCode/code_fillform.txt";
	private static final String testDataFilePath = "./generatedCode/testData.csv";	
	private BufferedWriter outDataProvider;
	private BufferedWriter outFillForm;
	private BufferedWriter outTestdata;

	/**
	 * To generate code for additional  methods in to a file
	 * @param fields Fields[] locators for which code to be generated	
	 */
	public void generateAdditionalMethods(Field[] fields, String pageName) throws IOException{
		
		//final String pageName ="YourPageName";
		pageName = pageName.replace(" ", "").trim();

		clearFile();
		createDataProvider(pageName, fields);
		createFillForm(pageName, fields);		
		createCaptureError(pageName);
		createFieldValidation(pageName);

	}

	/**
	 * To clear the file in which code will be generated 
	 * 	
	 */
	private void clearFile() throws IOException {
		outDataProvider=null;
		outDataProvider = new BufferedWriter(new FileWriter(fillFormFilePath, false));
		outDataProvider.close();		
		
		outFillForm= null;
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, false));
		outFillForm.close();
		
		outTestdata= null;
		outTestdata = new BufferedWriter(new FileWriter(testDataFilePath, false));
		outTestdata.close();
	}

	/**
	 * To generate code for Data Provider 
	 * @param pageName String 
	 * @param fields Field[] fields or locators for which code to be generated
	 */

	private void createDataProvider(String pageName, Field[] fields) throws IOException {
		addDataProviderMethod(pageName);
		addDP(fields);
		closeDataProviderMethod();

	}

	/**
	 * To generate code for Data Provider method signature 
	 * @param pageName String 
	 * 
	 */

	private void addDataProviderMethod(String pageName) throws IOException {
		outDataProvider=null;
		outDataProvider = new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outDataProvider.newLine();
		outDataProvider.write("@DataProvider");	 
		outDataProvider.newLine();
		outDataProvider.write("public Object[][] getData(){");
		outDataProvider.newLine();
		outDataProvider.write("//Use CSVReader class bundled along the project to make use of functions to read from excel file");
		outDataProvider.newLine();
		outDataProvider.newLine();			
		outDataProvider.write("CSVReader datatable"+"=new"+" CSVReader"+"(\"change sheet path here\");");
		outDataProvider.newLine();
		outDataProvider.write("int rowcount=datatable.getRowCount("+");");
		outDataProvider.newLine();
		outDataProvider.write("Object[][] 	data = new Object[rowcount-1][1];");
		outDataProvider.newLine();
		outDataProvider.write("for (int i=2; i<rowcount+1; i++){");
		outDataProvider.newLine();
		//outDP.write("String sheetname=\"Sheet1\";");
		//outDP.newLine();
		outDataProvider.write("HashMap<String,String> hm = new HashMap<String,String>();");
		outDataProvider.newLine();
		outDataProvider.write("hm.put(\"Expected\", datatable.getCellData(\"Expected\", i));");
		outDataProvider.newLine();
		outDataProvider.close();		
	}


	/**
	 * To generate code for Data Provider method body 
	 * @param fields Fields[] fields or locators for which code to be generated	 
	 * 
	 */

	private void addDP(Field[] fields) throws IOException {
		//Input inputPage =new Input();

		for (Field field : fields) {

			System.out.println(field.getName());
			String fieldName = field.getName();

			if(fieldName.endsWith("_Txt")){
				addTextBoxMethodsDP(fieldName);
			}
			if(fieldName.endsWith("_DD")){
				addDropDownMethodsDP(fieldName);
			}
			if(fieldName.endsWith("_Chk")){
				addCheckBoxMethodsDP(fieldName);
			}
			if(fieldName.endsWith("_Rd")){
				addRadiobuttonMethodsDP(fieldName);
			}
		}		
	}

	/**
	 * To generate code for Data Provider method closure 
	 * 
	 */

	private void closeDataProviderMethod() throws IOException {
		outDataProvider=null;
		outDataProvider = new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outDataProvider.newLine();
		outDataProvider.write("data[i-2][0]=hm;");	 
		outDataProvider.newLine();
		outDataProvider.write("}");
		outDataProvider.newLine();
		outDataProvider.write("return data;");
		outDataProvider.write("}");
		outDataProvider.close();		
	}


	/**
	 * To generate code for method FillForm 
	 * @param pageName String 
	 * @param fields Field[] fields or locators for which code to be generated
	 */

	private void createFillForm(String pageName, Field[] fields) throws IOException {
		addFillFormMethodName(pageName);
		adFF(fields);
		closeFillForm();

	}

	/**
	 * To generate code for method FillForm  signature
	 * @param pageName String 
	 * @throws IOException if file path provided is not available
	 */

	private void addFillFormMethodName(String pageName) throws IOException {
		outFillForm= null;
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		//outFillForm.write("//Replace YourPageName with the actual name of the page");
		//outFillForm.newLine();
		outFillForm.write("public void fillForm_"+pageName+"(HashMap<String, String> hmdata){");
		outFillForm.newLine();
		outFillForm.close();
	}


	/**
	 * To generate code for method FillForm  body
	 * @param fields Field[] fields or locators for which code to be generated 
	 * @throws IOException if file path provided is not available
	 */

	private void adFF(Field[] fields) throws IOException {
		//Input inputPage =new Input();

		for (Field field : fields) {

			System.out.println(field.getName());
			String fieldName = field.getName();

			if(fieldName.endsWith("_Txt")){
				addTextBoxMethodsFF(fieldName);
			}
			if(fieldName.endsWith("_DD")){
				addDropDownMethodsFF(fieldName);
			}
			if(fieldName.endsWith("_Chk")){
				addCheckBoxMethodsFF(fieldName);
			}
			if(fieldName.endsWith("_Rd")){
				addRadiobuttonMethodsFF(fieldName);
			}

		}		
	}

	/**
	 * To generate code for method FillForm  closing
	 * 
	 * @throws IOException if file path provided is not available
	 */

	private void closeFillForm() throws IOException {
		outFillForm=null; 
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outFillForm.write("}");
		outFillForm.close();
	}

	/**
	 * To generate code for capture errors on the page
	 * @param pageName String
	 * @throws IOException if file path provided is not available
	 */

	private void createCaptureError(String pageName) throws IOException {
		outFillForm=null;
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));;
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.write("public String capture_Errors(){");
		outFillForm.newLine();
		outFillForm.write("String total_errors=\" \";");
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.write("//Replace YourPageName_Error_WE with locator which finds out all the errors in page. e.g. By.xpath(\"@name='error_lbl'\")");
		outFillForm.newLine();
		outFillForm.write("if(driver.findElements("+pageName+"_Error_WE"+").size()> 0 ){");
		outFillForm.newLine();
		outFillForm.write("List<WebElement> errors= driver.findElements(("+pageName+"_Error_WE));");
		outFillForm.newLine();
		outFillForm.write("for(int i=0; i<errors.size(); i++){");
		outFillForm.newLine();
		outFillForm.write("if(errors.get(i).isDisplayed()){");
		outFillForm.newLine();
		outFillForm.write("total_errors= total_errors"+"+"+"\", \""+"+errors.get(i).getText();");
		outFillForm.newLine();
		outFillForm.write("}");
		outFillForm.newLine();
		outFillForm.write("}");
		outFillForm.newLine();
		outFillForm.write("}");
		outFillForm.newLine();
		outFillForm.write("return total_errors;");
		outFillForm.newLine();
		outFillForm.write("}");
		outFillForm.close();

	}

	/**
	 * To generate code for test method FieldValidation
	 * @param pageName String
	 * @throws IOException if file path provided is not available
	 */

	private void createFieldValidation(String pageName) throws IOException {
		outFillForm=null;
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));;
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.write("@Test(dataProvider=\"getData\")");
		outFillForm.newLine();
		outFillForm.newLine();
		//outFillForm.write("//Replace YourPageName with the actual name of the page");
		//outFillForm.newLine();
		outFillForm.write("public void fieldValidations_"+pageName+"(HashMap<String, String> hm){");
		outFillForm.newLine();
		String pageName1 = pageName.toLowerCase();
		outFillForm.write(pageName+" "+pageName1+" = new "+pageName+"(driver);");
		outFillForm.newLine();
		outFillForm.write("//Replace the locator below to find the confirmation message after submitting the page");
		outFillForm.newLine();
		outFillForm.write("By confirmMsg=By.xpath(\"xPathToLocateConfirmationMessageAfterSubmittingPage\");");
		outFillForm.newLine();
		outFillForm.newLine();
		//outFillForm.write("//Replace YourPageName with the actual name of the page");
		//outFillForm.newLine();
		outFillForm.write(pageName1+".fillForm_"+pageName+"(hm);");
		outFillForm.newLine();
		outFillForm.write("String expected=hm.get(\"Expected\").toString();");
		outFillForm.newLine();
		outFillForm.write("if(expected.equalsIgnoreCase(\"submitted\")){");
		outFillForm.newLine();
		outFillForm.write("//Pass the case if the confirmation message is displayed otherwise fail the case returning the field level validation errors");
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.write("Assert.assertTrue(driver.findElements(confirmMsg).size()>0 && driver.findElement(confirmMsg).isDisplayed(), \"Fail-\"+"+pageName1+".capture_Errors()"+");");
		outFillForm.newLine();
		outFillForm.write("}else {");
		outFillForm.newLine();
		outFillForm.write("//Fail the case if the confirmation message is displayed otherwise Pass the case.");
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.write("Assert.assertFalse(driver.findElements(confirmMsg).size()>0 && driver.findElement(confirmMsg).isDisplayed(), \"Fail-Page Submitted for Negative data\""+");");
		outFillForm.newLine();
		outFillForm.write("}");
		outFillForm.newLine();
		outFillForm.newLine();
		outFillForm.write("}");
		outFillForm.newLine();
		outFillForm.close();

	}

	/**
	 * To generate code for data provider if the element type is Text box
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */

	private void addTextBoxMethodsDP(String fieldName) throws IOException {
		outDataProvider = null;

		String labelName = fieldName.substring(0, fieldName.length()-4);
		setColumnName(labelName);
		

		outDataProvider =  new BufferedWriter(new FileWriter(fillFormFilePath, true));

		outDataProvider.write("hm.put(\""+labelName+"\", "+"datatable.getCellData(\""+labelName+"\", i));");
		outDataProvider.newLine();
		outDataProvider.close();

	}

	/**
	 * To generate code for FillForm if the element type is Text box
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */

	private void addTextBoxMethodsFF(String fieldName) throws IOException {
		outFillForm= null;
		//String label_name;
		String	labelName = fieldName.substring(0, fieldName.length()-4);
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));;
		outFillForm.write("fill_"+labelName+"_Txt(hmdata.get(\""+labelName+"\").toString());");
		outFillForm.newLine();
		outFillForm.close();

	}


	/**
	 * To generate code for DataProvider if the element type is Drop down
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */
	private void addDropDownMethodsDP(String fieldName) throws IOException {

		outDataProvider = null;
		//String label_name;
		String labelName = fieldName.substring(0, fieldName.length()-3);
		setColumnName(labelName);
		


		outDataProvider =  new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outDataProvider.write("hm.put(\""+labelName+"\", "+"datatable.getCellData(\""+labelName+"\", i));");
		outDataProvider.newLine();
		outDataProvider.close();
	}

	/**
	 * To generate code for Fillform if the element type is Drop down
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */

	private void addDropDownMethodsFF(String fieldName) throws IOException {

		outFillForm= null;
		//String label_name;
		String labelName = fieldName.substring(0, fieldName.length()-3);

		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));

		outFillForm.write("select_"+labelName+"_DD(hmdata.get(\""+labelName+"\").toString());");
		outFillForm.newLine();
		outFillForm.close();

	}

	/**
	 * To generate code for DataProvider if the element type is Checkbox
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */
	private void addCheckBoxMethodsDP(String fieldName) throws IOException {

		//String label_name;
		String labelName = fieldName.substring(0, fieldName.length()-4);
		setColumnName(labelName);
		
		outDataProvider =  new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outDataProvider.write("hm.put(\""+labelName+"\", "+"datatable.getCellData(\""+labelName+"\", i));");
		outDataProvider.newLine();
		outDataProvider.close();
	}

	/**
	 * To generate code for Fillform if the element type is Checkbox
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */

	private void addCheckBoxMethodsFF(String fieldName) throws IOException {

		//String label_name;

		String labelName = fieldName.substring(0, fieldName.length()-4);
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outFillForm.newLine();

		outFillForm.write("String "+labelName+"_td="+"hmdata.get(\""+labelName+"\").toString();");
		outFillForm.newLine();
		outFillForm.write("if("+labelName+"_td.equalsIgnoreCase("+"\"yes\""+")){");
		outFillForm.write("select_"+labelName+"_Chk();");
		outFillForm.write("}");	

		outFillForm.newLine();
		outFillForm.write("if("+labelName+"_td.equalsIgnoreCase("+"\"no\""+")){");
		outFillForm.write("deSelect_"+labelName+"_Chk();");
		outFillForm.write("}");	
		outFillForm.newLine();
		outFillForm.close();


	}

	/**
	 * To generate code for DataProvider if the element type is Radio button
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */
	private void addRadiobuttonMethodsDP(String fieldName) throws IOException {
		outDataProvider = null;
		//String label_name;

		String labelName = fieldName.substring(0, fieldName.length()-3);
		setColumnName(labelName);
		
		outDataProvider =  new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outDataProvider.write("hm.put(\""+labelName+"\", "+"datatable.getCellData(\""+labelName+"\", i));");
		outDataProvider.newLine();
		outDataProvider.close();

	}

	/**
	 * To generate code for Fillform if the element type is Radio button
	 * @param fieldName String
	 * @throws IOException if file path provided is not available
	 */

	private void addRadiobuttonMethodsFF(String fieldName) throws IOException {
		outFillForm= null;
		//	String label_name;

		String labelName = fieldName.substring(0, fieldName.length()-3);
		outFillForm = new BufferedWriter(new FileWriter(fillFormFilePath, true));
		outFillForm.newLine();
		outFillForm.write("String "+labelName+"_td="+"hmdata.get(\""+labelName+"\").toString();");
		outFillForm.newLine();					
		outFillForm.write("if("+labelName+"_td.equalsIgnoreCase("+"\"yes\""+")){");
		outFillForm.write("select_"+labelName+"_Rd();");
		outFillForm.write("}");							
		outFillForm.newLine();
		outFillForm.close();


	}

	/**
	 * To add column name in testdata file
	 * @param heading String
	 * 
	 */

	public void setColumnName(String heading){

		try {
			outTestdata = new BufferedWriter(new FileWriter(testDataFilePath, true));
			CSVReader datatable = new CSVReader(testDataFilePath);
			if(datatable.getRowCount()==0){
				outTestdata.write("Expected");
				outTestdata.write(", "+"Expected ErrorMessage");
			}


			outTestdata.write(", "+heading);
			outTestdata.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}






}
