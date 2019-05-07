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
import pl.gitara.domain.AddDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class GuitarManagerTest {

	@Autowired
    GuitarManager guitarManager;

	List<Long> guitarIds;

	/**
	 * Helper method allowing for easier adding guitars to tests
	 * @param manufacturer
	 * @param model
	 * @return
	 */
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

		addGuitarHelper("Mayones","Duvell");
		addGuitarHelper("Gibson","SG");
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
	public void deleteGuitarTest() {
		Guitar guitar = guitarManager.findGuitarById(guitarIds.get(1));
		assertNotNull(guitar);
		guitarManager.deleteGuitar(guitar);
		assertNull(guitarManager.findGuitarById(guitarIds.get(1)));
	}

	@Test
	public void findByGuitarModelTest() {
		List<Guitar> guitars = guitarManager.findGuitarsByModel("vel");
		assertEquals("Duvell", guitars.get(0).getModel());
	}
}
