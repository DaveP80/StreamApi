import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Numbers {
    static Scanner scan = new Scanner(System.in);
    private int a;
    private int b;
    private int c;
    private int d;

    public Numbers() {
    }

    public Numbers(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public static double getRandomDouble(double maximum, double minimum) {
        return ((double) (Math.random() * (maximum - minimum))) + minimum;
    }

    public static void main(String[] args) {


        while (true) {

            try {
                System.out.println("enter 4 integers");

                int a = scan.nextInt();
                int b = scan.nextInt();
                int c = scan.nextInt();
                int d = scan.nextInt();

                Numbers nums = new Numbers(a, b, c, d);

                ArrayList<Integer> ArrLis = new ArrayList<Integer>();
                ArrLis.add(nums.getA());
                ArrLis.add(nums.getB());
                ArrLis.add(nums.getC());
                ArrLis.add(nums.getD());

                Object[] arr = ArrLis.toArray(); //example of using ArrayList method

                System.out.println("Elements of ArrayList"
                        + " as Array: "
                        + Arrays.toString(arr));

                Stream<Integer> myStream = ArrLis.stream(); //obtain (line 70) a stream of the array list
                //obtain minimum and maximum value of the stream
//        System.out.println(Integer.valueOf(d).compareTo(Integer.valueOf(d))); // return 0 || -1 || 1

                Optional<Integer> minVal = myStream.min(Integer::compare);

                if (minVal.isPresent()) System.out.println("Minimum value: " + minVal.get());
                Thread.sleep(1000);

                //must obtain a new stream because previous call to min() is a terminal operation
                //that consumed the stream.
                myStream = ArrLis.stream();
                Optional<Integer> maxVal = myStream.max(Integer::compare);
                if (maxVal.isPresent()) System.out.println(("Maximum value: " + maxVal.get()));
                // sort the stream by the use of sorted
                Stream<Integer> sortedStream = ArrLis.stream().sorted();
//        System.out.println(sortedStream);
                System.out.print("Sorted Stream: ");
                Thread.sleep(500);
                sortedStream.forEach((n) -> System.out.print(n + " "));
                //void forEach(Consumer<?superT>action)
                //Consumer is a generic functional interface declared in java.util.function.its abstract
                //method is accept(), shown here:
                //  void accept(T objRef)
                System.out.println();
                //Display only the odd values by use of filter ().

                Stream<Integer> oddVals = ArrLis.stream().sorted().filter((n) -> (n % 2) == 1);
                System.out.print("Odd Values: ");
                //the filter() method filters a stream based on a predicate. It returns a new stream that
                //contains only those elements that satisfy the predicate. shown here:
                //Stream<T>filter(Predicate<?super t>pred)
                //Predicate is a generic functional interface defined in java.util.function. its abstract
                //method is test(), which is below:
                //boolean test (T objRef)
                //It returns true if the object referred to by objRef satisfies the predicate, and false otherwise
                //the lambda expression passed to filter() implements this method.
                //Because filter(), or any other intermediate operation returns a new stream, it is possible to filter
                //a filtered stream a second time, this is demonstrated by filtering n>5.
                oddVals.forEach((n) -> System.out.print(n + " "));
                System.out.println();
                Thread.sleep(1000);
                //Display only the odd values that are greater than 5, filter operations
                //are pipelined.
                oddVals = ArrLis.stream().filter((n) -> (n % 2) == 1)
                        .filter((n) -> n > 5);
                System.out.print("Odd values greater than 5: ");
                oddVals.forEach((n) -> System.out.print(n + " "));
                System.out.println("\n");

                System.out.println("Computing 3 random double values");

                Thread.sleep(10);System.out.print(">");
                int i;
                for (i=0; i<20; i++) {
                   Thread.sleep(50); System.out.print(">");}

                DecimalFormat df = new DecimalFormat("#.##");

                double ad = Double.parseDouble(df.format(getRandomDouble(30, 1)));
                double bd = Double.parseDouble(df.format(getRandomDouble(30, 1)));
                double cd = Double.parseDouble(df.format(getRandomDouble(30, 1)));

                ArrayList<Double> myList = new ArrayList<>();

                myList.add(ad);
                myList.add(bd);
                myList.add(cd);

                System.out.println("\n"+"myList: "+myList);
                //two ways to obtain the double product of the elements
                Optional<Double> productObj = myList.stream().reduce((x,y) -> x*y);
                if(productObj.isPresent()) System.out.println("Product as Optional(rounded): "
                +Math.round(productObj.get()));

                double product = myList.stream().reduce(1.0, (x, y) -> x * y); //another way
                System.out.println();
                //Parallel Streams. In order to parallel process, use parallelStream() method. call
                //parallel() method on a sequential stream.
                //the above reduce() operation can be parallelized by substituting parallelStream() for the
                //call to stream();
                //accumulator and combiner. combiner defines the function that combines two values that been produced
                //by the accumulator function. accumulator uses identity value version of reduce() method.

                //Use the combiner version of reduce() to compute the product of the square roots of each
                //element in myList.
                double productofSqrRoots = myList.parallelStream().reduce(
                        1.0,
                        (x, y) -> x * Math.sqrt(y),
                        (x, y) -> x * y
                );
                System.out.println("Product of square roots(rounded): " + Double.parseDouble(df.format(productofSqrRoots)));
                System.out.println();
                Thread.sleep(500);
                //mapping from one stream to another.
                //transformation (computation of square roots) occurs during mapping, rather than reduction
                Stream<Double> sqrtRootStrm = myList.stream().map((x) -> Math.sqrt(x));
                //find the product of the square roots.
                double productOfSqrRoots = sqrtRootStrm.reduce(1.0, (x, y) -> x * y);// output is the same (raw)
                //Make a primitive stream, use stream() followed by mapToInt() to create an IntStream that contains
                //the ceiling of each value.
                System.out.print("Original myList[] values: ");
                myList.stream().forEach((x) -> {
                    System.out.print(x + " ");
                });
                System.out.println();
                //map the ceiling of the elements in myList to an IntStream.
                IntStream nStrm = myList.stream().mapToInt((n) -> (int) Math.ceil(n));
                System.out.print("        Ceiling values: ");
                nStrm.forEach((n) -> {
                    System.out.print(n + " ");
                });
                System.out.println("\n");

                LinkedList<Double> numList = new LinkedList<>();

                numList.add((double) nums.getA());
                numList.add((double) nums.getB());
                numList.add((double) nums.getC());
                numList.add((double) nums.getD());
                numList.add(ad);
                numList.add(bd);
                numList.add(cd);
                System.out.println("2 arrays combined: " + numList + "\n");
                System.out.println("Press 1 to exit, press keyboard to continue");
                while (scan.nextInt() == 1) {
                    System.exit(0);
                }
            } catch (InputMismatchException e) {
                System.out.println("enter integers");
                scan.next();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
