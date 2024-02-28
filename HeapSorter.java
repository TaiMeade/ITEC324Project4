import java.util.Scanner;
public class HeapSorter {
    /**
     * Repeatedly reads from standard input,
     * line by line, until an empty line or end of input is found.
     * Takes all lines of input and sorts them,
     * using heap sort.
     */

    // Declare global variables
    private static Heap heapSorterTest = new Heap(); // the heap used for the testing
    private static String userInput; // the user's input in the form of a string
    private static Scanner input = new Scanner(System.in); // the scanner used to get the user's input

    public static void main(String[] args) throws Exception {

        // Print instructions to screen
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Please enter an empty string to output all entered strings in order (greatest to least).");
        System.out.println("                        NOTE: all entries are compared as strings                       ");
        System.out.println("----------------------------------------------------------------------------------------\n");

        // Using recursion continuously get the user's input until they enter an empty string
        getUserInput();

        /*
            The size of the heap...(will be passed in as the parameter(s) to the printSortedHeap()
            function)...not necessary as I could also use heapSorterTest.size() as the parameters,
            but this makes the code cleaner and more readable in my opinion
         */
        int sizeOfHeap = heapSorterTest.size(); // create variable to store the heap's size

        // Print out each item in order of greatest to least (as determined by "compareTo()")
        System.out.println(); // for formatting
        System.out.println("Strings in sorted order (greatest to least):\n");
        printSortedHeap(sizeOfHeap, sizeOfHeap);

    }

    /**
     * Get the user's input continuously until they enter an empty string.
     */
    private static void getUserInput() {
        // base case for recursion
        if (userInput == "") {
            return;
        }
        System.out.print("Please enter a string: "); // prompt the user for their input
        userInput = input.nextLine();

        // Don't insert the empty line into the heap...since it is meant to be the entry that symbolizes the end of user input
        if (userInput != "") {
            heapSorterTest.insert(userInput);
        }
        getUserInput(); // recursive call
    }

    /**
     * Print out the contents of the heap in sorted order (greatest to least)
     * @param initialSize the initial size (used to 'index' the order since there is no loop)
     * @param heapSize the actual size of the heap that is updated every recursive call
     */
    private static void printSortedHeap(int initialSize, int heapSize) throws Exception {
        // base case for recursion
        if (heapSize == 0) {
            return;
        }
        System.out.println((initialSize - heapSize + 1) + ": " + heapSorterTest.extractMax()); // print out the entry # followed by the entry itself
        printSortedHeap(initialSize, heapSize - 1); // recursive call with the same initial size and the heap size being decremented by 1
    }

}
