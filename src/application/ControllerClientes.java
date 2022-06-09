
package application;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ControllerClientes {
	private String dni,name,apellidos,genero;
	private String script,id;
	private int rest;
	Conexion connect = new Conexion(); 
	
	
	@FXML
	private TextField inputName;
	
	@FXML
	private TextField inputLastName;
	
	@FXML 
	private TextField inputIdentificacion;
	
	@FXML 
	private ComboBox<String> boxGenero;
	
	@FXML 
	private Button buttonNew;
	
	@FXML
	private Button buttonUpdate;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private ImageView imgSearch;
	
	@FXML
	private Text textAlert;
	
	@FXML
	void clickSearch (MouseEvent event) throws IOException, SQLException {
		//preparacion de datos de consulta.
		connect.conectar();
		ResultSet rst ;
		textAlert.setText("");
		boxGenero.getItems().clear();
		try (Statement stm = connect.getCon().createStatement()){
			dni = inputIdentificacion.getText();
			this.script = "SELECT * FROM clientes WHERE cedula = "+dni+"";
			rst = stm.executeQuery(script);
			if(rst.next()) {
				//disabled of button
				buttonNew.setDisable(true);
				buttonUpdate.setDisable(false);
				buttonDelete.setDisable(false);
				inputIdentificacion.setDisable(true);
				inputName.setDisable(false);
				inputLastName.setDisable(false);
				boxGenero.setDisable(false);
				//insert info of client
				this.name = rst.getString("nombre");
				this.apellidos = rst.getString("apellidos");
				this.genero = rst.getString("genero");
				inputName.setText(name);
				inputLastName.setText(apellidos);
				boxGenero.setValue(genero);
				if(genero.equals("M")) {
					boxGenero.getItems().addAll("M","F","U-O");
				}else {
					boxGenero.getItems().addAll("F","M","U-O");
				}
			}else {
				textAlert.setText("cedula no encontrada, ingrese nuevamente o registrese");
				buttonUpdate.setDisable(true);
				buttonDelete.setDisable(true);
				buttonNew.setDisable(false);
				inputName.setDisable(false);
				inputLastName.setDisable(false);
				boxGenero.setDisable(false);
				boxGenero.getItems().addAll("M", "F","U-O");
			}
			connect.desconectar();
		}catch(Exception e) {
			System.out.println("se presento un error en seatch (lupa)");
			//e.printStackTrace();
		}
		connect.desconectar();
	}

	@FXML
	void clickNew (MouseEvent event ) throws IOException, SQLException{
		connect.conectar();
		try (Statement stm = connect.getCon().createStatement()){
			this.dni = inputIdentificacion.getText();
			this.name = inputName.getText();
			this.apellidos = inputLastName.getText();
			this.genero = boxGenero.getValue();
			if( (null == dni || dni.isEmpty()) || (null == name || name.isEmpty()) || 
					(null == apellidos || apellidos.isEmpty()) || (null == genero || genero.isEmpty())){
				textAlert.setText("ingrese todos los datos solicitados");
			}else{
				script = "INSERT INTO clientes(cedula,nombre,apellidos,genero,estado) "
						+ "VALUES ('"+dni+"','"+name+"','"+apellidos+"','"+genero+"','A')";
				rest = stm.executeUpdate(script);
				if(rest != 0) {
					textAlert.setText("!registro exitoso¡");
					restaurarDatos();
				}else {
					textAlert.setText("error al grabar los datos por favor verifique");
				}
				connect.desconectar();
			}
		}catch(Exception e){
			System.out.println("error en registro de nuevo de cliente");
			e.printStackTrace();
		}
		connect.desconectar();
	}
	
	@FXML
	void clickUpdate (MouseEvent event) throws IOException, SQLException{
		connect.conectar();
		ResultSet rst;
		try (Statement stm = connect.getCon().createStatement()) {
			this.dni = inputIdentificacion.getText();
			this.name = inputName.getText();
			this.apellidos = inputLastName.getText();
			this.genero = boxGenero.getValue();
			this.id = "SELECT id FROM clientes WHERE "+dni+"= cedula";
			rst = stm.executeQuery(id);
			System.out.println(rst);
			if(rst.next()) {
				if(name == null || name.isEmpty()) {
					textAlert.setText("ingrese datos en la casilla nombre");
				}else if(apellidos == null || apellidos.isEmpty() ) {
					textAlert.setText("ingrese datos en la casilla apellidos");
				}else {
					this.script = "UPDATE clientes SET cedula = '"+dni+"',nombre = '"+name+"',apellidos ='"+apellidos+"',genero = '"+genero+"' WHERE id =" +rst.getInt("id")+ "";
					int res = stm.executeUpdate(script);
					System.out.println(res);
					if(res !=0){
						textAlert.setText("actualizacion exitosa");
						inputIdentificacion.setDisable(false);
						restaurarDatos();
					}else {
						textAlert.setText("error en actualizacion");
					}
					connect.desconectar();
				}
			}else {
				System.out.println("error en consulta");
			}
		}catch(Exception e) {
			System.out.println("erro en actualizacion de datos de cliente ");
			//e.printStackTrace();
		}
		connect.desconectar();
	}
	
	
	@FXML
	void clickDelete (MouseEvent event) throws IOException, SQLException{
		connect.conectar();
		ResultSet rst;
		try (Statement stm = connect.getCon().createStatement()){
			this.dni = inputIdentificacion.getText();
			this.id = "SELECT *  FROM clientes WHERE "+dni+"=cedula";
			rst = stm.executeQuery(id);
			if(rst.next()) {
				this.script = "UPDATE clientes SET estado = 'I' WHERE id ="+rst.getInt("id")+"";
				rest = stm.executeUpdate(script);
				if(rest !=0) {
					textAlert.setText("eliminacion de cliente exitoso");
					inputIdentificacion.setDisable(false);
					restaurarDatos();
				}
			}else {
				System.out.println("erro en consulta sql");
			}
			
		}catch(Exception e) {
			System.out.println("error en delete de cliente");
			e.printStackTrace();
		}
		connect.desconectar();
	}
	// Metodo de javafx que sirve para inicializar combox y demas en java apenas se abra una ventana
	@FXML
	void initialize (){
		//start of combobox
		inputName.setDisable(true);
		inputLastName.setDisable(true);
		boxGenero.setDisable(true);
        buttonUpdate.setDisable(true);
		buttonDelete.setDisable(true);
		buttonNew.setDisable(true);
	}
	
	@FXML
	private void restaurarDatos() {
		inputName.clear();
		inputLastName.clear();
		inputIdentificacion.clear();
		boxGenero.getItems().clear();
		boxGenero.setValue("");
		buttonUpdate.setDisable(true);
		buttonDelete.setDisable(true);
		buttonNew.setDisable(true);
		inputName.setDisable(true);
		inputLastName.setDisable(true);
		boxGenero.setDisable(true);
	}
	
	
}
