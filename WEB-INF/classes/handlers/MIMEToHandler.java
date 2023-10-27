package handlers;

import java.util.HashMap;
import java.util.Map;

public class MIMEToHandler {

	private Map<String, DataHandler> mimeToHandlers;
	private Map<String, String> formatToMIME;

	public MIMEToHandler() {
		mimeToHandlers = new HashMap<>();
		mimeToHandlers.put("application/xml", new XMLHandler());
		mimeToHandlers.put("text/plain", new TXTHandler());
		mimeToHandlers.put("application/json", new JSONHandler());

		formatToMIME = new HashMap<>();
		formatToMIME.put("xml", "application/xml");
		formatToMIME.put("txt", "text/plain");
		formatToMIME.put("json", "application/json");
	}

	public DataHandler getHandlerByMIME(String mimeType) {
		return mimeToHandlers.get(mimeType);
	}

	public DataHandler getHandlerByFormat(String format) {

		String mimeType = formatToMIME.get(format);

		return getHandlerByMIME(mimeType);
	}

}
