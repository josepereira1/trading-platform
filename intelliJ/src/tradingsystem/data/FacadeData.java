package tradingsystem.data;

import tradingsystem.business.IAtorTypeNotValidException;
import tradingsystem.business.recursoshumanos.IAtor;
import tradingsystem.business.trading.IAtivo;
import tradingsystem.business.trading.ICFD;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Future;

public class FacadeData implements IFacadeData {

	private AtorDAO utilizadores;
	private AtivoDAO ativos;
	private CFDDAO cfds;
	private static IFacadeData data;

	private FacadeData() throws SQLException, ClassNotFoundException {
		this.utilizadores = new AtorDAO();
		this.ativos = new AtivoDAO();
		this.cfds = new CFDDAO();
	}

	//TODO copy documentation from inner classes to this class

	/**
	 * 
	 * @param username
	 */
	public IAtor getUtilizador(String username, String userType) throws SQLException, IAtorTypeNotValidException {
		return this.utilizadores.get(username, userType);
	}

	public Collection<IAtivo> getAtivos() throws IOException, IAtorTypeNotValidException {
		return this.ativos.values();
	}

	/**
	 * 
	 * @param cfd
	 */
	public void putCFD(ICFD cfd) {
		this.cfds.put(cfd);
	}

	/**
	 * 
	 * @param id
	 */
	public void removeCFD(String id) {
		this.cfds.remove(id);
	}

	/**
	 * 
	 * @param utilizador
	 */
	public void putUtilizador(IAtor utilizador) throws SQLException, IAtorTypeNotValidException {
		this.utilizadores.put(utilizador);
	}

	/**
	 * 
	 * @param id
	 */
	public float getValorAtualAtivo(String id) throws IOException {
		return this.ativos.getValorAtual(id);
	}

	/**
	 *  @param id
	 * @param TP
	 * @param SL
	 * @return
	 */
	public Future<Void> setCFDlimits(String id, float TP, float SL) {
		return this.cfds.setLimits(TP, SL, id);
	}

	/**
	 * 
	 * @param username
	 * @param valor
	 */
	public void addFundos(String username, float valor) throws SQLException {
		this.utilizadores.addFundos(username, valor);
	}

	/**
	 * 
	 * @param id
	 */
	public Future<ICFD> getCFD(String id) {
		return this.cfds.get(id);
	}

	/**
	 * 
	 * @param username
	 */
	public Future<Collection<ICFD>> getCFDs(String username) {
		return this.cfds.getCFDs(username);
	}

	/**
	 * 
	 * @param idCFD
	 */
	public Future<Float> getTakeProfit(String idCFD) {
		return this.cfds.getTakeProfit(idCFD);
	}

	/**
	 * 
	 * @param idCFD
	 */
	public Future<Float> getStopLess(String idCFD) {
		return this.cfds.getStopLess(idCFD);
	}

	public static IFacadeData getInstance() throws SQLException, ClassNotFoundException {
		if (data == null) data = new FacadeData();
		return data;
	}

	/**
	 * 
	 * @param idCFD
	 */
	public Future<Float> getValorInvestidoCFD(String idCFD) {
		return this.cfds.getValorInvestidoCFD(idCFD);
	}

	public Future<Integer> getNumeroDeAtivosCFD(String idCFD) {
		return this.cfds.getNumeroDeAtivosCFD(idCFD);
	}

	/**
	 * 
	 * @param username
	 */
	public boolean containsUtilizador(String username, String userType) throws SQLException, IAtorTypeNotValidException {
		return this.utilizadores.contains(username, userType);
	}

	/**
	 * 
	 * @param idCFD
	 */
	public Future<Boolean> containsCFD(String idCFD) {
		return this.cfds.contains(idCFD);
	}

}