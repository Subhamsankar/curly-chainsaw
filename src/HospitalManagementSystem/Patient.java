package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient
{
	private Connection co;
	private Scanner sc;
	public Patient(Connection connection,Scanner scanner)
	{
		this.co=connection;
		this.sc=scanner;
	}
	public void addPatient()
	{
		sc.nextLine();
		System.out.print("Enter patient's name: ");
	    String name=sc.nextLine();
	    System.out.print("Enter patient's age: ");
	    int age=sc.nextInt();
	    sc.nextLine();
	    System.out.print("Enter patient's gender: ");
	    String gender=sc.next();
		
		try {
			String query="insert into patients (name,age,gender) values(?,?,?)";
			PreparedStatement pre=co.prepareStatement(query);
			pre.setString(1, name);
			pre.setInt(2, age);
			pre.setString(3, gender);
			int affectedRows=pre.executeUpdate();
			if(affectedRows>0)
			{
				System.out.println("Patient added successfully.");
			}
			else
			{
				System.out.println("Failed to add patient.");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void viewPatient()
	{
		String query="select * from patients";
		try
		{
			PreparedStatement pre=co.prepareStatement(query);
			ResultSet res=pre.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-----------+--------------------------------+-------+--------+");
			System.out.println("|Patient Id |              Name              | Age   | Gender |");
			System.out.println("+-----------+--------------------------------+-------+--------+");
			while(res.next())
			{
				int id=res.getInt("id");
				String name=res.getString("name");
				int age=res.getInt("age");
				String gender=res.getString("gender");
				System.out.printf("| %-9s | %-30s | %-5s | %-6s |\n",id,name,age,gender);
				System.out.println("+-----------+--------------------------------+-------+--------+");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public boolean getPatientById(int id)
	{
		String query="select * from patients where id=?";
		try
		{
			PreparedStatement pre=co.prepareStatement(query);
			pre.setInt(1, id);
			ResultSet res=pre.executeQuery();
			if(res.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
