package pl.gitara.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.gitara.domain.Guitar;
import pl.gitara.domain.Owner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class GuitarManagerTest {

	@Autowired
    GuitarManager guitarManager;
	List<Long> guitarIds;
	List<Long> ownerIds;

	/**
	 * Helper method allowing for easier adding guitars to tests
	 * @param manufacturer
	 * @param model
	 * @return
	 */


	
    private Owner addOwnerHelper(String name, List<Guitar> guitars) {
        Long ownerId;
		Owner owner;
		owner = new Owner();
		owner.setName(name);
		owner.setGuitars(guitars);
		owner.setId(null);
		ownerIds.add(ownerId = guitarManager.addOwner(owner));
		assertNotNull(ownerId);
        return owner;
}
	private Guitar addGuitarHelper(String mf, String ml) {
		Long guitarId;
		Guitar guitar;
		guitar = new Guitar();
		guitar.setManufacturer(mf);
		guitar.setModel(ml);
		guitar.setId(null);
		guitarIds.add(guitarId = guitarManager.addGuitar(guitar));
		assertNotNull(guitarId);
		return guitar;
	}



	@Before
	public void setup() {
		guitarIds = new LinkedList<>();
		ownerIds = new LinkedList<>();

		addGuitarHelper("Mayones","Duvell");
		addGuitarHelper("Gibson","SG");
		addGuitarHelper("???", "LTD");
		Guitar guitarr = addGuitarHelper("Cort","Katana");

		
		addOwnerHelper("Steve",new LinkedList<Guitar>());
		ArrayList<Guitar> guitars = new ArrayList<Guitar>();
		guitars.add(guitarr);
		addOwnerHelper("Jason",guitars);
	}

	@Test
	public void addGuitarTest() {
		assertTrue(guitarIds.size() > 0);
	}

	@Test
	public void getAllGuitarsTest() {
		List <Long> foundIds = new LinkedList<>();

		for (Guitar guitar: guitarManager.findAllGuitars()) {
			if (guitarIds.contains(guitar.getId())) foundIds.add(guitar.getId());
		}
		assertEquals(guitarIds.size(), foundIds.size());
	}

	@Test
	public void getGuitarByIdTest() {
		Guitar guitar = guitarManager.findGuitarById(guitarIds.get(0));
		assertEquals("Mayones",guitar.getManufacturer());
		assertEquals("Duvell", guitar.getModel());
	}

    @Test
	public void findAllOwnersTest() {
		List <Long> foundIds = new LinkedList<>();
		for (Owner owner: guitarManager.findAllOwners()) {
			if (ownerIds.contains(owner.getId())) foundIds.add(owner.getId());
		}
		assertEquals(ownerIds.size(), foundIds.size());
}

	@Test
	public void deleteGuitarTest() {
		Guitar guitar = guitarManager.findGuitarById(guitarIds.get(1));
		assertNotNull(guitar);
		guitarManager.deleteGuitar(guitar);
		assertNull(guitarManager.findGuitarById(guitarIds.get(1)));
	}

	@Test
	public void updateGuitarWithOwnerTest() {
		Guitar guitar = guitarManager.findGuitarById(guitarIds.get(3));
		assertNotNull(guitar);
		guitar.setManufacturer("Les Paul");
		guitarManager.updateGuitar(guitar);
		Guitar updatedGuitar = guitarManager.findGuitarById(guitarIds.get(3));
		assertEquals("Les Paul", updatedGuitar.getManufacturer());
	}


	@Test
	public void updateGuitarWithoutTest() {
		Guitar guitar = guitarManager.findGuitarById(guitarIds.get(2));
		assertNotNull(guitar);
		guitar.setManufacturer("ESP");
		guitarManager.updateGuitar(guitar);
		Guitar updatedGuitar = guitarManager.findGuitarById(guitarIds.get(2));
		assertEquals("ESP", updatedGuitar.getManufacturer());
	}

	@Test
	public void updateOwnerWithGuitarTest() {
		Owner owner = guitarManager.findOwnerById(ownerIds.get(1));
		assertEquals(1, owner.getGuitars().size());
		assertNotNull(owner);
		owner.setName("Andrzej");
		guitarManager.updateOwner(owner);
		Owner updatedOwner = guitarManager.findOwnerById(ownerIds.get(1));
		assertEquals("Andrzej", updatedOwner.getName());
	}

	@Test
	public void updateOwnerWithoutGuitarTest() {
		Owner owner = guitarManager.findOwnerById(ownerIds.get(0));
		assertEquals(0, owner.getGuitars().size());
		assertNotNull(owner);
		owner.setName("Andrzej");
		guitarManager.updateOwner(owner);
		Owner updatedOwner = guitarManager.findOwnerById(ownerIds.get(0));
		assertEquals("Andrzej", updatedOwner.getName());
	}

	@Test
	public void findByGuitarModelTest() {
		List<Guitar> guitars = guitarManager.findGuitarsByModel("vel");
		assertEquals("Duvell", guitars.get(0).getModel());
	}

	@Test
	public void findByGuitarManufacturerTest() {
		List<Guitar> guitars = guitarManager.findGuitarsByManufacturer("ayo");
		assertEquals("Mayones", guitars.get(0).getManufacturer());
	}

	@Test()
    public void changeGuitarOwnerTest() {
        Guitar guitar = guitarManager.findGuitarById(guitarIds.get(3));
        Owner oldOwner = guitarManager.findOwnerById(ownerIds.get(1));
        Owner newOwner = guitarManager.findOwnerById(ownerIds.get(0));
        guitarManager.changeGuitarOwner(guitar, oldOwner, newOwner);
        assertEquals(0, guitarManager.findOwnerById(ownerIds.get(1)).getGuitars().size());
        assertEquals(1, guitarManager.findOwnerById(ownerIds.get(0)).getGuitars().size());
        assertEquals(guitar.getId(), guitarManager.findOwnerById(ownerIds.get(0)).getGuitars().get(0).getId());
    }

    @Test
    public void deleteGuitarWithOwnerTest() {
        Owner owner = guitarManager.findOwnerById(ownerIds.get(1));
        assertEquals(1, owner.getGuitars().size());
        int prevSize = guitarManager.findAllGuitars().size();
        Guitar guitar = guitarManager.findGuitarById(guitarIds.get(3));
        assertNotNull(guitar);
        guitarManager.deleteGuitarWithOwner(guitar, owner);
        assertNull(guitarManager.findGuitarById(guitarIds.get(3)));
        assertEquals(prevSize - 1, guitarManager.findAllGuitars().size());
        assertEquals(0, owner.getGuitars().size());
    }

    @Test
    public void deleteGuitarWithoutOwnerTest() {
        int prevSize = guitarManager.findAllGuitars().size();
        Guitar guitar = guitarManager.findGuitarById(guitarIds.get(2));
        assertNotNull(guitar);
        guitarManager.deleteGuitar(guitar);
        assertNull(guitarManager.findGuitarById(guitarIds.get(2)));
        assertEquals(prevSize - 1, guitarManager.findAllGuitars().size());
	}

}
