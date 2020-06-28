package stage3;

public class Stack {
    private int top;
    private int[] stack;

    public Stack(int capacity) {
        this.top = -1;
        this.stack = new int[capacity];
    }

    public void push(int val) {
        if (this.top < this.stack.length-1)
            this.stack[++this.top] = val;
    }

    public int pop() {
        return this.top == -1 ? -1 : this.stack[this.top--];
    }

    public int peek() {
        return this.top == -1 ? -1 : this.stack[this.top];
    }

    public String display() {
        StringBuilder result = new StringBuilder("");
        for (int i = this.top; i >= 0; i--)
            result.append(this.stack[i]).append(" ");
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    public int getLength() {
        return this.top+1;
    }

    public void clear() {
        this.stack = new int[this.stack.length];
        this.top = -1;
    }
}
