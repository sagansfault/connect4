package me.cam.connect4;

public class Util {

    public static int move(String dir, int initialSlot) {

        int slot = 0;

        switch (dir.toLowerCase()) {
            case "up":
                slot = initialSlot - 9;
                break;
            case "down":
                slot = initialSlot + 9;
                break;
            case "left":
                slot = initialSlot - 1;
                break;
            case "right":
                slot = initialSlot + 1;
                break;
        }

        return slot;
    }
}