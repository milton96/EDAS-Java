package edas;

import java.util.ArrayList;

/**
 *
 * @author Milton
 */
public class EDAS {
    
    public static ArrayList<ArrayList<Integer>> poblacion = new ArrayList<>();  //guarda las cadenas de individuos de 1 y 0
    public static ArrayList<Integer> individuos = new ArrayList<>();            //cadena de individuos de 1 y 10
    public static ArrayList<Integer> hx = new ArrayList<>();                    //guarda el valor de la cadena    
    public static ArrayList<Double> probabilidades = new ArrayList<>();         //probabilidades de obtener 1 o 0
    
    /*  genera la poblacion inicial en base a los individuos recibidos y al total
        de poblacion que se desea, se usa una probabilidad de 0.5 por ser la
        poblacion incial, tambien cuenta el total de 1 de cada cadena para agregarlo
        al arraylist hx */
    public void generarPoblacionInicial(int pobla, int indivi){
        for(int i=0; i<pobla; i++){
            individuos = new ArrayList<>();
            int cont = 0;
            for(int j=0; j<indivi; j++){                
                double r = Math.random();
                if(r <= 0.5){
                    individuos.add(0);
                }else{
                    individuos.add(1);
                    cont++;
                }
            }
            poblacion.add(individuos);
            hx.add(cont);
        }
    }
    
    /*  ordena el arraylist hx de mayor a menor y en base a ese ordenamiento mueve
        tambien el arraylist de poblacion   */    
    public void ordenar(ArrayList<ArrayList<Integer>> auxPoblacion, ArrayList<Integer> auxHx){
        int keyHx;
        ArrayList<Integer> keyIndividuos;
        int i;
        for(int j=1; j<auxHx.size(); j++){
            keyHx = auxHx.get(j);
            keyIndividuos = auxPoblacion.get(j);
            i = j-1;
            while(i>=0 && auxHx.get(i)<keyHx){
                auxHx.set(i+1, auxHx.get(i));
                auxPoblacion.set(i+1, auxPoblacion.get(i));
                i--;
            }
            auxHx.set(i+1, keyHx);
            auxPoblacion.set(i+1, keyIndividuos);
        }
        poblacion = new ArrayList(auxPoblacion);
        hx = new ArrayList(auxHx);
    }
    
    /*  obtiene las nuevas probabilidades de obtener 1 o 0 y las guarda en un 
        arraylist que se vacia con cada cambio de generacion    */
    public void obtenerProbabilidades(int tSelec, int indivi){
        probabilidades = new ArrayList<>();
        for(int i=0; i<indivi; i++){
            int cont = 0;
            for(int j=0; j<tSelec; j++){
                if(poblacion.get(j).get(i) == 1)
                    cont++;
            }
            probabilidades.add((double) cont/tSelec);
        }
    }
    
    /*  aqui genera la nueva poblacion en base a las nuevas probabilidades y
        para eso se deben vaciar los arraylist hx y poblacion   */
    public void generarNuevaPoblacion(int pobla, int indivi){
        hx = new ArrayList<>();
        poblacion = new ArrayList<>();
        for(int i=0; i<pobla; i++){
            individuos = new ArrayList<>();
            int cont = 0;
            for(int j=0; j<indivi; j++){
                double r = Math.random();
                if(r <= probabilidades.get(j)){
                    individuos.add(0);
                }else{
                    individuos.add(1);
                    cont++;
                }
            }
            poblacion.add(individuos);
            hx.add(cont);
        }        
    }
    
    public static void main(String[] args){
        int seleccion = 10;
        int tPoblacion = 20;
        int tIndividuos = 6;
        int generaciones = 10;
        int x = 0;
        
        System.out.println("Poblacion Inicial"); 
        EDAS edas = new EDAS();
        edas.generarPoblacionInicial(tPoblacion, tIndividuos);
        edas.ordenar(poblacion, hx);
        edas.obtenerProbabilidades(seleccion, tIndividuos);
        for(int i=0; i<poblacion.size(); i++)
            System.out.println(poblacion.get(i) + " - " + hx.get(i));
        for(int i=0; i<probabilidades.size(); i++)
            System.out.print(probabilidades.get(i) + " ");
        System.out.println("\n---------------------------------"); 
        
        while(x < generaciones){
            System.out.println("Generacion: " + (x+1));  
            edas.generarNuevaPoblacion(tPoblacion, tIndividuos);
            edas.ordenar(poblacion, hx);
            edas.obtenerProbabilidades(seleccion, tIndividuos);
            for(int i=0; i<poblacion.size(); i++)
                System.out.println(poblacion.get(i) + " - " + hx.get(i));
            for(int i=0; i<probabilidades.size(); i++)
                System.out.print(probabilidades.get(i) + " ");
            System.out.println("\n---------------------------------"); 
            x++;
        }
    }
}
