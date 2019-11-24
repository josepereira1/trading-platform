package tradingsystem.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import tradingsystem.business.trading.Ativo;
import tradingsystem.business.trading.IAtivo;
import tradingsystem.business.trading.ITradingAbstractFactory;
import tradingsystem.business.trading.TradingAbstractFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


public class AtivoDAO {

	private static final String APIToken = "demo";
	private ITradingAbstractFactory tradingAbastractFactory;

	public AtivoDAO() {
		this.tradingAbastractFactory = new TradingAbstractFactory();
	}


	/**
	 * Devolve um JSONObject com a informação obtida através do método GET.
	 * @param url url em formato String.
	 * @return ...
	 */
	private static JSONObject RESTGet(String url) throws IOException {
		return new JSONObject(new JSONTokener(new URL(url).openStream()));
	}

	/**
	 * ...
	 * @return ...
	 */
	public Collection<IAtivo> values() throws IOException {
		Collection<IAtivo> res = new ArrayList<>();
		String url = "https://api.worldtradingdata.com/api/v1/stock?symbol=SNAP,TWTR,VOD.L&api_token=" + APIToken;
		JSONArray arr = RESTGet(url).getJSONArray("data");
		for(int i=0; i<arr.length(); i++) {
			JSONObject o = (JSONObject) arr.get(i); // gets() each IAtivo
			String id = o.getString("symbol");
			String designacao = o.getString("name");
			float valorVenda = o.getFloat("price");
			float min=0.97f, max=0.99f;
			float underPercentage = min + new Random().nextFloat() * (max - min);
			float valorCompra = valorVenda*underPercentage;
			IAtivo ativo = new Ativo(); //TODO substituir pela chamada ao ITradingAbastractFactory
			ativo.setId(id);
			ativo.setDesignacao(designacao);
			ativo.setValorVenda(valorVenda);
			ativo.setValorCompra(valorCompra);
			res.add(ativo);
		}
		return res;
	}


	/**
	 * ...
	 * @param id
	 * @return ...
	 */
	public float getValorAtual(String id) throws IOException {
		//TODO quando utilizar o token "real" meter apenas o symbol=id (remover as restantes empresas)
		String url = "https://api.worldtradingdata.com/api/v1/stock?symbol=" + id + ",TWTR,VOD.L&api_token=" + APIToken;
		return ((JSONObject) RESTGet(url).getJSONArray("data").get(0)).getFloat("price");
	}

}