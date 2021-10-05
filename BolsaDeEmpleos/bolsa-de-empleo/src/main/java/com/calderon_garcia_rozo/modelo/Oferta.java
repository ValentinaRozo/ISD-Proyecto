package com.calderon_garcia_rozo.modelo;

public class Oferta {
    private String codOferta;
    private String codEmpleador;
    private int sector;
    private int servidor;

    public String getCodOferta() {
		return codOferta;
	}

	public void setCodOferta(String codOferta) {
		this.codOferta = codOferta;
	}

    public String getCodEmpleador() {
		return codEmpleador;
	}

	public void setCodEmpleador(String codEmpleador) {
		this.codEmpleador = codEmpleador;
	}

    public int getSector() {
		return sector;
	}

	public void setSector (int sector) {
		this.sector = sector;
	}

    public int getServidor() {
		return servidor;
	}

	public void setServidor (int servidor) {
		this.servidor = servidor;
	}

    
}
