package pl.gitara.service;

import java.util.List;

import pl.gitara.domain.Guitar;
import pl.gitara.domain.Owner;

public interface GuitarManager {

	Long addOwner(Owner owner);
	Owner findOwnerById(Long id);
	void deleteOwner(Owner owner);
	void updateOwner(Owner owner);
	List<Owner>  findAllOwners();


	void changeGuitarOwner(Guitar guitar, Owner oldOwner, Owner newOwner);

	Long addGuitar(Guitar guitar);
	Guitar findGuitarById(Long id);
	void deleteGuitar(Guitar guitar);
	void deleteGuitarWithOwner(Guitar guitar, Owner owner);
	void updateGuitar(Guitar guitar);
	List<Guitar>  findAllGuitars();
	List<Guitar> findGuitarsByManufacturer(String manufacturerNameFragment);
	List<Guitar> findGuitarsByModel(String modelNameFragment);
}
