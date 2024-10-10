module BadProject {
	opens main;
	opens database;
	opens model;
	requires javafx.graphics;
	requires javafx.controls;
	requires jfxtras.labs;
	requires java.sql;
	requires javafx.base;
}