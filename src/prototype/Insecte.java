package prototype;

public abstract class Insecte {
	
	private int life;
	private int food;
	private int water;
	private int attack;
	
	public Insecte(){
		this.life=10;
		this.food=10;
		this.water=10;
		this.attack=1;
	}
	
	public abstract void eat();
	
	public abstract void drink();
}
