package com.titanpay.accounting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.titanpay.accounting.dataclass.Address;
import com.titanpay.accounting.dataclass.CsvReader;
import com.titanpay.accounting.dataclass.PaymentMethod;
import com.titanpay.accounting.dataclass.Receipt;
import com.titanpay.accounting.dataclass.TimeCard;
import com.titanpay.accounting.entity.Employee;
import com.titanpay.accounting.entity.HourlyEmployee;
import com.titanpay.accounting.entity.SalariedEmployee;
import com.titanpay.accounting.view.PayrollOverviewControler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class MainApp extends Application {
	private static final String PERSISTENCE_UNIT_NAME = "titanDB";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;
	private List<HourlyEmployee> employeesHour = CsvReader.parseHourlyEmployees("hourly_employees.csv");
	private List<SalariedEmployee> employeesSal = CsvReader.parseSalariedEmployees("salaried_employees.csv");
	private Stage primaryStage;
	private BorderPane rootLayout;

	public MainApp() {
	}

	public void runAddHourlyEmployee() {
		for (HourlyEmployee employee : employeesHour) {
			List<TimeCard> hourETimeCard = CsvReader.parseTimeCards("timecards.csv", employee.getEmplid());
			employee.setTimeCardsArray(hourETimeCard);
		}
		EntityManager em = factory.createEntityManager();
		Query idQuery = em.createQuery("select e.emplid from Employee e");
		// sets allow you to check if something exists REALLY FAST
		List<String> ids = new ArrayList<String>(idQuery.getResultList());
		for (HourlyEmployee employee : employeesHour) {
			// if our id Does Not ALREADY EXIST in the database
			if (ids.contains(employee.getEmplid()) == false) {
				// then add it
				addHourlyEmployee(employee.getEmplid(), employee.getFName(), employee.getLName(), employee.getAddress(),
					employee.getPaymentMethod(), employee.getUnionDues(), employee.getTimeCards(),
					employee.getHourlyRate());
			}
		}

	}

	public void runAddSalariedEmployee() {
		for (SalariedEmployee employee : employeesSal) {
			List<Receipt> salEReceipt = CsvReader.parseReceipts("receipts.csv", employee.getEmplid());
			employee.setReceiptsArray(salEReceipt);
		}
		
		EntityManager em = factory.createEntityManager();
		Query idQuery = em.createQuery("select e.emplid from Employee e");
		
		List<String> ids = new ArrayList<String>(idQuery.getResultList());
		for (SalariedEmployee employee : employeesSal) {
			// if our id doesn' ALREADY EXIST in the database
			if (ids.contains(employee.getEmplid()) == false) {
				// then add it
				addSalariedEmployee(employee.getEmplid(), employee.getFName(), employee.getLName(), employee.getAddress(),
					employee.getPaymentMethod(), employee.getUnionDues(), employee.getReceipts(),
					employee.getCommissionRate(), employee.getSalary());
			}
		}

	}

	public static void addHourlyEmployee(int Emplid, String fName, String lName, Address address,
			PaymentMethod paymentMethod, double UnionDues, ArrayList<TimeCard> timeCards, double hourlyRate) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		HourlyEmployee todo = new HourlyEmployee();
		todo.setEmplid(Emplid);
		todo.setFName(fName);
		todo.setLName(lName);
		todo.setAddress(address);
		todo.setPaymentMethod(paymentMethod);
		todo.setUnionDues(UnionDues);
		todo.setHourlyRate(hourlyRate);
		todo.setTimeCards(timeCards);

		em.persist(todo);
		em.getTransaction().commit();

		em.close();

	}

	public static void addSalariedEmployee(int Emplid, String fName, String lName, Address address,
			PaymentMethod paymentMethod, double UnionDues, ArrayList<Receipt> receipts, double CommissionRate,
			double salary) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		
		
		SalariedEmployee todo = new SalariedEmployee();
		todo.setEmplid(Emplid);
		todo.setFName(fName);
		todo.setLName(lName);
		todo.setAddress(address);
		todo.setPaymentMethod(paymentMethod);
		todo.setUnionDues(UnionDues);
		todo.setCommissionRate(CommissionRate);
		todo.setReceipts(receipts);
		todo.setSalary(salary);

		em.persist(todo);
		em.getTransaction().commit();

		em.close();

	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TitanPay App");
		initRootLayout();
		showPayrollOverview();

	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPayrollOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PayrollOverview.fxml"));
			AnchorPane payrollOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(payrollOverview);

			// Give the controller access to the main app.
			PayrollOverviewControler controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void importEmployees() {
		//Adds employees from the 2 lists we have to the data base
		runAddSalariedEmployee();
		runAddHourlyEmployee();

	}
	public void clearDatabase(){
		try{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("select e from Employee e");
		List<Employee> resultList = query.getResultList();
		for (Employee employee : resultList) {
			em.remove(employee);
		}
		
		
		em.getTransaction().commit();
	} catch(Exception e){
		e.printStackTrace();
	}
		}

	public static EntityManagerFactory getFactory() {
		return factory;
	}

	public static void setFactory(EntityManagerFactory factory) {
		MainApp.factory = factory;
	}
}
