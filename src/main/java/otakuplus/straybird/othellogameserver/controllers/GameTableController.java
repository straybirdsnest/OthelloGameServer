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

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/enter", method = RequestMethod.POST)
    public void enterGameTable(@PathVariable Long gameTableId,@PathVariable Long seatId, @RequestBody Long userId)
    {
        User user = userRepository.findOne(userId);
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if(user != null && gameTable != null){
            if(seatId == 0L){
                gameTable.setPlayerA(user);
            }else if(seatId == 1L){
                gameTable.setPlayerB(user);
            }
            gameTableRepository.save(gameTable);
        }
    }

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/leave", method = RequestMethod.POST)
    public void leaveGameTable(@PathVariable Long gameTableId, @PathVariable Long seatId, @RequestBody Long userId){
        User user = userRepository.findOne(userId);
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if(user != null && gameTable != null){
            if(seatId == 0L){
                gameTable.setPlayerA(null);
            }else if(seatId == 1L){
                gameTable.setPlayerB(null);
            }
            gameTableRepository.save(gameTable);
        }
    }
}
