package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


import downloader.ParallelDownloader;
import downloader.SequentialDownloader;
import stockanalyzer.ctrl.Controller;


public class UserInterface 
{

	public static void print(String e){
		System.out.println(e);
	}

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){ ctrl.process("BABA, AMZN, INTC");	}

	public void getDataFromCtrl2(){
		ctrl.process("HOOD, VZ, RDBX");
	}

	public void getDataFromCtrl3(){
		ctrl.process("PAL.VI, XOM, PDD");
	}
	//public void getDataFromCtrl4(){}
	//public void getDataForCustomInput(){}
	public void getDataFromCtrl5(){
		var list = Arrays.asList("BABA", "AMZN", "INTC", "HOOD", "VZ", "RDBX", "PAL.VI", "XOM", "PDD");
		SequentialDownloader sq = new SequentialDownloader();
		ParallelDownloader pq = new ParallelDownloader();
		long time1, time2;

		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list, sq);
		time2 = System.currentTimeMillis();
		System.out.println("The sequential Downloader requires " + (time2 - time1) + "ms");

		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list, pq);
		time2 = System.currentTimeMillis();
		System.out.println("The parallel Downloader requires " + (time2 - time1) + "ms");
	}

	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Choice 1", this::getDataFromCtrl1);
		menu.insert("b", "Choice 2", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		//menu.insert("i", "Choice User Input:",this::getDataForCustomInput);
		//menu.insert("z", "Choice User Input:",this::getDataFromCtrl4);
		menu.insert("d", "Downloader:", this::getDataFromCtrl5);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
