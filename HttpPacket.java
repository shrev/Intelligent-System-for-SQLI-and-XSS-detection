package sqlidefender;

public class HttpPacket {
	
	String requestMethod;
	String requestUri;
	String requestProtocol;
	String requestParametrs;
	
	String headerUpgradeInsecureRequests;
	String headerAccept;
	String headerConnection;
	String headerReferer;
	String headerCacheControl;
	String headerAcceptEncoding;
	String headerUserAgent;
	String headerAcceptLanguage;
	String headerHost;
	
	String packetBody;

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getRequestProtocol() {
		return requestProtocol;
	}

	public void setRequestProtocol(String requestProtocol) {
		this.requestProtocol = requestProtocol;
	}

	public String getRequestParametrs() {
		return requestParametrs;
	}

	public void setRequestParametrs(String requestParametrs) {
		this.requestParametrs = requestParametrs;
	}

	public String getHeaderUpgradeInsecureRequests() {
		return headerUpgradeInsecureRequests;
	}

	public void setHeaderUpgradeInsecureRequests(String headerUpgradeInsecureRequests) {
		this.headerUpgradeInsecureRequests = headerUpgradeInsecureRequests;
	}

	public String getHeaderAccept() {
		return headerAccept;
	}

	public void setHeaderAccept(String headerAccept) {
		this.headerAccept = headerAccept;
	}

	public String getHeaderConnection() {
		return headerConnection;
	}

	public void setHeaderConnection(String headerConnection) {
		this.headerConnection = headerConnection;
	}

	public String getHeaderReferer() {
		return headerReferer;
	}

	public void setHeaderReferer(String headerReferer) {
		this.headerReferer = headerReferer;
	}

	public String getHeaderCacheControl() {
		return headerCacheControl;
	}

	public void setHeaderCacheControl(String headerCacheControl) {
		this.headerCacheControl = headerCacheControl;
	}

	public String getHeaderAcceptEncoding() {
		return headerAcceptEncoding;
	}

	public void setHeaderAcceptEncoding(String headerAcceptEncoding) {
		this.headerAcceptEncoding = headerAcceptEncoding;
	}

	public String getHeaderUserAgent() {
		return headerUserAgent;
	}

	public void setHeaderUserAgent(String headerUserAgent) {
		this.headerUserAgent = headerUserAgent;
	}

	public String getHeaderAcceptLanguage() {
		return headerAcceptLanguage;
	}

	public void setHeaderAcceptLanguage(String headerAcceptLanguage) {
		this.headerAcceptLanguage = headerAcceptLanguage;
	}

	public String getHeaderHost() {
		return headerHost;
	}

	public void setHeaderHost(String headerHost) {
		this.headerHost = headerHost;
	}

	public String getPacketBody() {
		return packetBody;
	}

	public void setPacketBody(String packetBody) {
		this.packetBody = packetBody;
	}
}