import java.io.File;

public class WCTPValidation {

	public static void PCD_06_01(File message) {
		Report messageReport = new Report("[PCD-06]", "No MCR", message.getName();
		Validator messageValidator = new Validator(message, messageReport, true, false, false, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	
	public static void main(String[] args) {
		File inputFile = new File("VolumeXMLFile.xml");
		
	}

}
