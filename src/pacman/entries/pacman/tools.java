/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entries.pacman;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import java.util.*;
import pacman.controllers.examples.*;
/**
 *
 * @author student
 */
public class tools {
    Random rand=new Random();
    StarterGhosts cur_ghost_moves=new StarterGhosts();
    public MOVE[] generateRandMoveSeq(Game game,long timeDue,int depth)
    {
        int i;
        MOVE[] seq=new MOVE[depth];
        Game cur=game.copy();
        for(i=0;i<depth;i++)
        {
            MOVE[] next_possible_moves=cur.getPossibleMoves(game.getPacmanCurrentNodeIndex());
            int moves_size=next_possible_moves.length;
            int chosen=rand.nextInt(moves_size);
            seq[i]=(next_possible_moves[chosen]);
            cur.advanceGame(next_possible_moves[chosen],cur_ghost_moves.getMove(cur,timeDue));  
                        
        }
        return seq;
        
    }
    public MOVE getRandMove()
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
    
    
    public int getScoreAfter(Game game,MOVE[] moves,long timeDue)
    {
        Game cur=game.copy();
        int i;
        for(i=0;i<moves.length;i++)
        {
            
            cur.advanceGame(moves[i], cur_ghost_moves.getMove(cur,timeDue));
            
        }
        return cur.getScore();
    }
    
    public MOVE[] getAMutation(MOVE[] seq)
    {
        int i,j,l;
        l=seq.length;
        i=rand.nextInt(l);
        MOVE[] res=seq.clone();
        res[i]=getRandMove();
        return res;
    }
    public void displayTimeLeft(long timeDue)
    {
        long cur_time=System.currentTimeMillis();
        long time_left=timeDue-cur_time;
        System.out.println("You finish before due by "+time_left);
    }
    
    
    public void displayMoveSeq(MOVE[] seq)
    {
        int i,j,k;
        for(i=0;i<seq.length;i++)
        {
            System.out.print(seq[i]+" ");
        }
        System.out.println();
    }
    public MOVE[][] getSurvivors(Game game,MOVE[][] p,int miu,long timeDue,int depth)
    {
        int i,j,k;
        int population=p.length;
        int[] scores=new int[population],sorted_scores=new int[population];
        MOVE[][] res=new MOVE[miu][depth];
        
        for(i=0;i<population;i++)
        {
            //displayMoveSeq(p[i]);
            scores[i]=this.getScoreAfter(game,p[i],timeDue);
            sorted_scores[i]=scores[i];
        }
        Arrays.sort(sorted_scores);
        
        int benchmark=sorted_scores[population-miu-1];
        
        int res_point=0;
        for(i=0;i<scores.length;i++)
        {
            
            if(scores[i]>=benchmark)
            {
                //System.out.println("got one!");
                res[res_point++]=p[i].clone();
                
            }
            
            if(res_point==miu)
                break;
        }
       
        return res;
    }
}
