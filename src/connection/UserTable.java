package connection;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is a table form in the database. This table collect all user detail.
 * @author Vittunyuta Maeprasart
 *
 */
@DatabaseTable(tableName = "userdata")
public class UserTable implements Serializable {

	@DatabaseField(id = true)
	private String Username;
	@DatabaseField(canBeNull = false)
	private String Password;
	@DatabaseField
	private String CharacterName;
	@DatabaseField
	private int HP;
	@DatabaseField
	private int ATK;
	@DatabaseField
	private int Money;
	@DatabaseField
	private int WinRound;
	@DatabaseField
	private int LoseRound;
	@DatabaseField
	private double WPM;

	/**
	 * no-arg constructor for ORMLite
	 */
	public UserTable() {
		// ORMLite needs a no-arg constructor
	}

	/**
	 * creating new user
	 * 
	 * @param name
	 *            = user name
	 * @param password
	 *            = password
	 */
	public UserTable(String name, String password, String characterName) {
		this.Username = name;
		this.Password = password;
		this.CharacterName = characterName;
		this.HP = 1000;
		this.ATK = 10;
		this.Money = 0;
		this.WinRound = 0;
		this.LoseRound = 0;
		this.WPM = 0.0;
	}

	// all getter and setter methods of all fields.
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getCharacterName() {
		return CharacterName;
	}

	public void setCharacterName(String characterName) {
		CharacterName = characterName;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getATK() {
		return ATK;
	}

	public void setATK(int aTK) {
		ATK = aTK;
	}

	public int getMoney() {
		return Money;
	}

	public void setMoney(int money) {
		Money = money;
	}

	public int getWinRound() {
		return WinRound;
	}

	public void setWinRound(int winRound) {
		WinRound = winRound;
	}

	public int getLoseRound() {
		return LoseRound;
	}

	public void setLoseRound(int loseRound) {
		LoseRound = loseRound;
	}

	public double getWPM() {
		return WPM;
	}

	public void setWPM(double wPM) {
		WPM = wPM;
	}

}
