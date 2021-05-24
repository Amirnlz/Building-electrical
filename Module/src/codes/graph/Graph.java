package codes.graph;

import codes.picture.Picture;

import java.io.File;
import java.io.IOException;

public class Graph {

   // File file;
    Picture picture;

    public Graph(Picture picture) {
        this.picture = picture;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    private File pictureToGraph(){
        return  null;
    }

//    this is shouldn't be void, complete it later
    private void makeGraph(){

    }

    private boolean isCompleteGraph(){
        return false;
    }

    private  void setWeight(){

    }

//    should return a graph complete it later
    private void findOptimizedGraph(){
//        PrimeAlghorithm getPrime=new PrimeAlghorithm();
//        return getPrime.Prime(this.file);
    }

    private void filterGraph(){

    }

// should return graph
    private Picture getFinalGraph(){
        return  null;
    }

    //show filtered graph for gui
    private File drawGraph() throws IOException {

        File myFile=new File("H:\\myPic.jpg");
        if(!myFile.exists())
            myFile.createNewFile();
       this.picture.save(myFile);
       return myFile;
    }
}
