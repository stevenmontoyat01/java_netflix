module inventario_inter {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires mysql.connector.java;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
