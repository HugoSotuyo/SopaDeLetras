/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sopadeletras;

import java.util.Random;
import java.util.Scanner;

/**
 *PUNTO A TENER EN CUENTA: EL PROGRAMA NO VERIFICA QUE SE ENCUENTREN MAS DE
     * UNA VEZ LA MISMA PALABRA, LO SIGUE TOMANDO COMO VALIDO, ESO PODRIAS
     * HACERLO VOS COMO UNA MINI TAREA ;)
     *
 * @author hdsot
 */
public class SopaDeLetras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Creamos la matriz
      
        //matrizSopaLetras
        String[][] matrizSopaLetras = new String[10][10];

        //llenamos la matriz
        ingresarPalabras(matrizSopaLetras);

        rellenarSopaLetras(matrizSopaLetras);

        iniciarSopaLetras(matrizSopaLetras);

    }
   public static void ingresarPalabras(String[][] matrizSopaLetras) {
        Random random = new Random();

        Scanner leer;
        leer = new Scanner(System.in);
        String palabra;

        //col y fil
        int columna;
        int fila;

        System.out.println("INGRESAREMOS LAS PALABRAS A LA SOPA DE LETRAS, SOLO 3!");
        for (int i = 0; i < 3; i++) {
            System.out.println("Ingrese una palabra no mayor a 10 digitos:");
            palabra = leer.nextLine();
            while (palabra.length() > 10) {
                System.out.println("ERROR INGRESE UNA PALABRA MAS CORTA!!!!!!!!!");
                System.out.println("Ingrese una palabra no mayor a 10 digitos:");
                palabra = leer.nextLine();

            }

            //le da un lugar random a las filas y las columnas
            fila = random.nextInt(10);//lugar entre 0 y 10
            columna = random.nextInt(10 - palabra.length());//lugar entre 0 y (10 - largo de palabra)

            //posicionOcupada = True (invertir)
            boolean posicionOcupada = true;

            for (int j = 0; j < palabra.length(); j++) {

                //mientras la matriz en la posicion actual no este vacia y la bandera este en falso, va a entrar aqui
                //esto se hace para no sobrescribir la primera letra, abria que verificar que no sobrescriba mas de una letra
                //yo no lo hice para acortar el tiempo del ejercicio pero podrias intentarlo vos :)
                while (matrizSopaLetras[fila][columna] != null && posicionOcupada) {
                    fila = random.nextInt(10);
                    columna = random.nextInt(10 - palabra.length());
                }

                //al salir o no entrar al bucle, se pone la bandera en True para que no entre mientras recorre la matriz para
                //guardar la palabra bien 
                posicionOcupada = false;

                // j = posicion actual en la palabra, c =  donde se coloco la primera letra de la palabra
                //se suma para obtener la posicion actual de la columna
                //matrizSopaLetras
                matrizSopaLetras[fila][j + columna] = palabra.substring(j, j + 1);

            }

            posicionOcupada = false;

        }

    }

    //rellenarMatriz
    public static void rellenarSopaLetras(String[][] matrizSopaLetras) {

        Random random = new Random();

        char caracterAux;

        //RECORRE FILAS
        for (int i = 0; i < 10; i++) {
            //RECORRE COLUMNAS
            for (int j = 0; j < 10; j++) {
                //VERIFICA QUE LA MATRIZ ESTE VACIA PARA NO SOBRESCRIBIR LAS PALABRAS INGRESADAS.
                if (matrizSopaLetras[i][j] == null) {
                    //Hace un numero random, lo suma a las 'a' y al ser convertido al char, 
                    //te podria asignar todos los caracteres del abecedario en MINUSCULA
                    caracterAux = (char) ('a' + random.nextInt(26));
                    //.valueOf() convierte el char en un string
                    matrizSopaLetras[i][j] = String.valueOf(caracterAux);
                }

            }

        }
    }

    //iniciarSopaLetras
    public static void iniciarSopaLetras(String[][] matrizSopaLetras) {
        int palabrasEncontradas = 0;
        //banderaPalabraEncontrada
        boolean banderaPalabaEncontrada;

        System.out.println("------VAMOS A BUSCAR LAS PALABRAS------");
        //esta hasta el 4 porque decidi dar 4 intentos nomas, vos podes personalizarlo.
        for (int i = 0; i < 4; i++) {

            //si ya encontraste las 3 palabras antes de los 4 intentos, no va a realizar las siguientes acciones.
            if (palabrasEncontradas == 3) {
                break;
            }

            mostrarMatriz(matrizSopaLetras);
            banderaPalabaEncontrada = buscarPalabra(matrizSopaLetras);

            // verificamos el estado de la bandera
            if (banderaPalabaEncontrada) {

                palabrasEncontradas += 1;
                System.out.println("ENCONTRASTE UNA PALABRA!! ");

            } else {
                System.out.println("Oh, la palabra no estaba ahi :(");
            }

        }

        //si al salir del for las palabrasEncontras (contador) es distinto de 3, quiere decir que gasto
        //los 4 intentos pero sin encontrar las palabras, por lo que perdio
        if (palabrasEncontradas != 3) {
            System.out.println("Oh perdiste :( Suerte en la proxima!");

        } else { //caso contrario, gasto los 4 intentos pero si encontro las palabras.
            System.out.println("--- GANASTE, ENCONTRASTE TODAS LAS PALABRAS ---");
        }

    }

    public static boolean buscarPalabra(String[][] matrizSopaLetras) {

        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese la palabra a buscar:");
        //palabra
        String palabra = leer.nextLine();

        System.out.println("Ingrese la fila donde cree que se encuentra: (0,9)");
        int fila = leer.nextInt();
        System.out.println("Ingrese la columna donde cree que se encuentra: (0,9)");
        int columna = leer.nextInt();

        int limite = columna + palabra.length();
        int contadorPosicion = 0;

        for (int i = columna; i < limite; i++) {

            //verifica con el equals que el subestring de palabra, sea igual a lo que hay en la matriz en la posicion (fila, i)
            // al agregarle el ! al principio estaria verificando que sean distintos, ya que el ! hace referencia al diferente
            if (!matrizSopaLetras[fila][i].toLowerCase().equals(palabra.toLowerCase().substring(contadorPosicion, contadorPosicion + 1))) {

                //el .toLowerCase retorna la cadena en minusculas, al agregarlo tanto en la matriz como en la palabra buscada
                // no abra problema si las ingresamos diferenciadas (minusculas o mayusculas)
                //entra aqui cuando no coinciden por lo tanto no es la palabra buscada
                return false;
            }
            //el contador es para poder ir avanzando en las posiciones de la palabra

            contadorPosicion += 1;
        }
        return true;

        //devuelve true o false, al encontrar o no (respectivamente) la palabra.
    }

    public static void mostrarMatriz(String[][] Matriz) {

        for (int i = 0; i < 10; i++) {
            System.out.print("[");
            for (int j = 0; j < 9; j++) {
                System.out.print(Matriz[i][j] + '|');
            }
            System.out.println(Matriz[i][9] + ']');

        }

    }

}
 
