import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SchemaErrorHandler implements ErrorHandler {
	
	private boolean validation = true;
	private Report messageReport;
	
	public SchemaErrorHandler(Report messageReport) {
		this.messageReport = messageReport;
	}
	
	@Override
	public void warning(SAXParseException exception) throws SAXException {
		messageReport.addWarning(new Warning(exception));
		//System.out.println("Warning: ");
		//System.out.println(exception.getMessage());
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		messageReport.addError(new Error(exception));
		//System.out.println("Error: ");
		//System.out.println(exception.getMessage());
		validation = false;
		
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		messageReport.addFatalError(new FatalError(exception));
		//System.out.println("Fatal Error: ");
		//System.out.println(exception.getMessage());
		validation = false;
	}
	
	public boolean getValidation() {
		return validation;
	}

}
