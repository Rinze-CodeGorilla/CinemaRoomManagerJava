package cinema;

import java.util.Scanner;

public class Cinema {

  private boolean[][] reserved;
  private int rows;
  private int seatsPerRow;

  public Cinema(int rows, int seatsPerRow) {
    this.reserved = new boolean[rows][seatsPerRow];
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
  }

  public static void main(String[] args) {
    var s = new Scanner(System.in);
    System.out.println("Enter the number of rows:");
    var rows = s.nextInt();
    System.out.println("Enter the number of seats in each row:");
    var seatsPerRow = s.nextInt();

    var c = new Cinema(rows, seatsPerRow);

    int choice;
    do {
      System.out.println("1. Show the seats");
      System.out.println("2. Buy a ticket");
      System.out.println("3. Statistics");
      System.out.println("0. Exit");
      choice = s.nextInt();
      switch (choice) {
        case 1:
          c.printCinema();
          break;
        case 2:
          var validInput = false;
          while (!validInput) {
            System.out.println("Enter a row number:");
            var row = s.nextInt();
            System.out.println("Enter a seat number in that row:");
            var column = s.nextInt();
            try {
              c.reserve(row, column);
              System.out.println("Ticket price: $" + c.getSeatPrice(row, column));
              validInput = true;
            } catch (Exception $e) {
              System.out.println($e.getMessage());
            }
          }
          break;
        case 3:
          c.printStatistics();
          break;
        default:
          break;
      }
    } while (choice != 0);
  }

  public void printStatistics() {
    this.printCinema();

    var seats = this.rows * this.seatsPerRow;
    var purchasedTickets = 0;
    var currentIncome = 0;
    var totalIncome = 0;
    var pi = 0;
    for (var row = 0; row < rows; row++) {
      for (var column = 0; column < seatsPerRow; column++) {
        if (reserved[row][column]) {
          purchasedTickets++;
          currentIncome += getSeatPrice(row + 1, column + 1);
        }
        totalIncome += getSeatPrice(row + 1, column + 1);
      }
    }
    System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
    System.out.printf("Percentage: %.2f%%\n", purchasedTickets * 100.0 / seats);
    System.out.printf("Current income: $%d\n", currentIncome);
    System.out.printf("Total income: $%d\n", totalIncome);
  }

  /**
   * @param row 1-based row index
   * @param column 1-based column index
   */
  public int getSeatPrice(int row, int column) {
    if (rows * seatsPerRow <= 60) {
      return 10;
    }
    if (row <= rows /2) {
      return 10;
    }
    return 8;
  }
  public void reserve(int row, int column) throws Exception {
    if (row <= 0 || column <= 0 || row > this.rows || column > this.seatsPerRow) {
      throw new Exception("Wrong input!");
    }
    if (this.reserved[row - 1][column - 1]) {
      throw new Exception("That ticket has already been purchased");
    }
    this.reserved[row - 1][column - 1] = true;
  }

  public void printCinema() {
    System.out.println("Cinema:");
    System.out.print(" ");
    for (int i = 1; i <= this.seatsPerRow; i++) {
      System.out.print(" " + i);
    }
    System.out.println();
    for (int row = 1; row <= this.rows; row++) {
      System.out.print(row);
      for (int column = 1; column <= this.seatsPerRow; column++) {
        System.out.print(this.reserved[row - 1][column - 1] ? " B" : " S");
      }
      System.out.println();
    }
  }
}
