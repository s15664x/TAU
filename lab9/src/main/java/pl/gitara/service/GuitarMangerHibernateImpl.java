package pl.gitara.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.gitara.domain.Guitar;

@Component
@Transactional
public class GuitarMangerHibernateImpl implements GuitarManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long addGuitar(Guitar guitar) {
		// Inna wersja dodawania do bazy danych - korzystamy z save - to nie dostepne w JPA
		return (Long) sessionFactory.getCurrentSession().save(guitar);
	}


	@Override
	public Guitar findGuitarById(Long id) {
		return (Guitar) sessionFactory.getCurrentSession().get(Guitar.class, id);
	}

	@Override
    public void updateGuitar(Guitar guitar) {
        sessionFactory.getCurrentSession().update(guitar);
	}
	@Override
	public List<Guitar> findGuitarsByModel(String modelNameFragment) {
		return (List<Guitar>) sessionFactory.getCurrentSession()
				.getNamedQuery("guitar.findGuitarsByModel")
				.setString("modelNameFragment", "%"+modelNameFragment+"%")
				.list();
	}
	@Override
	public List<Guitar> findGuitarsByManufacturer(String manufacturerNameFragment) {
		return (List<Guitar>) sessionFactory.getCurrentSession()
		.getNamedQuery("guitar.findGuitarsByManufacturer")
		.setString("manufacturerNameFragment", "%"+manufacturerNameFragment+"%")
		.list();
	}

	@Override
	public void deleteGuitar(Guitar guitar) {
			sessionFactory.getCurrentSession().delete(guitar);
	}

	@Override
	public List<Guitar> findAllGuitars() {
		return sessionFactory.getCurrentSession().getNamedQuery("guitar.all")
				.list();
	}



}
