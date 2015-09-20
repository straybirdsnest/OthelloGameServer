package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import otakuplus.straybird.othellogameserver.models.GameTable;

@RestController
public class GameHallController {
	
	@RequestMapping(value = "/GameTable/{tableId}", method = RequestMethod.GET)
	GameTable gameTable(@PathVariable("tableId") int tableId){
		GameTable gameTable = new GameTable();
		return gameTable;
	}

}
