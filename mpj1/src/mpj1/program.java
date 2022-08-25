package mpj1;
import mpi.*;
public class program {
	public static void main(String args[])
	{
		
		MPI.Init(args);
		int S=0;
		int myrank= MPI.COMM_WORLD.Rank();
		int buff =myrank;
		int[] message_in= {buff};
		int[] message_out= {1};
		if (myrank==0)
		{
			message_in[0]=buff;
			MPI.COMM_WORLD.Send(message_in, 0, 1, MPI.INT, myrank+1, 1);
			MPI.COMM_WORLD.Recv(message_out, 0, 1, MPI.INT, MPI.COMM_WORLD.Size()-1, 1);
			S=message_out[0];
			System.out.println(S);
			
		}
		else 
		{
			MPI.COMM_WORLD.Recv(message_out, 0, 1, MPI.INT, myrank-1, 1);
			S=message_out[0]+myrank;
			message_in[0]= S;
			if(myrank<MPI.COMM_WORLD.Size()-1)
			MPI.COMM_WORLD.Send(message_in, 0, 1, MPI.INT, myrank+1, 1);
			else
			MPI.COMM_WORLD.Send(message_in, 0, 1, MPI.INT,0, 1);	
		}

		MPI.Finalize();
	}

}
