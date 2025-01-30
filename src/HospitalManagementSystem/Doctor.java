package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
	private Connection co;
	public Doctor(Connection connection)
	{
		this.co=connection;
	}
	public void viewDoctor()
	{
		String query="select * from doctors";
		try
		{
			PreparedStatement pre=co.prepareStatement(query);
			ResultSet res=pre.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+-----------+-------------------+--------------- +");
			System.out.println("|Doctor Id  |        Name       | Specialization |");
			System.out.println("+-----------+-------------------+--------------- +");
			while(res.next())
			{
				int id=res.getInt("id");
				String name=res.getString("name");
				String specialization=res.getString("specialization");
				System.out.printf("|%-11s|%-19s|%-16s|\n",id,name,specialization);
				System.out.println("+-----------+-------------------+--------------- +");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public boolean getDoctorById(int id)
	{
		String query="select * from doctors where id=?";
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
