/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2so;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Larissa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LinkedList<Pagina> listaFifo = new LinkedList<Pagina>(),
                           listaOTM  = new LinkedList<Pagina>(),
                           listaLRU  = new LinkedList<Pagina>();
        int nQuadro = 0;
        try {
         // TODO code application logic here
         BufferedReader leitura = new BufferedReader(new FileReader("C:/Programa/Fernando/pagina.txt"));
         
         String linha = leitura.readLine();
         
         nQuadro = Integer.parseInt(linha);
         
         while(leitura.ready()){
            linha = leitura.readLine();
            
            listaFifo.add(new Pagina(Integer.parseInt(linha)));
            listaOTM.add(new Pagina(Integer.parseInt(linha)));
            listaLRU.add(new Pagina(Integer.parseInt(linha)));
         }
         //listaFifo = (LinkedList < Processo >)lista.clone();    
            Fifo(listaFifo, nQuadro);
            Otm(listaOTM, nQuadro);
            Lru(listaLRU, nQuadro);

      } catch (FileNotFoundException ex) {
         JOptionPane.showMessageDialog(null, "Erro na abertura do arquivo");
         System.exit(1);
      } catch (IOException ex) {
         JOptionPane.showMessageDialog(null, "Problema na leitura do arquivo");
         System.exit(1);
      }
    }
    
    public static void Fifo(LinkedList<Pagina> listaFifo, int n){
        int faltaPagina = 0,
            quadroOcupado = 0,
            pagEncontrada = 0;
        LinkedList<Pagina> quadros = new LinkedList<Pagina>();
        Pagina pagAtual;
        
        while(!listaFifo.isEmpty()){
            pagAtual = listaFifo.removeFirst();
            if(quadroOcupado < n){
                quadros.add(pagAtual);
                faltaPagina++;
                quadroOcupado++;
                continue;
            }
            if(quadroOcupado == n){
                for(Pagina p : quadros){
                    if(p.getnPagina() == pagAtual.getnPagina()){
                        pagEncontrada++;
                        break;
                    }
                }
                if(pagEncontrada == 0){
                    faltaPagina++;
                    quadros.removeFirst();
                    quadros.add(pagAtual);
                }else{
                    pagEncontrada--;
                }
            }
        }
        
        System.out.println("FIFO " + faltaPagina);    
    }
    
    
    public static void Otm(LinkedList<Pagina> listaFifo, int n){
        int faltaPagina = 0,
            quadroOcupado = 0,
            pagEncontrada = 0,
            pagNaoUtilizada = 0,
            tempo = 0,
            maior;
        LinkedList<Pagina> quadros = new LinkedList<Pagina>();
        Pagina pagAtual;
        
        while(!listaFifo.isEmpty()){
            pagAtual = listaFifo.removeFirst();
            tempo = pagNaoUtilizada = 0;
            if(quadroOcupado < n){
                quadros.add(pagAtual);
                faltaPagina++;
                quadroOcupado++;
                continue;
            }
            if(quadroOcupado == n){
                for(Pagina p : quadros){
                    p.setTempo(0);
                }
                
                for(Pagina p : quadros){
                    if(p.getnPagina() == pagAtual.getnPagina()){
                        pagEncontrada++;
                        break;
                    }
                }
                if(pagEncontrada == 0){
                    faltaPagina++;
                    
                    for(Pagina quadroAtual : quadros){
                        if(!listaFifo.isEmpty()){
                            for(Pagina p : listaFifo){
                                tempo++;
                                if( (quadroAtual.getnPagina() == p.getnPagina() ) && (quadroAtual.getTempo() == 0) ){
                                    quadroAtual.setTempo(tempo);
                                }
                            }
                        }
                    }
                    
                    maior = -1;
                    for(Pagina p : quadros){
                        if(p.getTempo() > maior){
                            maior = p.getTempo();
                        }
                    }
                    for(Pagina p : quadros){
                        if(p.getTempo() == 0){
                            pagNaoUtilizada++;
                            quadros.remove(p);
                            quadros.add(pagAtual);
                            break;
                        }
                    }
                    if(pagNaoUtilizada == 0){
                        for(Pagina q : quadros){
                            if(q.getTempo() == maior){
                                quadros.remove(q);
                                quadros.add(pagAtual);
                                break;
                            }
                        }
                    }

                }else{
                    pagEncontrada--;
                }
            }
        }
        
        System.out.println("OTM " + faltaPagina);    
    }
    
        public static void Lru(LinkedList<Pagina> listaFifo, int n){
        int faltaPagina = 0,
            quadroOcupado = 0,
            pagEncontrada = 0,
            menor,
            tempo;
        LinkedList<Pagina> quadros = new LinkedList<Pagina>();
        Pagina pagAtual;
        
        for(tempo = 0; !listaFifo.isEmpty(); tempo++){
            pagAtual = listaFifo.removeFirst();
            
            if(quadroOcupado < n){
                pagAtual.setTempo(tempo);
                quadros.add(pagAtual);
                faltaPagina++;
                quadroOcupado++;
                continue;
            }
            if(quadroOcupado == n){
                for(Pagina p : quadros){
                    if(p.getnPagina() == pagAtual.getnPagina()){
                        pagEncontrada++;
                        p.setTempo(tempo);
                        break;
                    }
                }
                if(pagEncontrada == 0){
                    faltaPagina++;
                    menor = quadros.getFirst().getTempo();
                    
                    for(Pagina p : quadros){
                        if(menor > p.getTempo()){
                            menor = p.getTempo();
                        }
                    }
                    for(Pagina p : quadros){
                        if(p.getTempo() == menor){
                            quadros.remove(p);
                            pagAtual.setTempo(tempo);
                            quadros.add(pagAtual);
                            break;
                        }
                    }
                //pagNaoEncontrada    
                }else{
                    pagEncontrada--;
                }
            }
        }//for

        System.out.println("LRU " + faltaPagina);    
    }
}
