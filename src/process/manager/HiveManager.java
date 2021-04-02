package process.manager;

import java.util.ArrayList;

import data.Bee;
import data.Coordinate;
import data.Environment;
import data.Hive;
import data.Insect;
import data.Nest;

public class HiveManager implements NestManager {

	private Hive nest;
	private int waitTime = 0;
	private NestManagerState state = NestManagerState.WANDERING;

	public HiveManager(Hive nest) {
		this.nest = nest;
	}

	@Override
	public void update() {

		switch (state) {
		case WANDERING:
			idle();
			break;

		case REPRODUCTION:
			reproduce();
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + state);
		}
	}

	@Override
	public void reproduce() {
		// TODO
	}
	
	@Override
	public void idle() {
		addInsectNest();
		
		int currentInsects = getNest().getCurrentInsects();
		
		if( currentInsects > 2) {
			setWaitTime(getNest().getTimeReproduction());
			//TODO stop insects
			setState(NestManagerState.REPRODUCTION);
		}
		
	}
	
	public void addInsectNest() {
		//Use getEnvironment method
		ArrayList<Insect> insects = Environment.getInstance().getInsects();
		Coordinate nestPosition = getNest().getPosition();

		double abscissa;
		double ordinate;

		for (Insect insect : insects) {
			abscissa = insect.getCurrentPosition().getAbscissa();
			ordinate = insect.getCurrentPosition().getOrdinate();
			
			if ( (nestPosition.getAbscissa() == abscissa) && 
					(nestPosition.getOrdinate() == ordinate) ) {
				nest.addBee((Bee) insect);
				nest.incrementCurrentInsects();
				
			}
		}
	}

	@Override
	public Nest getNest() {
		return nest;
	}
	
	public NestManagerState getState() {
		return state;
	}

	public void setState(NestManagerState state) {
		this.state = state;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

}
