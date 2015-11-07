import java.util.Arrays;
public class test{
	public static void main(String[] args){
		PredictTable prTable = new PredictTable();
		prTable.initPredictTable();
		System.out.println(prTable.toString() );

		String[] numbers = {"one", "two"};
		String[] numeric = {"three", "two"};
		String[] digits = {"one", "two"};

		System.out.println(Arrays.equals(numbers, digits));

	}
}
