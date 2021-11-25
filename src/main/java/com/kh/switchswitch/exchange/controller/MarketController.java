package com.kh.switchswitch.exchange.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.service.CardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("market")
public class MarketController {
	
	private final CardService cardService;
	
	@GetMapping("cardmarket")
	public void exchangeCard() {}

	@ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
//    @GetMapping(value = "getcard", produces = "application/json; charset=utf8")
    @GetMapping("getcard")
	public String responseBodyJason(HttpServletResponse response) {
		ObjectMapper mapper = new ObjectMapper();
		
		response.addHeader("Access-Control-Allow-Origin","*");
		
		List<Card> allCard = cardService.selectAllCard();
		String json = "";
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allCard);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("json={}" ,json);
		return json;
	}
	
}
