package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="Subham@123";
	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		Scanner sc=new Scanner(System.in);
		try
		{
			Connection con=DriverManager.getConnection(url,username,password);
			Patient obj=new Patient(con,sc);
			Doctor obj1=new Doctor(con);
			while(true)
			{
				System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println("1.Add Patient");
				System.out.println("2.View Patients");
				System.out.println("3.View Doctors");
				System.out.println("4.Book Appointment");
				System.out.println("5.Exit");
				System.out.println("Enter your choice: ");
				int choice=sc.nextInt();
				switch(choice)
				{
				    case 1:
					    obj.addPatient();
					    System.out.println();
					    break;
					case 2:
						obj.viewPatient();
						System.out.println();
						break;
					case 3:
					    obj1.viewDoctor();
					    System.out.println();
					    break;
					case 4:
						bookAppointment(obj,obj1,con,sc);
						System.out.println();
						break;
					case 5:
						System.out.println("THANK YOU!");
						return;
					default:
						System.out.println("Enter valid choice!!");
						break;
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patient obj,Doctor obj1,Connection con,Scanner sc)
	{
		System.out.println("Enter patient id: ");
		int patientId=sc.nextInt();
		System.out.println("Enter doctor id: ");
		int doctorId=sc.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD)");
		String appointmentDate=sc.next();
		if(obj.getPatientById(patientId)&&obj1.getDoctorById(doctorId))
		{
			if(checkDoctorAvailability(doctorId,appointmentDate,con))
			{
				String appointmentQuery="insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
				try
				{
					PreparedStatement pre=con.prepareStatement(appointmentQuery);
					pre.setInt(1, patientId);
					pre.setInt(2, doctorId);
					pre.setString(3, appointmentDate);
					int rowsAffected=pre.executeUpdate();
					if(rowsAffected>0)
					{
						System.out.println("Appointment booked.");
					}
					else
					{
						System.out.println("Failed to book appointment");
					}
				}catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Doctor not available on this date.");
			}
		}
		else
		{
			System.out.println("Either patient or doctor doesn't exist.");
		}
	}
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection con)
	{
		String query="select count(*) from appointments where doctor_id=? and appointment_date=?";
		try
		{
			PreparedStatement pre=con.prepareStatement(query);
			pre.setInt(1, doctorId);
			pre.setString(2, appointmentDate);
			ResultSet res=pre.executeQuery();
			if(res.next())
			{
				int count=res.getInt(1);
				if(count==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
