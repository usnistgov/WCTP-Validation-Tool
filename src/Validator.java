import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class Validator {
	
	private File messageFile;
	private Document messageDocument;
	private Accessor messageAccessor;
	private Report messageReport;
	private boolean PCD_06;
	private boolean PCD_07;
	private boolean MCR;
	private boolean pairedMCR;
	private boolean unpairedMCR;
	private boolean callback;
	private boolean attachments;
	private boolean alertInformation;
	
	public Validator(File messageFile, Report messageReport, boolean PCD_06, boolean PCD_07, boolean MCR, boolean pairedMCR, 
			boolean unpairedMCR, boolean callback, boolean attachments, boolean alertInformation) {
		this.messageFile = messageFile;
		this.messageReport = messageReport;
		this.PCD_06 = PCD_06;
		this.PCD_07 = PCD_07;
		this.MCR = MCR;
		this.pairedMCR = pairedMCR;
		this.unpairedMCR = unpairedMCR;
		this.callback = callback;
		this.attachments = attachments;
		this.alertInformation = alertInformation;		
	}
	
	public void validate() {
		messageReport.setDocumentValidation(createDocument());
	}
	
	private boolean createDocument() {
		boolean validation = true; 
		try {
			SAXReader reader = new SAXReader();
			reader.setValidation(false);
			reader.setErrorHandler(new SchemaErrorHandler());
			this.messageDocument = reader.read(messageFile);
		} catch (DocumentException e) {
			System.out.println("System error: a document was unale to be created.");
			System.exit(1);
		}
	}
	
	
	
}
