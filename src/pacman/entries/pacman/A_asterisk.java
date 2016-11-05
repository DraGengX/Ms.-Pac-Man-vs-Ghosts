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

public class A_asterisk {
    public Vector<Game> nodes=new Vector();
    public Vector<Integer> scores=new Vector();
    public Vector<MOVE> first_moves=new Vector();
    int max_node;
    A_asterisk(int mn)
    {
        max_node=mn;
    }
    public int getMinfIndex()
    {
        int i;
        int min_score=Integer.MAX_VALUE,max_index=0;
        for(i=0;i<scores.size();i++)
        {
            if(min_score>scores.get(i))
            {
                min_score=scores.get(i);
                max_index=i;
            }
        }
        return max_index;
    }
    public int h(Game game)
    {
        int pills=game.getPillIndices().length;
        int power_pills=game.getPowerPillIndices().length;
        int ghosts=0;
        int res=0;
        ghosts+=game.wasGhostEaten(Constants.GHOST.PINKY)?1:0;
        ghosts+=game.wasGhostEaten(Constants.GHOST.INKY)?1:0;
        ghosts+=game.wasGhostEaten(Constants.GHOST.SUE)?1:0;
        ghosts+=game.wasGhostEaten(Constants.GHOST.BLINKY)?1:0;
        res+=pills*10;
        
        return res;
    }
    public int f(Game game)
    {
        int res=0;
        res-=game.getScore();
        if(game.wasPacManEaten())
            res+=1000000;
        res+=h(game);
        return res;
        
    }
    public MOVE getMove(Game game,long timeDue)
    {
        StarterGhosts cur_ghost_moves=new StarterGhosts();
      
        for(MOVE fm:game.getPossibleMoves(game.getPacmanCurrentNodeIndex()))
        {
            Game next_game=game.copy();
            next_game.advanceGame(fm,cur_ghost_moves.getMove(next_game,timeDue));
            nodes.add(next_game);
            scores.add(f(next_game));
            first_moves.add(fm);
        }
        
        
        while(nodes.size()>0&&nodes.size()<max_node)
        {
            int i=getMinfIndex();
            int end=scores.size()-1;
            MOVE cur_first_move=first_moves.remove(i);
            int cur_score=scores.remove(i);
            Game cur_game=nodes.remove(i);
            for(MOVE next_move:cur_game.getPossibleMoves(cur_game.getPacmanCurrentNodeIndex()))
            {
                Game next_game=cur_game.copy();
                next_game.advanceGame(next_move,cur_ghost_moves.getMove(next_game,timeDue));
                nodes.add(next_game);
                scores.add(f(next_game));
                first_moves.add(cur_first_move);
            }
               
        }
        int max_score=0;
        MOVE res=MOVE.NEUTRAL;
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).getScore()>max_score)
            {
                max_score=nodes.get(i).getScore();
                res=first_moves.get(i);
            }
        }
        
        return res;
    }
    
}
