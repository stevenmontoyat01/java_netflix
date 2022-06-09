package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ControllerVentas {
	
	Connection con = null;
	PreparedStatement PreparedStament = null;
	ResultSet resulset;
	String dato, query;
	int user,movie,plan_id,rst;
	Conexion connect = new Conexion ();
	
	@FXML
	private ComboBox <String> menuClientes;
	
	@FXML
	private ComboBox <String> menuProducts;
	
	@FXML 
	private Button buttonOk;
	
	@FXML
	private ComboBox<String> menuPlan;
	
	@FXML
	private Text textAlert;
	
	
	
	@FXML
	void clickVentas(MouseEvent event) throws IOException{
		try {
			String producto = menuProducts.getValue();
			String cliente = menuClientes.getValue();
			String plan = menuPlan.getValue();
			System.out.printf("%5S %10S %15S %n", producto,cliente,plan);
			if(cliente == null || cliente.isEmpty()) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("validacion de datos");
				alerta.setContentText("por favor seleccione un cliente");
				alerta.showAndWait();
			}else if (producto == null || producto.isEmpty()) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("validacion de datos");
				alerta.setContentText("por favor seleccione un producto");
				alerta.showAndWait();
			}else if (plan==null || plan.isEmpty()) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("validacion de datos");
				alerta.setContentText("por favor ingrese la cantidad a comprar");
				alerta.showAndWait();
			}else {
				//conectarme a la base de datos con connect.conectarme();
				connect.conectar();
				try (Statement stm = connect.getCon().createStatement()){
					String [] tmp = cliente.split(" ");
					query = "SELECT id FROM clientes WHERE nombre ='"+tmp[0]+"'";
					resulset = stm.executeQuery(query);
					if(resulset.next() ) {
						user = resulset.getInt("id");
						tmp = producto.split(" ");
						if(tmp.length >1) {
							query = "SELECT codigo FROM productos WHERE nombre ='"+tmp[0]+" "+tmp[1]+"'";
						}else {
							query = "SELECT codigo FROM productos WHERE nombre ='"+tmp[0]+"'";
						}
						System.out.println(query);
						resulset= stm.executeQuery(query);
						if(resulset.next()) {
							movie = resulset.getInt("codigo");
							query = "SELECT id FROM subscripciones WHERE nomsubscrip ='"+plan+"'";
							System.out.println(query);
							resulset = stm.executeQuery(query);
							if(resulset.next()) {
								plan_id = resulset.getInt("id"); 
								query = "INSERT INTO ventas(cliente,producto,subscripcion) "
										+ "VALUES ('"+user+"','"+movie+"','"+plan_id+"')";
								 rst = stm.executeUpdate(query);
								 if(rst !=0) {
									 textAlert.setText("!venta exitosa¡");
									 restaurarDatos();
									 connect.desconectar();
								 }else {
									 textAlert.setText("hubo un error");
								 }
							}else {
								System.out.println("error en tercer condicional");
							}
						}else {
							System.out.println("error en segundo condicional");
						}
					}else {
						System.out.println("error en primer condicional");
					}
					
				}catch(Exception e) {
					System.out.println("se presento un error en ventas");
					e.printStackTrace();
				}
			}
			
		}catch(Exception e){
			System.out.println("se presento un ERROR");
			//e.printStackTrace();
		}
		connect.desconectar();
	}
	
	@FXML
	void initialize() throws IOException, SQLException {
		connect.conectar();
		ResultSet rst;
		//preparar conexion data clientes;
		query = "SELECT nombre,apellidos FROM clientes WHERE estado = 'A' ORDER BY nombre,apellidos";
		try (Statement stm = connect.getCon().createStatement()){
			rst = stm.executeQuery(query);
			while (rst.next()) {
				dato = String.format("%s %s",rst.getString("nombre"), rst.getString("apellidos"));
				menuClientes.getItems().add(dato);
			}
		}catch (Exception e) {
			System.out.println("error en consulta de clientes");
			//e.printStackTrace();
		}
		//preparar conexion data products
		query = "SELECT nombre from productos WHERE estado = 'A' ORDER BY nombre";
		try (Statement stm = connect.getCon().createStatement()){
			rst = stm.executeQuery(query);
			while(rst.next()) {
				dato = String.format("%s",rst.getString("nombre"));
				menuProducts.getItems().add(dato);
			}
		}catch(Exception e) {
			System.out.println("error en consultas productos");
			//e.printStackTrace();
		}
		//preparar conexion data planes
		
		try(Statement stm = connect.getCon().createStatement()){
			query = "SELECT nomsubscrip FROM subscripciones ORDER BY nomsubscrip";
			rst = stm.executeQuery(query);
			while(rst.next()) {
				dato = String.format("%s", rst.getString("nomsubscrip"));
				menuPlan.getItems().add(dato);
			}
		}catch(Exception e) {
			System.out.println("error en consultas de subscripciones");
			//e.printStackTrace();
		}
		connect.desconectar();
	}
	
	@FXML
	void restaurarDatos() {
		menuClientes.setValue("");
		menuProducts.setValue("");
		menuPlan.setValue("");
	}
		
}
