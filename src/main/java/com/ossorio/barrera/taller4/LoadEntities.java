package com.ossorio.barrera.taller4;

import java.util.ArrayList;
import java.util.Collection;

import com.ossorio.barrera.taller4.dao.interfaces.UsvInstitutionDao;
import com.ossorio.barrera.taller4.model.*;
import com.ossorio.barrera.taller4.service.interfaces.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ossorio.barrera.taller4.repository.PersonRepository;
import com.ossorio.barrera.taller4.repository.PersonRoleRepository;
import com.ossorio.barrera.taller4.repository.RoleeRepository;
import com.ossorio.barrera.taller4.repository.UserrRepository;
import com.ossorio.barrera.taller4.repository.UsvInstitutionRepository;
import com.ossorio.barrera.taller4.security.ApplicationUserRole;

import javax.transaction.Transactional;

@Configuration
public class LoadEntities {

	@Bean
	CommandLineRunner initDatabase(UserrRepository userrRepository, RoleeRepository roleeRepository,
			PersonRoleRepository personRoleRepository, PersonRepository personRepository) {
		return args -> {
			if(personRepository.findAll().iterator().hasNext()){
				return;
			}
			// Adding first user with role ADMIN
			final Rolee role1 = new Rolee();
			role1.setRoleName(ApplicationUserRole.OPERATOR.name());
			final PersonRole personRole1 = new PersonRole();
			personRole1.setRolee(role1);
			final Person person1 = new Person();
			person1.setPersName("Barrera");
			person1.setPersonRoles(new ArrayList<PersonRole>());
			person1.addPersonRole(personRole1);
			final PersonRolePK personRolePK = new PersonRolePK();
			personRolePK.setPersPersId(person1.getPersId());
			personRolePK.setRoleRoleId(role1.getRoleId());
			personRole1.setId(personRolePK);
			roleeRepository.save(role1);
			personRepository.save(person1);
			// personRoleRepository.save(personRole1);
			final Userr user1 = new Userr();
			user1.setPerson(person1);
			user1.setUserName("bard");
			user1.setUserPassword("123");
			userrRepository.save(user1);

			// Adding second user with role OPERATOR
			final Rolee role2 = new Rolee();
			role2.setRoleName(ApplicationUserRole.OPERATOR.name());
			roleeRepository.save(role2);
			final PersonRole personRole2 = new PersonRole();
			personRole2.setRolee(role2);
			// personRoleRepository.save(personRole2);
			final Person person2 = new Person();
			person2.setPersName("Ossorio");
			person2.setPersonRoles(new ArrayList<PersonRole>());
			person2.addPersonRole(personRole2);
			personRepository.save(person2);
			final Userr user2 = new Userr();
			user2.setPerson(person2);
			user2.setUserName("jinx");
			user2.setUserPassword("123");
			userrRepository.save(user2);
		};
	}

	@Bean
	CommandLineRunner loadInstitutions(UsvInstitutionRepository institutionRepository,
									   EpidemeventService epidemeventService, SymptomService symptomService,
									   SympweightbydayService sympweightbydayService, UsvInstitutionService usvInstitutionService,
									   ContactfenceService contactfenceService, PersonFenceService personFenceService, PersonService personService) {

		return args -> {
			if(epidemeventService.findAll().size() > 0){
				return;
			}
			if(contactfenceService.findAll().size() > 0){
				return;
			}
			final UsvInstitution institution1 = new UsvInstitution();
			institution1.setInstId(0);
			institution1.setInstAcademicserverurl("https://url.com");
			institution1.setInstAcadextradataurl("https://url.com");
			institution1.setInstAcadloginurl("https://url.com");
			institution1.setInstAcadpersoninfoidurl("https://url.com");
			institution1.setInstAcadphysicalspacesurl("https://url.com");
			institution1.setInstLdapurl("https://url.com");
			institution1.setInstName("Institution 1126");
			usvInstitutionService.save(institution1);

			final Epidemevent epidemevent = new Epidemevent();
			epidemevent.setEpieveId(0);
			epidemeventService.save(epidemevent);

			final Symptom symptom1 = new Symptom();
			symptom1.setSympName("Headache");
			symptomService.save(symptom1);

			final Contactfence cf = new Contactfence();
			contactfenceService.save(cf);
			final Contactfence cf2 = new Contactfence();
			contactfenceService.save(cf2);
			final PersonFencePK pfpk = new PersonFencePK();
			pfpk.setPersPersId(personService.findById(1L).getPersId());
			pfpk.setContfenContfenId(contactfenceService.findAll().get(0).getContfenId());
			final PersonFence pf = new PersonFence();
			pf.setContactfence(cf);
			pf.setId(pfpk);
			pf.setPerson(personService.findById(1L));
			personFenceService.save(pf);
//			final Sympweightbyday sympWeight1 = new Sympweightbyday();
//			sympWeight1.setSympweidaysId(0);
//			sympWeight1.setSympweidaysMax(BigDecimal.valueOf(10));
//			sympWeight1.setSympweidaysMin(BigDecimal.valueOf(10));
//			sympWeight1.setSympweidaysWeight(BigDecimal.valueOf(10));
//			sympweightbydayService.save(sympWeight1, questionId)

		};

	}

}
