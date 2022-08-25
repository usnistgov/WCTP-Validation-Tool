import org.xml.sax.SAXParseException;

public class FatalError extends Issue {

	public FatalError(SAXParseException e) {
		super(e);
	}

	public FatalError(String s, String path) {
		super(s, path);
		
	}

}
