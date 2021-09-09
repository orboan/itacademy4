package com.vehicles.domain;

public class Wheel {
	private String brand;
	private double diameter;

	public Wheel(String brand, double diameter) {
		this.brand = brand;
		this.diameter = diameter;
	}

	public String getBrand() {
		return brand;
	}

	public double getDiameter() {
		return diameter;
	}
	
	//Cal fer override d'equals i hashcode
	@Override
	public boolean equals(Object o) {
	    if(o == null)
	    {
	        return false;
	    }
	    if (o == this)
	    {
	        return true;
	    }
	    if (getClass() != o.getClass())
	    {
	        return false;
	    }
	     
	    Wheel w = (Wheel) o;
	    return (this.brand.equals(w.brand) && this.diameter == w.diameter);
	}
	
	@Override
	public int hashCode()
	{
	    final int PRIME = 31;
	    int result = 1;
	    result = PRIME * result + 
	    		(int)(this.diameter * 1000) + 
	    		this.brand.hashCode();
	    return result;
	}
}
