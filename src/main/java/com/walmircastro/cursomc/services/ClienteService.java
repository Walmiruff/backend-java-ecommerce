package com.walmircastro.cursomc.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.walmircastro.cursomc.domain.Cidade;
import com.walmircastro.cursomc.domain.Cliente;
import com.walmircastro.cursomc.domain.Endereco;
import com.walmircastro.cursomc.domain.enums.TipoCliente;
import com.walmircastro.cursomc.dto.ClienteDTO;
import com.walmircastro.cursomc.dto.ClienteNewDTO;
import com.walmircastro.cursomc.repositories.ClienteRepository;
import com.walmircastro.cursomc.repositories.EnderecoRepository;
import com.walmircastro.cursomc.services.exceptions.DataIntegrityException;
import com.walmircastro.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException( // Mine class personalized of exceptions
	 			"Objeto não encontrado! Id: " + id + ", Tipo: "+ Cliente.class.getName()
	 	));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Cliente update(Cliente obj) {
		Cliente newObj = this.find(obj.getId());
		this.updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma cliente com pedidos relacionados.");
		}

	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome() ,objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		
		cli.setEnderecos(Arrays.asList(end));
		
		Set<String> hash_SetTel = new HashSet<String>();
		hash_SetTel.add(objDto.getTelefone1());
	    if (objDto.getTelefone2()!=null) {
			hash_SetTel.add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			hash_SetTel.add(objDto.getTelefone3());
		}
		cli.setTelefones(hash_SetTel);
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
