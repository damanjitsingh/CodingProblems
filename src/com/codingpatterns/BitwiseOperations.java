package com.codingpatterns;

public class BitwiseOperations {
    /*************************************************************************************************************/
    /**
     * In an array of size n-1, that can contain numbers from 1 to n find the missing number.
     * @param arr
     * @return
     */
    public static int findMissingNumber (int[] arr, int n) {
        int expected = 1;
        for (int i=2; i<=n; i++) {
            expected = expected ^ i;
            System.out.println(expected);
        }

        System.out.println("Actual values are: ");
        int actual = arr[0];
        for (int i=1; i<arr.length; i++) {
            actual = actual ^ arr[i];
            System.out.println(actual);
        }

        return expected ^ actual;
    }

    /*************************************************************************************************************/

    /**
     * In a non-empty array of numbers, every number appears exactly twice except two numbers that
     * appear only once. Find the two numbers that appear only once.
     * @param args
     * Here are the steps of our algorithm:

        Taking XOR of all numbers in the given array will give us XOR of num1 and num2, calling this XOR as n1xn2.
        Find any bit which is set in n1xn2. We can take the rightmost bit which is ‘1’. Let’s call this rightmostSetBit.
        Iterate through all numbers of the input array to partition them into two groups based on rightmostSetBit.
        Take XOR of all numbers in both the groups separately. Both these XORs are our required numbers.
        e.g. nums = { 1, 4, 2, 1, 3, 5, 6, 2, 3, 5 }
        n1xn2 = 2
        rightmostSetBit = 2 (2nd bit from the right)
        Starting with first member of nums:
        1 has 2nd bit set to 0 so num2 = 0^1
        4 has 2nd bit set to 0 so num2 = 0^1^4
        2 has 2nd bit set to 1 so num1 = 0^2
        1 has 2nd bit set to 0 so num2 = 0^1^4^1
        3 has 2nd bit set to 1 so num1 = 0^2^3
        5 has 2nd bit set to 0 so num2 = 0^1^4^1^5
        6 has 2nd bit set to 1 so num1 = 0^2^3^6
        2 has 2nd bit set to 1 so num1 = 0^2^3^6^2
        3 has 2nd bit set to 1 so num1 = 0^2^3^6^2^3
        5 has 2nd bit set to 0 so num2 = 0^1^4^1^5^5
        now num1 = (0^2^3^6^2^3) = 6
        num2 = 0^1^4^1^5^5 = 4

    */
    public static int[] findSingleNumbers(int[] nums) {
        // get the XOR of the all the numbers
        int n1xn2 = 0;
        for (int num : nums) {
          n1xn2 ^= num;
        }
        System.out.println(n1xn2);

        // get the rightmost bit that is '1'
        int rightmostSetBit = 1;
        while ((rightmostSetBit & n1xn2) == 0) {
          rightmostSetBit = rightmostSetBit << 1; // shift rightmostSetBit number by 1
        }
        System.out.println(rightmostSetBit);

        int num1 = 0, num2 = 0;
        for (int num : nums) {
          if ((num & rightmostSetBit) != 0) // the bit is set
            num1 ^= num;
          else // the bit is not set
            num2 ^= num;
        }
        return new int[] { num1, num2 };
      }

    /*************************************************************************************************************/

    public static void main(String[] args) {
        int[] arr = new int[] {1,3,4,5,6};
        System.out.println("Missing number is: " + BitwiseOperations.findMissingNumber(arr, 6));

        arr = new int[] { 1, 4, 2, 1, 3, 5, 6, 2, 3, 5 };
        int[] result = BitwiseOperations.findSingleNumbers(arr);
        System.out.println("Single numbers are: " + result[0] + ", " + result[1]);

        arr = new int[] { 2, 1, 3, 2 };
        result = BitwiseOperations.findSingleNumbers(arr);
        System.out.println("Single numbers are: " + result[0] + ", " + result[1]);
    }
}
