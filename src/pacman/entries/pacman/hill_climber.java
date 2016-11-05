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
import pacman.game.Constants;

/**
 *
 * @author student
 */
public class hill_climber {
    hill_climber(int d,int m)
    {
        depth_limit=d;
        mutation_limit=m;
    }
    int depth_limit,mutation_limit;
    int l=0;
    Vector<MOVE> seq=new Vector();
    Random rand = new Random();
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
    Vector<MOVE> copyArray(MOVE[] moves)
    {
        Vector<MOVE> res=new Vector();
        for(int i=0;i<moves.length;i++)
        {
            res.add(moves[i]);
        }
        return res;
    }
    MOVE[] copyVector(Vector<MOVE> moves)
    {
        int i;
        int l=moves.size();
        MOVE[] res=new MOVE[l];
        for(i=0;i<l;i++)
        {
            res[i]=moves.get(i);
        }
        return res;
    }
    private static final int MIN_DISTANCE=20;
    public MOVE getMove(Game game, long timeDue)
    {
        
        generateInitial(game,timeDue);
        int m=0;
        int i,j,k;
        while(m<mutation_limit)
        {
            Game cur=game.copy();
            m++;
            MOVE[][][] neighbor=new MOVE[depth_limit][5][depth_limit];
            int[][] scores=new int[depth_limit][5];
            for(i=0;i<depth_limit;i++)
            {
                for(j=0;j<5;j++)
                {
                    neighbor[i][j]=copyVector(seq);
                    if(j==0)
                        neighbor[i][j][i]=MOVE.UP;
                    else if(j==1)
                        neighbor[i][j][i]=MOVE.RIGHT;
                    else if(j==2)
                        neighbor[i][j][i]=MOVE.LEFT;
                    else if(j==3)
                        neighbor[i][j][i]=MOVE.NEUTRAL;
                    else if(j==4)
                        neighbor[i][j][i]=MOVE.DOWN;                
                }
            }
            for(i=0;i<depth_limit;i++)
            {
                for(j=0;j<5;j++)
                {
                    scores[i][j]=getScoreAfter(cur,copyArray(neighbor[i][j]),timeDue);
                
                }
            }
            int best_score=Integer.MIN_VALUE;
            MOVE[] best_move=new MOVE[depth_limit];
            int cur_score=getScoreAfter(cur,seq,timeDue);
            for(i=0;i<depth_limit;i++)
            {
                for(j=0;j<5;j++)
                {
                    if(best_score<scores[i][j])
                    {
                        best_score=scores[i][j];
                        best_move=neighbor[i][j].clone();
                    }
                }
            }
            if(best_score>cur_score)
                seq=copyArray(best_move);
            else
                break;
            
            
        }
        System.out.println("m: "+m);
        return seq.get(0);
    }
    
    
}
