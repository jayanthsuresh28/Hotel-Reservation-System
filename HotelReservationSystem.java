import java.io.*;
import java.util.*;

class Room {
    int roomNo;
    String category;
    boolean available;

    Room(int roomNo, String category) {
        this.roomNo = roomNo;
        this.category = category;
        this.available = true;
    }
}

class Booking {
    String customerName;
    int roomNo;
    String category;

    Booking(String customerName, int roomNo, String category) {
        this.customerName = customerName;
        this.roomNo = roomNo;
        this.category = category;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);
    }

    static void viewRooms() {
        System.out.println("\nAvailable Rooms");
        System.out.println("-----------------------------");

        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room: " + r.roomNo +
                        " | Category: " + r.category);
            }
        }
    }

    static void bookRoom() {

        viewRooms();

        System.out.print("\nEnter Room Number: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        for (Room r : rooms) {

            if (r.roomNo == roomNo && r.available) {

                System.out.print("Enter Payment Amount: ₹");
                double amount = sc.nextDouble();
                sc.nextLine();

                System.out.println("Payment Successful!");

                r.available = false;

                Booking b = new Booking(name, roomNo, r.category);
                bookings.add(b);

                saveBooking(b);

                System.out.println("Room Booked Successfully.");
                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    static void cancelBooking() {

        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Booking> it = bookings.iterator();

        while (it.hasNext()) {

            Booking b = it.next();

            if (b.roomNo == roomNo) {

                it.remove();

                for (Room r : rooms) {
                    if (r.roomNo == roomNo)
                        r.available = true;
                }

                System.out.println("Booking Cancelled.");
                return;
            }
        }

        System.out.println("Booking Not Found!");
    }

    static void viewBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No Bookings Found.");
            return;
        }

        System.out.println("\nBooking Details");
        System.out.println("-----------------------------");

        for (Booking b : bookings) {

            System.out.println("Customer : " + b.customerName);
            System.out.println("Room No  : " + b.roomNo);
            System.out.println("Category : " + b.category);
            System.out.println("-----------------------------");
        }
    }
    static void saveBooking(Booking b) {

        try {

            FileWriter fw = new FileWriter("bookings.txt", true);

            fw.write(b.customerName + ","
                    + b.roomNo + ","
                    + b.category + "\n");

            fw.close();

        } catch (Exception e) {

            System.out.println("File Error");

        }

    }

}