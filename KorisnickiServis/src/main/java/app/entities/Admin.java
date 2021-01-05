package app.entities;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

	public Admin() {
	}

	public Admin(String username, String sifra) {
		super(username, sifra);
	}

}
