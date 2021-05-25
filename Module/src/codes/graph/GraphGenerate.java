package codes.graph;

import codes.picture.InputPicture;
import codes.picture.Picture;

import java.io.File;

public class GraphGenerate {


    public Picture filterGraph(File file){

        Picture picture=new Picture(file);

        int width=picture.width();
        int height=picture.height();
        int divide=2;
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
                if(minimized.getRGB(i,j)>=-16777216&&minimized.getRGB(i,j)<=-16678526)
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
}
