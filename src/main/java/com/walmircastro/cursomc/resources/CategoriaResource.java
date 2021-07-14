package com.walmircastro.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.walmircastro.cursomc.domain.Categoria;
import com.walmircastro.cursomc.dto.CategoriaDTO;
import com.walmircastro.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody CategoriaDTO objDto) { // DTO used for valid
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody CategoriaDTO objDto, @PathVariable Integer id) { // DTO used for valid
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() { // DTO used for not serialize products
		List<Categoria> lista = service.findAll();
		List<CategoriaDTO> listaDTO = lista.stream()
				.map(el -> new CategoriaDTO(el))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(    // DTO used for not serialize products
			@RequestParam(value="page", defaultValue ="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue ="24") Integer linesPerPage,
			@RequestParam(value="direction", defaultValue ="ASC") String direction,
			@RequestParam(value="orderBy", defaultValue ="nome") String orderBy) {
		Page<Categoria> lista = service.findPage(page, linesPerPage, direction, orderBy);
		Page<CategoriaDTO> listaDTO = lista.map(el -> new CategoriaDTO(el));
		return ResponseEntity.ok().body(listaDTO);
	}

}
