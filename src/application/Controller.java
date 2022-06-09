package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.protocol.Resultset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
	
	
	
	@FXML
	private TextField InputUser;
	
	@FXML
	private TextField InputPassword;
	
	@FXML
	private Button ButtonLogin;
	
	@FXML 
	private Label label; 
	
	Connection con = null;
	PreparedStatement preparredStatement  = null;
	Resultset resulset = null;
	
	@FXML
	void ingresarBtn(ActionEvent event) throws SQLException{
		String login = InputUser.getText();
        String clave = InputPassword.getText();
        Conexion conect = new Conexion();
        conect.conectar();
        if(conect.isConectado()){
            String query = "SELECT codigo FROM usuarios WHERE login = '"+login+"' AND clave = '"+clave+"'";
            try (Statement stm = conect.getCon().createStatement()){
                ResultSet rst = stm.executeQuery(query);
                if(rst.next()){
                    //System.out.println("entra");
                    //Cerrrar la ventana anterior
                    //System.out.println("btnIngr: "+btnIngr);
                    Stage stage = (Stage) ButtonLogin.getScene().getWindow();
                    stage.close();
                    //Crear la nueva ventana
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage = new Stage();
                    //stage.close();
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    //FXMLHomeController data = (FXMLHomeController)loader.getController();
                    //data.setBienvenidoLbl("Hola : "+login+" Bienvenido.");
                    stage.show();
                    conect.desconectar();
                       
                }
                else
                    label.setText("Usuario o Clave no validos");
                
            } catch (Exception e) {
                System.out.println("ERROR: Aborting...");
                //e.printStackTrace();
      
            }
        }
	}
	
	
}
