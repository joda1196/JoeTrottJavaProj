public class Helper {
    public void IntegerValidator(int input) {
        try {
            Integer.parseInt(String.valueOf(input));
        } catch (NumberFormatException e){
            System.out.println("Invalid Input Must be Integer");
        }
    }

}
