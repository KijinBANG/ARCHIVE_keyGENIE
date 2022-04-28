package kijin.bang.keygenie.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class ChatBotServiceImpl implements ChatBotService {

	@Override
	public String download(HttpServletRequest request, HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		try {
			//String addr = "http://192.168.0.8:9000/";
			String question = URLEncoder.encode(request.getParameter("question"), "UTF-8");

			String addr = "http://localhost:9000/chatbot?question=" + question;
			System.out.println(addr);
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(20000);
				conn.setUseCaches(false);
				System.out.println("1");
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStreamReader isr = new InputStreamReader(conn.getInputStream());
					BufferedReader br = new BufferedReader(isr);
					System.out.println("2");
					while (true) {
						String line = br.readLine();
						System.out.println("line:" + line);
						if (line == null) {
							break;
						}
						sb.append(line + "\n");
					}
					br.close();
					conn.disconnect();
				}
			}
		} catch (Exception e) {
			System.out.println("가져오기 실패:" + e.getMessage());
		}
		return sb.toString();
	}
}
