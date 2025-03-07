import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Milestone {
    static class MotorphInventory {
        String dateEntered, stockLabel, brand, engineNumber, status;

        MotorphInventory(String d, String s, String b, String e, String st) {
            dateEntered = d;
            stockLabel = s;
            brand = b;
            engineNumber = e;
            status = st;
        }

        public String toString() {
            return String.join(", ", dateEntered, stockLabel, brand, engineNumber, status);
        }
    }

    static class BSTNode {
        MotorphInventory data;
        BSTNode left, right;

        BSTNode(MotorphInventory item) {
            this.data = item;
            left = right = null;
        }
    }

    static class BinarySearchTree {
        BSTNode root;

        void insert(MotorphInventory item) {
            root = insertRec(root, item);
        }

        BSTNode insertRec(BSTNode root, MotorphInventory item) {
            if (root == null) {
                return new BSTNode(item);
            }
            if (item.engineNumber.compareTo(root.data.engineNumber) < 0)
                root.left = insertRec(root.left, item);
            else if (item.engineNumber.compareTo(root.data.engineNumber) > 0)
                root.right = insertRec(root.right, item);
            return root;
        }

        MotorphInventory search(String engineNumber) {
            return searchRec(root, engineNumber);
        }

        MotorphInventory searchRec(BSTNode root, String engineNumber) {
            if (root == null || root.data.engineNumber.equals(engineNumber))
                return (root != null) ? root.data : null;
            if (engineNumber.compareTo(root.data.engineNumber) < 0)
                return searchRec(root.left, engineNumber);
            return searchRec(root.right, engineNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<MotorphInventory> inventory = readCSV("Insert CSV file path");
        BinarySearchTree bst = new BinarySearchTree();
        inventory.forEach(bst::insert);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Delete Item");
            System.out.println("3. Edit Item");
            System.out.println("4. Search Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addItem(inventory, bst, scanner); break;
                case 2: deleteItem(inventory, bst, scanner); break;
                case 3: editItem(inventory, bst, scanner); break;
                case 4: searchInventory(bst, scanner); break;
                case 5:
                    writeCSV("Insert CSV file path", inventory);
                    System.out.println("Exiting program...");
                    return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void editItem(ArrayList<MotorphInventory> inventory, BinarySearchTree bst, Scanner scanner) {
        System.out.print("Enter Engine Number to modify: ");
        String engineNumber = scanner.nextLine();
        MotorphInventory item = bst.search(engineNumber);
        
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        
        System.out.println("Editing: " + item);
        System.out.print("Enter new brand: ");
        item.brand = scanner.nextLine();
        System.out.print("Enter new status: ");
        item.status = scanner.nextLine();
        System.out.println("Item updated successfully.");
    }

    private static void searchInventory(BinarySearchTree bst, Scanner scanner) {
        System.out.print("Enter Engine Number to search: ");
        String engineNumber = scanner.nextLine();
        MotorphInventory result = bst.search(engineNumber);
        
        if (result != null) {
            System.out.println("Item found: " + result);
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void addItem(ArrayList<MotorphInventory> inventory, BinarySearchTree bst, Scanner scanner) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String stockLabel = "new", status = "on-hand";

        System.out.print("Enter Stock Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Engine Number: ");
        String engineNumber = scanner.nextLine();

        if (bst.search(engineNumber) != null) {
            System.out.println("Error: Engine Number already exists.");
            return;
        }

        MotorphInventory newItem = new MotorphInventory(date, stockLabel, brand, engineNumber, status);
        inventory.add(newItem);
        bst.insert(newItem);
        System.out.println("Stock added successfully.");
    }

    private static void deleteItem(ArrayList<MotorphInventory> inventory, BinarySearchTree bst, Scanner scanner) {
        System.out.print("Enter Engine Number to delete: ");
        String engineNumber = scanner.nextLine();
        
        MotorphInventory item = bst.search(engineNumber);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        
        inventory.removeIf(i -> i.engineNumber.equals(engineNumber));
        System.out.println("Item deleted successfully.");
    }

    private static ArrayList<MotorphInventory> readCSV(String filename) throws IOException {
        ArrayList<MotorphInventory> inventory = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.readLine();
        String line;
        
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5) inventory.add(new MotorphInventory(data[0], data[1], data[2], data[3], data[4]));
        }
        br.close();
        return inventory;
    }

    private static void writeCSV(String filename, ArrayList<MotorphInventory> inventory) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write("Date, Label, Brand, Engine Number, Status\n");
        
        for (MotorphInventory item : inventory) {
            bw.write(item.toString() + "\n");
        }
        bw.close();
    }
}
