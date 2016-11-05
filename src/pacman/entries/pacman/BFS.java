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
public class BFS {
    private MOVE myMove=MOVE.NEUTRAL;
    private Queue<Game> states=new LinkedList();
    private Queue depth=new LinkedList();
    private int depth_limit;
    private Queue<MOVE> first_moves=new LinkedList();
    public Vector<Integer> score=new Vector();
    public Vector<MOVE> fm=new Vector();
    
    BFS(int dl)
    {
        depth_limit=dl;
    }
    public MOVE getMove(Game game, long timeDue) 
    {
        
        long startTime = System.currentTimeMillis();
        StarterGhosts cur_ghost_moves=new StarterGhosts();
        
        for(MOVE first_move:game.getPossibleMoves(game.getPacmanCurrentNodeIndex()))
        {
            Game cur=game.copy();
            
            cur.advanceGame(first_move,cur_ghost_moves.getMove(cur,timeDue));
            
            states.add(cur);
            first_moves.add(first_move);
            depth.add(1);
            
        }
        
        while(states.size()>0)
        {
            //System.out.println(states.size());
            
            if((int)(depth.peek())>=(depth_limit))
            {
                
                if(states.peek().wasPacManEaten())
                {
                    first_moves.remove();
                    depth.remove();
                    states.remove();
                }
                else
                {
                    score.add(states.remove().getScore());
                    fm.add(first_moves.remove());
                    depth.remove();
                }
            }
            else
            {
                if(states.peek().wasPacManEaten())
                {
                    first_moves.remove();
                    depth.remove();
                    states.remove();
                }
                else
                {
                    
                    
                    Game cur=states.remove().copy();
                    int cur_depth=(int)depth.remove();
                    MOVE cur_first_move=first_moves.remove();
                    for(MOVE next_move:cur.getPossibleMoves(cur.getPacmanCurrentNodeIndex()))
                    {
                        Game next=cur.copy();
                        next.advanceGame(next_move,cur_ghost_moves.getMove(cur,timeDue));
                        first_moves.add(cur_first_move);
                        depth.add(cur_depth+1);
                        states.add(next);
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
