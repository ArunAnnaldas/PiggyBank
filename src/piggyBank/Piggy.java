package piggyBank;

import javax.swing.JOptionPane;

public class Piggy {

	static private double[] arrDouble_TransactionDetail = new double[10];
	static double double_balance;
	static double double_deposit;
	static double double_lastTransaction;
	static int int_counter = -1;
	static String str_customerName = "";
	static String str_spclChars = "!@#$%^&*()_<>?,./;':\"/*-+[]{}";
	static String str_UserInput="";
	static boolean flag = true;
	static boolean flag_name = false;

	public static void main(String[] args) {

		while (flag) {

			str_customerName = JOptionPane.showInputDialog("Please enter your first name");
			dataValidation(str_customerName);

			while (flag_name && flag) {

				int int_ActionNumber=0;
				try {
					str_UserInput = JOptionPane.showInputDialog("Welcome to Piggy Bank: " + str_customerName + " "
							+ "\n\nPlease enter the section number :\n " + "1. Deposit cash \n "
							+ "2. Withdraw \n " + "3. View Balance \n " + "4. Statement \n " + "5. Quit");
					
					if(str_UserInput == null)
						int_ActionNumber = 5;
					else
						int_ActionNumber = Integer.parseInt(str_UserInput);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "It seems you have not entered a valid number.");
				}

				switch (int_ActionNumber) {
				case 1:
					deposit();
					break;
				case 2:
					withdraw();
					break;
				case 3:
					viewBalance();
					break;
				case 4:
					statement();
					break;
				case 5:
					flag = false;
					JOptionPane.showMessageDialog(null, "Thank You for using PiggyBank " + str_customerName);
					break;
				default:
					JOptionPane.showMessageDialog(null, "Please enter a valid Number");
					break;
				}
			}
		}
	}

	static void deposit() {
		int int_ErrorCounter = 0;
		while (int_ErrorCounter < 3) {
			try {
				double_deposit = Double
						.parseDouble(JOptionPane.showInputDialog("Please enter the amount to be deposited."));

				if (double_deposit >= 0 && double_balance >= 0) {
					double_balance = double_balance + double_deposit;
					double_lastTransaction = double_deposit;
					int_counter++;
					tracker();
					break;
				} else {
					JOptionPane.showMessageDialog(null, "Please enter an amount greater than 0");
				}

			} catch (Exception e) {
				int_ErrorCounter++;
				if (int_ErrorCounter >= 3)
					JOptionPane.showMessageDialog(null, "You have exceeded maximum error input. Please try again.");
				else
					JOptionPane.showMessageDialog(null,
							"You have maximum " + (3 - int_ErrorCounter) + " attempts left.");
			}
		}
	}

	static void tracker() {
		if (int_counter >= 10) {
			for (int i = 0; i < arrDouble_TransactionDetail.length - 1; i++) {
				arrDouble_TransactionDetail[i] = arrDouble_TransactionDetail[i + 1];
			}
			int_counter--;
			arrDouble_TransactionDetail[int_counter] = double_lastTransaction;
		} else
			arrDouble_TransactionDetail[int_counter] = double_lastTransaction;
	}

	static void withdraw() {

		double double_withdrawalAmount;

		try {
			double_withdrawalAmount = Double
					.parseDouble(JOptionPane.showInputDialog("Please enter the withdrawal amount."));

			if (double_withdrawalAmount <= double_balance) {
				if (double_withdrawalAmount < 0) {
					JOptionPane.showMessageDialog(null, "Please enter an amount greater than 0");
				} else {
					double_balance = double_balance - double_withdrawalAmount;
					double_lastTransaction = -double_withdrawalAmount;
					int_counter++;
					tracker();
				}
			}

			else {
				JOptionPane.showMessageDialog(null,
						"Please enter an amount lesser than your balance. \n Or \n Please view balance first.");
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
					"Please enter an amount lesser than your balance. \n Or \n Please view balance first.");
		}
	}

	static void viewBalance() {
		JOptionPane.showMessageDialog(null,
				"Dear " + str_customerName + "\n" + "Your current balance is : " + double_balance);
	}

	static void statement() {
		StringBuilder sb = new StringBuilder();
		sb.append("Please find the details of the last 10 transactions mentioned below \n ");
		for (int i = 0; i < arrDouble_TransactionDetail.length; i++) {
			if (arrDouble_TransactionDetail[i] != 0)
				sb.append("\nTransaction # " + (i + 1) + " : " + arrDouble_TransactionDetail[i]);
		}
		JOptionPane.showMessageDialog(null, sb);
	}

	static boolean dataValidation(String content) {
		if (content != null && !content.equalsIgnoreCase("")) {
			for (int i = 0; i < content.length(); i++) {
				if (str_spclChars.indexOf(content.charAt(i)) != -1) {
					JOptionPane.showMessageDialog(null,
							"Please enter only your first name. No Special characters are allowed");
					return false;
				}
			}
			flag_name = true;
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Please enter your first name.");
			return false;
		}
	}
}
