import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class Validator {
	
	private File messageFile;
	private Document messageDocument;
	private Accessor messageAccessor;
	private Report messageReport;
	private String messageSchema;
	private boolean PCD_06;
	private boolean PCD_07;
	private boolean MCR;
	private boolean pairedMCR;
	private boolean unpairedMCR;
	private boolean callback;
	private boolean attachments;
	private boolean synchronous;
	private boolean synchronous_pass;
	private boolean synchronous_fail;
	private boolean asynchronous;
	private boolean alertInformation;
	
	public Validator(File messageFile, Report messageReport, boolean PCD_06, boolean PCD_07, boolean MCR, boolean pairedMCR, 
			boolean unpairedMCR, boolean callback, boolean attachments, boolean synchronous, boolean synchronous_pass, boolean synchronous_fail, boolean asynchronous, boolean alertInformation) {
		this.messageFile = messageFile;
		this.messageReport = messageReport;
		this.PCD_06 = PCD_06;
		this.PCD_07 = PCD_07;
		this.MCR = MCR;
		this.pairedMCR = pairedMCR;
		this.unpairedMCR = unpairedMCR;
		this.callback = callback;
		this.attachments = attachments;
		this.synchronous = synchronous;
		this.synchronous_pass = synchronous_pass;
		this.synchronous_fail = synchronous_fail;
		this.asynchronous = asynchronous;
		this.alertInformation = alertInformation;		
	}
	
	public void validate() {
		messageReport.setDocumentValidation(createDocument());
		if (messageReport.getDocumentValidation() == true) {
			messageReport.setSchemaValidation(schemaValidation());
		} else {
			messageReport.addFatalError(new FatalError("A document was unable to be created from the file provided."));
		}
		if (messageReport.getSchemaValidation() == true) {
			this.messageAccessor = new Accessor(messageFile, messageDocument);
			messageReport.setContentValidation(contentValidation());
		} else {
			messageReport.addFatalError(new FatalError("The document was unable to be validated against the appropriate schema."));
		}
		if (messageReport.getContentValidation() == true) {
		} else {
			messageReport.addFatalError(new FatalError("The document's content was unable to be validated."));
		}
		
		
	}
	
	private boolean createDocument() {
		SchemaErrorHandler errorHandler = new SchemaErrorHandler(messageReport);
		try {
			SAXReader reader = new SAXReader();
			reader.setValidation(false);
			reader.setErrorHandler(errorHandler);
			this.messageDocument = reader.read(messageFile);
		} catch (DocumentException e) {
			System.out.println("A system error occured with docuent creation.");
			System.exit(1);
		}
		return errorHandler.getValidation();
	}
	
	private boolean schemaValidation() {
		SchemaErrorHandler errorHandler = new SchemaErrorHandler(messageReport);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			schemaFinder();
			factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource(messageSchema)}));
			SAXParser parser = factory.newSAXParser();
			SAXReader reader = new SAXReader(parser.getXMLReader());
			reader.setValidation(false);
			reader.setErrorHandler(errorHandler);
			reader.read(messageFile);
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println("A system error occured with schema validation.");
			System.exit(2);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.out.println("A system error occured with schema validation.");
			System.exit(2);
		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println("A system error occured with schema validation.");
			System.exit(2);
		}
		
		return errorHandler.getValidation();
		
	}
	
	private void schemaFinder() {
		if (alertInformation == true) {
			messageSchema =  "wctp-dtd-ihepcd-pcd06-v1r2.xsd";
		} else if (callback == true || attachments == true) {
			messageSchema = "wctp-dtd-ihepcd-pcd06-v1r1.xsd";
		} else {
			messageSchema = "wctp-dtd-v1r3.xsd";
		}
		
		messageReport.setMessageSchema(messageSchema);
		
	}
	
	private boolean contentValidation() {
		boolean contentValidation = true;
		
		//Version
		if (messageAccessor.getVersion() == null) {
			contentValidation = false;
			messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
		} else {
			String version = messageAccessor.getVersion().getValue();
			System.out.println(version);
			if (alertInformation == true) {
				if (!version.equals("wctp-dtd-ihepcd-pcd06-v1r2")) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
				}
			} else if (callback == true || attachments == true) {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2"))) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
				}
			} else if (pairedMCR == true) {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2") || version.equals("wctp-dtd-v1r3"))) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
				}
			} else if (unpairedMCR == true) {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2") || version.equals("wctp-dtd-v1r3") || version.equals("wctp-dtd-v1r2"))) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
				}
				if (version.equals("wctp-dtd-v1r2")) {
					messageReport.addWarning(new Warning("WCTP version of wctp-dtd-v1r3 or after is suggested.", messageAccessor.getVersion().getPath()));
				}
			} else {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2") || version.equals("wctp-dtd-v1r3") || version.equals("wctp-dtd-v1r2") || version.equals("wctp-dtd-v1r1"))) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
				}
				if (version.equals("wctp-dtd-v1r2") || version.equals("wctp-dtd-v1r1")) {
					messageReport.addWarning(new Warning("WCTP version of wctp-dtd-v1r3 or after is suggested.", messageAccessor.getVersion().getPath()));
				}
			}
		}
		
		//PCD-06 Messages
		if (PCD_06 == true) {
			
			//SubmitHeader -- Universal to all PCD-06 Messages
			
			//SubmitTimestamp
			if (messageAccessor.getSubmitTimestamp() != null) {
				try {
					//Need to clarify about precision verification...
					LocalDateTime timestamp = LocalDateTime.parse(messageAccessor.getSubmitTimestamp().getValue());
				} catch (DateTimeParseException e) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Submit Timestamp value.", messageAccessor.getSubmitTimestamp().getPath()));
				}
			} else {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Submit Timestamp value.", messageAccessor.getSubmitTimestamp().getPath()));
			}
			
			//Originator
			if (!(messageAccessor.getSenderID() != null && messageAccessor.getSenderID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Sender ID value.", messageAccessor.getSenderID().getPath()));
			}			
			/*if (!(messageAccessor.getSecurityCode() != null && messageAccessor.getSecurityCode().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Security Code value.", messageAccessor.getSecurityCode().getPath()));
			}*/
			
			//MessageControl
			if (!(messageAccessor.getMessageID() != null && messageAccessor.getMessageID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Message ID value.", messageAccessor.getMessageID().getPath()));
			}
			if (!(messageAccessor.getTransactionID() != null && messageAccessor.getTransactionID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Transaction ID value.", messageAccessor.getTransactionID().getPath()));
			}
			if (!(messageAccessor.getAllowResponse() != null && (messageAccessor.getAllowResponse().getValue().toUpperCase().equals("TRUE") || messageAccessor.getAllowResponse().getValue().toUpperCase().equals("FALSE")))) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Allow Response value.", messageAccessor.getAllowResponse().getPath()));
			}
			if (!(messageAccessor.getDeliveryPriority() != null && (messageAccessor.getDeliveryPriority().getValue().toUpperCase().equals("HIGH") || messageAccessor.getDeliveryPriority().getValue().toUpperCase().equals("NORMAL") || messageAccessor.getDeliveryPriority().getValue().toUpperCase().equals("LOW")))) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Delivery Priority value.", messageAccessor.getDeliveryPriority().getPath()));
			}
			if (!(messageAccessor.getNotifyWhenDelivered() != null && (messageAccessor.getNotifyWhenDelivered().getValue().toUpperCase().equals("TRUE") || messageAccessor.getNotifyWhenDelivered().getValue().toUpperCase().equals("FALSE")))) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Notify When Delivered value.", messageAccessor.getNotifyWhenDelivered().getPath()));
			}
			if (!(messageAccessor.getPreformatted() != null && (messageAccessor.getPreformatted().getValue().toUpperCase().equals("TRUE") || messageAccessor.getPreformatted().getValue().toUpperCase().equals("FALSE")))) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid/Missing Preformatted value.", messageAccessor.getPreformatted().getPath()));
			}
			
			//Recipient
			if (!(messageAccessor.getRecipientID() != null && messageAccessor.getRecipientID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Recipient ID value.", messageAccessor.getRecipientID().getPath()));
			}
			
			
			//No MCR Messages
			if (MCR == false) {
				//Alphanumeric
				if (!(messageAccessor.getAlphanumeric() != null && messageAccessor.getAlphanumeric().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Alphanumeric value.", messageAccessor.getAlphanumeric().getPath()));
				}
			}
			
			//MCR Messages
			if (MCR == true) {
				//MessageText
				if (!(messageAccessor.getMessageText() != null && messageAccessor.getMessageText().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Message Text value.", messageAccessor.getMessageText().getPath()));
				}
				
				//Unpaired MCR Messages
				if (unpairedMCR == true) {					
					//Choices
					for (int i = 0; i < messageAccessor.getChoices().getValues().size(); i++) {
						int choiceNum = i + 1;
						Node n = messageAccessor.getChoices().getValues().get(i);
						if (!(n.getText() != null && n.getText().length() > 0)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Choice " + choiceNum + " value." , messageAccessor.getChoices().getPath()));
						}
					}
				}
				
				//Paired MCR Messages
				if (pairedMCR == true) {					
					//ChoicePairs
					for (int i = 0; i < messageAccessor.getChoicePairs().getValues().size(); i++) {
						int choicePairNum = i + 1;
						Node n = messageAccessor.getChoicePairs().getValues().get(i);
						Node nSendChoice = n.selectSingleNode("/wctp-SendChoice");
						if (!(nSendChoice != null && nSendChoice.getText().length() > 0)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid SendChoice " + choicePairNum + " value." , messageAccessor.getChoicePairs().getPath() + "/wctp-SendChoice"));
						}
						Node nReplyChoice = n.selectSingleNode("/wctp-ReplyChoice");
						if (!(nReplyChoice != null && nReplyChoice.getText().length() > 0)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid ReplyChoice " + choicePairNum + " value." , messageAccessor.getChoicePairs().getPath() + "/wctp-ReplyChoice"));
						}
					}
				}
				
				//Callback
				if (callback == true) {
					if (!(messageAccessor.getDialback() != null && messageAccessor.getDialback().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Dialback String value.", messageAccessor.getDialback().getPath()));
					}
				}
				
				//WCM Images
				if (attachments == true) {
					for (int i = 0; i < messageAccessor.getWCMImages().getValues().size(); i++) {
						int imageNum = i + 1;
						Node n = messageAccessor.getWCMImages().getValues().get(i);
						if (!(n != null && (n.valueOf("@Format").toUpperCase().equals("SVG") || n.valueOf("@Format").toUpperCase().equals("JPEG") || n.valueOf("@Format").toUpperCase().equals("PNG") || n.valueOf("@Format").toUpperCase().equals("BMP")))) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Format " + imageNum + " value." , messageAccessor.getWCMImages().getPath() + "/wctp-Image/@Format"));
						}
						if (!(n != null && n.valueOf("@Format").toUpperCase().equals("BASE64"))) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Encoding " + imageNum + " value." , messageAccessor.getWCMImages().getPath() + "/wctp-Image/@Encoding"));
						}
					}
				}
				
				//Alert Information
				if (alertInformation == true) {
					if (!(messageAccessor.getIHEPCDACM().getValue_Node() != null)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Alert Information value.", "Null"));
					}
				}
			}
		}
		
		//PCD-07 messages
		if (PCD_07 == true) {
			//Synchronous Messages
			if (synchronous == true) {
				//Successful Synchronous Messages
				if (synchronous_pass == true) {
					if (!(messageAccessor.getSuccessCode() != null && messageAccessor.getSuccessCode().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Success Code value.", messageAccessor.getSuccessCode().getPath()));
					}
					if (!(messageAccessor.getSuccessText() != null && messageAccessor.getSuccessText().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Success Text value.", messageAccessor.getSuccessText().getPath()));
					}
				}
				//Unsuccessful Synchronous Message
				if (synchronous_fail == true) {
					if (!(messageAccessor.getErrorCode() != null && messageAccessor.getErrorCode().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Error Code value.", messageAccessor.getErrorCode().getPath()));
					}
					if (!(messageAccessor.getErrorText() != null && messageAccessor.getErrorText().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Error Text value.", messageAccessor.getErrorText().getPath()));
					}
				}
			}
			
			if (asynchronous == true) {
				//ResponseHeader				
				if (messageAccessor.getResponseTimestamp() != null) {
					try {
						//Need to clarify about precision verification...
						LocalDateTime timestamp = LocalDateTime.parse(messageAccessor.getResponseTimestamp().getValue());
					} catch (DateTimeParseException e) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Response Timestamp value.", messageAccessor.getResponseTimestamp().getPath()));
					}
				} else {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Response Timestamp value.", messageAccessor.getResponseTimestamp().getPath()));
				}
				if (messageAccessor.getRespondingToTimestamp() != null) {
					try {
						//Need to clarify about precision verification...
						LocalDateTime timestamp = LocalDateTime.parse(messageAccessor.getRespondingToTimestamp().getValue());
					} catch (DateTimeParseException e) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Responding To Timestamp value.", messageAccessor.getRespondingToTimestamp().getPath()));
					}
				} else {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Responding To Timestamp value.", messageAccessor.getRespondingToTimestamp().getPath()));
				}
				if (!(messageAccessor.getOnBehalfOfRecipientID() != null && messageAccessor.getOnBehalfOfRecipientID().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid On Behalf Of Recipient ID value.", messageAccessor.getOnBehalfOfRecipientID().getPath()));
				}
				
				//Originator
				if (!(messageAccessor.getSenderID_Response() != null && messageAccessor.getSenderID_Response().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Sender ID value.", messageAccessor.getSenderID_Response().getPath()));
				}			
				
				//MessageControl
				if (!(messageAccessor.getMessageID_Response() != null && messageAccessor.getMessageID_Response().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Message ID value.", messageAccessor.getMessageID_Response().getPath()));
				}
				if (!(messageAccessor.getTransactionID_Response() != null && messageAccessor.getTransactionID_Response().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Transaction ID value.", messageAccessor.getTransactionID_Response().getPath()));
				}
				
				//Asynchronous Non-MCR Messages
				if (MCR == false) {
					if (!(messageAccessor.getNotificationType() != null && (messageAccessor.getNotificationType().getValue().toUpperCase().equals("READ") || messageAccessor.getNotificationType().getValue().toUpperCase().equals("DELIVERED") || messageAccessor.getNotificationType().getValue().toUpperCase().equals("QUEUED") || messageAccessor.getNotificationType().getValue().toUpperCase().equals("IHEPCDCALLBACKSTART") || messageAccessor.getNotificationType().getValue().toUpperCase().equals("IHEPCDCALLBACKEND")))) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Notification Type value.", messageAccessor.getNotificationType().getPath()));
					}
					
					//Alert Information
					if (alertInformation == true) {
						if (!(messageAccessor.getIHEPCDACM_Status() != null)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Alert Information value.", "Null"));
						}
					}	
				}	
				
				//Asynchronous MCR Messages
				if (MCR == true) {
					if (!(messageAccessor.getAlphanumeric_Response_MCR() != null && messageAccessor.getAlphanumeric_Response_MCR().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Alphanumeric value.", messageAccessor.getAlphanumeric_Response_MCR().getPath()));
					}
					
					//Alert Information
					if (alertInformation == true) {
						if (!(messageAccessor.getIHEPCDACM_Reply() != null)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Alert Information value.", "Null"));
						}
					}
				}
			}
		}
		return contentValidation;
	}
}
