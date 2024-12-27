package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class CRUDUsingJDBC {

	private static final String URL = "jdbc:mysql://localhost:3306/Schools";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public static Connection connection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void createStudent(String FristName, String LastName, Date DOB, char Gender, int ClassID) {
		String query = "insert into Students (FirstName, LastName, DOB, Gender, ClassID) values (?,?,?,?,?)";
		try (Connection con = connection(); PreparedStatement st = con.prepareStatement(query)){
			st.setString(1, FristName);
			st.setString(2, LastName);
			st.setDate(3, DOB);
			st.setString(4, String.valueOf(Gender));
			st.setInt(5, ClassID);

			int rows = st.executeUpdate();
			System.out.println(rows+" added");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void readStudents() {
		String query = "select * from Students";
		try (Connection con = connection(); Statement st = con.createStatement()){
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("StudentID");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				Date dob = rs.getDate("DOB");
				String Gender = rs.getString(String.valueOf("Gender"));
				int classID = rs.getInt("ClassID");
				System.out.println(id+ " - "+firstName+" "+lastName+" | "+dob+" | "+Gender+" | "+classID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}


	public static void updateStudent(String FristName, String LastName, Date DOB, char Gender, int ClassID, int StudentID) {
		String query = "update Students set FirstName = ?, LastName = ?, DOB = ?, Gender = ?, ClassID = ? where StudentID = ? ";
		try (Connection con = connection(); PreparedStatement st = con.prepareStatement(query)){
			st.setString(1, FristName);
			st.setString(2, LastName);
			st.setDate(3, DOB);
			st.setString(4, String.valueOf(Gender));
			st.setInt(5, ClassID);
			st.setInt(6, StudentID);
			int rows = st.executeUpdate();
			System.out.println(rows+" row updated");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteStudent(int StudentID) {
		String query = "delete from Students where StudentID = ? ";
		try (Connection con = connection(); PreparedStatement st = con.prepareStatement(query)){
			st.setInt(1, StudentID);
			int rows = st.executeUpdate();
			System.out.println(rows+" row deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println("Choose an option: 1-Create 2-Read 3-Update 4-Delete 5-Exit");
			System.out.print("Enter the choosen Option in Numbers : ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.println();
				System.out.print("Enter First Name: ");
				String firstName = scanner.nextLine();
				System.out.print("Enter Last Name: ");
				String lastName = scanner.nextLine();
				System.out.print("Enter DOB (yyyy-MM-dd): ");
				Date dob = Date.valueOf(scanner.nextLine());
				System.out.print("Enter Gender (M/F): ");
				char gender = scanner.nextLine().charAt(0);
				System.out.print("Enter Class ID: ");
				int classID = scanner.nextInt();
				createStudent(firstName, lastName, dob, gender, classID);
				break;

			case 2:
				System.out.println();
				System.out.println("StudentID - FirstName LastName | DOB | Gender | ClassID");
				readStudents();
				break;

			case 3:
				System.out.println();
				System.out.print("Enter Student ID to update: ");
				int studentIDToUpdate = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter First Name: ");
				String updatedFirstName = scanner.nextLine();
				System.out.print("Enter Last Name: ");
				String updatedLastName = scanner.nextLine();
				System.out.print("Enter DOB (yyyy-MM-dd): ");
				Date updatedDob = Date.valueOf(scanner.nextLine());
				System.out.print("Enter Gender (M/F): ");
				char updatedGender = scanner.nextLine().charAt(0);
				System.out.print("Enter Class ID: ");
				int updatedClassID = scanner.nextInt();
				updateStudent(updatedFirstName, updatedLastName, updatedDob, updatedGender, updatedClassID, studentIDToUpdate);
				break;

			case 4:
				System.out.println();
				System.out.print("Enter Student ID to delete: ");
				int studentIDToDelete = scanner.nextInt();
				deleteStudent(studentIDToDelete);
				break;

			case 5:
				System.out.println();
				System.out.println("Exited");
				scanner.close();
				run = false;
				break;

			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
		
//		String query = "select * from Students";
//		String URL = "jdbc:mysql://locahost:3306/Schools";
//		String USER = "root";
//		String PASSWORD = "root";
//		
//		Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
//		Statement st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		rs.next();
//		System.out.println(query);

	}

}


