package mpj1;
import mpi.*;
public class program {
	public static void main(String args[])
	{
		MPI.Init(args);
		int myrank= MPI.COMM_WORLD.Rank();
		int size= MPI.COMM_WORLD.Size();
		int message=myrank;
		if((myrank % 2) == 0)
		{
			if((myrank + 1) != size)
				MPI.COMM_SELF.Send_init(args, 2,0 ,MPI.INT, myrank+1, message);
		
			}else
			{ if(myrank != 0)
				MPI.COMM_SELF.Recv_init(args, 2,0 ,MPI.INT, myrank-1, message);
				System.out.println(message);
		}

		MPI.Finalize();
	}

}
