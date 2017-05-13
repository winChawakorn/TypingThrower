package game;

public class Player {
	private String name;
	private int HP;
	private int damage;

	public Player(String name, int HP, int damage) {
		this.name = name;
		this.HP = HP;
		this.damage = damage;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		this.HP = HP;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
