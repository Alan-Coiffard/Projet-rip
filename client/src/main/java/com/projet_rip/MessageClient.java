package com.projet_rip;

public class MessageClient {
    
	private int compteur = 0;
	private String message = "";
    
	public synchronized String getMessage() {
        while (compteur <= 0)
            try {
                wait();
            }
            catch (InterruptedException ex) {}
        this.compteur--;
        String msg = this.message;
        notify();
        return msg;
    }
    
	public synchronized void setMessage(String msg) {    	
    	while (compteur != 0)
            try {
                wait();
            }
            catch (InterruptedException ex) {}
        this.message = msg;
        this.compteur++;
        notify();
    }
}