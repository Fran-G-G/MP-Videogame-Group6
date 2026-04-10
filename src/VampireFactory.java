import java.util.Scanner;

public class VampireFactory implements AbstractFactory {

    private Scanner scanner;

    @Override
    public void createProduct() {
        System.out.print("Nombre del vampiro: ");
        String name = scanner.nextLine();

        System.out.print("Edad del vampiro: ");
        int age = Integer.parseInt(scanner.nextLine());

        int health = 5;
        int power = 3;
        Vampire vampire = new Vampire(name, health, power, age);


        EquipmentBuilder equipmentBuilder = new EquipmentBuilder();
        equipmentBuilder.build();


        vampire.addMinions();

    }
}
