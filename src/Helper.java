import java.util.Scanner;

public class Helper {
    public int validNumber(String prompt){
        Scanner scanner = new Scanner(System.in);
        int i = -1;
        while(i < 0){
            System.out.println(prompt);
            String number = scanner.next();

            try{
                i = Integer.parseInt(number);
                if(i < 0){
                    System.out.println("Invalid Input: Must Be Positive Integer");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid Input: Must Be Positive Integer");
                i = -1;
            }
        }
        return i;
    }

    public String validString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input = " ";

        while (input.isBlank()) {
            System.out.print("Enter a non-blank string: ");
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        }

        return input;
    }

}
