public class test{
	public static void main(String[] args){
		PredictTable prTable = new PredictTable();
		prTable.openFile("DELETETHIS.java~");
		String str[] = prTable.getLineSplit();
		for(int i=0; i < str.length; i++){
			System.out.println(str[i]);
		}
		prTable.closeFile();
	}
}
