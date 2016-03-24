import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 /*
  * 해야될 것:
  * 
  * 
  */

public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다."; 
    public static final int MAX_RESULT_SIZE = 201;
    public static final int MAX_INPUT_SIZE = 100;
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("(?<sign>[[+][-]]?)(?<num>[0-9]+)");
   
    // fields
    private char sign;
    private int[] intArray; // only digits (excluding sign)
    private int digit = 0;
    
    public BigInteger(int i)
    {
    	this(Integer.toString(i));
    }
 
    public BigInteger(int[] num1, char inputSign) // parameter 바꿔도 되나? num1의 크기가 100이 넘을 수도 있나?
    {
    	int i;
    	sign = inputSign;
    	intArray = new int[MAX_INPUT_SIZE];
    	
    	for (i = 0; num1[i] != 0; i++); // find the index of first significant digit
    	digit = num1.length - i;
    	
    	for (i = 0; i < num1.length; i++)
    		intArray[MAX_INPUT_SIZE - num1.length + i] = num1[i];
    }
 
    public BigInteger(String s)
    {
    	Matcher matcher = EXPRESSION_PATTERN.matcher(s);
    	
    	// determine sign and digit
    	if (matcher.find())
    	{
    		digit = matcher.group("num").length();
    		
    		if (matcher.group("sign").equals(""))
    			sign = '+';

    		else
    			sign = matcher.group("sign").charAt(0);
    	}

    	intArray = new int[MAX_INPUT_SIZE];
    	
    	// convert string to int array
    	for (int i = 0; i < digit; i++)
    		intArray[MAX_INPUT_SIZE - digit + i] = Integer.valueOf(matcher.group("num").charAt(i)) - 48; // char '0' is (int) 48
    }
 
    
    public BigInteger add(BigInteger big)
    {
    	char resultSign;
    	int[] resultArray = new int[MAX_RESULT_SIZE];
    	
    	if (sign == big.getSign())
    	{
    		//add two intArrays and set resultArray
    		resultSign = sign; //set resultSign
    	}
    	
    	else
    	{
    		
    	}
    }
 
    public BigInteger subtract(BigInteger big)
    {
    }
 
    public BigInteger multiply(BigInteger big)
    {
    }
 
    public char getSign()
    {
    	return this.sign;
    }
    
    public int[] getArray()
    {
    	return this.intArray;
    }
    
    public int getDigit()
    {
    	return this.digit;
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
        BigInteger result;
        
        // evaluate expression
        switch (spaceRemovedInput.charAt(operatorIndex))
        {
        	case '+' : 
        		result = bigNum1.add(bigNum2);
        		break;
        
        	case '-' : 
        		result = bigNum1.subtract(bigNum2);
        		break;
        
        	case '*' : 
        		result = bigNum1.multiply(bigNum2);
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
