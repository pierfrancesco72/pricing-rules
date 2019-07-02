package io.pf.pricing.model;


public abstract class IdComponente {
	public enum TipoComponente {
		NUMERICA("N"), STRINGA("A");
		
		private String valoreDb;  
		private TipoComponente(String val) {
			valoreDb = val;
		}
		
		public String getValoreDb() {
	        return valoreDb;
	    }
		
		@Override
	    public String toString() {
	        return this.getValoreDb();
	    }
		
		public static TipoComponente daDB(String valoreDb) {
	        for (TipoComponente tc : TipoComponente.values()) {
	            if (tc.valoreDb.equalsIgnoreCase(valoreDb)) {
	                return tc;
	            }
	        }
	        return null;
	    }
	}
	private Integer id;
	private TipoComponente tipo;
	private Integer lunghezza;
	private Integer decimali;
	
	public IdComponente() {}
	
	public IdComponente(Integer idCompo, Integer interi, Integer decimali) {
		id = idCompo;
		this.lunghezza = interi;
		this.decimali = decimali;
	}
	

	public Integer getDecimali() {
		return decimali;
	}

	public void setDecimali(Integer decimali) {
		this.decimali = decimali;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public TipoComponente getTipo() {
		return tipo;
	}



	public void setTipo(TipoComponente tipo) {
		this.tipo = tipo;
	}



	public Integer getLunghezza() {
		return lunghezza;
	}



	public void setLunghezza(Integer lunghezza) {
		this.lunghezza = lunghezza;
	}
	
}
