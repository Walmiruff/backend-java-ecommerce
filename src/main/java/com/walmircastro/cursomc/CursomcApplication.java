package com.walmircastro.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.walmircastro.cursomc.domain.Categoria;
import com.walmircastro.cursomc.domain.Cidade;
import com.walmircastro.cursomc.domain.Cliente;
import com.walmircastro.cursomc.domain.Endereco;
import com.walmircastro.cursomc.domain.Estado;
import com.walmircastro.cursomc.domain.ItemPedido;
import com.walmircastro.cursomc.domain.Pagamento;
import com.walmircastro.cursomc.domain.PagamentoComBoleto;
import com.walmircastro.cursomc.domain.PagamentoComCartao;
import com.walmircastro.cursomc.domain.Pedido;
import com.walmircastro.cursomc.domain.Produto;
import com.walmircastro.cursomc.domain.enums.EstadoPagamento;
import com.walmircastro.cursomc.domain.enums.TipoCliente;
import com.walmircastro.cursomc.repositories.CategoriaRepository;
import com.walmircastro.cursomc.repositories.CidadeRepository;
import com.walmircastro.cursomc.repositories.ClienteRepository;
import com.walmircastro.cursomc.repositories.EnderecoRepository;
import com.walmircastro.cursomc.repositories.EstadoRepository;
import com.walmircastro.cursomc.repositories.ItemPedidoRepository;
import com.walmircastro.cursomc.repositories.PagamentoRepository;
import com.walmircastro.cursomc.repositories.PedidoRepository;
import com.walmircastro.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired 
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		cat1.setProdutos(Arrays.asList(p1,p2,p3));
		cat2.setProdutos(Arrays.asList(p2, p4));
		cat3.setProdutos(Arrays.asList(p5, p6));
		cat4.setProdutos(Arrays.asList(p1, p2, p3, p7));
		cat3.setProdutos(Arrays.asList(p8));
		cat6.setProdutos(Arrays.asList(p9, p10));
		cat7.setProdutos(Arrays.asList(p11));
		
		p1.setCategorias(Arrays.asList(cat1, cat4));
		p2.setCategorias(Arrays.asList(cat1, cat2, cat4));
		p3.setCategorias(Arrays.asList(cat1, cat4));
		p4.setCategorias(Arrays.asList(cat2));
		p5.setCategorias(Arrays.asList(cat3));
		p6.setCategorias(Arrays.asList(cat3));
		p7.setCategorias(Arrays.asList(cat4));
		p8.setCategorias(Arrays.asList(cat5));
		p9.setCategorias(Arrays.asList(cat6));
		p10.setCategorias(Arrays.asList(cat6));
		p11.setCategorias(Arrays.asList(cat7));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
		
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2, c3));
		
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		Set<String> hash_Set = new HashSet<String>(); 
		hash_Set.addAll(Arrays.asList("27363323", "983838383"));
		cli1.setTelefones(hash_Set);
		
		Endereco e1 = new Endereco(null, "Rua da Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 80", "Centro", "38777012", cli1, c2);
		
		cli1.setEnderecos(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"),cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.setPedidos(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 0.00, 1, 800.00);
		
		Set<ItemPedido> hash_Set_0 = new HashSet<ItemPedido>(); 
		hash_Set_0.addAll(Arrays.asList(ip1, ip2));
		
		Set<ItemPedido> hash_Set_1= new HashSet<ItemPedido>(); 
		hash_Set_1.addAll(Arrays.asList(ip1));
		
		Set<ItemPedido> hash_Set_2= new HashSet<ItemPedido>(); 
		hash_Set_2.addAll(Arrays.asList(ip2));
		
		Set<ItemPedido> hash_Set_3= new HashSet<ItemPedido>(); 
		hash_Set_3.addAll(Arrays.asList(ip3));
		
		ped1.setItens(hash_Set_0);
		ped2.setItens(hash_Set_3);
		
		p1.setItens(hash_Set_1);
		p2.setItens(hash_Set_3);
		p3.setItens(hash_Set_2);
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		// pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		// produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
	}

}
