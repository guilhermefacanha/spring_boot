package lab.microservice.labservice.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lab.microservice.labservice.business.UserBusiness;
import lab.microservice.labservice.model.User;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DbInitializationUtil implements InitializingBean {

	@Autowired
	UserBusiness userBusiness;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("=========== Db Initialization===========");
		try {
			createUsers();
		} catch (Exception e) {
			log.error("Error trying to save new users",e);
		}
	}

	private void createUsers() throws Exception {
		Faker faker = new Faker();
		
		for (int i = 0; i < 10; i++) {
			String firstName = faker.gameOfThrones().character();
			
			User user = User.builder()
							.city(faker.gameOfThrones().city())
							.email(firstName.toLowerCase().replace(" ", ".")+"@gameofthrones.com")
							.name(firstName)
							.phone(faker.phoneNumber().phoneNumber())
							.salary(getRandomSalary())
							.birthDate(getRandomBirthDate())
							.build();
			
			userBusiness.salvar(user);
		}
	}

	private Date getRandomBirthDate() {
		Random rand = new Random();
		int nextIntDay = rand.nextInt(31) + 1;
		int nextIntMonth = rand.nextInt(12) + 1;
		int nextIntYear = 90 + rand.nextInt(10);
		
		Calendar cal = Calendar.getInstance();
		cal.set(nextIntYear, nextIntMonth, nextIntDay);
		
		return cal.getTime();
	}

	private double getRandomSalary() {
		Random rand = new Random(10000);
		return rand.nextInt(72000);
	}


}
