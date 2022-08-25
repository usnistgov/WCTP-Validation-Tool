import org.xml.sax.SAXParseException;

public class Issue {
	private Exception e;
	private int lineNum;
	private int colNum;
	private String s;
	private String path;
	private String message;
	
	public Issue(SAXParseException e) {
		this.e = e;
		this.lineNum = e.getLineNumber();
		this.colNum = e.getColumnNumber();
		this.message = e.getMessage();
	}
	
	public Issue(String s, String path) {
		//Testing Github...
		this.message = s;
		this.path = path;
		
	}
	
	
}
