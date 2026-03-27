package DB;

public class Singleton {
    private final Singleton instance;
    private final Adapter adapter;

    public Singleton(){
        this.instance = this;
        this.adapter = new Adapter();
    }
    public Singleton(Singleton instance){
        this.instance = instance;
        this.adapter = new Adapter();
    }

    public Singleton getInstance(){
        return this.instance;
    }

    public void prueba(){
        adapter.prueba();
    }
}
