package pl.gitara.service;

import java.util.List;

import pl.gitara.domain.Guitar;

public interface GuitarManager {
	
	Long addGuitar(Guitar guitar);
	Guitar findGuitarById(Long id);
	void deleteGuitar(Guitar guitar);
	void updateGuitar(Guitar guitar);
	List<Guitar>  findAllGuitars();
	List<Guitar> findGuitarsByManufacturer(String manufacturerNameFragment);
	List<Guitar> findGuitarsByModel(String modelNameFragment);
}
