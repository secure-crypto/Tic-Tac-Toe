import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
Create function renderBoard(String)
Create function updateBoard(int index, char)
Create function boolean isGameOver(String board)


---------
| O X O |
| X O X |
| X O X |
---------


5 x 3

0 1 2 x
3 4 5 y
6 7 8 z
9 a b w
c d e u


row-major => OXOXOXXOX
col-major => OXXXOOOXX

size = 3


3 * i + j


(0, 0) => 0, (0, 1) => 1, (0, 2) => 2
(1, 0) => 3, (1, 1) => 4, (1, 2) => 5
(2, 0) => 6 , (2, 1) => 7, (2, 2) => 8



*/

class Main {

  static String state = "_________";
  static char player = 'X';
  static JButton[] buttons = new JButton[9];
  static JFrame frame = new JFrame("Tic Tac Toe");
  static String message = "";

  public static void renderBoard(String board) {
    System.out.println("---------");
    for (int i = 0; i < 3; i++) {
      System.out.print("| ");
      for (int j = 0; j < 3; j++) {
        System.out.print(board.charAt(3 * i + j) + " ");
      }
      System.out.println("|");
    }
    System.out.println("---------");
  }

  public static void startGame() {   
    Scanner scanner = new Scanner(System.in);
    String state = "_________";
    char player = 'O';
    renderBoard(state);
    while(!isGameOver(state)) {
      // Handle input
      int position;
      do {
        System.out.println("Enter a move position for " + player);
        position = scanner.nextInt();
      }
      while (!isValidPosition(state, position));
      // Update State
      state = updateBoard(state, position, player);
      player = player == 'O' ? 'X' : 'O';
      // Render state
      renderBoard(state);
    }
  }

  public static boolean isValidPosition(String board, int position) {
    if (position >= 0 && position <= 8 && board.charAt(position) == '_') {
      return true;
    }
    else if (position < 0 || position > 8) {
      System.out.println("Please enter a valid position between 0 to 8 (inclusive)");
      return false;
    }
    else {
      System.out.println("Position Occupied for this position");
      return false;
    }
  }

  public static String updateBoard(String currentState, int index, char xo) {

    String newState = currentState.substring(0, index) + xo + currentState.substring(index + 1);

    return newState;
  }

  public static boolean isGameOver(String board) {
    int[][] indices = {
      {0, 1, 2},
      {3, 4, 5},
      {6, 7, 8},
      {0, 3, 6},
      {1, 4, 7},
      {2, 5, 8},
      {0, 4, 8},
      {2, 4, 6}
    };
    for(int i = 0; i < 8; i++) {
      if (board.charAt(indices[i][0]) != '_' 
      && board.charAt(indices[i][0]) == board.charAt(indices[i][1])
      && board.charAt(indices[i][1]) == board.charAt(indices[i][2])) {
        message = String.format("%c Wins the game", board.charAt(indices[i][0]));
        // System.out.println(board.charAt(indices[i][0]) + " Wins the game");
        System.out.println(message);
        return true;
      }
    }

    for (int j = 0; j < 8; j++) {
      if (board.charAt(j) == '_') {
        return false;
      }
    }

    message = "Game tie";
    System.out.println(message);
    return true;
  }

  public static void main(String[] args) {
    frame.setSize(500, 700);
    frame.setLayout(null);
    
    int xCoordinate = 50;
    int yCoordinate = 50;
    int size = 80;
    for (int i = 0; i <= 8; i++) {
      buttons[i] = new JButton("");
      buttons[i].setFont(new Font("Arial", Font.PLAIN, 50));
      buttons[i].setBounds(xCoordinate, yCoordinate, size, size);
      xCoordinate += size;
      if (i == 2 || i == 5) {
        xCoordinate = 50;
        yCoordinate += size;
      }
      JButton curButton = buttons[i];
      int index = i;
      buttons[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          curButton.setText(Character.toString(player));
          curButton.setEnabled(false);
          updateState(index);
          if (isGameOver(state)) {
            disableBoard();
            displayMessage();
          }
          System.out.println(state);
        }
      });
      frame.add(buttons[i]);
    }
    
    frame.setVisible(true);
    //startGame();
    renderBoardInWindow(state, buttons);
  }

  public static void displayMessage() {
    JLabel label = new JLabel(message);
    label.setBounds(200,100,200,50);
    label.setFont(new Font("Arial", Font.PLAIN, 32));
    frame.add(label);
  }

  public static void disableBoard() {
    for (int i = 0; i < 9; i++) {
      buttons[i].setEnabled(false);
    }
  }

  public static void updateState(int index) {
    state = state.substring(0, index) + player + 
        state.substring(index + 1);
    if(player == 'X') {
      player = 'O';
    }
    else {
      player = 'X';
    }
  }

  public static void renderBoardInWindow(String board, JButton[] buttons) {
    for (int i = 0; i < 9; i++) {
      if (board.charAt(i) != '_') {
        buttons[i].setText(Character.toString(board.charAt(i)));
        buttons[i].setEnabled(false);
      }
    }
  }


}

// class MyClass implements ActionListener {
//   public void actionPerformed(ActionEvent ae) {
//     System.out.println("Action performed !!");
//   }
// }

// if (board.charAt(0) != '_' && board.charAt(0) == board.charAt(1) && board.charAt(1) == board.charAt(2)) {
    //   System.out.println(board.charAt(0) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(3) != '_' && board.charAt(3) == board.charAt(4) && board.charAt(4) == board.charAt(5)) {
    //   System.out.println(board.charAt(3) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(6) != '_' && board.charAt(6) == board.charAt(7) && board.charAt(7) == board.charAt(8)) {
    //   System.out.println(board.charAt(6) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(0) != '_' && board.charAt(0) == board.charAt(3) && board.charAt(3) == board.charAt(6)) {
    //   System.out.println(board.charAt(0) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(1) != '_' && board.charAt(1) == board.charAt(4) && board.charAt(4) == board.charAt(7)) {
    //   System.out.println(board.charAt(1) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(2) != '_' && board.charAt(2) == board.charAt(5) && board.charAt(5) == board.charAt(8)) {
    //   System.out.println(board.charAt(2) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(0) != '_' && board.charAt(0) == board.charAt(4) && board.charAt(4) == board.charAt(8)) {
    //   System.out.println(board.charAt(0) + " Wins the game");
    //   return true;
    // }
    // else if (board.charAt(2) != '_' && board.charAt(2) == board.charAt(4) && board.charAt(4) == board.charAt(6)) {
    //   System.out.println(board.charAt(2) + " Wins the game");
    //   return true;
    // }