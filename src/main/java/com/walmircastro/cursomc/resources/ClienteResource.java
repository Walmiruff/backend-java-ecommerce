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

import com.walmircastro.cursomc.domain.Cliente;
import com.walmircastro.cursomc.dto.ClienteDTO;
import com.walmircastro.cursomc.dto.ClienteNewDTO;
import com.walmircastro.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody ClienteNewDTO objDto) { // DTO used for valid
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody ClienteDTO objDto, @PathVariable Integer id) { // DTO used for valid
		Cliente obj = service.fromDTO(objDto);
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
	public ResponseEntity<List<ClienteDTO>> findAll() { // DTO used for not serialize products
		List<Cliente> lista = service.findAll();
		List<ClienteDTO> listaDTO = lista.stream()
				.map(el -> new ClienteDTO(el))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(    // DTO used for not serialize products
			@RequestParam(value="page", defaultValue ="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue ="24") Integer linesPerPage,
			@RequestParam(value="direction", defaultValue ="ASC") String direction,
			@RequestParam(value="orderBy", defaultValue ="nome") String orderBy) {
		Page<Cliente> lista = service.findPage(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> listaDTO = lista.map(el -> new ClienteDTO(el));
		return ResponseEntity.ok().body(listaDTO);
	}

}
