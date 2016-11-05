package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove=MOVE.NEUTRAL;
	private tools mytool=new tools();
	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		DFS myDFS=new DFS(12);
                BFS myBFS=new BFS(12);
                iterative_deepening myid=new iterative_deepening(10);
                A_asterisk myA=new A_asterisk(1000);
                annealing myAnnealing=new annealing(10,3000);
                hill_climber myhc=new hill_climber(20,500);
                mutation myMutation=new mutation(10,10,100);
                crossover myCrossover=new crossover(10,100,100);
                myMove=myCrossover.getMove(game, timeDue);
                mytool.displayTimeLeft(timeDue);
		return myMove;
	}
}


