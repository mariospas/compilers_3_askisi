package my_src;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class Fun_or_Ident
{
	boolean function;   //true if function false if identifier
	String Type;         // identifier(int,float,etc) or function's return type
	int numOfArgs;     //if function
	LinkedHashMap<String,String> arg = new LinkedHashMap<String,String>();
	LinkedHashMap<String,String> var = new LinkedHashMap<String,String>();
	List<String> argTypes;
	
}