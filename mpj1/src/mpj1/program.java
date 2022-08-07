package mpj1;
import mpi.*;
public class program {
	public static void main(String args[])
	{
		MPI.Init(args);
		int rank= MPI.COMM_WORLD.Rank();
		int size= MPI.COMM_WORLD.Size();
		System.out.println(rank);
		MPI.Finalize();
	}

}
