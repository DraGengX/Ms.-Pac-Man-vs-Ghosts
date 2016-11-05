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
import pacman.entries.*;


/**
 *
 * @author student
 */
public class mutation {
    mutation(int d,int p,int r)
    {
        depth=d;
        population=p;
        round=r;
        miu=population/2;
        lamda=population-miu;
    }
    Random rand=new Random();
    tools mytool=new tools();
    MOVE[][] variation(MOVE[][] survivors)
    {
        int i,j,k;
        MOVE[][] res=new MOVE[population][depth];
        for(i=0;i<survivors.length;i++)
        {
            res[i]=survivors[i].clone();
        }
        for(i=0;i<survivors.length;i++)
        {
            MOVE[] cur=survivors[i].clone();
            j=rand.nextInt(depth);
            MOVE rand_move=mytool.getRandMove();
            while(rand_move==cur[j])
            {
                rand_move=mytool.getRandMove();
            }
            cur[j]=rand_move;
            res[i+survivors.length]=cur;
        }
        return res;
    }
    int population,depth,round,miu,lamda;
    MOVE getMove(Game game, long timeDue)
    {
        int i,j,k;
        
        
        MOVE[][] p=new MOVE[population][depth];
        for(i=0;i<population;i++)
        {
            p[i]=mytool.generateRandMoveSeq(game,timeDue,depth);
        }
        
        int step=0;
        while(step<round)
        {
            step++;
            MOVE[][] survivors=mytool.getSurvivors(game,p,miu,timeDue,depth);
            
            p=variation(survivors);
        
        }
        
        return p[0][0];
    }
    
}
