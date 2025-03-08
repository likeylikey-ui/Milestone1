import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Milestone {

    static class MotorphInventory {
        String dateEntered, stockLabel, brand, engineNumber, status, warehouseLocation, maintenanceStatus;
        String dateManufactured, model, lastUpdated;

        MotorphInventory(String d, String s, String b, String e, String st, String w, String m, String dm, String mo, String lu) {
            dateEntered = d;
            stockLabel = s;
            brand = b;
            engineNumber = e;
            status = st;
            warehouseLocation = w;
            maintenanceStatus = m;
            dateManufactured = dm;
            model = mo;
            lastUpdated = lu;
        }

        public String toString() {
            return String.join(", ", dateEntered, stockLabel, brand, engineNumber, status, warehouseLocation, maintenanceStatus, dateManufactured, model, lastUpdated);
        }

        public static int compareByEngineNumber(MotorphInventory a, MotorphInventory b) {
            return a.engineNumber.compareTo(b.engineNumber);
        }
    }

    static class Node {
        MotorphInventory data;
        Node left, right;

        public Node(MotorphInventory data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    static class BinarySearchTree {
        Node root;

        public BinarySearchTree() {
            this.root = null;
        }

        public void insert(MotorphInventory data) {
            root = insertRec(root, data);
        }

        private Node insertRec(Node root, MotorphInventory data) {
            if (root == null) {
                root = new Node(data);
                return root;
            }

            if (MotorphInventory.compareByEngineNumber(data, root.data) < 0) {
                root.left = insertRec(root.left, data);
            } else if (MotorphInventory.compareByEngineNumber(data, root.data) > 0) {
                root.right = insertRec(root.right, data);
            }

            return root;
        }

        public MotorphInventory search(String engineNumber) {
            return searchRec(root, engineNumber);
        }

        private MotorphInventory searchRec(Node root, String engineNumber) {
            if (root == null || root.data.engineNumber.equals(engineNumber)) {
                return root != null ? root.data : null;
            }

            if (engineNumber.compareTo(root.data.engineNumber) < 0) {
                return searchRec(root.left, engineNumber);
            }
            return searchRec(root.right, engineNumber);
        }

        public List<MotorphInventory> inOrder() {
            List<MotorphInventory> result = new ArrayList<>();
            inOrderRec(root, result);
            return result;
        }

        private void inOrderRec(Node root, List<MotorphInventory> result) {
            if (root != null) {
                inOrderRec(root.left, result);
                result.add(root.data);
                inOrderRec(root.right, result);
            }
        }

        public void delete(String engineNumber) {
            root = deleteRec(root, engineNumber);
        }

        private Node deleteRec(Node root, String engineNumber) {
            if (root == null) {
                return root;
            }

            if (engineNumber.compareTo(root.data.engineNumber) < 0) {
                root.left = deleteRec(root.left, engineNumber);
            } else if (engineNumber.compareTo(root.data.engineNumber) > 0) {
                root.right = deleteRec(root.right, engineNumber);
            } else {
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                root.data = minValue(root.right);
                root.right = deleteRec(root.right, root.data.engineNumber);
            }
            return root;
        }

        private MotorphInventory minValue(Node root) {
            MotorphInventory minVal = root.data;
            while (root.left != null) {
                minVal = root.left.data;
                root = root.left;
            }
            return minVal;
        }
    }

    public static void main(String[] args) throws IOException {
        BinarySearchTree bst = new BinarySearchTree();
        Scanner scanner = new Scanner(System.in);

        // Read inventory data and populate BST
        List<MotorphInventory> inventory = readCSV("C:\\Users\\Christine\\Downloads\\MotorPH Inventory Data - March 2023 Inventory Data.csv");
        for (MotorphInventory item : inventory) {
            bst.insert(item);
        }

        // Main menu loop
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Delete Item");
            System.out.println("3. Sort Inventory");
            System.out.println("4. Search Inventory");
            System.out.println("5. Modify Item");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Handle user choice
            switch (choice) {
                case 1: addItem(bst, scanner); break;
                case 2: deleteItem(bst, scanner); break;
                case 3: sortInventory(bst, scanner); break;
                case 4: searchInventory(bst, scanner); break;
                case 5: modifyItem(bst, scanner); break;
                case 6:
                    System.out.println("Exiting program...");
                    return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Read inventory data from CSV
    private static List<MotorphInventory> readCSV(String filename) throws IOException {
        List<MotorphInventory> inventory = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.readLine(); // Skip header
        String line;
        
        // Create MotorphInventory objects
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 10) inventory.add(new MotorphInventory(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9]));
        }
        br.close();
        return inventory;
    }

    private static List<MotorphInventory> mergeSort(List<MotorphInventory> list, boolean ascending) {
        if (list.size() <= 1) return list;

        int middle = list.size() / 2;
        List<MotorphInventory> left = mergeSort(list.subList(0, middle), ascending);
        List<MotorphInventory> right = mergeSort(list.subList(middle, list.size()), ascending);

        return merge(left, right, ascending);
    }

    private static List<MotorphInventory> merge(List<MotorphInventory> left, List<MotorphInventory> right, boolean ascending) {
        List<MotorphInventory> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            int comparison = MotorphInventory.compareByEngineNumber(left.get(i), right.get(j));
            if (ascending ? comparison <= 0 : comparison >= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }
        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }

   private static void addItem(BinarySearchTree bst, Scanner scanner) {
   
    System.out.print("Enter Brand: ");
    String brand = scanner.nextLine();
    System.out.print("Enter Engine Number: ");
    String engineNumber = scanner.nextLine();
    
    // Check if the engine number already exists
    if (bst.search(engineNumber) != null) {
        System.out.println("Error: Engine Number already exists in the inventory.");
        return;  // Exit the method if engine number is found
    }

    System.out.print("Enter Warehouse Location: ");
    String warehouseLocation = scanner.nextLine();
    System.out.print("Enter Date Manufactured (YYYY-MM-DD): ");
    String dateManufactured = scanner.nextLine();
    System.out.print("Enter Model: ");
    String model = scanner.nextLine();
    
    // Default values
    String stockLabel = "new";  // Default stock label
    String status = "on-hand";  // Default status
    String maintenanceStatus = "completed";  // Default maintenance status

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateEntered = sdf.format(new Date()); // Current date as date entered
    String lastUpdated = dateEntered;  // Same as dateEntered initially

    MotorphInventory newItem = new MotorphInventory(dateEntered, stockLabel, brand, engineNumber, status, warehouseLocation, maintenanceStatus, dateManufactured, model, lastUpdated);

    bst.insert(newItem);
    System.out.println("Item added successfully.");
}

    // Delete item from BST
    private static void deleteItem(BinarySearchTree bst, Scanner scanner) {
        System.out.print("Enter Engine Number to delete: ");
        String engineNumber = scanner.nextLine();

        MotorphInventory itemToDelete = bst.search(engineNumber);

        if (itemToDelete == null) {
            System.out.println("Error: Item with engine number '" + engineNumber + "' not found.");
        } else {
            // Check if stock label is 'old' and status is 'sold'
            if ("old".equals(itemToDelete.stockLabel) && "sold".equals(itemToDelete.status)) {
                System.out.println("You are about to delete the following item:");
                System.out.println(itemToDelete);
                System.out.print("Do you want to confirm deleting this item? (yes/no): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    bst.delete(engineNumber);
                    System.out.println("Stock deleted successfully.");
                } else {
                    System.out.println("Item not deleted.");
                }
            } else {
                System.out.println("Error: The item cannot be deleted because it is not 'old' or its status is not 'sold'.");
            }
        }
    }

    // Sort Inventory
    private static void sortInventory(BinarySearchTree bst, Scanner scanner) {
        List<MotorphInventory> sortedList = bst.inOrder();
        System.out.println("Choose sort order: ");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Choose an option (1-2): ");
        int sortOrder = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean ascending = sortOrder == 1;
        List<MotorphInventory> sortedInventory = mergeSort(sortedList, ascending);

        System.out.println("Sorted Inventory:");
        for (MotorphInventory item : sortedInventory) {
            System.out.println(item);
        }
    }

    // Search Inventory for an item
    private static void searchInventory(BinarySearchTree bst, Scanner scanner) {
        System.out.print("Enter Engine Number to search: ");
        String engineNumber = scanner.nextLine();
        MotorphInventory item = bst.search(engineNumber);

        if (item != null) {
            System.out.println("Item found:");
            System.out.println(item);
        } else {
            System.out.println("Item not found.");
        }
    }

    // Modify Item in BST
    private static void modifyItem(BinarySearchTree bst, Scanner scanner) {
        System.out.print("Enter Engine Number of the item you want to modify: ");
        String engineNumber = scanner.nextLine();

        MotorphInventory itemToModify = bst.search(engineNumber);

        if (itemToModify == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("Item found: ");
        System.out.println(itemToModify);

        System.out.println("What would you like to modify?");
        System.out.println("1. Stock Label");
        System.out.println("2. Status");
        System.out.println("3. Maintenance Status");
        System.out.print("Choose an option (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastUpdated = sdf.format(new Date()); // Update last updated time

        switch (choice) {
            case 1:
                System.out.print("Enter new Stock Label: ");
                String newStockLabel = scanner.nextLine();
                System.out.print("Are you sure you want to change the Stock Label to '" + newStockLabel + "'? (yes/no): ");
                String confirmStockLabel = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirmStockLabel)) {
                    itemToModify.stockLabel = newStockLabel;
                }
                break;
            case 2:
                System.out.print("Enter new Status: ");
                String newStatus = scanner.nextLine();
                System.out.print("Are you sure you want to change the Status to '" + newStatus + "'? (yes/no): ");
                String confirmStatus = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirmStatus)) {
                    itemToModify.status = newStatus;
                }
                break;
            case 3:
                System.out.print("Enter new Maintenance Status (1 - Completed, 2 - In Progress, 3 - Failed - Defective): ");
                int maintenanceChoice = scanner.nextInt();
                scanner.nextLine();
                String maintenanceStatus = "";
                switch (maintenanceChoice) {
                    case 1: maintenanceStatus = "Completed"; break;
                    case 2: maintenanceStatus = "In Progress"; break;
                    case 3: maintenanceStatus = "Failed - Defective"; break;
                    default: System.out.println("Invalid choice, defaulting to 'In Progress'.");
                            maintenanceStatus = "In Progress"; break;
                }
                System.out.print("Are you sure you want to change the Maintenance Status to '" + maintenanceStatus + "'? (yes/no): ");
                String confirmMaintenanceStatus = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirmMaintenanceStatus)) {
                    itemToModify.maintenanceStatus = maintenanceStatus; // Set the new status
                }
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        // Update the Last Updated field
        itemToModify.lastUpdated = lastUpdated;

        System.out.println("Item modified successfully.");
        System.out.println("Updated item: ");
        System.out.println(itemToModify);
    }
}
