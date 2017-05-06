package Game;

public class Player {
	private int HP;
	private int damage;

	public Player(int HP, int damage) {
		this.HP = HP;
		this.damage = damage;
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
