package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import otakuplus.straybird.othellogameserver.models.GameTable;
import otakuplus.straybird.othellogameserver.models.GameTableRepository;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserRepository;
import otakuplus.straybird.othellogameserver.network.OthelloGameSocketIOServer;

@RestController
public class GameTableController {

    @Autowired
    private OthelloGameSocketIOServer othelloGameSocketIOServer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameTableRepository gameTableRepository;

    @RequestMapping(value = "/api/gametable/{id}/enter", method = RequestMethod.POST)
    public void enterGameTable(@PathVariable Long gameTableId, @RequestBody Long userId)
    {
        User user = userRepository.findOne(userId);
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if(user != null && gameTable != null){
            if(gameTable.getPlayerA() == null){
                gameTable.setPlayerA(user);
            }else if(gameTable.getPlayerB() == null){
                gameTable.setPlayerB(user);
            }
            gameTableRepository.save(gameTable);
        }
    }

    @RequestMapping(value = "/api/gametable/{id}/leave", method = RequestMethod.POST)
    public void leaveGameTable(@PathVariable Long gameTableId, @RequestBody Long userId){

    }
}
