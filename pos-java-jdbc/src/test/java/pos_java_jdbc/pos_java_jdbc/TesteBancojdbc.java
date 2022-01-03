package pos_java_jdbc.pos_java_jdbc;

 

import java.util.List;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancojdbc {

		//adicionar usuario
		@Test
		public void initBanco() {
			UserPosDAO userPosDAO = new UserPosDAO();
			Userposjava userposjava = new Userposjava();
			
		 
			userposjava.setNome("Wendell");
			userposjava.setEmail("Wendell@gmail.com");
			
			userPosDAO.salvar(userposjava);
			
		}
		//listar usuarios
		@Test
		public void initListar() {
			UserPosDAO dao = new UserPosDAO();
			try {
				List<Userposjava> list = dao.listar();
				
				for(Userposjava userposjava : list) {
					System.out.println(userposjava);
					System.out.println("------------------------------------");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		//consultar um usuario
		@Test
		public void initBuscar() {
			UserPosDAO dao = new UserPosDAO();
			try {
				 Userposjava  userposjava = dao.buscar(6L); 
				 System.out.println(userposjava);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		//listar nome usuario
		@Test
		public void initListar2() {
			UserPosDAO dao = new UserPosDAO();
			try {
				List<Userposjava> list = dao.listar();
				
				for(Userposjava userposjava : list) {
					System.out.println(userposjava.getNome());
					System.out.println("------------------------------------");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
		
		//atualizar usuario
		@Test
		public void initAtualizar() {
			try {
				UserPosDAO dao = new UserPosDAO();
			
			Userposjava objetoBanco  = dao.buscar(10L);
			
			objetoBanco.setNome("Nome mudado com método atualizar");
			
			dao.atualizar(objetoBanco);
			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		//deletar usuario
		@Test
		public void initDeletar() {
			
			try {
				
				UserPosDAO dao = new UserPosDAO();
				dao.deletar(9L);
				 
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		//adicionar usuario de acordo com usuario
		@Test
		public void testeInsertTelefone() {
			Telefone telefone = new Telefone();
			telefone.setNumero("(91) 9 1252-2314 ");
			telefone.setTipo("casa");
			telefone.setUsuario(2L);
			UserPosDAO dao = new UserPosDAO();
			dao.salvarTelefone(telefone);
		}
		
		
		//listar um usuario com seus telefones
		@Test
		public void testeCarregarFoneUser () throws Exception {
			 
			UserPosDAO dao = new UserPosDAO();
			
			List<BeanUserFone> beanUserFones = dao.listaUserFone(1L);
			
			for(BeanUserFone beanUserFone : beanUserFones) {
				
				System.out.println(beanUserFone);
				System.out.println("---------------------------------------");
			}
			 
		}
		
		
		//deletar filho e depois pai
		@Test
		public void testeDeleteUserFone() {
			UserPosDAO dao = new UserPosDAO();
			dao.deletarFonesPorUser(2L); 
		}
		
	 
		
		
		
}
