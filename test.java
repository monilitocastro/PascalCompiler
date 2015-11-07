import java.util.Arrays;
public class test{
	public static void main(String[] args){
		PredictTable prTable = new PredictTable();
		prTable.initPredictTable();
		System.out.println(prTable.toString() );

		String[] arr = {"<PROGRAM>","<ID>","<body>","<END>","<PERIOD>"};
		System.out.println(prTable.equals("<program>","<PROGRAM>",arr));

	}
}
