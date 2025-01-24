import java.util.ArrayList;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePrice;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePrice) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public double calculateprice(int days) {
        return basePrice * days;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

};

class Customer {
    private String name;
    private String id;

    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getCustomerId() {
        return id;
    }

    public String getCustomerName() {
        return name;
    }

};

class Rental {
    private Car car;
    private Customer customer;
    private int day;

    public Rental(Car car, Customer customer, int day) {
        this.car = car;
        this.customer = customer;
        this.day = day;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDay() {
        return day;
    }

};

class carSystem {
    private ArrayList<Car> cars;
    private ArrayList<Customer> customers;
    private ArrayList<Rental> rentals;

    public carSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addcar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {

        if (car.getIsAvailable()) {
            car.rent();
            Rental rental = new Rental(car, customer, days);
            rentals.add(rental);

        } else {
            System.out.println("Car is not available.");
        }
    }

    public void returnCar(Car car) {

        Rental rentaltoremove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentaltoremove = rental;
                break;
            }
        }
        if (rentaltoremove != null) {
            rentals.remove(rentaltoremove);
            car.returnCar();
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("Car not found in the rental list.");
        }
    }

    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
            System.out.println("==welcome to car rental system==");
            System.out.println("1. rent a car");
            System.out.println("2. return a car");
            System.out.println("3. exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("==Rent a car==");
                System.out.println("Enter your name: ");
                String customername = scanner.nextLine();

                System.out.println("==Available cars==");
                for (Car car : cars) {
                    if (car.getIsAvailable()) {
                        System.out.println(car.getCarId() + " " + car.getBrand() + " " + car.getModel() + " "
                                + car.getBasePrice());
                    }
                }
                System.out.println("Enter car id you want rent: ");
                String carId = scanner.nextLine();

                System.out.println("Enter number of days you want to rent: ");
                int days = scanner.nextInt();
                scanner.nextLine();

                Customer customer = new Customer("CUS" + (customers.size() + 1), customername);
                customers.add(customer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.getIsAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalprice = selectedCar.calculateprice(days);
                    System.out.println("\n ==rental information==");
                    System.out.println("customer id: " + customer.getCustomerId());
                    System.out.println("customer name: " + customer.getCustomerName());
                    System.out.println("car id: " + selectedCar.getCarId());
                    System.out.println("car brand: " + selectedCar.getBrand());
                    System.out.println("car model: " + selectedCar.getModel());
                    System.out.println("number of days: " + days);
                    System.out.println("total price: " + totalprice);

                    System.out.println("Do you want to confirm the rental? (yes/no)");
                    String confirm = scanner.nextLine();
                    if (confirm.equals("yes")) {
                        rentCar(selectedCar, customer, days);
                        System.out.println("Car rented successfully.");
                    } else {
                        System.out.println("Rental cancelled.");
                    }
                }
                 else {
                    System.out.println("Car not found or not available.");
                }
            } else if (choice == 2) {
                System.out.println("==Return a car==");
                System.out.println("Enter car id you want to return: ");
                String carId = scanner.nextLine();
                Car cartoreturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.getIsAvailable()) {
                        cartoreturn = car;
                        break;
                    }
                }
                Customer customer = null;
                if(cartoreturn!=null){
                    for(Rental rental: rentals){
                        if(rental.getCar()==cartoreturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                }
                if(customer!=null){
                    returnCar(cartoreturn);
                    System.out.println("Car returned successfully." + customer.getCustomerName());
                }
                else{
                    System.out.println("Car not found in the rental list.");
                }
            } else if (choice == 3) {
                break;

            }else{
                System.out.println("Invalid choice.please enter a valid choice.");
            }
            }
            System.out.println("Thank you for using the car rental system.");
        }

    }
};

public class carRentalSystem {

    public static void main(String[] args) {
        System.out.println("Welcome to the Car Rental System!");
        carSystem carsystem = new carSystem();
        Car car1 = new Car("CAR", "Toyota", "Corolla", 100);
        Car car2 = new Car("CAR", "Honda", "Civic", 120);
        Car car3 = new Car("CAR", "Suzuki", "Swift", 80);
        carsystem.addcar(car1);
        carsystem.addcar(car2);
        carsystem.addcar(car3);
        carsystem.menu();
        
    }

}
