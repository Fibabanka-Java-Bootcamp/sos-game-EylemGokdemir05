package org.kodluyoruz;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {

            Scanner scanner=new Scanner(System.in);
            System.out.print("Satır:");
            int row=scanner.nextInt();
            System.out.print("Sütun:");
            int column=scanner.nextInt();

            String matrix[][]=new String[row][column];

            if (matrix.length >= 3 && matrix[0].length >= 3 && matrix.length <= 7 && matrix[0].length <= 7 && matrix.length == matrix[0].length){

                String[] gamers={"gamer", "computer"};
                String[] characters={"s", "o"};
                int gamerIdx=new Random().nextInt(gamers.length);
                String gamerRnd=(gamers[gamerIdx]);
                System.out.println("Oyuna başlayacak oyuncu:"+gamerRnd);

                int scoreGamer = 0, scoreComputer = 0;

                int getRow = 0, getColumn = 0;

                do {
                    int characterIdx = new Random().nextInt(characters.length);
                    String characterRnd = (characters[characterIdx]);

                    do {
                        System.out.println("\nYazılacak harf:" + characterRnd);
                        if (gamerIdx == 0) {
                            System.out.println("\nGamer");

                            System.out.print("Yazmak istediğiniz satır:");
                            getRow = scanner.nextInt();
                            System.out.print("Yazmak istediğiniz sütun:");
                            getColumn = scanner.nextInt();

                            if (getRow >= 0 && getRow < matrix.length && getColumn >= 0 && getColumn < matrix[0].length) {
                                if (matrix[getRow][getColumn] == null) {
                                    matrix[getRow][getColumn] = characterRnd;


                                    System.out.println("Board : " + showBoard(matrix));

                                    //SOS kontrolü ve puan verme
                                    int tempScore = calculateScore(matrix, getRow, getColumn, scoreComputer);
                                    if (tempScore > 0){
                                        scoreGamer += calculateScore(matrix, getRow, getColumn, scoreGamer);
                                        System.out.println("scoreGamer : " + scoreGamer);

                                        gamerIdx = 0; //Sıra hala gamer'da.
                                    }else{
                                        gamerIdx = 1; //Sıra computer'a geçti.
                                    }
                                }
                            }else{
                                System.out.println("Hatalı değerler girdiniz, lütfen tekrar deneyin.");
                            }
                        } else if (gamerIdx == 1) {
                            System.out.println("\nComputer");

                            getRow = (int) ((Math.random() * (matrix.length)));
                            System.out.println("Yazılacak satır:" + getRow);

                            getColumn = (int) ((Math.random() * (matrix[0].length)));
                            System.out.println("Yazılacak sütun:" + getColumn);

                            if (getRow >= 0 && getRow < matrix.length && getColumn >= 0 && getColumn < matrix[0].length) {
                                if (matrix[getRow][getColumn] == null) {
                                    matrix[getRow][getColumn] = characterRnd;

                                    System.out.println("Board : " + showBoard(matrix));

                                    //SOS kontrolü ve puan verme
                                    int tempScore = calculateScore(matrix, getRow, getColumn, scoreComputer);
                                    if (tempScore > 0){
                                        scoreComputer += tempScore;
                                        System.out.println("scoreComputer : " + scoreComputer);

                                        gamerIdx = 1; //Sıra hala computer'da.
                                    }else{
                                        gamerIdx = 0; //Sıra gamer'a geçti.
                                    }
                                }
                            }else{
                                System.out.println("Hatalı değerler girildi! Tekrar değer atanacak.");
                            }
                        }

                    } while (getRow >= matrix.length && getColumn >= matrix[0].length);
                }while(matrixIsEmpty(matrix));

                if (scoreComputer > scoreGamer){
                    System.out.println("Oyunu " + scoreComputer + " puanla bilgisayar kazandı!");
                }else if(scoreComputer < scoreGamer){
                    System.out.println("Oyunu " + scoreGamer + " puanla siz kazandınız. Tebrikler!!");
                }else{
                    System.out.println("Oyun berabere sonuçlandı.");
                }


            }else{
                System.out.println("Lütfen 3-7 arasında ve birbirine eşit sayılar girin.");
            }
        } catch (NegativeArraySizeException ex) {
            System.out.println("Satır ve sütun negatif değer alamaz!");
        }
    }

    //Matrix'de boş alan kalmayana kadar oyunu devam ettirir.
    public static boolean matrixIsEmpty(String matrix[][]){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == null){
                    return  true;
                }
            }
        }
        return  false;
    }

    //Oyun tahtasını gösterir.
    public static String showBoard(String matrix[][]){

        String result = "\n    ";

        for(int i = 0; i < matrix.length; i++){
            result += i + "  ";
        }

        for(int i = 0; i < matrix.length; i++){
            result += "\n " + i + "  ";
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == null){
                    result += " " + "  ";
                }else{
                    result += matrix[i][j] + "  ";
                }
            }
        }

        return result;
    }

    //Skor hesaplar.
    public static int calculateScore(String matrix[][], int row, int column, int score){
        score = 0;

        //O harfinin yerinin kontrolü(+1/-1)
        if(matrix[row][column].equals("o")){

            //O'nun sağ-sol kontrolü(S için)
            if(column - 1 >= 0 && column + 1 < matrix[0].length){
                if(matrix[row][column - 1] != null && matrix[row][column + 1] != null){
                    if(matrix[row][column - 1].equals("s") && matrix[row][column + 1].equals("s")){
                        score++;
                    }
                }
            }

            //O'nun üst-alt kontrolü(S için)
            if(row - 1 >= 0 && row + 1 < matrix.length){
                if (matrix[row - 1][column] != null && matrix[row +1][column] != null){
                    if(matrix[row - 1][column].equals("s") && matrix[row +1][column].equals("s")){
                        score++;
                    }
                }
            }

            //O'nun çapraz kontrolü(S için)
            if(row - 1 >= 0 && column - 1 >= 0 && row + 1 < matrix.length && column + 1 < matrix[0].length){
                if (matrix[row - 1][column - 1] != null && matrix[row + 1][column + 1] != null){
                    if ((matrix[row - 1][column - 1].equals("s") && matrix[row + 1][column + 1].equals("s"))){
                        score++;
                    }
                }

                if (matrix[row - 1][column + 1] != null && matrix[row + 1][column - 1] != null){
                    if (matrix[row - 1][column + 1].equals("s") && matrix[row + 1][column - 1].equals("s")){
                        score++;
                    }
                }
            }
        }



        //S harfinin yerinin kontrolü(+2/-2)
        if (matrix[row][column].equals("s")){

            //S'nun alt kontrolü(S ve O için)
            if (row + 2 < matrix.length){
                if (matrix[row + 1][column] != null && matrix[row + 2][column] != null){
                    if ((matrix[row + 1][column]).equals("o") && (matrix[row + 2][column]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun üst kontrolü(S ve O için)
            if (row - 2 >= 0){
                if (matrix[row - 1][column] != null && matrix[row - 2][column] != null){
                    if ((matrix[row - 1][column]).equals("o") && (matrix[row - 2][column]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun sağ kontrolü(S ve O için)
            if (column + 2 < matrix[0].length){
                if (matrix[row][column + 1] != null && matrix[row][column + 2] != null){
                    if ((matrix[row][column + 1]).equals("o") && (matrix[row][column + 2]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun sol kontrolü(S ve O için)
            if (column - 2 >= 0){
                if (matrix[row][column - 1] != null && matrix[row][column - 2] != null){
                    if ((matrix[row][column - 1]).equals("o") && (matrix[row][column - 2]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun sol üst çapraz kontrolü(S ve O için)
            if (row - 2 >= 0 && column - 2 >= 0){
                if (matrix[row - 1][column - 1] != null && matrix[row - 2][column - 2] != null){
                    if ((matrix[row - 1][column - 1]).equals("o") && (matrix[row - 2][column - 2]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun sağ alt çapraz kontrolü(S ve O için)
            if (row + 2 < matrix.length && column + 2 < matrix[0].length){
                if (matrix[row + 1][column + 1] != null && matrix[row + 2][column + 2] != null){
                    if ((matrix[row + 1][column + 1]).equals("o") && (matrix[row + 2][column + 2]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun sağ üst çapraz kontrolü(S ve O için)
            if (row - 2 >= 0 && column + 2 < matrix[0].length){
                if (matrix[row - 1][column + 1] != null && matrix[row - 2][column + 2] != null){
                    if ((matrix[row - 1][column + 1]).equals("o") && (matrix[row - 2][column + 2]).equals("s")){
                        score++;
                    }
                }
            }

            //S'nun sol alt çapraz kontrolü(S ve O için)
            if (row + 2 < matrix.length && column - 2 >= 0){
                if (matrix[row + 1][column - 1] != null && matrix[row + 2][column - 2] != null){
                    if ((matrix[row + 1][column - 1]).equals("o") && (matrix[row + 2][column - 2]).equals("s")){
                        score++;
                    }
                }
            }
        }

        return score;
    }
}