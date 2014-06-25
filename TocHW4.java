package hw4;

import java.io.*;
import java.net.*;
import org.json.*;

public class TocHW4
{
	public static void main(String[] args) throws IOException, JSONException
	{
		int  year,county=1,mm=0,M,min=0,k=0,check=1,cm=1;       //交易價格+完成年份
		String s ;
		int[] max = new int[10];
		max[0]=1;
		
		Road[] road= new Road[2000]; 
		Road[] comp = new Road[2000];
		try 
		{ 
			URL url = new URL(args[0]);
			URLConnection con = url.openConnection();
			InputStreamReader isr = new InputStreamReader(con.getInputStream(),"UTF-8");
			
            JSONTokener jsontokener = new JSONTokener(isr);
            JSONArray jsonarray = new JSONArray(jsontokener);
            
     //取出地址種類-----------------------------------------------------
            for(int i=1;i<jsonarray.length();i++)
            {
            	check=1;
            	JSONObject obj = jsonarray.getJSONObject(i);
            	s = obj.getString("土地區段位置或建物區門牌");
            	year = obj.getInt("交易年月");
            	//System.out.println("i="+i);
            	
            	comp[i] = new Road(s.substring(0,findindex(s)),year);
            	for(int j=1;j<i;j++){
            		//System.out.println("j="+j);
            		//System.out.println("comp[j].road="+comp[j].road);
            		//System.out.println("comp[i].road="+comp[i].road);
            		if(comp[j].road.equals(comp[i].road)){
            			check=0;
            			break;
            		}             		
            	} 
            	if(check==1 && comp[i].road.length()>1){
            		road[k] = new Road(comp[i].road,comp[i].year);
            		//System.out.println("road["+k+"].road="+road[k].road);
            		k++;
            	}
            }//---------------年份比較---------------------------
            for(int i=1;i<k;i++)
            {
            	//System.out.println("road["+i+"]="+road[i].road);
            	
            	county=1;
            	for(int j=1;j<jsonarray.length();j++){
            		check=0;
            		
            		JSONObject obj = jsonarray.getJSONObject(j);
            		s = obj.getString("土地區段位置或建物區門牌");
            		
            		if(findindex(s)==0)
                		continue;
            		s = s.substring(0,findindex(s));
            		year = obj.getInt("交易年月");            		
            		if(road[i].road.equals(s)){
            			for(int a=0;a<county;a++){
            				if(year == road[i].month[a]){
            					check=0;
            					break;
            				}else{
            					check=1;
            				}
            			}
            			if(check==1){
            				road[i].month[county] = year;
    						road[i].county++;
    						county++;
    						//System.out.println("cm="+cm);
            			}
            		}
            	}
            	if(road[i].county>=road[max[cm]].county){
            		max[cm] = i;
            	}
            }
            for(int i=0;i<k;i++){
            	
            	if(road[max[cm]].county ==road[i].county ){
            		cm++;
            		max[cm] = i;
            	}
            }//-------------取出最大成交價及最小成交價----------
            for(int b=2;b<=cm;b++){
            	
            	
            	for(int c=1;c<jsonarray.length();c++){
            		JSONObject obj = jsonarray.getJSONObject(c);
            		s = obj.getString("土地區段位置或建物區門牌");
            		mm = obj.getInt("總價元");
            		if(findindex(s)==0)
                		continue;
            		s = s.substring(0,findindex(s));
            		if(road[max[b]].road.equals(s)){
            			if(road[max[b]].max<mm){
            				road[max[b]].max = mm;
            			} 
            			if(road[max[b]].min>mm){
            				road[max[b]].min = mm;
            			}
            		}
            	}
            	System.out.println(road[max[b]].road+", 最高成交價: "+road[max[b]].max+"0, 最低成交價: "+road[max[b]].min);
            	
            }
        }   /////////////////////////      
		
        
        catch(ArrayIndexOutOfBoundsException e) 
        { 
            System.out.println(
            	"using: java FileDemo pathname"); 
        } 
	}
	static public int findindex(String s){
		int index=0;
		if(s.indexOf('道')>=0){
			index = s.indexOf('道');
			//System.out.println("道 ");
		}else if(s.indexOf('路')>=0){
			index = s.indexOf('路');
			//System.out.println("路 ");
		}else if(s.indexOf('街')>=0){
			index = s.indexOf('街');
			//System.out.println("街");
		}else if(s.indexOf('巷')>=0){
			index = s.indexOf('巷');
			//System.out.println("巷");
		}else {
			return 0;
		}
		return index+1;
	}
}
