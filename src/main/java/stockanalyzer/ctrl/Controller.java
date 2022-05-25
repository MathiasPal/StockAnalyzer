package stockanalyzer.ctrl;

import downloader.Downloader;
import stockanalyzer.exceptions.ExceptionThrower;
import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;
import yahooApi.beans.YahooResponse;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Controller {

	YahooFinance yahooFinance = new YahooFinance();

//https://github.com/MathiasPal/StockAnalyzer

	public void process(String ticker) {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO implement methods for
		//1) Daten laden
		//2) Daten Analyse

		try {
			switch (ticker) {
				case "BABA, AMZN, INTC":
					getData("BABA, AMZN, INTC");
					break;

				case "HOOD, VZ, RDBX":
					getData("HOOD, VZ, RDBX");
					break;

				case "PAL.VI, XOM, PDD":
					getData("PAL.VI, XOM, PDD");
					break;

				//case "BABA, AMZN, INTC, HOOD, VZ, RDBX, PAL.VI, XOM, PDD":
					//getData("BABA, AMZN, INTC, HOOD, VZ, RDBX, PAL.VI, XOM, PDD");
					//break;

				default:
					System.out.println("Error");
			}
		} catch (ExceptionThrower e){
			UserInterface.print("An error occured.");
		}

	}
	

	public Object getData(String searchString) throws ExceptionThrower {
		try {
			List<String> tickers = Arrays.asList(searchString.split(", "));
			YahooResponse response = yahooFinance.getCurrentData(tickers);
			QuoteResponse quotes = response.getQuoteResponse();
			System.out.println(quotes.getResult().stream().mapToDouble(Result::getAsk).max().orElseThrow(() -> new ExceptionThrower("An error occured. There is no highest Value.")));
			System.out.println(quotes.getResult().stream().mapToDouble(Result::getAsk).average().orElseThrow(() -> new ExceptionThrower("An error occured. There is no average Value.")));
			System.out.println(quotes.getResult().stream().mapToDouble(Result::getAsk).count());
			//quotes.getResult().stream().forEach(quote -> System.out.println("52 Week High " + quote.getFiftyTwoWeekHigh() + "; " + "50 Day Average: " + quote.getFiftyDayAverage() + "; " + "Number of Datasets: " + quote.getRegularMarketVolume()));
		} catch (ExceptionThrower e) {
			UserInterface.print("When accessing the Data an error occured!");
		}
		return null;
	}

	public void closeConnection() {}

	public void downloadTickers (List<String> tickers, Downloader downloader) {
		downloader.process(tickers);
	}
}
