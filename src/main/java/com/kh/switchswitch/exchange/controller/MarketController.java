package com.kh.switchswitch.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.card.model.dto.SearchCard;
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

//	전체카드조회
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

//	카테고리
	@CrossOrigin("*")
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("category")
    @ResponseBody
    public String search(@RequestBody SearchCard searchCard, HttpServletResponse response) throws JsonMappingException, JsonProcessingException {
        
		log.info("sting={}" ,searchCard);
		
        List<Card> allCard = cardService.selectCardTrim(searchCard);
        
        String json = new Gson().toJson(allCard);
        log.info("json={}" ,json);
        
        return json;
    }
	
	
	
}
