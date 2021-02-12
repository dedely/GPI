package process;

import java.util.ArrayList;
import java.util.HashMap;

import data.Ant;
import data.Bee;
import data.Coordinate;
import data.Environment;
import data.Insect;
import data.NaturalResource;
import data.TileCoordinate;
import process.manager.AntManager;
import process.manager.BeeManager;
import process.manager.BugManager;
import test.manual.SimuPara;

public class Simulation {
	private SimulationEntry simulationEntry;
	private Environment environment;
	private SimulationState state;
	private Integer currentInsectId = 1;
	private Integer currentResourceId = 1;

	private HashMap<Integer, BugManager> bugManagersByIds = new HashMap<Integer, BugManager>();
	private ArrayList<Integer> deadInsectsIds = new ArrayList<Integer>();

	public Simulation(SimulationEntry simulationEntry) {
		this.simulationEntry = simulationEntry;
		buildSimulation();
	}

	private void buildSimulation() {
		int size = simulationEntry.getMapSize();
		Integer[][] map = new Integer[size][size];

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				map[y][x] = Integer.valueOf((int) (Math.random() * (SimuPara.TILESET_SIZE)));
			}
		}

		environment = Environment.getInstance();
		environment.setMap(map);
		int insectCount = simulationEntry.getInsectCount();
		createInsects(insectCount);
		createResources();
	}

	private void createInsects(int insectCount) {
		ArrayList<Insect> insects = new ArrayList<Insect>();
		for (int i = 1; i < insectCount + 1; i++) {
			Ant ant = new Ant(getNextInsectId(), new Coordinate(15 * i, 15 * i), i, i, i, i);
			Bee bee = new Bee(getNextInsectId(), new Coordinate(40 * i, 60 * i), i, i, i, i);
			BugManager antManager = new AntManager("1", "peaceful", ant);
			BugManager beeManager = new BeeManager("2", "peaceful", bee);
			insects.add(bee);
			insects.add(ant);
			bugManagersByIds.put(bee.getId(), beeManager);
			bugManagersByIds.put(ant.getId(), antManager);
		}
		environment.setInsects(insects);
	}

	private void createResources() {
		NaturalResource flower = new NaturalResource(NaturalResource.FLOWER, getNextResourceId(), 10, new TileCoordinate(0, 0));
	}

	public void simulate() {
		for (BugManager bugManager : bugManagersByIds.values()) {
			bugManager.update();
		}
	}

	public void add(Insect insect) {
		ArrayList<Insect> insects = environment.getInsects();
		insects.add(insect);
	}

	public void remove(Insect insect) {
		ArrayList<Insect> insects = environment.getInsects();
		insects.remove(insect);
	}

	public void remove(Integer bugManagerId) {
		bugManagersByIds.remove(bugManagerId);
	}

	public void removeAllDeadInsects() {
		for (Integer id : deadInsectsIds) {
			Insect insect = bugManagersByIds.get(id).getInsect();
			environment.getInsects().remove(insect);
			remove(id);
		}
		deadInsectsIds.clear();
	}

	public void addDeadInsect(Integer id) {
		deadInsectsIds.add(id);
	}

	public ArrayList<BugManager> getExplorerManagers() {
		return new ArrayList<BugManager>(bugManagersByIds.values());
	}

	public ArrayList<Insect> getInsects() {
		return environment.getInsects();
	}

	public Environment getEnvironment() {
		return environment;
	}

	public Integer[][] getMap() {
		return environment.getMap();
	}

	public void setState(SimulationState state) {
		this.state = state;
	}

	public boolean isRunning() {
		return state == SimulationState.RUNNING;
	}

	public SimulationState getState() {
		return state;
	}

	private Integer getNextInsectId() {
		return ++currentInsectId;
	}
	
	private Integer getNextResourceId() {
		return ++currentResourceId;
	}
}