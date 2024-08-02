package testNewFunc.wyjatki;

class ExcGenerate {
    public static void genException() {
        int[] nums = new int[5];
        nums[10] = 1; // To spowoduje IndexOutOfBoundsException
    }

    public static double genArithmeticException() {
        int[] nums = new int[5];
        double result = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                throw new ArithmeticException("Arithmetic exception - dzielenie przez zero");
            }
            result = (double) 100 / i;
            System.out.println(result);
        }
        return result;
    }

    public static double genIndexOutOfBoundsException() {
        int[] nums = {22, 33, 44, 55, 66, 77, 88, 99, 234, 234, 56, 78, 345, 34, 24};
        int[] nums2 = {1, 2, 3, 4, 5};
        double result = 0;

        for (int i = 0; i < nums.length; i++) {
            result = (double) nums[i] / nums2[i]; // To spowoduje IndexOutOfBoundsException
        }

        return result;
    }
}


public class ExceptionsTest {
    public static void main(String[] args) {
        try {
            try {
                ExcGenerate.genException();
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("Index Out Of Bounds Exception");
            }

            try {
                System.out.println(ExcGenerate.genArithmeticException());
            } catch (ArithmeticException exception) {
                System.out.println("Arithmetic Exception");
            }

            try {
                System.out.println(ExcGenerate.genIndexOutOfBoundsException());
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("Index Out Of Bounds Exception");
            }
        } catch (Throwable exception) {
            System.out.println("Klauzula Throwable: " + exception.getMessage());
        }
    }
}
