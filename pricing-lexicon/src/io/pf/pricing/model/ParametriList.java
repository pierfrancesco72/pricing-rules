package io.pf.pricing.model;

import java.util.ArrayList;


public class ParametriList extends ArrayList<String> {

	private static final long serialVersionUID = 1L;
	
	public ParametriList() {
		super();
	}
	
	public ParametriList(String strParametriListino) {
		super();
		String[] pars = strParametriListino.split("\\,");
		
		for (String par : pars) {
			this.add(par);
		}
	}
	
	@Override
	public synchronized boolean equals(Object o) {
		ParametriList pars = (ParametriList)o;
		System.out.println("EQUALS:"+pars+"->"+this);
		for (String par : pars) {
			if (!this.contains(par))
				return false;
		}
		
		return pars.size() == this.size();
	}

	/*@Override
	public synchronized boolean equals(Object o) {
		String[] pars = ((String)o).split("\\,");
		
		for (String par : pars) {
			if (!this.contains(par))
				return false;
		}
		
		if (pars.length != this.size())
			return false;
		
		return true;
	}*/
}
