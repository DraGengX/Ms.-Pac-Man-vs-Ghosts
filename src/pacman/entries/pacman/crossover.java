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
public class crossover {
    crossover(int d,int p,int r)
    {
        depth=d;
        population=p;
        round=r;
        miu=population/2;
        lamda=population-miu;
    }
    Random rand=new Random();
    tools mytool=new tools();
    int population,depth,round,miu,lamda;
    
    
    MOVE[] createChildFrom(MOVE[] A,MOVE[] B)
    {
        MOVE[] res=new MOVE[A.length];
        int i;
        for(i=0;i<A.length;i++)
        {
            if(i<A.length/2)
                res[i]=A[i];
            else
                res[i]=B[i];
        }
        return res;
        
        
    }
    MOVE[][] variation(MOVE[][] survivors)
    {
        int i,j,k;
        MOVE[][] res=new MOVE[population][depth];
        
        for(i=0;i<survivors.length;i++)
        {
            res[i]=survivors[i].clone();
        }
        
        for(;i<res.length;i++)
        {
            
            int A=rand.nextInt(survivors.length);
            int B=rand.nextInt(survivors.length);
            while(A==B)
            {
                A=rand.nextInt(survivors.length);
                B=rand.nextInt(survivors.length);
            }
            res[i]=createChildFrom(survivors[A],survivors[B]);
            
        }
        //System.out.println(res[0][0]);
        return res;
        
        
    }
    MOVE getMove(Game game, long timeDue)
    {
        int i,j,k;
        
        
        MOVE[][] p=new MOVE[population][depth];
        for(i=0;i<population;i++)
        {
            p[i]=mytool.generateRandMoveSeq(game,timeDue,depth).clone();
        
        }
        //System.out.println(p[0][0]);
        int step=0;
        while(step<round)
        {
            step++;
            
            MOVE[][] survivors=mytool.getSurvivors(game,p,miu,timeDue-100,depth);
            p=variation(survivors).clone();
            
        }
        //mytool.displayTimeLeft(timeDue);
        
        return p[0][0];
    }
    
    
}
