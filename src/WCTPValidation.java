import java.io.File;

public class WCTPValidation {
	
	public static void generic(File message) {
		Report messageReport = new Report("Generic", "N/A -- *Extremely limited content validation is performed for generic messages.", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, false, false, false, false, false, false, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_01(File message) {
		Report messageReport = new Report("[PCD-06]", "No MCR", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, false, false, false, false, false, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_02(File message) {
		Report messageReport = new Report("[PCD-06]", "Unpaired MCR", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, false, true, false, false, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_03(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with No Additions", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, false, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_04(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, false, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_05(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with WCM", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, true, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_06(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with Alert Information", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, false, false, false, false, false, true);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_07(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback and WCM", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, true, false, false, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_08(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback and Alert Information", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, false, false, false, false, false, true);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_09(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with WCM and Alert Information", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, true, false, false, false, false, true);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_06_10(File message) {
		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback, WCM, and Alert Information", message.getName());
		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, true, false, false, false, false, true);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_07_01(File message) {
		Report messageReport = new Report("[PCD-07]", "Synchronous Status Update with Success", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, true, false, false, false, false, false, true, true, false, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_07_02(File message) {
		Report messageReport = new Report("[PCD-07]", "Synchronous Status Update with Failure", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, true, false, false, false, false, false, true, false, true, false, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_07_03(File message) {
		Report messageReport = new Report("[PCD-07]", "Asynchronous Status Update", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, true, false, false, false, false, false, false, false, false, true, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_07_04(File message) {
		Report messageReport = new Report("[PCD-07]", "Asynchronous Response with MCR", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, true, true, false, false, false, false, false, false, false, true, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_07_05(File message) {
		Report messageReport = new Report("[PCD-07]", "Asynchronous Status Update with Alert Information", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, true, true, false, false, false, false, false, false, false, true, false);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void PCD_07_06(File message) {
		//Testing GitHub...
		Report messageReport = new Report("[PCD-07]", "Asynchronous Response with MCR and Alert Information", message.getName());
		Validator messageValidator = new Validator(message, messageReport, false, true, true, false, false, false, false, false, false, false, true, true);
		messageValidator.validate();
		messageReport.getReport();
	}
	
	public static void main(String[] args) {
		File inputFile = new File("TestCase2.xml");
		PCD_06_02(inputFile);
		
	}

}
