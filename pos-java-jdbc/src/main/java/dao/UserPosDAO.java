package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {
	private Connection connection;
	
	public UserPosDAO(){
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(Userposjava userposjava) {
		try {
			String sql = "insert into userposjava (nome, email) values (?,?)";
			PreparedStatement insertStatement = connection.prepareStatement(sql);
			 
			insertStatement.setString(1, userposjava.getNome());
			insertStatement.setString(2, userposjava.getEmail());
			insertStatement.execute();
			connection.commit();//salvar no bando
		}catch(Exception e) {
			try {
				connection.rollback();//reverte a operação
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	
	//consultar e listar objetos
	public List<Userposjava> listar() throws Exception {
		List<Userposjava> list = new ArrayList<Userposjava>();
		
		String sql = "select * from userposjava";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			
			list.add(userposjava);
		}
		
		
		
		return list;
	}

	//consultar um objeto
		public Userposjava buscar(Long id) throws Exception{
			Userposjava retorno = new  Userposjava();
			
			String sql = "select * from userposjava where id = " + id;
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				 
				retorno.setId(resultado.getLong("id"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setEmail(resultado.getString("email"));
				
			 
			} 
			return retorno;
		}
		
		//consultar e listar objetos 2
		public List<Userposjava> listar1() throws Exception{
			List<Userposjava> list = new ArrayList<Userposjava>();
			
			String sql = "select * from userposjava";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				Userposjava userposjava = new Userposjava();
				userposjava.setId(resultado.getLong("id"));
				userposjava.setNome(resultado.getString("nome"));
				userposjava.setEmail(resultado.getString("email"));
				
				list.add(userposjava);
			}
			
			return list;
		}
		
		//atualizar
		public void atualizar(Userposjava userposjava) throws SQLException  {
			try {
			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());

			statement.execute();
			connection.commit();
			}catch(Exception e){
				connection.rollback();
				e.printStackTrace();
			}
		}
		//deletar usuario
		public void deletar(Long id)  {
			try {
				String sql = "delete from userposjava where id = "+ id;
				PreparedStatement preparedstatement = connection.prepareStatement(sql);	
				preparedstatement.execute();
				connection.commit();
			}catch( SQLException e){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		//salvar telefone
		public void salvarTelefone(Telefone telefone) {
			
			try {
				String sql = "INSERT INTO  telefoneuser(numero, tipo, usuariopessoa) VALUES ( ?, ?, ?);";
				PreparedStatement statement = connection.prepareStatement(sql);	
				statement.setString(1, telefone.getNumero());
				statement.setString(2, telefone.getTipo());
				statement.setLong(3,telefone.getUsuario());
				statement.execute(); 
				connection.commit();
				//nao tem commit pq o cliente ta na conexao
			}catch(Exception e) {
				try {
					connection.rollback();
				} catch (SQLException e1) { 
					e1.printStackTrace();
				}
			}
		}
		//deletar telefone
		public void deletarTelefone(Long id)  {
			try {
				String sql = "DELETE FROM  public.telefoneuser WHERE = " + id;
				PreparedStatement preparedstatement = connection.prepareStatement(sql);	
				preparedstatement.execute();
				connection.commit();
			}catch( SQLException e){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
		//listar determinado usuario e seu telefones
		public List<BeanUserFone> listaUserFone(Long idUser) throws Exception{
			
			List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
			
			String sql = " select nome, numero, email from telefoneuser  as fone ";
			sql +=" inner join userposjava as userp ";
			sql +=" on fone.usuariopessoa = userp.id ";
			sql +=" where userp.id = "+ idUser;
			
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				
				while(resultSet.next()) {
					BeanUserFone userFone = new BeanUserFone();
					
					userFone.setEmail(resultSet.getString("email"));
					userFone.setNome(resultSet.getString("nome"));
					userFone.setNumero(resultSet.getString("numero"));
					
					beanUserFones.add(userFone);
				}
				
			}catch (Exception e){ 
				e.printStackTrace();
			}
			
			return beanUserFones;
			 
		}
		
		
		//deletar em cascata
		//filhos depois pai
		public void deletarFonesPorUser(Long idUser) {
			try {
			String sqlFone = " delete from telefoneuser where usuariopessoa ="+ idUser;
			String sqlUser = " delete from userposjava where id = "+ idUser;
			
			 
				PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
				preparedStatement.executeUpdate();
				connection.commit();
				
				preparedStatement = connection.prepareStatement(sqlUser);
				preparedStatement.executeUpdate();
				connection.commit();
				
			} catch (SQLException e) { 
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}

		 
	 
}
 


 
