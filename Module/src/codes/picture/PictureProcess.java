package codes.picture;

import java.io.File;

public class PictureProcess {

    File file;
    int mines=0;
    final int BLACK_CODE=-16777216;
    final int minRecWidth=58;
    final int maxRecWidth=65;
    final int sourceCross=42;




    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Picture removeNoises(){

        Picture picture=new Picture(this.file);

        int width=picture.width();
        int height=picture.height();
        int divide=3;
        int divided=divide*divide;

        Picture minimized=new Picture(width/divide,height/divide);//picture for minimize origin picture and get average
        Picture filtered=new Picture(width/divide,height/divide);//picture for copying minimized pic

        for(int i=0;i<width/divide;i++){
            for(int j=0;j<height/divide;j++){

                filtered.setRGB(i,j,-1);

                //minimizing origin picture and get average
                int rgb= average(i,j,divide,picture);

                minimized.setRGB(i,j,rgb/divided);
                //check if pixels are black then copy to filtered graph
                if(minimized.getRGB(i,j)>=-16777216&&minimized.getRGB(i,j)<=-16178526)
                    filtered.setRGB(i,j,-16777216);

                //copy filtered picture into origin picture and return to primary size
                backToOriginSize(i,j,divide,picture,filtered);

            }
        }

        return picture;
    }

    private void backToOriginSize(int i, int j, int divide, Picture picture,Picture filtered) {

        for(int k=i*divide;k<(i+1)*divide;k++){
            for(int h=j*divide;h<(j+1)*divide;h++){
                picture.setRGB(k,h,filtered.getRGB(i,j));
            }
        }
    }

    private int average(int i, int j, int divide,Picture picture) {

        int temp=0;
        for(int k=i*divide;k<(i+1)*divide;k++){
            for(int h=j*divide;h<(j+1)*divide;h++){
                temp+=picture.getRGB(k,h);
            }
        }
        return temp;
    }

    public void detectWalls(){

    }

    public void detectSwitch(){

    }

    public void detectSource(){

    }

    public void detectJunctionBox(){



    }
    public int calculatePixels(){
        return 0;
    }


    //this method just for test
    public void justShowSourcePlace(){

        Picture picture=removeNoises();

        int[]test= findWeight(picture);

        //for showing source place in picture with blue color
        picture.setRGB(test[1],test[0],255);
        picture.setRGB(test[1]+1,test[0],255);
        picture.setRGB(test[1]+2,test[0],255);  picture.setRGB(test[1]+4,test[0],255);
        picture.setRGB(test[1]+3,test[0],255);

        picture.show();
    }


    public int[] findWeight(Picture pic){


        int temp=0;
        boolean isRectangle=false;

        for(int i=0;i<pic.height();i++){
            for(int j=0;j<pic.width();j++){
                    if(pic.getRGB(j,i)==BLACK_CODE){
                        temp++;
                        if ((temp >= minRecWidth && temp <= maxRecWidth)) {//check width of black pixels is arrive to 60px or not
                            isRectangle = Recognize(i,j,temp,pic);
                            if (!isRectangle)
                                temp -= mines;
                            else
                                return getPoint(i, j);
                        }
                }
                else
                    temp=0;
            }
        }
        return null;
    }


    private boolean Recognize( int i, int j,int temp,Picture pic) {


        for(int k=i;k<i+sourceCross;k++){
            for(int h=j;h>j-temp;h--){
                if(k>=pic.height()||h<0)
                    return  false;
                if(pic.getRGB(h,k)!=BLACK_CODE){
                    mines=h;
                    return false;
                }

            }

        }
        return true;
    }


    private int[] getPoint(int i,int j){
        int holder[]=new int[2];
        holder[0]=i;holder[1]=j;
        return holder;
    }



}


