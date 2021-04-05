package starter;

public enum WebServiceEndPoints {
    GET_ISSUES("https://api.github.com/repos/CDumitrescu94/AndroidApp/issues", "GET");

    private final String url;
    private final String method;

    WebServiceEndPoints(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }
    public String getMethod(){
        return method;
    }
}
