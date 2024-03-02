package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SampleController {
	@FXML
	private Button Login,back,addpet,register,addbtn,homeback,view,dog,cat,others;
	@FXML
	private Button SignUp;
	@FXML
	private Label label,myLabel,signuplabel,addlabel,breedlabel,breedlabel1,label1,label2,label3,label4;
	@FXML
	TextField txtuser = new TextField();
	
	
	@FXML
	TextField txtphone = new TextField();
	@FXML
	TextField txtloc = new TextField();
	@FXML
	TextField txtnewuser = new TextField();
	@FXML
	TextField txtemail = new TextField();
	@FXML
	TextField txtnewpass = new TextField();
	@FXML
	private RadioButton optadopt;
	@FXML
	private RadioButton optsell;
	
	
	@FXML
	TextField txtPetname = new TextField();
	@FXML
	TextField txtbreed = new TextField();
	@FXML
	TextField txtAge = new TextField();
	@FXML
	TextField txtColour = new TextField();
	@FXML
	TextField txtlocation = new TextField();
	
	@FXML
	TextField txtSpecies = new TextField();
	@FXML
	private PasswordField txtpass;
	
	private Stage stage;
	private Scene scene;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	@FXML
	void login(ActionEvent event) throws IOException {
		String uname = txtuser.getText();
	    String pass = txtpass.getText();
	    
	    if (uname.isEmpty() && pass.isEmpty()) {
	        label.setText("Username and Password should not be empty");
	    } else if (uname.isEmpty()) {
	        label.setText("Username should not be empty");
	    } else if (pass.isEmpty()) {
	        label.setText("Password should not be empty");
	    } else {
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/pas", "root", "Jeeva2212.");
	            pst = con.prepareStatement("select * from user where username = ? and password = ?");
	            pst.setString(1, uname);
	            pst.setString(2, pass);
	            rs = pst.executeQuery();
	            if (rs.next()) {
	                pst = con.prepareStatement("select role from user where username = ?");
	                pst.setString(1, uname);
	                ResultSet rsRole = pst.executeQuery();

	                if (rsRole.next()) {
	                    String role = rsRole.getString("role");
	   
	                    if ("Adopt".equals(role)) {
	                        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Homepage1.fxml"));
	                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	                        scene = new Scene(root);
	                        stage.setScene(scene);
	                        stage.show();
	                    } else {
	                        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Homepage.fxml"));
	                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	                        scene = new Scene(root);
	                        stage.setScene(scene);
	                        stage.show();
	                    }
	                }
	            } else {
	                label.setText("Invalid username or password");
	                txtuser.clear();
	                txtpass.clear();
	            }
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@FXML
	void register(ActionEvent event) throws IOException {
		String newuname = txtnewuser.getText();
		String newpassword = txtnewpass.getText();
		String newloc = txtloc.getText();
		String newph = txtphone.getText();
		String newemail = txtemail.getText();
		if(newuname == ""||newpassword == ""||newloc == ""||newemail == ""||myLabel.getText()==""||newph =="") {
			signuplabel.setText("All the Fields are compulsory");
		}
		else if(newph.length()!=10)
			signuplabel.setText("Enter Valid phone number");
		else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/pas","root","Jeeva2212.");
				String q = "insert into user (username, password, location, mobile_no, email, role) values (?, ?, ?, ?, ?, ?)";
				pst = con.prepareStatement(q);
				pst.setString(1, newuname);
				pst.setString(2, newpassword);
				pst.setString(3, newloc);
				pst.setString(4, newph);
				pst.setString(5, newemail);
				pst.setString(6, myLabel.getText());
				int n = pst.executeUpdate();
				if(n>0) {
					if(myLabel.getText()=="Adopt") {
						BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Homepage1.fxml"));
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
					}
					else {
						BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Homepage.fxml"));
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
					}
				}
			} catch (ClassNotFoundException | SQLException e) {
				signuplabel.setText("Username "+newuname+" already taken.");
				txtnewuser.clear();
				e.printStackTrace();
			}
			
		}
	}
	@FXML
	
	void add(ActionEvent event) throws IOException {
		String petname = txtPetname.getText();
		String breed = txtbreed.getText();
		String age = txtAge.getText();
		String col = txtColour.getText();
		String location = txtlocation.getText();
		String Species = txtSpecies.getText();
		String user = txtuser.getText();
		
		if(petname == ""||breed == ""||age == ""||col == ""||Species==""||location ==""||user=="") {
			breedlabel.setText("All the Fields are compulsory");
			breedlabel1.setText("");
		}
		
		else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/pas","root","Jeeva2212.");
				String q = "insert into pet (petname,breed,age,colour,location,species,username) values (?, ?, ?, ?, ?, ?,?)";
				pst = con.prepareStatement(q,Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, petname);
				pst.setString(2, breed);
				pst.setString(3, age);
				pst.setString(4, col);
				pst.setString(5, location);
				pst.setString(6, Species);
				pst.setString(7, user);
				pst.executeUpdate();
				try(ResultSet rs = pst.getGeneratedKeys()){
				if(rs.first()) {
					breedlabel.setText("");
						breedlabel1.setText(petname+" with petID "+rs.getLong(1)+" is added Successfully!!");
						txtPetname.clear();
						txtbreed.clear();
						txtAge.clear();
						txtColour.clear();
						txtlocation.clear();
						txtSpecies.clear();
						txtuser.clear();
				}}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	@FXML
	void removepet(ActionEvent event) throws SQLException, ClassNotFoundException {
		String id = txtuser.getText();
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/pas","root","Jeeva2212.");
		
		String q = "Delete from pet where petid=?";
		try {
			pst = con.prepareStatement("select petname from pet where petid = ?");
			pst.setInt(1, Integer.parseInt(id));
	        ResultSet rspet = pst.executeQuery();

	        if (rspet.next()) {
	            String pname = rspet.getString("petname");
			pst = con.prepareStatement(q);
			pst.setInt(1, Integer.parseInt(id));
			pst.executeUpdate();
			breedlabel.setText( pname+" removed");
	        }
	        else {
	        	breedlabel.setText("Invalid pet id");
	        }
		} catch (SQLException e) {
			breedlabel.setText("Enter valid id");
			e.printStackTrace();
		}
		
	}
	@FXML
	void viewpetdetails(ActionEvent event) throws SQLException, ClassNotFoundException {
		String id = txtuser.getText();
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/pas","root","Jeeva2212.");
		try {
			pst = con.prepareStatement("select petname,breed,age,species from pet where petid = ?");
			pst.setInt(1, Integer.parseInt(id));
	        ResultSet rspet = pst.executeQuery();

	        if (rspet.next()) {
	            String pname = rspet.getString("petname");
	            String pbreed = rspet.getString("breed");
	            String page = rspet.getString("age");
	            String pspecies = rspet.getString("species");
	            label1.setText("PET NAME: "+pname);
	            label2.setText("SPECIES: "+pspecies);
	            label3.setText("AGE: "+page);
	            label4.setText("BREED: "+pbreed);
	        }
	        else {
	        	breedlabel.setText("Invalid pet id");
	        	label1.setText("");
	        	label2.setText("");
	        	label3.setText("");
	        	label4.setText("");
	        	
	        }
		} catch (SQLException e) {
			breedlabel.setText("Enter valid id");
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	void switchaddpet(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("addpage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	void switchviewpet(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("viewpet.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	void switchremovepet(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("removepet.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	
	@FXML
	void getOption(ActionEvent event) {
		if(optadopt.isSelected()) {
			myLabel.setText("Adopt");
			optsell.setSelected(false);
		}
		 if(optsell.isSelected())
			myLabel.setText("Sell");
			optadopt.setSelected(false);
	}
	@FXML
	
	public void switchcreateLogin(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Signup.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root,600,485);
		stage.setScene(scene);
		stage.show();
	}
@FXML
	
	public void switchtoHome(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Homepage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
@FXML

public void switchtoHome1(ActionEvent event) throws IOException {
	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Homepage1.fxml"));
	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage.setScene(scene);
	stage.show();
}
	@FXML
	public void switchtoLogin(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

		
	@FXML
	void switchdog(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("dog.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	void switchcat(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("cat.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	void switchothers(ActionEvent event) throws IOException {
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("others.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}	
	
}