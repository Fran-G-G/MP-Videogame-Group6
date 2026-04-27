package Game;

/**
 * Base Factory for creating the common part of all types of minions.
 */
public abstract class MinionFactory implements AbstractFactory<AbstractMinion> {

    @Override
    public abstract AbstractMinion createProduct();

}
