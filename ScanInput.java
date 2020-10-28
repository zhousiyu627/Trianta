import java.util.Scanner;
import java.io.*;

public class ScanInput {
    private static BufferedReader kb =
            new BufferedReader(new InputStreamReader(System.in));
    private Scanner scanner;

    public String scanString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int scanInt() {
        int value = 0;
        boolean isValidateInput = false;
        while (!isValidateInput) {
            try {
                String input = this.scanString();
                value = Integer.valueOf(input);
                isValidateInput = true;
            } catch (Exception e) {
                System.out.print("Invalid input! Please input again a valid int number: ");
            }
        }
        return value;
    }
    public static int scanInt(int minnum,int maxnum)
    {
        while (true) {
            try {
                String s = kb.readLine();
                if(Integer.parseInt(s)>=minnum && Integer.parseInt(s)<=maxnum)
                    return Integer.parseInt(s);
                else{
                    System.out.print("That integer is out of range.  Enter again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("That is not an integer.  Enter again: ");
            } catch (IOException e) {
                // should never happen
            }
        }
    }
    public static int scanPInt()
    {
        while (true) {
            try {
                String s = kb.readLine();
                if(Integer.parseInt(s)>0 && Integer.parseInt(s)<100000)
                    return Integer.parseInt(s);
                else{
                    System.out.print("That integer is out of range.  Enter again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("That is not an integer.  Enter again: ");
            } catch (IOException e) {
                // should never happen
            }
        }
    }
    public static String readString()
    {
        while (true) {
            try {
                return kb.readLine();
            } catch (IOException e) {
                // should never happen
            }
        }
    }
    public static boolean readBoolean()
    {
        String s = null;

        while (true) {
            try {
                s = kb.readLine();
            } catch (IOException e) {
                // should never happen
            }

            if (s.equalsIgnoreCase("yes") ||
                    s.equalsIgnoreCase("y") ||
                    s.equalsIgnoreCase("true") ||
                    s.equalsIgnoreCase("t")) {
                return true;
            } else if (s.equalsIgnoreCase("no") ||
                    s.equalsIgnoreCase("n") ||
                    s.equalsIgnoreCase("false") ||
                    s.equalsIgnoreCase("f")) {
                return false;
            } else {
                System.out.print("Please enter the world yes/no: ");
            }
        }
    }



}
