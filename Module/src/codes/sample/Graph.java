package sample;

import java.io.File;

public class Graph {

    File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File pictureToGraph(){
        return  null;
    }

    public boolean isCompleteGraph(){
        return false;
    }

    public  void setWeight(){

    }

    public File findOptimizedGraph(){
        PrimeAlghorithm getPrime=new PrimeAlghorithm();
        return getPrime.Prime(this.file);
    }

    public void filterGraph(){

    }

    public File getFinalGraph(){
        return  null;
    }

    public File drawGraph(){
        return null;
    }
}
