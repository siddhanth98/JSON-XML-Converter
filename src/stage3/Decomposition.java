package stage3;
import java.util.Scanner;

public class Decomposition {
    private static Scanner sc =  new Scanner(System.in);
    public static void main(String[] args) {
        decomposeWrapper(sc.nextInt());
    }

    public static void decomposeWrapper(int n) {
        Stack st = new Stack(n);
        for (int i = 0; i < n; i++)
            st.push(1);
        decompose(n, st);
    }

    public static void decompose(int n, Stack st2) {
        if (st2.peek() == n || n == 0) // n is the only element left to display
            System.out.println(n);
        else {
            // pop(), pop(), sum, push(), call decomposeHelper
            int sum = st2.peek(), index = 0;
            Stack st1 = new Stack(n);
            st1.push(st2.pop());
            int[] temp = new int[st2.getLength()];
            while(st2.peek() != -1)
                temp[index++] = st2.pop();
            for(int i = temp.length-1; i >= 0; i--)
                st2.push(temp[i]);
            decomposeHelper(st1, st2);
            st2.clear();
            for(int i = temp.length-1; i >= 0; i--)
                st2.push(temp[i]);

            st2.push(sum + st2.pop());
            decompose(n, st2);
        }
    }

    public static void decomposeHelper(Stack st1, Stack st2) {
        while(st2.peek() != -1) {
            if (st2.peek() > st1.peek() && st2.getLength() == 1)
                return;
            else if (st2.getLength() == 1) {
                System.out.print(st1.display());
                System.out.print(" " + st2.display());
                System.out.println();
                return;
            }
            else if (st2.peek() <= st1.peek() && st2.getLength() >= 2) {
                System.out.print(st1.display());
                System.out.print(" " + st2.display());
                System.out.println();
                int top1 = st2.pop(), top2 = st2.pop();
                if (top1 + top2 <= st1.peek())
                    st2.push(top1+top2);
                else if (st2.peek() == -1)
                    return;
                else {
                    st1.push(top1);
                    st2.push(top2 + st2.pop());
                }
            }
            else return;
        }
    }
}
