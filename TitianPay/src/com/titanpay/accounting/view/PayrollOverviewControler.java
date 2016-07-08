package com.titanpay.accounting.view;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.titanpay.accounting.MainApp;
import com.titanpay.accounting.entity.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PayrollOverviewControler {
	@FXML
	private TextField startDateField;
	@FXML
	private TextField endDateField;
	@FXML
	private ListView<String> payedS;

	private ObservableList<String> payedPeople = FXCollections.observableArrayList();

	private boolean payrollClicked = false;
	private boolean importEClicked = false;
	private boolean clearDClicked = false;

	private MainApp mainApp;

	private static EntityManagerFactory factory2;

	public PayrollOverviewControler() {
	}

	@FXML
	private void initialize() {

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleRunPayroll() {
		
			factory2 = Persistence.createEntityManagerFactory("titanDB");
			EntityManager em2 = factory2.createEntityManager();
			
			// this is the equivalent of calling "select * from Employee"
			Query q = em2.createQuery("select e from Employee e");
			List<Employee> todoList = q.getResultList();
			System.out.println("Size: " + todoList.size());
			if (!payrollClicked && todoList.size()!=0) {
			for (Employee todo : todoList) {
				if (todo.payToString(startDateField.getText(), endDateField.getText()) != null)
					payedPeople.add(todo.payToString(startDateField.getText(), endDateField.getText()));
				// System.out.println(todo);//.getFName()+" "+todo.getEmplid());
			}
			System.out.println("Size: " + todoList.size());
			
			payedS.setItems(payedPeople);

			if(todoList.size()>0)
			payrollClicked = true;
		}
	}

	@FXML
	private void handleImportEmployees() {
		//if (!importEClicked)
		mainApp.importEmployees();
		importEClicked = true; 
	}
	@FXML
	private void handleClearDataBase(){
		if (!clearDClicked)
		mainApp.clearDatabase();
		clearDClicked = true;
		
		
		
	}
}
