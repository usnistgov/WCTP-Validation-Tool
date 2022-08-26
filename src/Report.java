import java.util.ArrayList;
import java.util.List;

public class Report {
	private String messageType;
	private String messageDescription;
	private String fileName;
	private String messageSchema;
	private boolean documentValidation;
	private boolean schemaValidation;
	private boolean contentValidation;
	private boolean finalValidation;
	private List<Warning> warningList;
	private List<Error> errorList;
	private List<FatalError> fatalErrorList;
	
	public Report(String messageType, String messageDescription, String fileName) {
		this.messageType = messageType;
		this.messageDescription = messageDescription;
		this.fileName = fileName;
		this.warningList = new ArrayList<Warning>();
		this.errorList = new ArrayList<Error>();
		this.fatalErrorList = new ArrayList<FatalError>();
	}
	
	public void setMessageSchema(String messageSchema) {
		this.messageSchema = messageSchema;
	}
	
	public void setDocumentValidation(boolean documentValidation) {
		this.documentValidation = documentValidation;
	}
	
	public void setSchemaValidation(boolean schemaValidation) {
		this.schemaValidation = schemaValidation;
	}
	
	public void setContentValidation(boolean contentValidation) {
		this.contentValidation = contentValidation;
	}
	
	public boolean getDocumentValidation() {
		return documentValidation;
	}
	
	public boolean getSchemaValidation() {
		return schemaValidation;
	}
	
	public boolean getContentValidation() {
		return contentValidation;
	}
	
	public void addWarning(Warning warning) {
		warningList.add(warning);
	}
	
	public void addError(Error error) {
		errorList.add(error);
	}
	
	public void addFatalError(FatalError fatalError) {
		fatalErrorList.add(fatalError);
	}
	
	public int numWarnings() {
		return warningList.size();
	}
	
	public int numErrors() {
		return errorList.size();
	}
	
	public int numFatalErrors() {
		return fatalErrorList.size();
	}
	
	public void getReport() {
		System.out.println("Message Validation Report");
		System.out.println();
		System.out.println("File Name: " + fileName);
		System.out.println("Transaction Type: " + messageType);
		System.out.println("Message Type: " + messageDescription);
		System.out.println("Schema Used: " + messageSchema);
		System.out.println("");
		
		if (documentValidation == true) {
			System.out.println("Document Creation & Wellformedness Validation Result: PASS"); 
		} else {
			System.out.println("Document Creation & Wellformedness Validation Result: FAIL");
		}
		if(schemaValidation == true) {
			System.out.println("Schema Validation Result: PASS");
		} else {
			System.out.println("Schema Validation Result: FAIL");
		}
		if (contentValidation == true) {
			System.out.println("Message Parsing Validation Result: PASS");
		} else {
			System.out.println("Message Parsing Validation Result: FAIL");
		}
		if (documentValidation == true && schemaValidation == true && contentValidation == true) {
			System.out.println("Overall Validation Result: PASS");
		} else {
			System.out.println("Overall Validation Result: FAIL");
		}
		System.out.println("*Only components that failed will have errors or fatal errors.  Components that passed or failed can have warnings.");
		System.out.println();
		
		System.out.println("Warnings: " + numWarnings());
		for (int i = 0; i < warningList.size(); i++) {
			int num = i + 1;
			System.out.println("#" + num);
			System.out.println(warningList.get(i).getMessage());
			if (warningList.get(i).getLineNum() != -1) {
				System.out.println("Line Number: " + warningList.get(i).getLineNum());
			}
			if (warningList.get(i).getColNum() != -1) {
				System.out.println("Column Number: " + warningList.get(i).getColNum());
			}
			if (warningList.get(i).getPath() != null) {
				System.out.println("Path: " + warningList.get(i).getPath());
			}			
		}
		System.out.println();

		
		System.out.println("Errors: " + numErrors());
		for (int i = 0; i < errorList.size(); i++) {
			int num = i + 1;
			System.out.println("#" + num);
			System.out.println(errorList.get(i).getMessage());
			if (errorList.get(i).getLineNum() != -1) {
				System.out.println("Line Number: " + errorList.get(i).getLineNum());
			}
			if (errorList.get(i).getColNum() != -1) {
				System.out.println("Column Number: " + errorList.get(i).getColNum());
			}
			if (errorList.get(i).getPath() != null) {
				System.out.println("Path: " + errorList.get(i).getPath());
			}
		}
		System.out.println();

		
		System.out.println("Fatal Errors: " + numFatalErrors());
		for (int i = 0; i < fatalErrorList.size(); i++) {
			int num = i + 1;
			System.out.println("#" + num);
			System.out.println(fatalErrorList.get(i).getMessage());
			if (fatalErrorList.get(i).getLineNum() != -1) {
				System.out.println("Line Number: " + fatalErrorList.get(i).getLineNum());
			}
			if (fatalErrorList.get(i).getColNum() != -1) {
				System.out.println("Column Number: " + fatalErrorList.get(i).getColNum());
			}
			if (fatalErrorList.get(i).getPath() != null) {
				System.out.println("Path: " + fatalErrorList.get(i).getPath());
			}
		}
		System.out.println();
	}
		
	
}
