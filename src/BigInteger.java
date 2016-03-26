import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다."; 
    public static final int MAX_SIZE = 200;
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("(?<sign>[[+][-]]?)(?<num>[0-9]+)");
   
    // fields
    private char sign;
    private int[] intArray; // only digits (excluding sign)
    private int digit = 0;
    
    public BigInteger(int i)
    {
    	this(Integer.toString(i));
    }
 
    public BigInteger(int[] num1, char inputSign)
    {
    	int i;
    	this.sign = inputSign;
    	this.intArray = new int[MAX_SIZE];
    	
    	for (i = 0; num1[i] == 0; i++); // find the index of first significant digit
    	this.digit = num1.length - i;
    	
    	for (i = 0; i < num1.length; i++)
    		this.intArray[MAX_SIZE - num1.length + i] = num1[i];
    }
 
    public BigInteger(String s)
    {
    	Matcher matcher = EXPRESSION_PATTERN.matcher(s);
    	
    	// determine sign & digit
    	if (matcher.find())
    	{
    		this.digit = matcher.group("num").length();
    		
    		if (matcher.group("sign").equals(""))
    			this.sign = '+';

    		else
    			this.sign = matcher.group("sign").charAt(0);
    	}

    	this.intArray = new int[MAX_SIZE];
    	
    	// convert string to int array
    	for (int i = 0; i < digit; i++)
    		this.intArray[MAX_SIZE - digit + i] = Integer.valueOf(matcher.group("num").charAt(i)) - 48; // char '0' is (int) 48
    }
 
    public BigInteger add(BigInteger big)
    {
    	char resultSign;
    	int[] resultArray = new int[MAX_SIZE];
    	
    	if (this.sign == big.getSign())
    	{
    		resultArray = addArrays(big);
    		resultSign = this.sign;
    	}
    	
    	else
    	{
    		resultArray = subtractArrays(big);
    		
    		if (isAbsoluteNotSmaller(big))
    			resultSign = this.sign;
    			
    		else
    			resultSign = big.getSign();
    	}
    	
    	BigInteger result = new BigInteger(resultArray, resultSign);
    	return result;
    }
 
    public BigInteger subtract(BigInteger big)
    {
    	char resultSign;
    	int[] resultArray = new int[MAX_SIZE];
    	
    	if (sign == big.getSign())
    	{
    		resultArray = subtractArrays(big.getArray());
    		
    		if (isAbsoluteNotSmaller(big))
    			resultSign = this.sign;
    		
    		else
    			resultSign = big.getSign();
    	}
    	
    	else
    	{
    		resultArray = addArrays(big.getArray());
    		resultSign = this.sign;
    	}
    	
    	BigInteger result = new BigInteger(resultArray, resultSign);
    	return result;
    }
 
    public BigInteger multiply(BigInteger big)
    {
    	char resultSign;
    	int[] resultArray = new int[MAX_SIZE];
    	int multiplied;
    	
    	if (this.sign == big.getSign())
    		resultSign = '+';
    	
    	else
    		resultSign = '-';
    	
    	BigInteger result = new BigInteger(resultArray, resultSign);
    	return result;
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
    
    private int[] addArrays(BigInteger big)
    {
    	int[] resultArray = new int[MAX_SIZE];
    	int added = 0;
    	
    	for (int i = MAX_SIZE -1; i > 0; i--) //주의
    	{
    		added = this.intArray[i] + big.getArray()[i];
    		resultArray[i] += added%10;
    		resultArray[i-1] += added/10;
    	}
    	
    	return resultArray;
    }
    
    private int[] subtractArrays(BigInteger big)
    {
    	int[] resultArray = new int[MAX_SIZE];
    	int subtracted = 0;
    	int[] biggerArray;
    	int[] smallerArray;
    	
    	if (isAbsoluteNotSmaller(big))
    	{
    		biggerArray = this.intArray;
    		smallerArray = big.getArray();
    	}
    	
    	else
    	{
    		biggerArray = big.getArray();
    		smallerArray = this.intArray;
    	}
    	
    	for (int i = MAX_SIZE -1; i > 0; i--)
    	{
    		subtracted = biggerArray[i] - smallerArray[i];
    		if (subtracted < 0)
    		{
    			resultArray[i] += subtracted + 10;
    			biggerArray[i-1] -= 1; //주의
    		}
    		
    		else
    			resultArray[i] += subtracted;
    	}
    	
    	return resultArray;
    }
    
    public boolean isAbsoluteNotSmaller(BigInteger big)
    {
    	if (this.digit > big.digit)
    		return true;
    	
    	else if (this.digit < big.digit)
    		return false;
    	
    	else
    	{
    		for (int i = MAX_SIZE - this.digit; i < MAX_SIZE; i++)
    		{
    			if (this.intArray[i] > big.getArray()[i])
    				return true;
    			
    			else if (this.intArray[i] < big.getArray()[i])
    				return false;
    		}
    		
    		return true;
    	}
    	
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
