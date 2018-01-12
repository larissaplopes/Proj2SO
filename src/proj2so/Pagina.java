/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2so;

/**
 *
 * @author Larissa
 */
public class Pagina {
    private int nPagina;
    private int tempo;
    
    public Pagina(int n) {
        nPagina = n;
        tempo = 0;
    }

    public int getnPagina() {
        return nPagina;
    }

    public void setnPagina(int nPagina) {
        this.nPagina = nPagina;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
}
