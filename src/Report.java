import java.util.List;

public class Report {
	private String messageType;
	private String messageDescription;
	private String fileName;
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
	
	public void addWarning(Warning warning) {
		warningList.add(warning);
	}
	
	public void addError(Error error) {
		errorList.add(error);
	}
	
	public void addFatalError(FatalError fatalError) {
		fatalErrorList.add(fatalError);
	}
	
	public int numWarnings( ) {
		return warningList.size();
	}
	
	public int numErrors() {
		return errorList.size();
	}
	
	public int numFatalErrors() {
		return fatalErrorList.size();
	}
	
	
}
