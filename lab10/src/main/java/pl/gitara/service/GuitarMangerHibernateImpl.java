package pl.gitara.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.gitara.domain.Guitar;
import pl.gitara.domain.Owner;

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
	public void deleteGuitarWithOwner(Guitar guitar, Owner owner) {
			sessionFactory.getCurrentSession().delete(guitar);
			owner.getGuitars().remove(guitar);
			
	}

	@Override
	public List<Guitar> findAllGuitars() {
		return sessionFactory.getCurrentSession().getNamedQuery("guitar.all")
				.list();
	}

	@Override
	public List<Owner> findAllOwners() {
		return sessionFactory.getCurrentSession().getNamedQuery("owner.all")
				.list();
	}

	@Override
	public void deleteOwner(Owner owner) {
			sessionFactory.getCurrentSession().delete(owner);
	}

	@Override
    public void updateOwner(Owner owner) {
        sessionFactory.getCurrentSession().update(owner);
	}

	@Override
	public Long addOwner(Owner owner) {
		return (Long) sessionFactory.getCurrentSession().save(owner);
	}

	@Override
	public Owner findOwnerById(Long id) {
		return (Owner) sessionFactory.getCurrentSession().get(Owner.class, id);
	}

	@Override
	public void changeGuitarOwner(Guitar guitar, Owner oldOwner, Owner newOwner) {
        oldOwner.getGuitars().remove(guitar);
        sessionFactory.getCurrentSession().update(oldOwner);
        newOwner.getGuitars().add(guitar);
        sessionFactory.getCurrentSession().update(newOwner);
	}

}
