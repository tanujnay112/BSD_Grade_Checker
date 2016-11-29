package com.tanuj.bsdgrades;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.annotation.TargetApi;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Connection {

	private List<String> cookies;
	  private HttpsURLConnection conn;
	 
	  private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36";
	 
	  public static String main(String user, String pass) throws Exception {
	 
		String url = " https://grades.bsd405.org/Pinnacle/Gradebook/Logon.aspx?ReturnUrl=%2fPinnacle%2fGradebook";
	 
		Connection http = new Connection();
		// make sure cookies is turned on
		CookieManager manager = new CookieManager();
		CookieHandler.setDefault(manager);
		// 1. Send a "GET" request, so that you can extract the form's data.
		String postParams = http.getFormParams(user, pass);
	 
		// 2. Construct above post's content and then send a POST request for
		// authentication
		http.sendPost(url, postParams);
		List<HttpCookie> cookies = manager.getCookieStore().getCookies();
		return cookies.get(1).toString();
	  }
	 
	  private void sendPost(String url, String postParams) throws Exception {
	 
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Host", "grades.bsd405.org");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Origin","https://grades.bsd405.org");
		conn.setRequestProperty("Accept",
			"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		if(cookies != null){
		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
		}
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Referer", "https://grades.bsd405.org/Pinnacle/Gradebook/Logon.aspx?ReturnUrl=%2fPinnacle%2fGradebook%2fDefault.aspx");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
		conn.setRequestProperty("Cache-Control", "max-age=0");
		conn.setDoOutput(true);
		conn.setDoInput(true);
	 
		// Send post request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();
		System.out.println("Getting cookiessssssss");
		System.out.println(conn.toString());
		System.out.println(conn.getContent().toString());
		Map<String, List<String>> headers = conn.getHeaderFields();
		cookies = headers.get("Set-Cookie");
		
	  }
	 
	  public String getFormParams(String username, String password)
			throws UnsupportedEncodingException {
		System.out.println("Getting Cookie...");
		
		String result = "__LASTFOCUS=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwUJNTkxNzI3MDIzD2QWAmYPZBYCAgMPZBYGAgEPZBYCAgkPZBYCAgEPZBYIAgMPFgIeB1Zpc2libGVoZAIFDxYCHwBoZAIHDxYCHwBoZAIJDxYCHgVzdHlsZQUjdmVydGljYWwtYWxpZ246bWlkZGxlO2Rpc3BsYXk6bm9uZTtkAgMPDxYCHwBoZGQCBw9kFghmD2QWAgINDxYCHgVjbGFzcwUQc2luZ2xlU2Nob29sTGlzdBYCAgEPZBYCAgEPEGQPFgFmFgEQBQ5EZWZhdWx0IERvbWFpbgUIUGlubmFjbGVnZGQCAg9kFgICEw9kFgICAQ9kFgICAQ8QZGQWAGQCBw8PFgIeBFRleHQFJFBpbm5hY2xlIEdyYWRlYm9vayAyMDEzIFdpbnRlciBCcmVha2RkAggPDxYCHwMFMUNvcHlyaWdodCAoYykgMjAxMyBTY2FudHJvbi4gQWxsIHJpZ2h0cyByZXNlcnZlZC5kZGTcMHMIJMuSJf69zVMkdt1fFUa1bQ%3D%3D&__VIEWSTATEGENERATOR=3853A6CF&__EVENTVALIDATION=%2FwEdAAbAOWVG2QbK1noAKI5GkbEc5kBM8zNaqfLGz57%2BS8bBjbNqKG4ze75wfDF3jGnz2WYcsEDYVnMNViQNPbidd3pjcYtsyIuv%2Bgw5n%2FjqOEufoTmvJzX9Oh%2BiBZ%2BEzcXDTVC6%2FcIOfErFw2bfJNGE2UnPNMkNCA%3D%3D&ctl00%24ContentPlaceHolder%24Username="+username+"&ctl00%24ContentPlaceHolder%24Password="+password+"&ctl00%24ContentPlaceHolder%24lstDomains=Pinnacle&ctl00%24ContentPlaceHolder%24LogonButton=Sign+in&PageUniqueId=117f0958-a7e6-4442-86fb-ba8a3e4106ea";
		return result;
	  }
	 
	  public List<String> getCookies() {
		return cookies;
	  }
	 
	  public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	  }
}