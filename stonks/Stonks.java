// Navya Jain 
// 10/10/2024
// CSE 122 
// P0: Stonks
// TA: Caleb Hsu

import java.util.*;
import java.io.*;

// This class reads stock data from a file and allows a user to interact
// with this data in a simulation of a stock market. The user can buy 
// stocks by setting a purchasing budget, sell shares, and save their 
// portfolio to a file. The user can also choose to stop the simulation 
// and note their portfolio value. 
public class Stonks {
    public static final String STOCKS_FILE_NAME = "stonks.tsv";

    public static void main(String[] args) throws FileNotFoundException {
        File stocksFile = new File(STOCKS_FILE_NAME);
        System.out.println("Welcome to the CSE 122 Stocks Simulator!");
        Scanner fileScan = new Scanner(stocksFile);
        String numberOfTickersAsString = fileScan.nextLine();
        int numberOfTickers = Integer.parseInt(numberOfTickersAsString);
        System.out.println("There are " + numberOfTickers + " stocks on the market:");
        String[] stocks = new String[numberOfTickers];
        double[] prices = new double[numberOfTickers];
        double[] portfolio = new double[numberOfTickers];
        populateValues(stocks, prices, fileScan);
        String userChoice = "";
        Scanner console = new Scanner(System.in);
        while (!userChoice.equalsIgnoreCase("Q")) {
            System.out.println();
            System.out.println("Menu: (B)uy, (Se)ll, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");
            userChoice = console.nextLine();
            if (userChoice.equalsIgnoreCase("B")) {
                buyStocks(stocks, prices, portfolio, console);
            }
            else if (userChoice.equalsIgnoreCase("Se")) {
                sellStocks(stocks, portfolio, console);
            }
            else if (userChoice.equalsIgnoreCase("S")) {
                savePortfolio (stocks, portfolio, console);
            }
            else if (!userChoice.equalsIgnoreCase("Q")) {
                System.out.println("Invalid choice: " + userChoice);
                System.out.println("Please try again");
            }
        }
        double portfolioValue = calculatePortfolio(portfolio, prices);
        System.out.println("Your portfolio is currently valued at: $" + portfolioValue);
    }

    // Behavior: 
    //   - This method populates the stock market data and displays it for the user. 
    //     The user can see all the available tickers and the price for one share of that
    //     stock. 
    // Parameters:
    //   - stocks: the String[] (String array) being populated with the stock ticker symbols.
    //   - prices: the double[] (double array) being populated with the price of one share 
    //     for each ticker.
    //   - fileScan: the file Scanner reading the file containing all the stock market data.
    // Returns: none
    // Exceptions: none
    public static void populateValues(String[] stocks, double[] prices, Scanner fileScan) {
        int index = 0;
        fileScan.nextLine();
        while (fileScan.hasNextLine()) {
            String stockData = fileScan.nextLine();
            Scanner lineScanner = new Scanner(stockData);
            stocks[index] = lineScanner.next();
            prices[index] = lineScanner.nextDouble();
            System.out.println(stocks[index] + ": " + prices[index]);
            index++;
        }
    }

    // Behavior: 
    //   - This method allows a user to buy stocks by choosing a stock ticker and budget. 
    //     It will then add the bought stocks to the user's portfolio. If the budget is 
    //     less than $5, the user will be informed that there is a minimum budget of $5. 
    // Parameters:
    //   - stocks: the String[] (String array) containing all the stock ticker symbols.
    //   - prices: the double[] (double array) containing the price of one share for each ticker.
    //   - portfolio: the double[] (double array) being filled with data about the user's 
    //     current stock portfolio. 
    //   - console: the Scanner accepting user input for features such as choosing a budget.
    // Returns: none
    // Exceptions: none   
    public static void buyStocks(String[] stocks, double[] prices, 
                                 double[] portfolio, Scanner console) {
        int indexUserStock = chooseTicker(stocks, console);
        System.out.print("Enter your budget: ");
        String userBudgetString = console.nextLine();
        double userBudget = Double.parseDouble(userBudgetString);
        if (userBudget >= 5.00) {
            portfolio[indexUserStock] += userBudget / prices[indexUserStock];
            System.out.println("You successfully bought " + stocks[indexUserStock] + ".");
        }
        else {
            System.out.println("Budget must be at least $5");
        } 
    }
    
    // Behavior: 
    //   - This method allows a user to sell stocks by choosing a stock ticker and 
    //     the amount of shares to sell. It will then adjust the user's portfolio
    //     according to the sale. If the user tries to sell more shares of a stock
    //     than they own, they will be brought back to the main menu to try again. 
    // Parameters:
    //   - stocks: the String[] (String array) containing all the stock ticker symbols.
    //   - portfolio: the double[] (double array) being filled with data about the user's 
    //     current stock portfolio. 
    //   - console: the Scanner accepting user input for features such as choosing the
    //     number of shares to sell.
    // Returns: none
    // Exceptions: none   
    public static void sellStocks(String[] stocks, double[] portfolio, Scanner console) {
        int indexUserStock = chooseTicker(stocks, console);
        System.out.print("Enter the number of shares to sell: ");
        String userStockSellString = console.nextLine();
        double userStockSell = Double.parseDouble(userStockSellString);
        if (userStockSell <= portfolio[indexUserStock]) {
            portfolio[indexUserStock] -= userStockSell;
            System.out.println("You successfully sold " + userStockSell + 
                               " shares of " + stocks[indexUserStock] + ".");
        }
        else {
            System.out.println("You do not have enough shares of " + stocks[indexUserStock] + 
                               " to sell " + userStockSell + " shares.");
        } 
    }

    // Behavior: 
    //   - This method allows a user to choose a stock ticker from the available list, and
    //     returns this ticker as an index (from the String array stocks). 
    // Parameters:
    //   - stocks: the String[] (String array) containing all the stock ticker symbols.
    //   - console: the Scanner accepting user input for features such as choosing the
    //     stock ticker (to either sell or buy with).
    // Returns:
    //   - int: the index of the selected stock ticker (from the String[] stocks), or -1
    //          if no valid ticker is chosen.
    // Exceptions: none
    public static int chooseTicker(String[] stocks, Scanner console){
        System.out.print("Enter the stock ticker: ");
        String userStockChoice = console.nextLine();
        for (int indexUserStock = 0; indexUserStock < stocks.length; indexUserStock++) {
            if (stocks[indexUserStock].equalsIgnoreCase(userStockChoice)) {
                return indexUserStock;
            }
        }
        return -1; // arbitrary return, user is assumed to input a valid ticker
    }

    // Behavior: 
    //   - This method allows a user to save their stock portfolio to a new file with
    //     a name specified by the user. This file will have the stock ticker and how 
    //     many shares of that stock they have at the time of saving.
    // Parameters:
    //   - stocks: the String[] (String array) containing all the stock ticker symbols.
    //   - portfolio: the double[] (double array) containing the user's current stock 
    //     portfolio.
    //   - console: the Scanner accepting user input for features such as choosing the
    //     number of shares to sell.
    // Returns: none
    // Exceptions: none
    public static void savePortfolio(String[] stocks, double[] portfolio, 
                                     Scanner console) throws FileNotFoundException {
        System.out.print("Enter new portfolio file name: ");
        PrintStream portfolioStream = new PrintStream(new File(console.nextLine()));
        for (int i = 0; i < portfolio.length; i++) {
            if (portfolio[i] > 0.0) {
                portfolioStream.println(stocks[i] + " " + portfolio[i]);
            }
        }
    }
    
    // Behavior: 
    //   - This method calculates the total value of the user's stock portfolio 
    //     based on how many shares they currently own of each stock ticker.
    // Parameters:
    //   - prices: the double[] (double array) containing the price of one share for each ticker.
    //   - portfolio: the double[] (double array) containing the user's current stock 
    //     portfolio.
    // Returns:
    //   - double: the total value of the portfolio when the method is called. 
    // Exceptions: none
    public static double calculatePortfolio(double[] portfolio, double[] prices) {
        double portfolioValue = 0;
        for (int i = 0; i < portfolio.length; i++) {
            portfolioValue += portfolio[i] * prices[i];
        }
        return portfolioValue;
    }
}
