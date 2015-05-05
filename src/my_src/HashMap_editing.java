package my_src;

import java.util.*;
import java.lang.Exception;

import my_src.Assume;
import sun.org.mozilla.javascript.tools.idswitch.IdValuePair;
import syntaxtree.*;
import visitor.*;

public class HashMap_editing
{
	LinkedHashMap<String,LinkedHashMap<String,Fun_or_Ident>> Table = new LinkedHashMap<String,LinkedHashMap<String,Fun_or_Ident>>();
	LinkedHashMap<String,String> DeclClasses;   //classname,extend class
	
	//(ClassName,(INT-Number,MethodName))
	LinkedHashMap<String,LinkedHashMap<Integer,String>>  VTables = new LinkedHashMap<String,LinkedHashMap<Integer,String>>();
	//(ClassName,(INT-Number,IDName))
	LinkedHashMap<String,LinkedHashMap<Integer,String>>  ClassIds = new LinkedHashMap<String,LinkedHashMap<Integer,String>>();
	
	LinkedHashMap<String,Integer> class_depth = new LinkedHashMap<String,Integer>();
	
	int max_depth;
	
	public HashMap_editing(LinkedHashMap<String,LinkedHashMap<String,Fun_or_Ident>>Table1, LinkedHashMap<String,String> DeclClasses1)
	{
		Table = Table1;
		DeclClasses = DeclClasses1;
		max_depth = 0;
	}
	
	public int Max_depth()
	{
		String class_name;
		String extend_class;
		int depth = 0;
		int max_depth = 0;
		
		Set<String> classes = DeclClasses.keySet();
		for(Iterator<String> it = classes.iterator(); it.hasNext();)
		{
			depth = 0;
			class_name = it.next().toString();
			extend_class = DeclClasses.get(class_name);
			while(extend_class != null)
			{
				depth++;
				extend_class = DeclClasses.get(extend_class);
			}
			if(max_depth < depth) max_depth = depth;
			class_depth.put(class_name, depth);
			
		}
		return max_depth;
	}
			
	
	public void Select_VTable_Data()
	{
		LinkedHashMap<Integer, String> vtableTemp;
		LinkedHashMap<Integer, String> idTemp;
		LinkedHashMap<String, Fun_or_Ident> funcs;
		
		String class_name;
		String extend_class;
		String fun_name;
		String id_name;
		//int max_depth = 0;
		
		//max_depth = Max_depth();
		
		Set<String> classes = DeclClasses.keySet();
		for(Iterator<String> it = classes.iterator(); it.hasNext();)
		{
			//depth = 0;
			vtableTemp = new LinkedHashMap<Integer, String>();
			idTemp = new LinkedHashMap<Integer, String>();
			class_name = it.next().toString();
			extend_class = DeclClasses.get(class_name);
			
			if(extend_class != null) //insert extend_class methods and ids
			{
			    //methods
				LinkedHashMap<Integer, String> temp;
				temp = VTables.get(extend_class);
				
				Set<Integer> extended_funs = temp.keySet();
				for(Iterator<Integer> iter = extended_funs.iterator(); iter.hasNext();)
				{
					int id = iter.next();
					fun_name = temp.get(id);
					vtableTemp.put(id, fun_name);
				}
				
				//ids
				LinkedHashMap<Integer, String> temp2;
				temp2 = ClassIds.get(extend_class);
				
				Set<Integer> extended_ids = temp2.keySet();
				for(Iterator<Integer> iter = extended_ids.iterator(); iter.hasNext();)
				{
					int id = iter.next();
					id_name = temp2.get(id);
					idTemp.put(id, id_name);
				}
			}
			
			//Insert class methods
			funcs = Table.get(class_name);
			
			Set<String> methods_name = funcs.keySet();
			for(Iterator<String> iterat = methods_name.iterator(); iterat.hasNext();)
			{
				String method = iterat.next().toString();
				if(method.charAt(0) == '#')
				{
					method = method.substring(1);
					if(vtableTemp.containsValue(extend_class+"_"+method))
					{
						int id = 0;
						String Name = null;
						Set<Integer> previous_funs = vtableTemp.keySet();
						Name = vtableTemp.get(id);
						for(Iterator<Integer> iter = previous_funs.iterator(); iter.hasNext();)
						{				
							if(Name.equals(extend_class+"_"+method))
							{
								vtableTemp.put(id, method);
								break;
							}
							id = iter.next();
							Name = vtableTemp.get(id);
						}
					}
					else
					{
						int size = vtableTemp.size();
						vtableTemp.put(size,class_name+"_"+method);
					}
				}
			}
			
			//Insert class ids	
			funcs = Table.get(class_name);
			
			methods_name = funcs.keySet();
			for(Iterator<String> iterat = methods_name.iterator(); iterat.hasNext();)
			{
				String method = iterat.next().toString();
				if(!(method.charAt(0) == '#'))
				{
					/*if(idTemp.containsValue(extend_class+"_"+method))
					{
						int id = 0;
						String Name = null;
						Set<Integer> previous_funs = idTemp.keySet();
						Name = idTemp.get(id);
						for(Iterator<Integer> iter = previous_funs.iterator(); iter.hasNext();)
						{
							if(Name.equals(extend_class+"_"+method))
							{
								idTemp.put(id, method);
								break;
							}
							id = iter.next();
							Name = idTemp.get(id);			
						}
					}*/
					
						int size = idTemp.size();
						idTemp.put(size,class_name+"_"+method);
					
				}
			}
			
			VTables.put(class_name,vtableTemp);
			ClassIds.put(class_name, idTemp);
			
		}
		
		
		
		
	}
}