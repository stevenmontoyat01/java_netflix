package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ControllerMovies {


	private String name,tipo;
	private String script;
	private int rest;
	Conexion connect = new Conexion ();
	
	
	
	@FXML
	private TextField inputName;
	
	@FXML
	private TextField inputCategoria;
	
	@FXML
	private Text textAlert;
	
	@FXML
	private Button buttonNew;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private Button buttonUpdate;
	
	@FXML
	private Button buttonView;
	
	@FXML
	private ScrollPane scroll;
	
	@FXML
	private TextArea textMovies;
	
	
	
	@FXML
	void clickSearch (MouseEvent event) throws IOException, SQLException {
		connect.conectar();
		ResultSet rst;
		try(Statement stm = connect.getCon().createStatement()){
			this.name= inputName.getText();
			this.tipo = inputCategoria.getText();
			this.script = "SELECT * FROM productos WHERE nombre='"+name+"'";
			rst = stm.executeQuery(script);
			if(rst.next()) {
				inputName.setDisable(true);
				inputCategoria.setDisable(false);
				inputName.setText(name);
				inputCategoria.setText(tipo);
				buttonUpdate.setDisable(false);
				buttonDelete.setDisable(false);
				connect.desconectar();
			}else {
				inputCategoria.setDisable(false);
				buttonNew.setDisable(false);
			}
		}catch(Exception e){
			System.out.println("error en click img");
			e.printStackTrace();
		}
		connect.desconectar();
	}
	
	@FXML
	void clickNew (ActionEvent event) throws IOException, SQLException {
		connect.conectar();
		try(Statement stm = connect.getCon().createStatement()){
			this.name=inputName.getText();
			this.tipo = inputCategoria.getText();
			if(name == null || name.isEmpty()) {
				textAlert.setText("digite datos en casilla nombre");
			}else if (tipo == null || tipo.isEmpty()) {
				textAlert.setText("digite datos en casilla categoria");
			}else {
				this.script = "INSERT INTO productos(nombre,categoria,estado) VALUES ('"+name+"','"+tipo+"','A')";
				rest = stm.executeUpdate(script);
				if(rest !=0) {
					textAlert.setText("!proceso exitoso¡");
					restaurarDatos();
					connect.desconectar();
				}else {
					textAlert.setText("error no se pudo agregar");
				}
			}
		}catch(Exception e) {
			System.out.println("error en agregar pelicula");
			//e.printStackTrace();
		}
		connect.desconectar();
	}
	
	@FXML
	void clickUpdate(ActionEvent event) throws IOException, SQLException  {
		connect.conectar();
		ResultSet rst;
		try(Statement stm = connect.getCon().createStatement()){
			this.name = inputName.getText();
			this.tipo = inputCategoria.getText();
			this.script = "SELECT codigo FROM productos WHERE nombre ='"+name+"'";
			rst = stm.executeQuery(script);
			if(rst.next()) {
				if(tipo == null || tipo.isEmpty()) {
					textAlert.setText("digite datos en la casilla categoria");
				}else {
					this.script = "UPDATE productos SET categoria ='"+tipo+"'"
							+ "WHERE codigo ="+rst.getInt("codigo")+"";
					rest = stm.executeUpdate(script);
					if(rest!=0) {
						textAlert.setText("!actualizacion exitosa¡");
						connect.desconectar();
						restaurarDatos();
					}else {
						textAlert.setText("error en la actualizacion");
					}
				}
			}else {
				System.out.println("error en consulta de update");
			}
		}catch (Exception e) {
			System.out.println("error en actualizacion de peliculas o series");
			e.printStackTrace();
		}
		connect.desconectar();
	}
	
	@FXML
	void clickDelete(ActionEvent event) throws IOException, SQLException {
		connect.conectar();
		ResultSet rst;
		try (Statement stm = connect.getCon().createStatement()){
			this.name = inputName.getText();
			this.tipo = inputCategoria.getText();
			this.script = "SELECT * FROM productos WHERE nombre ='"+name+"'";
			rst = stm.executeQuery(script);
			if(rst.next()) {
				this.script = "UPDATE productos SET estado = 'I' WHERE codigo ="+rst.getInt("codigo")+"";
				rest = stm.executeUpdate(script);
				if(rest!=0) {
					textAlert.setText("!elimindo exitosamente¡");
					connect.desconectar();
					restaurarDatos();
				}else {
					textAlert.setText("no se puedo eliminar");
				}
			}else{
				System.out.println("error en consulta");
			}
		}catch(Exception e) {
			System.out.println("error en delete de movie y serie");
			e.printStackTrace();
		}
		connect.desconectar();
	}

	@FXML 
	void clickView(ActionEvent event) throws IOException, SQLException {
		connect.conectar();
		ResultSet rst;
		try (Statement stm = connect.getCon().createStatement()){
			this.script = "SELECT * FROM productos";
			rst = stm.executeQuery(script);
			if(rst.next()) {
				scroll.setVisible(true);
				textMovies.setText(rst.getString("nombre"));
			}else {
				System.out.println("error en consulta");
			}
		}catch (Exception e) {
			System.out.println("erro en vista de movies y serie");
			//e.printStackTrace();
		}
	}
	
	@FXML
	void initialize() {
		inputCategoria.setDisable(true);
		buttonNew.setDisable(true);
		buttonUpdate.setDisable(true);
		buttonDelete.setDisable(true);
	}
	
	@FXML
	void restaurarDatos() {
		inputName.clear();
		inputCategoria.clear();
		buttonNew.setDisable(true);
		buttonUpdate.setDisable(true);
		buttonDelete.setDisable(true);
		inputName.setDisable(false);
		inputCategoria.setDisable(true);
		scroll.setVisible(false);
	}


}
