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
                int rgb=0;//save average of origin picture pixels

                //minimizing origin picture and get average
                for(int k=i*divide;k<(i+1)*divide;k++){
                    for(int h=j*divide;h<(j+1)*divide;h++){
                        rgb+=picture.getRGB(k,h);
                    }
                }

                minimized.setRGB(i,j,rgb/divided);
                //check if pixels are black then copy to filtered graph
                if(minimized.getRGB(i,j)>=-16777216&&minimized.getRGB(i,j)<=-16178526)
                    filtered.setRGB(i,j,-16777216);

                //copy filtered picture into origin picture and return to primary size
                for(int k=i*divide;k<(i+1)*divide;k++){
                    for(int h=j*divide;h<(j+1)*divide;h++){
                        picture.setRGB(k,h,filtered.getRGB(i,j));
                    }
                }
            }
        }

        return picture;
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

        int[]test= findWeight(picture,minRecWidth,maxRecWidth,sourceCross);

        //for showing source place in picture with blue color
        picture.setRGB(test[1],test[0],255);
        picture.setRGB(test[1]+1,test[0],255);
        picture.setRGB(test[1]+2,test[0],255);  picture.setRGB(test[1]+4,test[0],255);
        picture.setRGB(test[1]+3,test[0],255);

        picture.show();
    }


    public int[] findWeight(Picture pic,int minWidth,int maxWidth,int cross){

        int sum=0,temp=0;
        boolean isRectangle=false;

        for(int i=0;i<pic.height();i++){
            for(int j=0;j<pic.width();j++){
                    if(pic.getRGB(j,i)==BLACK_CODE){
                        temp++;
                        if((temp>=minWidth&&temp<=maxWidth)){//check width of black pixels is arrive to 60px or not

                            isRectangle=Recognize(temp,i,j,cross,pic);
                            if(!isRectangle)
                                temp-=mines;
                            else{
                                int holder[]=new int[2];
                                holder[0]=i;holder[1]=j;
                                return holder;
                            }
                        }
                }
                else{
                    temp=0;
                }
            }
        }
        return null;
    }


    private boolean Recognize(int temp, int i, int j,int cross,Picture pic) {

        for(int k=i;k<i+cross;k++){
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
}


