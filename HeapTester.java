import java.util.ArrayList;
import java.util.Collections;

public class HeapTester {
    public static void main(String[] args) throws Exception {
        Heap test = new Heap<Integer>();
        ArrayList<Integer> numList = new ArrayList<>();

        // Add 1 - 1,000,000 to the numList array
        for (int i = 1; i <= 1_000_000; i++) {
            numList.add(i);
        }
        Collections.shuffle(numList); // Shuffle the numList array

        // Insert each number 1 - 1,000,000 into the Heap (in the shuffle order)
        for (int i = 0; i < numList.size(); i++) {
            test.insert(numList.get(i));
        }

        // print out test-related data to terminal
        for (int i = 1_000_000; i >= 1; i--) {
            System.out.println("-------------------------------");
            System.out.println("Max: " + test.findMax());
            System.out.println("Height: " + test.height());
            System.out.println("Size: " + test.size());
            System.out.println("The heap is empty? " + test.isEmpty());
            System.out.println("Extracted Max: " + test.extractMax());
        }

        System.out.println("-------------------------------");
        System.out.println("The heap should now be empty...current size: " + test.size());
        System.out.println("Heap is empty? " + test.isEmpty());

        // for debugging
        /*
        numList.add(8);
        numList.add(1);
        numList.add(2);
        numList.add(5);
        numList.add(7);
        numList.add(4);
        numList.add(3);
        numList.add(6);
         */

        /*
        System.out.println("Height: " + test.height());
        System.out.println("Max: " + test.findMax());
        System.out.println("Size before removal: " + test.size());
        System.out.println("Extracted Max: " + test.extractMax());
        System.out.println("Size after removal: " + test.size());
        System.out.println("New max: " + test.findMax());
        System.out.println("New height: " + test.height());
        System.out.println("\n--------------------------------------");
        System.out.println("Another Extracted Max: " + test.extractMax());
        System.out.println("Size after removal: " + test.size());
        System.out.println("New max: " + test.findMax());
        System.out.println("\n--------------------------------------");
        System.out.println("Another Extracted Max: " + test.extractMax());
        System.out.println("Size after removal: " + test.size());
        System.out.println("New max: " + test.findMax());
        System.out.println("\n--------------------------------------");
        System.out.println("Another Extracted Max: " + test.extractMax());
        System.out.println("Size after removal: " + test.size());
        System.out.println("New max: " + test.findMax());
        System.out.println("\n--------------------------------------");

        test.insert(2000000);
        System.out.println("New max: " + test.findMax());
        System.out.println("Size after insertion: " + test.size());
        System.out.println("Another Extracted Max: " + test.extractMax());
        System.out.println("Size after removal: " + test.size());
         */

        /*
        System.out.println("Extracted Max: " + test.extractMax());
        for (int i = 1; i <= 1000; i++) {
            System.out.println("Extracted Max: " + test.extractMax());
        }
        System.out.println("Size after removals: " + test.size());
         */
    }
}
