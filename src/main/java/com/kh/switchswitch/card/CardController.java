package com.kh.switchswitch.card;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("card")
public class CardController {

	@GetMapping("card-form")
	public void cardForm() {}
}
