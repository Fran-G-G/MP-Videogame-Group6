/**
 * Base Factory for creating the common part of all types of minions.
 */
public abstract class MinionFactory implements AbstractFactory {

    @Override
    public abstract void createProduct();

}
