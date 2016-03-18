import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 /*
  * 해야될 것:
  * equals, hashcode method 추가
  * 
  */

public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다."; 
    public static final int MAX_RESULT_SIZE = 201;
    public static final int MAX_INPUT_SIZE = 101;
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("(?<sign>[[+][-]]?)(?<num>[0-9]+)");
   
    // fields
    private int sign = 0; // 1 if positive, -1 if negative
    private int[] intArray; // only digits (excluding sign)
    private int digit = 0;
    
    public BigInteger(int i)
    {
    	String input = Integer.toString(i);
    	this(input);
    }
 
    public BigInteger(int[] num1)
    {
    }
 
    public BigInteger(String s)
    {
    	Matcher matcher = EXPRESSION_PATTERN.matcher(s);
    	
    	if (matcher.find())
    	{
        	matcher.group("sign") //여기부터 고친다
    	}

    	for (char c : s.toCharArray())
    	{
    		
    	}
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
     	String spaceRemovedInput = input.replaceAll("\\s", ""); // remove white space
        Matcher matcher = EXPRESSION_PATTERN.matcher(spaceRemovedInput);
        String arg1 = "";
        String arg2 = "";
        int operatorIndex = 0;
        
        // parse input
        if (matcher.find())
        {
        	arg1 = matcher.group();
        	operatorIndex = matcher.end();
        }
        
        if (matcher.find(operatorIndex + 1))
        {
        	arg2 = matcher.group();
        }
        
        BigInteger bigNum1 = new BigInteger(arg1);
        BigInteger bigNum2 = new BigInteger(arg2);

        // evaluate expression
        switch (spaceRemovedInput.charAt(operatorIndex))
        {
        	case '+' : 
        		BigInteger result = bigNum1.add(bigNum2);
        		break;
        
        	case '-' : 
        		BigInteger result = bigNum1.subtract(bigNum2);
        		break;
        
        	case '*' : 
        		BigInteger result = bigNum1.multiply(bigNum2);
        		break;
        
        	default:
        		System.out.println("something wrong with operator input");
        		break;
        }
        
        return result;
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
