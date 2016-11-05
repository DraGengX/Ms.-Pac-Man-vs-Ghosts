/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entries.pacman;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import java.util.*;
import pacman.game.Constants;
import pacman.controllers.examples.*;

/**
 *
 * @author student
 */
public class iterative_deepening {
    int depth_limit;
    iterative_deepening(int d)
    {
        depth_limit=d;
    }
    public MOVE getMove(Game game,long timeDue)
    {
        
        int cur_depth=1;
        int i;
        Vector<Integer> score=new Vector();
        Vector<MOVE> fm=new Vector();
        for(cur_depth=1;cur_depth<depth_limit;cur_depth++)
        {
            DFS cur_dfs=new DFS(cur_depth);
            cur_dfs.getMove(game, timeDue);
            for(i=0;i<cur_dfs.fm.size();i++)
            {
                score.add(cur_dfs.score.get(i));
                fm.add(cur_dfs.fm.get(i));
            }
        }
        MOVE best_move=MOVE.NEUTRAL;
        int best_score=0;
    
        for(i=0;i<score.size();i++)
        {
            if(best_score<score.get(i))
            {
                best_score=score.get(i);
                best_move=fm.get(i);
            }
        }
        return best_move;
    }
}
