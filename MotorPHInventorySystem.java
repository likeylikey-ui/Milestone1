import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Milestone {
    static class MotorphInventory {
        String dateEntered, stockLabel, brand, engineNumber, status;

        // Constructor to initialize item
        MotorphInventory(String d, String s, String b, String e, String st) {
            dateEntered = d;
            stockLabel = s;
            brand = b;
            engineNumber = e;
            status = st;
        }

        // Return a string of item
        public String toString() {
            return String.join(", ", dateEntered, stockLabel, brand, engineNumber, status);
        }
    }

    public static void main(String[] args) throws IOException {
        // Read inventory data from CSV file
        ArrayList<MotorphInventory> inventory = readCSV("Insert CSV file path");
        Scanner scanner = new Scanner(System.in);
        
        // Main menu loop
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Delete Item");
            System.out.println("3. Sort Stocks by Brand");
            System.out.println("4. Search Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Handle user choice
            switch (choice) {
                case 1: addItem(inventory, scanner); break;
                case 2: deleteItem(inventory, scanner); break;
                case 3: sortByBrand(inventory, scanner); break;
                case 4: searchInventory(inventory, scanner); break;
                case 5:
                    // Write inventory data back to CSV
                    writeCSV("C:\\Users\\Christine\\Downloads\\MotorPH Inventory Data - March 2023 Inventory Data.csv", inventory);
                    System.out.println("Exiting program...");
                    return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Read inventory data
    private static ArrayList<MotorphInventory> readCSV(String filename) throws IOException {
        ArrayList<MotorphInventory> inventory = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.readLine(); // Skip header
        String line;
        
        // Create MotorphInventory objects
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5) inventory.add(new MotorphInventory(data[0], data[1], data[2], data[3], data[4]));
        }
        br.close();
        return inventory;
    }

    // Write inventory data
    private static void writeCSV(String filename, ArrayList<MotorphInventory> inventory) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write("Date, Label, Brand, Engine Number, Status\n");
        
        // Write each inventory item to the file
        for (MotorphInventory item : inventory) {
            bw.write(item.toString() + "\n");
        }
        bw.close();
    }

    //add a new item
    private static void addItem(ArrayList<MotorphInventory> inventory, Scanner scanner) {
        // Automatically set the current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        // Automatically set the stock label to "new" and the status to "on-hand"
        String stockLabel = "new";
        String status = "on-hand";

        System.out.print("Enter Stock Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Engine Number: ");
        String engineNumber = scanner.nextLine();

        // Check if the engine number already exists in the inventory
        for (MotorphInventory item : inventory) {
            if (item.engineNumber.equals(engineNumber)) {
                System.out.println("Error: Engine Number already exists in the inventory.");
                return; // Do not add the item if the engine number is already in the inventory
            }
        }

        // Create the new item
        MotorphInventory newItem = new MotorphInventory(date, stockLabel, brand, engineNumber, status);
        
        // Show the entered data for confirmation
        System.out.println("\nYou are about to add the following item:");
        System.out.println(newItem);
        System.out.print("Do you want to confirm adding this item? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            inventory.add(newItem);
            System.out.println("Stock added successfully.");
        } else {
            System.out.println("Item not added.");
        }
    }

    // delete an item
    private static void deleteItem(ArrayList<MotorphInventory> inventory, Scanner scanner) {
        System.out.print("Enter Engine Number to delete: ");
        String engineNumber = scanner.nextLine();

        boolean removed = false;
        Iterator<MotorphInventory> iterator = inventory.iterator();
        
        // Find the item to delete
        while (iterator.hasNext()) {
            MotorphInventory item = iterator.next();
            if (item.engineNumber.equals(engineNumber)) {
                // Show the item data to be deleted for confirmation
                System.out.println("\nYou are about to delete the following item:");
                System.out.println(item);
                System.out.print("Do you want to confirm deleting this item? (yes/no): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    // Check if stock is marked as "old" and "sold"
                    if (item.stockLabel.equalsIgnoreCase("old") && item.status.equalsIgnoreCase("sold")) {
                        iterator.remove(); // Remove the item from the inventory
                        removed = true;
                        System.out.println("Stock deleted successfully.");
                    } else {
                        System.out.println("Error: Item is not old or sold, cannot delete.");
                    }
                } else {
                    System.out.println("Item not deleted.");
                }
                break;
            }
        }

        // If item was not removed
        if (!removed) {
            System.out.println("Error: Item with engine number '" + engineNumber + "' not found or cannot be deleted.");
        }
    }

    private static void sortByBrand(ArrayList<MotorphInventory> inventory, Scanner scanner) {
        System.out.print("Choose sorting order (1 for Ascending, 2 for Descending): ");
        int order = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        int n = inventory.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                MotorphInventory current = inventory.get(j);
                MotorphInventory next = inventory.get(j + 1);
                
                // Compare brands of current and next inventory items
                int comparisonResult = current.brand.compareTo(next.brand);
                
                // If sorting is ascending (order == 1) or descending (order == 2)
                if ((order == 1 && comparisonResult > 0) || (order == 2 && comparisonResult < 0)) {
                    // Swap if they are in the wrong order
                    inventory.set(j, next);
                    inventory.set(j + 1, current);
                }
            }
        }

        // Display sorted inventory
        inventory.forEach(System.out::println);
    }

    // Method to search for items in inventory
    private static void searchInventory(ArrayList<MotorphInventory> inventory, Scanner scanner) {
        System.out.print("Search by (1.Date, 2.Label, 3.Brand, 4.Engine, 5.Status): ");
        int criterion = scanner.nextInt();
        scanner.nextLine(); // For newline

        System.out.print("Enter search value: ");
        String searchValue = scanner.nextLine();
        
        // Store the filtered inventory
        List<MotorphInventory> results = new ArrayList<>();
        
        // Filter the inventory
        for (MotorphInventory stock : inventory) {
            boolean matches = false;
            switch (criterion) {
                case 1: matches = stock.dateEntered.contains(searchValue); break;
                case 2: matches = stock.stockLabel.contains(searchValue); break;
                case 3: matches = stock.brand.contains(searchValue); break;
                case 4: matches = stock.engineNumber.contains(searchValue); break;
                case 5: matches = stock.status.contains(searchValue); break;
                default: break;
            }
            if (matches) {
                results.add(stock); // Add matching items to results
            }
        }

        // Check if any results were found and display appropriate message
        if (results.isEmpty()) {
            System.out.println("No matching items found.");
        } else {
            results.forEach(System.out::println); // Display matching items
        }
    }
}

