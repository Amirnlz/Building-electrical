package codes.picture;

import java.io.File;

public class PictureProcess {

    File file;
    private final int BLACK_CODE = -16777216;
    private final int WHITE_CODE = -1;
    private final int RED_CODE = 16711680;
    private final int GREEN_CODE = 65280;
    private final int YELLOW_CODE = 16776960;

    public Picture removeNoises() {
        Picture picture = new Picture(this.file);

        int width = picture.width();
        int height = picture.height();
        int divide = 3;
        int divided = divide * divide;

        Picture minimized = new Picture(width / divide, height / divide);//picture for minimize origin picture and get average
        Picture filtered = new Picture(width / divide, height / divide);//picture for copying minimized pic

        for (int i = 0; i < width / divide; i++) {
            for (int j = 0; j < height / divide; j++) {
                filtered.setRGB(i, j, -1);
                //minimizing origin picture and get average
                int rgb = average(i, j, divide, picture);
                minimized.setRGB(i, j, rgb / divided);
                //check if pixels are black then copy to filtered graph
                if (minimized.getRGB(i, j) >= -16777216 && minimized.getRGB(i, j) <= -16178526)
                    filtered.setRGB(i, j, -16777216);
                //copy filtered picture into origin picture and return to primary size
                backToOriginSize(i, j, divide, picture, filtered);
            }
        }
        return picture;
    }

    private void backToOriginSize(int i, int j, int divide, Picture picture, Picture filtered) {
        for (int k = i * divide; k < (i + 1) * divide; k++) {
            for (int h = j * divide; h < (j + 1) * divide; h++) {
                picture.setRGB(k, h, filtered.getRGB(i, j));
            }
        }
    }

    private int average(int i, int j, int divide, Picture picture) {
        int temp = 0;
        for (int k = i * divide; k < (i + 1) * divide; k++) {
            for (int h = j * divide; h < (j + 1) * divide; h++) {
                temp += picture.getRGB(k, h);
            }
        }
        return temp;
    }

    //this method just for test
    public void justShowSourcePlace() {
        Picture picture = removeNoises();
        picture = extendPicture(20, picture);
        findObjects(picture);
        picture.show();
    }

    public void findObjects(Picture pic) {
        for (int i = 0; i < pic.height(); i++)
            for (int j = 0; j < pic.width(); j++) {
                findSource(i, j, pic);
                findKeys(i, j, pic);
            }

        for (int i = 0; i < pic.height(); i++)
            for (int j = 0; j < pic.width(); j++)
                findBoxes(i, j, pic);

    }

    private void findSource(int i, int j, Picture pic) {
        if (isSource(i, j, pic)) {
            if (i < pic.height() / 2 && j < pic.width() / 2) {
                showNode(i + 12, j + 12, pic, "source");
            }
            if (i < pic.height() / 2 && j > pic.width() / 2) {
                showNode(i + 12, j, pic, "source");
            }
            if (i > pic.height() / 2 && j < pic.width() / 2) {
                showNode(i, j + 14, pic, "source");
            }
            if (i > pic.height() / 2 && j > pic.width() / 2) {
                showNode(i, j, pic, "source");
            }
        }
    }

    private void findKeys(int i, int j, Picture pic) {
        if (isKey(i, j, pic)) {
            if (i - 10 >= 0 && j - 10 >= 0) {
                if (pic.getRGB(j, i - 10) == BLACK_CODE && pic.getRGB(j - 10, i) == WHITE_CODE) {
                    showNode(i, j + 12, pic, "key");
                }
                if (pic.getRGB(j, i - 10) == WHITE_CODE && pic.getRGB(j - 10, i) == BLACK_CODE) {
                    showNode(i + 12, j, pic, "key");
                }
                if (pic.getRGB(j, i - 10) == WHITE_CODE && pic.getRGB(j - 10, i) == WHITE_CODE) {
                    showNode(i, j, pic, "key");
                }
            } else {
                if (j < 5) {
                    showNode(i, j + 12, pic, "key");
                } else {
                    showNode(i, j, pic, "key");
                }
            }
        }
    }

    private void findBoxes(int i, int j, Picture pic) {
        if (isBox(i, j, pic))
            showNode(i, j, pic, "box");
    }

    private void showNode(int y, int x, Picture picture, String flag) {
        switch (flag) {
            case "source":
                showSource(x, y, picture);
                break;
            case "key":
                showKey(x, y, picture);
                break;
            case "box":
                showBox(x, y, picture);
                break;
        }
    }

    private void showSource(int x, int y, Picture picture) {
        for (int i = y; i < y + 43; i++) {
            for (int j = x; j < x + 60; j++) {
                picture.setRGB(j, i, RED_CODE);
                picture.show();
            }
        }
    }

    private void showKey(int x, int y, Picture picture) {
        for (int i = y; i < y + 42; i++) {
            for (int j = x; j < x + 42; j++) {
                picture.setRGB(j, i, GREEN_CODE);
                picture.show();
            }
        }
    }

    private void showBox(int x, int y, Picture picture) {
        for (int i = y; i < y + 12; i++) {
            for (int j = x; j < x + 12; j++) {
                picture.setRGB(j, i, YELLOW_CODE);
                picture.show();
            }
        }
    }

    private boolean isBox(int y, int x, Picture picture) {
        int size = 8;
        if (y + size <= picture.height() && x + size <= picture.width()) {
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    if (i - size >= 0 && i + size < picture.height() && j - size >= 0 && j + size < picture.width()) {
                        if (picture.getRGB(j, i) != BLACK_CODE) {
                            return false;
                        } else {

                            if ((picture.getRGB(j, i + size) != WHITE_CODE && picture.getRGB(j, i - size) != WHITE_CODE) &&
                                    (picture.getRGB(j + size, i) != BLACK_CODE && picture.getRGB(j - size, i) != BLACK_CODE)) {
                                return false;
                            }

                            if ((picture.getRGB(j, i + size) != BLACK_CODE && picture.getRGB(j, i - size) != BLACK_CODE) &&
                                    (picture.getRGB(j + size, i) != WHITE_CODE && picture.getRGB(j - size, i) != WHITE_CODE)) {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return y + size <= picture.height() && x + size <= picture.width();
        }
        return true;
    }

    private boolean isKey(int y, int x, Picture picture) {
        int size = 42;
        if (y + size <= picture.height() && x + size <= picture.width()) {
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    if (picture.getRGB(j, i) != BLACK_CODE) {
                        return false;
                    }
                }
            }
        } else
            return y + size <= picture.height() && x + size <= picture.width();
        return true;
    }

    private boolean isSource(int y, int x, Picture picture) {
        if (y + 43 <= picture.height() && x + 63 <= picture.width()) {
            for (int i = y; i < y + 43; i++) {
                for (int j = x; j < x + 63; j++) {
                    if (picture.getRGB(j, i) != BLACK_CODE) {
                        return false;
                    }
                }
            }
        } else
            return y + 43 <= picture.height() && x + 63 <= picture.width();
        return true;
    }

    private Picture extendPicture(int extendNumber, Picture picture) {
        Picture extendedPicture = new Picture(picture.width() + extendNumber, picture.height() + extendNumber);
        fillWhite(extendedPicture);
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                extendedPicture.setRGB(j + (extendNumber / 2), i + (extendNumber / 2), picture.getRGB(j, i));
            }
        }
        return extendedPicture;
    }

    private void fillWhite(Picture picture) {
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                picture.setRGB(j, i, -1);
            }
        }
    }

    public void detectWalls(Picture picture) {

    }

    public void detectSwitch() {

    }

    public void detectSource() {

    }

    public void detectJunctionBox() {


    }

    public int calculatePixels() {
        return 0;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


}