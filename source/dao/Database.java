package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Patient;
import models.Doctor;
import models.Message;

public class Database {
//	private String jdbcURL = "jdbc:mysql://127.0.0.1:3306/dev";
//	private String jdbcUsername = "root";
//	private String jdbcPassword = "root123";

	private String jdbcURL = "jdbc:mysql://127.0.0.1:3306/PatientCarePlus";
	private String jdbcUsername = "root";
	private String jdbcPassword = "password123";

    String memoryURL = "jdbc:sqlite::memory:";

    private Connection conn;

    public static void main(String[] args) {
        Database db = new Database();
        try {
            String query = "SELECT * FROM users";
            ResultSet rs = db.getResultSet(query);
            if (rs == null) {
                System.out.println("No data");
                return;
            }
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public Patient authP(String username, String password) {
        System.out.printf("Authorizing Patient \nUsername: %s \nPassword: %s \n", username, password);
        Database db = new Database();
        try {
            String sql = "SELECT * FROM patients WHERE username=? AND password=?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            Patient pat = new Patient();
            pat.setId(rs.getInt("patient_id"));
            pat.setEmail(rs.getString("email"));
            pat.setLastName(rs.getString("last_name"));
            pat.setFirstName(rs.getString("first_name"));
            pat.setEmergencyPhone(rs.getString("emergency_phone"));
            pat.setEmergencyContact(rs.getString("emergency_contact"));
            pat.setDoctorsNotes(rs.getString("doctors_notes"));
            // pat.setMessages(getMessages(pat.getId()));
            return pat;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    public Patient getPatient(int id) {
        System.out.printf("Get Patient %d\n", id);
        Database db = new Database();
        try {
            String sql = "SELECT * FROM patients WHERE patient_id="+id;

            PreparedStatement ps = db.prepare(sql);

            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            Patient pat = new Patient();
            pat.setId(rs.getInt("patient_id"));
            pat.setEmail(rs.getString("email"));
            pat.setLastName(rs.getString("last_name"));
            pat.setFirstName(rs.getString("first_name"));
            pat.setEmergencyPhone(rs.getString("emergency_phone"));
            pat.setEmergencyContact(rs.getString("emergency_contact"));
            pat.setDoctorsNotes(rs.getString("doctors_notes"));
            // pat.setMessages(getMessages(id));
            return pat;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    public Patient patientForgotPassword(String email, String phone)
    {
    	Database db = new Database();
        try {
            String sql = "SELECT * FROM patients WHERE email=? AND emergency_phone=?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            Patient pat = new Patient();
            pat.setId(rs.getInt("patient_id"));
            pat.setUsername(rs.getString("username"));
            pat.setEmail(rs.getString("email"));
            pat.setLastName(rs.getString("last_name"));
            pat.setFirstName(rs.getString("first_name"));
            pat.setEmergencyPhone(rs.getString("emergency_phone"));
            pat.setEmergencyContact(rs.getString("emergency_contact"));
            pat.setDoctorsNotes(rs.getString("doctors_notes"));
            // pat.setMessages(getMessages(pat.getId()));

            return pat;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    public boolean resetPassword (int id, String password, int userType)
    {
    	Database db = new Database();
        try {
        	String sql ="UPDATE ";
        	if(userType==1) // patient
        	{
        		sql += "patients SET password=? WHERE patient_id=?";
        	}else {     //doctor
        		sql += "doctors SET password=? WHERE doctor_id=?";
        	}
        	PreparedStatement ps = db.prepare(sql);
        	ps.setString(1, password);
        	ps.setInt(2, id);
        	ps.execute();
        	return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    public Doctor authD(String username, String password) {
        System.out.printf("Authorizing Doctor \nUsername: %s \nPassword: %s \n", username, password);
        Database db = new Database();
        try {
            String sql = "SELECT * FROM doctors WHERE username=? AND password=?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            Doctor doc = new Doctor();
            doc.setId(rs.getInt("doctor_id"));
            doc.setPatientId(rs.getInt("patient_id"));
            doc.setPhone(rs.getString("phone"));
            doc.setEmail(rs.getString("email"));
            doc.setLastName(rs.getString("last_name"));
            doc.setFirstName(rs.getString("first_name"));
            doc.setWorkAddress(rs.getString("work_address"));
            doc.setCertification(rs.getString("certification"));
            // doc.setMessages(getMessages(doc.getId()));

            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    public ArrayList<Doctor> getDoctorForPatientId(int id) {
        System.out.printf("Get Doctor %n", id);
        Database db = new Database();
        try {
            String sql = "select distinct connections.patient_id, connections.doctor_id, phone, email, last_name, first_name, work_address, certification from connections join doctors on connections.doctor_id = doctors.doctor_id where connections.patient_id = ?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, "" + id);

            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;

            ArrayList<Doctor> docList = new ArrayList<>();

            do{
                Doctor doc = new Doctor();

                doc.setId(rs.getInt("doctor_id"));
                doc.setPatientId(rs.getInt("patient_id"));
                doc.setPhone(rs.getString("phone"));
                doc.setEmail(rs.getString("email"));
                doc.setLastName(rs.getString("last_name"));
                doc.setFirstName(rs.getString("first_name"));
                doc.setWorkAddress(rs.getString("work_address"));
                doc.setCertification(rs.getString("certification"));

                docList.add(doc);

                rs.next();
            }
            while(!rs.isAfterLast());

            // doc.setMessages(getMessages(doc.getId()));

            return docList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    public ArrayList<Patient> getPatientForDoctorId(int id) {
        System.out.printf("Get Patient %n", id);
        Database db = new Database();
        try {
            String sql = "select distinct connections.patient_id, connections.doctor_id, email, last_name, first_name, emergency_contact, emergency_phone, doctors_notes from connections join patients on connections.patient_id = patients.patient_id where connections.doctor_id = ?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, "" + id);

            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;

            ArrayList<Patient> patList = new ArrayList<>();

            do{
                Patient pat = new Patient();

                pat.setId(rs.getInt("patient_id"));
                pat.setEmail(rs.getString("email"));
                pat.setLastName(rs.getString("last_name"));
                pat.setFirstName(rs.getString("first_name"));
                pat.setEmergencyContact(rs.getString("emergency_contact"));
                pat.setEmergencyPhone(rs.getString("emergency_phone"));
                pat.setDoctorsNotes(rs.getString("doctors_notes"));

                patList.add(pat);

                rs.next();
            }
            while(!rs.isAfterLast());

            // doc.setMessages(getMessages(doc.getId()));

            return patList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    public int sendMessage(int senderType, int senderId, int sendToId, String subject, String message)
    {
    	 Database db = new Database();
         try {
        	 String insert = "INSERT INTO PatientCarePlus.messages(from_id, to_id, sender_type, subject, message, sent_date) VALUES (?,?,?,?,?,SYSDATE());";
        	 
             PreparedStatement ps = db.prepare(insert);
             ps.setInt(1, senderId);
             ps.setInt(2, sendToId);
             ps.setInt(3, senderType);
             ps.setString(4, subject);
             ps.setString(5, message);
             ps.executeUpdate();
             return 1;
         } catch (Exception ex) {
             ex.printStackTrace();
             return -1;
         } finally {
             db.close();
         }
    	
    }
    public int sendMessage(int rootMessageId, int senderType, int senderId, int sendToId, String subject, String message)
    {
    	 Database db = new Database();
         try {
        	 String insert = "INSERT INTO PatientCarePlus.messages(root_message_id, from_id, to_id, sender_type, subject, message, sent_date) VALUES (?,?,?,?,?,?,SYSDATE());";
        	 
             PreparedStatement ps = db.prepare(insert);
             ps.setInt(1, rootMessageId);
             ps.setInt(2, senderId);
             ps.setInt(3, sendToId);
             ps.setInt(4, senderType);
             ps.setString(5, subject);
             ps.setString(6, message);
             ps.executeUpdate();
             return 1;
         } catch (Exception ex) {
             ex.printStackTrace();
             return -1;
         } finally {
             db.close();
         }
    	
    }
    public List<Message>getMessages(int pid, int did)
    {
    	Database db = new Database();
        try {
        	String query = "select * from messages where (to_id=? and from_id=?) or (to_id=? and from_id=?) order by sent_date;";
        	PreparedStatement ps = db.prepare(query);
            ps.setString(1, "" + pid);
            ps.setString(2, "" + did);
            ps.setString(3, "" + did);
            ps.setString(4, "" + pid);
/*
 * int messageId, int fromId, int toId, int senderType, String subject, String message,
			String sentDate
 */
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            List<Message>messages=new ArrayList<Message>();
            do {
            	Message msg = new Message(
            			rs.getInt("root_message_id"),
            			rs.getInt("message_id"),
            			rs.getInt("from_id"),
            			rs.getInt("to_id"),
            			rs.getInt("sender_type"),
            			rs.getString("subject"),
            			rs.getString("message"),
            			rs.getString("sent_date")
            			);
            	messages.add(msg);
            }while(rs.next());
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        
    }
    public List<Message>getMessageThread(int id)
    {
    	Database db = new Database();
        try {
        	String query = "SELECT * FROM messages WHERE root_message_id="+id+" ORDER BY sent_date";
        	PreparedStatement ps = db.prepare(query);
/*
 * int messageId, int fromId, int toId, int senderType, String subject, String message,
			String sentDate
 */
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            List<Message>messages=new ArrayList<Message>();
            do {
            	Message msg = new Message(
            			rs.getInt("root_message_id"),
            			rs.getInt("message_id"),
            			rs.getInt("from_id"),
            			rs.getInt("to_id"),
            			rs.getInt("sender_type"),
            			rs.getString("subject"),
            			rs.getString("message"),
            			rs.getString("sent_date")
            			);
            	messages.add(msg);
            }while(rs.next());
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        
    }

    public Doctor getDoctor(int id){
        System.out.printf("Get Doctor %d\n", id);
        Database db = new Database();
        try{
            String sql = "select * from doctors where doctor_id=?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, "" + id);

            ResultSet rs = ps.executeQuery();

            if(rs == null || !rs.next()){
                System.out.println("yuck! im in here??");
                return null;
            }

            Doctor doc = new Doctor();
            doc.setId(rs.getInt("doctor_id"));
            doc.setPatientId(rs.getInt("patient_id"));
            doc.setPhone(rs.getString("phone"));
            doc.setEmail(rs.getString("email"));
            doc.setLastName(rs.getString("last_name"));
            doc.setFirstName(rs.getString("first_name"));
            doc.setWorkAddress(rs.getString("work_address"));
            doc.setCertification(rs.getString("certification"));
            // doc.setMessages(getMessages(doc.getId()));

            return doc;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        finally{
            db.close();
        }
    }

    public Doctor doctorForgotPassword(String email, String phone) {
        System.out.printf("Add Doctor %s. %s\n", email, phone);
        Database db = new Database();
        try {
            String sql = "SELECT * FROM doctors WHERE email=? AND phone=?";

            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.next())
                return null;
            Doctor doc = new Doctor();
            doc.setId(rs.getInt("doctor_id"));
            doc.setPatientId(rs.getInt("patient_id"));
            doc.setEmail(rs.getString("email"));
            doc.setPhone(rs.getString("phone"));
            doc.setLastName(rs.getString("last_name"));
            doc.setFirstName(rs.getString("first_name"));
            doc.setWorkAddress(rs.getString("work_address"));
            doc.setCertification(rs.getString("certification"));
            // doc.setMessages(getMessages(doc.getId()));
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
    //*****************************
    public boolean isEmailInUse(String userEmail) {
        try {
            String query = "SELECT COUNT(*) FROM patients WHERE email = ?";
            PreparedStatement ps = prepare(query);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, the email is in use
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    //******************************
    
    

    public boolean addUser(String username, String password, boolean checkBox) {
        try {
        	String sql;
        	if(checkBox) {
        		sql = "INSERT INTO patients (username,password) VALUES (?,?)";
        	}
        	else {
        		sql = "INSERT INTO doctors (username,password) VALUES (?,?)";
        	}
            
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deletePatient(int id) {
        try {
            String sql = "DELETE FROM patients WHERE patient_id=" + id;
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean addPatient(String email, String username, String password, String lastName, String firstName,
            String emergencyPhone, String emergencyContact, String doctorsNotes) {
        try {
            String sql = "INSERT INTO patients (email, username, password, last_name, first_name, emergency_phone, emergency_contact, doctors_notes) VALUES (?,?,?,?,?,?,?,?)";
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, lastName);
            ps.setString(5, firstName);
            ps.setString(6, emergencyPhone);
            ps.setString(7, emergencyContact);
            ps.setString(8, doctorsNotes);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updatePatient(int id, String userName, String password, String lastName, String firstName,
            String emergencyPhone, String emergencyContact, String doctorsNotes) {
        try {
            String sql = "UPDATE patients SET email=?,  last_name=?,first_name=?,emergency_phone=?, emergency_contact=?, doctors_notes=? WHERE patient_id=?";
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);

            ps.setString(1, userName);
            // ps.setString(2, password);
            ps.setString(2, lastName);
            ps.setString(3, firstName);
            ps.setString(4, emergencyPhone);
            ps.setString(5, emergencyContact);
            ps.setString(6, doctorsNotes);
            ps.setInt(7, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteDoctor(int id) {
        try {
            String sql = "DELETE FROM doctors WHERE doctor_id=" + id;
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean addDoctor(String email, String username, String password, String lastName, String firstName,
                             String workAddress, String certification, String phone) {
        try {
            String sql = "INSERT INTO doctors (email, username, password, last_name, first_name, work_address, certification, phone) VALUES (?,?,?,?,?,?,?,?)";
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, lastName);
            ps.setString(5, firstName);
            ps.setString(6, workAddress);
            ps.setString(7, certification);
            ps.setString(8, phone);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateDoctor(int id, String userName, String password, String lastName, String firstName,
            String phone, String workAddress, String certification) {
        try {
            String sql = "UPDATE doctors SET email=?,  last_name=?,first_name=?,work_address=?, certification=?,phone=? WHERE patient_id=?";
            Database db = new Database();
            PreparedStatement ps = db.prepare(sql);

            ps.setString(1, userName);
            // ps.setString(2, password);
            ps.setString(2, lastName);
            ps.setString(3, firstName);
            ps.setString(4, workAddress);
            ps.setString(5, certification);
            ps.setString(6, phone);
            ps.setInt(7, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        try {
            if (conn != null)
                return conn;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int execute(String sql) {
        try {
            if (conn == null)
                getConnection();

            PreparedStatement statement = conn.prepareStatement(sql);
            return statement.executeUpdate();
        } catch (SQLException e) {

        } finally {
            close();
        }

        return -1;
    }

    public int execute(String sql, Object... values) {
        try {
            if (conn == null)
                getConnection();

            PreparedStatement statement = conn.prepareStatement(sql);
            int row = 1;
            for (Object obj : values) {
                statement.setObject(row++, obj);
            }
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1);
                return newId;
            }
        } catch (SQLException e) {

        } finally {
            close();
        }

        return -1;
    }

    public PreparedStatement prepare(String sql) {
        try {
            if (conn == null)
                getConnection();

            PreparedStatement statement = conn.prepareStatement(sql);

            return statement;
        } catch (SQLException e) {

        } finally {
            // close();
        }

        return null;
    }

    public ResultSet getResultSet(String query, Object... values) {
        ResultSet rs = null;
        try {
            if (conn == null)
                getConnection();

            PreparedStatement statement = conn.prepareStatement(query);
            int row = 1;
            if (values != null && values.length > 0) {
                for (Object obj : values) {
                    statement.setObject(row++, obj);
                }
            }
            return statement.executeQuery();
        } catch (SQLException e) {

        } finally {
            // close();
        }
        return rs;
    }

    public boolean close() {
        try {
            if (conn == null)
                return true;
            conn.close();
            conn = null;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
