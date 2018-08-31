package com.example.ericschumacher.bouncer.Objects.Rpd;

public class Kindartikel {
String key;	
double preis;
double prio;
int lager;
boolean zugelassen=false;

public Kindartikel(String key, double preis, int lager){
	this.key = key;
	this.preis = preis*1.19;
	this.lager = lager;
}


public double getPreis() {
	return preis;
}
public void setPreis(double preis) {
	this.preis = preis;
}
public double getPrio() {
	return prio;
}
public void setPrio(double prio) {
	this.prio = prio;
}
public int getLager() {
	return lager;
}
public void setLager(int lager) {
	this.lager = lager;
}
public boolean isZugelassen() {
	return zugelassen;
}
public void setZugelassen(boolean zugelassen) {
	this.zugelassen = zugelassen;
}


public String getKey() {
	return key;
}


public void setKey(String key) {
	this.key = key;
}


}