package com.example.ericschumacher.bouncer.Objects.Rpd;

import java.sql.Date;

public class Warenbewegung {
	int anzahl;
	java.util.Date date;
	String kommentar;
	int lagerbestand;
	public Warenbewegung(int anzahl, java.util.Date date, String kommentar, int lagerbestand){
		
		this.anzahl = anzahl;
		this.date = date;
		this.kommentar = kommentar;
		this.lagerbestand = lagerbestand;
		
	}
	
	
	public int getfAnzahl() {
		return anzahl;
	}
	public void setfAnzahl(int fAnzahl) {
		this.anzahl = fAnzahl;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getKommentar() {
		return kommentar;
	}
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	public int getLagerbestand() {
		return lagerbestand;
	}
	public void setLagerbestand(int lagerbestand) {
		this.lagerbestand = lagerbestand;
	}
}
