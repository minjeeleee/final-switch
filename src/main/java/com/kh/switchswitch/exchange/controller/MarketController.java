package com.kh.switchswitch.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
		
		for (Card content : allCard) {
            Date regDt = content.getRegDate();
            SimpleDateFormat dt = new SimpleDateFormat("MM.dd");
            String dateFormat = dt.format(regDt);
            content.setDateParse(dateFormat);
        }
		
		String json = new Gson().toJson(allCard);
		
		log.info("json={}" ,json);
		return json;
	}
	
	@CrossOrigin("*")
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("category")
    @ResponseBody
    public String search(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        
//		response.addHeader("Access-Control-Allow-Origin","*");
		log.info("sting={}" ,param);
        
        List<Card> allCard = cardService.searchCategoryCard(param.get("category").toString());
        
        String json = new Gson().toJson(allCard);
        log.info("json={}" ,json);
        
        return json;
    }
	
	
	
}
