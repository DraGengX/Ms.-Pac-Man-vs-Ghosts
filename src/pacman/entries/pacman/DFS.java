/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman.entries.pacman;

import pacman.game.Constants.MOVE;
import pacman.game.Game;
import java.util.*;
import java.lang.*;
import pacman.controllers.examples.*;
public class DFS {
    private MOVE myMove=MOVE.NEUTRAL;
    private Stack<Game> states=new Stack();
    private Stack depth=new Stack();
    private int depth_limit;
    private Stack<MOVE> first_moves=new Stack();
    public Vector<Integer> score=new Vector();
    public Vector<MOVE> fm=new Vector();
    
    DFS(int dl)
    {
        depth_limit=dl;
    }
    public MOVE getMove(Game game, long timeDue) 
    {
        
        
        StarterGhosts cur_ghost_moves=new StarterGhosts();
        
        for(MOVE first_move:game.getPossibleMoves(game.getPacmanCurrentNodeIndex()))
        {
            Game cur=game.copy();
            
            cur.advanceGame(first_move,cur_ghost_moves.getMove(cur,timeDue-1000));
            
            states.push(cur);
            first_moves.push(first_move);
            depth.push(1);
            
        }
        
        while(states.size()>0)
        {
            //System.out.println(states.size());
            
            if((int)(depth.peek())>=(depth_limit))
            {
                
                if(states.peek().wasPacManEaten())
                {
                    first_moves.pop();
                    depth.pop();
                    states.pop();
                }
                else
                {
                    score.add(states.pop().getScore());
                    fm.add(first_moves.pop());
                    depth.pop();
                }
            }
            else
            {
                if(states.peek().wasPacManEaten())
                {
                    first_moves.pop();
                    depth.pop();
                    states.pop();
                }
                else
                {
                    
                    
                    Game cur=states.pop().copy();
                    int cur_depth=(int)depth.pop();
                    MOVE cur_first_move=first_moves.pop();
                    for(MOVE next_move:cur.getPossibleMoves(cur.getPacmanCurrentNodeIndex()))
                    {
                        Game next=cur.copy();
                        next.advanceGame(next_move,cur_ghost_moves.getMove(cur,timeDue-1000));
                        first_moves.push(cur_first_move);
                        depth.push(cur_depth+1);
                        states.push(next);
                    }
                }
            }
        }
        int i,best_score=-1;
        MOVE best_move=MOVE.NEUTRAL;
         
        for(i=0;i<score.size();i++)
        {
            if(best_score<(score.get(i)))
            {
                best_score=score.get(i);
                best_move=fm.get(i);
            }
        }
        
	return best_move;
    }
}
