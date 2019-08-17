package org.megloff.example.spring;

import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.megloff.example.spring.dao.CarDAO;
import org.megloff.example.spring.model.Car;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ExampleSpringXMLConfigMain {

	public static void main(String[] args) throws Exception {

		// initialize a logger
		LogManager.getLogManager().readConfiguration(ExampleSpringXMLConfigMain.class.getResourceAsStream("/logger.properties"));
		Logger log = Logger.getLogger(ExampleSpringXMLConfigMain.class.getName());
		//initialize Spring with Java Annotation Configuration 
		ConfigurableApplicationContext context = new GenericXmlApplicationContext("/springConfig.xml");

		CarDAO carDAO = context.getBean(CarDAO.class);

		// check and if there no cars, register some Cars
		if(carDAO.getCount() == 0) {
			log.info("while we have nothing we insert 3 models...");
			
			Car car1 = new Car("323","Mazda");
			carDAO.save(car1);
			
			Car car2 = new Car("Santa Fe","Hyundai");
			carDAO.save(car2);
			
			Car car3 = new Car("S2000","Ferrari");
			carDAO.save(car3);
		}
		
		// Get all the cars
		List<Car> cars = carDAO.getAllCars();
		for (Car car : cars) {
			log.info(car.toString());
		}
		
		// Delete all the cars
		cars = carDAO.getAllCars();
		log.info("deleting the car entries");
		for (Car car : cars) {
			carDAO.delete(car);
		}
		log.info("car entries in db: " + carDAO.getCount());
				
		context.close();
	}
}
