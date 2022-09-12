package mpj1;
import mpi.*;
import java.util.Random;
import java.util.Arrays;
public class program {
	public static void main(String args[])
	{
		int max =10;
		int min =1;
		int data[] ;
		int TAG = 0;
		int sd = 0;
		MPI.Init(args);
			int size = MPI.COMM_WORLD.Size();
			int rank= MPI.COMM_WORLD.Rank();
			int[] buffer = new int[size-3];
		    	if (rank>2)
		    	{
		    		data=new int[1];
		    		Random r = new Random();
		    		data[0]=r.nextInt(max-min)+min;
		    		MPI.COMM_WORLD.Isend(data, 0, 1, MPI.INT,rank%2, TAG);
		    	}
		    	else if (rank==0||rank==1)
		    	{
		    		
		    		Request[] req = new Request[size];
		    		for (int i=3;i<size;i++)
		    		{
		    			if (i%2==rank)
		    			{
		    				req[i]=MPI.COMM_WORLD.Irecv(buffer,sd,1,MPI.INT,i, TAG);
		    				sd+=1;
		    			}
		    		}
		    		Request.Waitall(req);
		    		Arrays.sort(buffer);
		    		for (int i=0;i<buffer.length;i++)
		    		System.out.print(buffer[i]+" ");
		    		System.out.println();
		    		MPI.COMM_WORLD.Isend(buffer,buffer.length-sd, sd, MPI.INT,2, TAG);
		    	}
		    	else
		    	{
		    		Request[] req = new Request[2];
		    		int count=MPI.COMM_WORLD.Probe(0, TAG).count;
		    		req[0]= MPI.COMM_WORLD.Irecv(buffer,0,count,MPI.INT,0, TAG);
		    		count=MPI.COMM_WORLD.Probe(1, TAG).count;
		    		req[1]= MPI.COMM_WORLD.Irecv(buffer,buffer.length-count,count,MPI.INT,1, TAG);
		    		Request.Waitall(req);
		    		for (int i=0;i<buffer.length;i++)
			    		System.out.print(buffer[i]+" ");
		    		System.out.println();
		    		int tec=0;
		    		int start_one=0;
		    		int start_two=buffer.length-count;
		    		int [] mass = new int[buffer.length];
		    		while (tec!=buffer.length)
		    		{
		    			if(buffer[start_one]>=buffer[start_two])
		    			{
		    				
		    				mass[tec]=buffer[start_two];
		    				tec+=1;
		    				start_two+=1;
		    				if (start_two == buffer.length)
		    					for(int j=start_one;j<buffer.length-count;j++)
		    					{
		    						
		    						mass[tec]= buffer[j];
		    						tec+=1;
		    					}
		    			}
		    			else
		    			{
		    				
		    				mass[tec]=buffer[start_one];
		    				tec+=1;
		    				start_one+=1;
		    				if (start_one == buffer.length-count)
		    					for(int j=start_two;j<buffer.length;j++)
		    					{
		    						
		    						mass[tec]= buffer[j];
		    						tec+=1;
		    					}
		    			}
		    		}
		    		for (int i=0;i<buffer.length;i++)
			    		System.out.print(mass[i]+" ");
		    	}
		    	
           
		MPI.Finalize(); 
	}

}
