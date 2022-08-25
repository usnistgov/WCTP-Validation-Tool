import java.io.File;

import org.dom4j.Document;

public class Accessor {
	
	private File messageFile;
	private Document messageDocument;
	
	public Accessor(File messageFile, Document messageDocument) {
		this.messageFile = messageFile;
		this.messageDocument = messageDocument;
	}
	
	
	
}
