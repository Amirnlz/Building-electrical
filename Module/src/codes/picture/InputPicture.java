package codes.picture;

import java.io.File;

public class InputPicture {

    File file;



    public Picture getFile() {

        Picture pic=new Picture(this.file);
        return pic;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
