package Game;

import java.io.Serializable;

/**
 * Admin user.
 */
public class Admin extends User implements Serializable {

    public Admin(String name, String nick, String password) {
        super(name, nick, password);
    }

}