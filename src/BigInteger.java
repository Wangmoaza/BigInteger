import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다."; 
    public static final int MAX_RESULT_LEN = 200;
    public static final int MAX_INPUT_LEN = 100;
    
    // fields
    private int sign = 0; // 1 if positive, -1 if negative
    private int[] arr;
    private int digit = 0;
    
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile(""); // 이건 뭘까
    
 
    public BigInteger(int i)
    {
    }
 
    public BigInteger(int[] num1)
    {
    }
 
    public BigInteger(String s)
    {
    	// find sign, save to field
    	// set arr
    	//
    }
 
    
    
    public BigInteger add(BigInteger big)
    {
    }
 
    public BigInteger subtract(BigInteger big)
    {
    }
 
    public BigInteger multiply(BigInteger big)
    {
    }
 
    @Override
    public String toString()
    {
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input, remove white space
        // using regex is allowed
 
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
    }
 
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input); // if quit == true, done = true; escape while loop
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
 
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
