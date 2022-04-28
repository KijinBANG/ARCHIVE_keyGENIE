package kijin.bang.keygenie.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kijin.bang.keygenie.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {
	@Autowired
	private ChatBotService chatBotService;
	
	@GetMapping("/chatbot")
	public String chatbot(HttpServletRequest request, HttpServletResponse response){
		String result = chatBotService.download(request, response);
		return result;
	}

}
