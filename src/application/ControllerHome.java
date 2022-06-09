package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ControllerHome {
	
	@FXML
	private MenuItem products;
	
	@FXML 
	private MenuItem clientes;
	
	@FXML 
	private MenuItem ventas;
	
	@FXML 
	private MenuItem exit;
	
	
	@FXML
	void ventasOneClick(ActionEvent event) throws IOException{
		
		try {
			Parent root = (new FXMLLoader(getClass().getResource("/fxml/ventas.fxml"))).load();
			Scene scene = new Scene(root);
			Stage contenedor = new Stage();
			contenedor.setTitle("Ventas");
			contenedor.setScene(scene);
			contenedor.show();
			
		}catch (Exception e){
			System.out.println("se presento un ERROR");
			e.printStackTrace();
		}
	}
	
	@FXML
	void productsOnesClick(ActionEvent event) throws IOException{
		
		try {
			Parent root = (new FXMLLoader(getClass().getResource("/fxml/movies.fxml"))).load();
			Scene scene = new Scene(root);
			Stage contenedor = new Stage();
			contenedor.setTitle("movies");
			contenedor.setScene(scene);
			contenedor.show();
			
		}catch (Exception e){
			System.out.println("se presento un ERROR");
			e.printStackTrace();
		}
	}
	
	@FXML
	void clientesOneClick (ActionEvent event) throws IOException{
		
		try {
			Parent root = (new FXMLLoader(getClass().getResource("/fxml/cliente.fxml"))).load();
			Scene scene = new Scene(root);
			Stage contenedor = new Stage();
			contenedor.setTitle("clientes");
			contenedor.setScene(scene);
			contenedor.show();
			
		}catch (Exception e){
			System.out.println("se presento un ERROR");
			e.printStackTrace();
		}
	}
	
	@FXML
	void exitOneClick (ActionEvent event) throws IOException{
		
		try {
			Parent root = (new FXMLLoader(getClass().getResource("/fxml/....fxml"))).load();
			Scene scene = new Scene(root);
			Stage contenedor = new Stage();
			contenedor.setTitle("Ventas");
			contenedor.setScene(scene);
			contenedor.show();
			
		}catch (Exception e){
			System.out.println("se presento un ERROR");
			e.printStackTrace();
		}
	}
		
}
