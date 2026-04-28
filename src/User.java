/**
 * Base class for all users in the system.
 */
public abstract class User {

    protected String name;
    protected String nick;
    protected String password;
    protected boolean blocked;

    public User(String name, String nick, String password) {
        if (password.length() < 8 || password.length() > 12) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres");
        }

        this.name = name;
        this.nick = nick;
        /**
         * Aqui deberia de haber una comprobación de la contraseña
         */
        this.password = password;
        this.blocked = false;
    }

    public String getNick() {
        return nick;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        blocked = true;
    }

    public void unblock() {
        blocked = false;
    }
}