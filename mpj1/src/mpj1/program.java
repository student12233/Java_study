package mpj1;
import mpi.*;
public class program {
	public static void main(String args[])
	{
		
		MPI.Init(args);
		int S=0;
		int myrank= MPI.COMM_WORLD.Rank();
		int buff =myrank;
		int[] message= new int[1];
		if (myrank==0)
		{
			message[0]=buff;
			MPI.COMM_WORLD.Isend(message, 0, 1, MPI.INT, myrank+1, 1);
			Request r=MPI.COMM_WORLD.Irecv(message, 0, 1, MPI.INT, MPI.COMM_WORLD.Size()-1, 1);
			S= myrank;
		    r.Wait();
		    S=S+message[0];
			System.out.println(S);
			
		}
		else 
		{
		    Request r=	MPI.COMM_WORLD.Irecv(message, 0, 1, MPI.INT, myrank-1, 1);
			
			S=myrank;
			r.Wait();
			S = S+message[0];
			message[0]=S;
			if(myrank<MPI.COMM_WORLD.Size()-1)
			MPI.COMM_WORLD.Isend(message, 0, 1, MPI.INT, myrank+1, 1);
			else
			MPI.COMM_WORLD.Isend(message, 0, 1, MPI.INT,0, 1);	
		}

		MPI.Finalize();
	}

}
