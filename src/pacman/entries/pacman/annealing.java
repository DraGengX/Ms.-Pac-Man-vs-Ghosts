/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entries.pacman;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import java.util.*;
import pacman.controllers.examples.StarterGhosts;
/**
 *
 * @author student
 */
public class annealing {
    annealing(int d,int m)
    {
        depth_limit=d;
        mutation_limit=m;
    }
    int depth_limit,mutation_limit;
    int l=0;
    Vector<MOVE> seq=new Vector();
    
    StarterGhosts cur_ghost_moves=new StarterGhosts();
    
    private void generateInitial(Game game,long timeDue)
    {
        int i;
        
        Game cur=game.copy();
        for(i=0;i<depth_limit;i++)
        {
            MOVE[] next_possible_moves=cur.getPossibleMoves(game.getPacmanCurrentNodeIndex());
            int moves_size=next_possible_moves.length;
            int chosen=rand.nextInt(moves_size);
            seq.add(next_possible_moves[chosen]);
            cur.advanceGame(next_possible_moves[chosen],cur_ghost_moves.getMove(cur,timeDue));  
                        
        }
        
    }
    Random rand = new Random();
    MOVE getRandMove()
    {
        int index=rand.nextInt(5);
        switch (index){
            case 1: return MOVE.UP;
            case 2: return MOVE.RIGHT;
            case 3: return MOVE.DOWN;
            case 4: return MOVE.LEFT;
            case 0: return MOVE.NEUTRAL;
            
        }
        return MOVE.NEUTRAL;
    }
    boolean whetherChange(double p)
    {
        double a=rand.nextDouble();
        if(a<=p)
            return true;
        else
            return false;
    }
    int getScoreAfter(Game game,Vector<MOVE> moves,long timeDue)
    {
        Game cur=game.copy();
        int i;
        for(i=0;i<moves.size();i++)
        {
            
            cur.advanceGame(moves.get(i), cur_ghost_moves.getMove(cur,timeDue));
            
        }
        return cur.getScore();
    }
    public MOVE getMove(Game game,long timeDue)
    {
        generateInitial(game,timeDue);
        int i,m=0;
        l=seq.size();
        Game cur=game.copy();
        if(l<depth_limit)
            System.out.println("DaDa!");
        while(m<mutation_limit)
        {
            int chosen=rand.nextInt(l);
            m++;
            Vector<MOVE> next_seq=new Vector();
            next_seq=(Vector)seq.clone();
            next_seq.set(chosen,getRandMove());
            int score_before=getScoreAfter(cur,seq,timeDue);
            int score_after=getScoreAfter(cur,next_seq,timeDue);
            
            int diff=score_after-score_before;
            if(diff>0)
                continue;
            else
            {
                double p=Math.exp(diff/1000);
                if(whetherChange(p))
                    seq=next_seq;
                
            }
            
            
        }
        return seq.get(0);
        
        
    }
}
