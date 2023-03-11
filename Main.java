import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int counter = 0;
        int[][] values = new int[4][4];

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 16 1 byte HEX values (Example: A1)");
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                counter++;
                System.out.println("Value " + counter + ": ");
                values[i][j] = scanner.nextInt(16);
            }
        }

        System.out.println("AES S-BOX output:");
        int[][] result = aesSBox(values, false);
        for (int[] ints : result) {
            for (int anInt : ints) {
                if (Integer.toHexString(anInt).length() == 1) {
                    System.out.print("0" + Integer.toHexString(anInt).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(anInt).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Inverse AES S-BOX output:");
        result = aesSBox(result, true);
        for (int[] ints : result) {
            for (int anInt : ints) {
                if (Integer.toHexString(anInt).length() == 1) {
                    System.out.print("0" + Integer.toHexString(anInt).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(anInt).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("AES Shift Rows output:");
        result = aesShiftRows(values, true);
        for (int[] ints : result) {
            for (int anInt : ints) {
                if (Integer.toHexString(anInt).length() == 1) {
                    System.out.print("0" + Integer.toHexString(anInt).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(anInt).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();


        System.out.println("Inverse AES Shift Rows output:");
        result = aesShiftRows(result, false);
        for (int[] ints : result) {
            for (int anInt : ints) {
                if (Integer.toHexString(anInt).length() == 1) {
                    System.out.print("0" + Integer.toHexString(anInt).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(anInt).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();


        System.out.println("AES Mix Columns output:");
        result = aesMixColumns(values, false);
        for (int[] ints : result) {
            for (int anInt : ints) {
                if (Integer.toHexString(anInt).length() == 1) {
                    System.out.print("0" + Integer.toHexString(anInt).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(anInt).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Inverse AES Mix Columns output:");
        result = aesMixColumns(result, true);
        for (int[] ints : result) {
            for (int anInt : ints) {
                if (Integer.toHexString(anInt).length() == 1) {
                    System.out.print("0" + Integer.toHexString(anInt).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(anInt).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Key Expansion output:");
        int[][] keyExpansionResult = keyExpansion(values);
        for (int i = 0; i < keyExpansionResult.length; i++) {
            System.out.print("Word " + i + ": ");
            for (int j = 0; j < keyExpansionResult[i].length; j++) {
                if (Integer.toHexString(keyExpansionResult[i][j]).length() == 1) {
                    System.out.print("0" + Integer.toHexString(keyExpansionResult[i][j]).toUpperCase() + " ");
                } else {
                    System.out.print(Integer.toHexString(keyExpansionResult[i][j]).toUpperCase() + " ");
                }
            }
            System.out.println();
        }
    }

    public static int[][] aesSBox(int[][] values, boolean inverse) {
        int x, y;
        String hexValue;
        int[][] result = new int[4][4];
        int[] usedBox;
        int[] sBox = {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
                0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
                0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
                0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
                0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
                0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
                0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
                0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
                0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
                0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
                0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
                0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
                0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
                0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
                0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
                0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16};
        int[] invSBox = {
                0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
                0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
                0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
                0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
                0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
                0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
                0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
                0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
                0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
                0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
                0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
                0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
                0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
                0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
                0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
                0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d};

        if (inverse) {
            usedBox = invSBox;
        } else {
            usedBox = sBox;
        }

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                hexValue = Integer.toHexString(values[i][j]);
                if (hexValue.length() == 1) {
                    x = 0;
                    y = Integer.parseInt(String.valueOf(hexValue.charAt(0)), 16);
                } else {
                    x = Integer.parseInt(String.valueOf(hexValue.charAt(0)), 16);
                    y = Integer.parseInt(String.valueOf(hexValue.charAt(1)), 16);
                }
                int address = (x * 16) + y;
                result[i][j] = usedBox[address];
            }
        }
        return result;
    }

    public static int[][] aesShiftRows(int[][] values, boolean left_right) {
        int[][] result = new int[4][4];

        if (left_right) {
            result[0][0] = values[0][0];
            result[0][1] = values[0][1];
            result[0][2] = values[0][2];
            result[0][3] = values[0][3];

            result[1][0] = values[1][1];
            result[1][1] = values[1][2];
            result[1][2] = values[1][3];
            result[1][3] = values[1][0];

            result[2][0] = values[2][2];
            result[2][1] = values[2][3];
            result[2][2] = values[2][0];
            result[2][3] = values[2][1];

            result[3][0] = values[3][3];
            result[3][1] = values[3][0];
            result[3][2] = values[3][1];
            result[3][3] = values[3][2];
        } else {

            result[0][0] = values[0][0];
            result[0][1] = values[0][1];
            result[0][2] = values[0][2];
            result[0][3] = values[0][3];

            result[1][0] = values[1][3];
            result[1][1] = values[1][0];
            result[1][2] = values[1][1];
            result[1][3] = values[1][2];

            result[2][0] = values[2][2];
            result[2][1] = values[2][3];
            result[2][2] = values[2][0];
            result[2][3] = values[2][1];

            result[3][0] = values[3][1];
            result[3][1] = values[3][2];
            result[3][2] = values[3][3];
            result[3][3] = values[3][0];
        }

        return result;
    }

    public static int[][] aesMixColumns(int[][] values, boolean inverse) {
        int value, matrixValue;
        int[] xorValues = new int[4];
        int[][] matrix = {{0x02, 0x03, 0x01, 0x01}, {0x01, 0x02, 0x03, 0x01}, {0x01, 0x01, 0x02, 0x03},
                {0x03, 0x01, 0x01, 0x02}};
        int[][] invMatrix = {{0x0e, 0x0b, 0x0d, 0x09}, {0x09, 0x0e, 0x0b, 0x0d}, {0x0d, 0x09, 0x0e, 0x0b},
                {0x0b, 0x0d, 0x09, 0x0e}};
        int[][] result = new int[4][4];

        if (inverse) {
            for (int i = 0; i < values.length; i++) {
                for (int j = 0; j < values.length; j++) {
                    int temp = 0;
                    for (int k = 0; k < values.length; k++) {

                        value = values[k][j];
                        matrixValue = invMatrix[i][k];

                        if (matrixValue == 9) {


                            for (int l = 0; l < 3; l++) {

                                value <<= 1;

                                if (Integer.toBinaryString(value).length() == 9) {
                                    value ^= 283;
                                }
                            }

                            value ^= values[k][j];

                        } else if (matrixValue == 11) {

                            value <<= 1;

                            if (Integer.toBinaryString(value).length() == 9) {
                                value ^= 283;
                            }


                            for (int l = 0; l < 2; l++) {

                                value <<= 1;

                                if (Integer.toBinaryString(value).length() == 9) {
                                    value ^= 283;
                                }

                                value ^= values[k][j];
                            }

                        } else if (matrixValue == 13) {

                            value <<= 1;

                            if (Integer.toBinaryString(value).length() == 9) {
                                value ^= 283;
                            }

                            value ^= values[k][j];

                            for (int l = 0; l < 2; l++) {

                                value <<= 1;

                                if (Integer.toBinaryString(value).length() == 9) {
                                    value ^= 283;
                                }
                            }

                            value ^= values[k][j];

                        } else if (matrixValue == 14) {

                            for (int l = 0; l < 2; l++) {

                                value <<= 1;

                                if (Integer.toBinaryString(value).length() == 9) {
                                    value ^= 283;
                                }

                                value ^= values[k][j];
                            }

                            value <<= 1;

                            if (Integer.toBinaryString(value).length() == 9) {
                                value ^= 283;
                            }
                        }

                        xorValues[k] = value;
                    }

                    for (int xorValue : xorValues) {
                        temp ^= xorValue;
                    }
                    result[i][j] = temp;
                }
            }

        } else {
            for (int i = 0; i < values.length; i++) {
                for (int j = 0; j < values.length; j++) {
                    int temp = 0;
                    for (int k = 0; k < values.length; k++) {

                        value = values[k][j];
                        matrixValue = matrix[i][k];

                        if (matrixValue == 1) {
                            xorValues[k] = value;

                        } else {

                            value <<= 1;

                            if (Integer.toBinaryString(value).length() == 9) {
                                value ^= 283;
                            }

                            if (matrixValue == 2) {
                                xorValues[k] = value;
                            } else if (matrixValue == 3) {
                                xorValues[k] = value ^ values[k][j];
                            }
                        }
                    }

                    for (int xorValue : xorValues) {
                        temp ^= xorValue;
                    }
                    result[i][j] = temp;
                }
            }
        }
        return result;
    }

    public static int[][] keyExpansion(int[][] values) {
        int x, y;
        int[][] result = new int[44][4];
        int[] hexValues = new int[4];
        int[] sBoxValues = new int[4];
        String hexValue;
        int[] sBox = {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
                0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
                0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
                0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
                0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
                0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
                0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
                0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
                0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
                0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
                0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
                0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
                0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
                0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
                0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
                0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16};

        int[] rcTable= {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36};

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                result[i][j] = values[i][j];
            }
        }

        for (int i = 4; i < 44; i++) {
            for (int j = 0; j < 4; j++) {
                if (i % 4 == 0) {
                    hexValues[0] = result[i - 1][1];
                    hexValues[1] = result[i - 1][2];
                    hexValues[2] = result[i - 1][3];
                    hexValues[3] = result[i - 1][0];

                    for (int k = 0; k < hexValues.length; k++) {
                        hexValue = Integer.toHexString(hexValues[k]);

                        if (hexValue.length() == 1) {
                            x = 0;
                            y = Integer.parseInt(String.valueOf(hexValue.charAt(0)), 16);
                        } else {
                            x = Integer.parseInt(String.valueOf(hexValue.charAt(0)), 16);
                            y = Integer.parseInt(String.valueOf(hexValue.charAt(1)), 16);
                        }
                        int address = (x * 16) + y;
                        sBoxValues[k] = sBox[address];
                    }

                    sBoxValues[0] ^= rcTable[(i/4)-1];


                    for (int k = 0; k < sBoxValues.length; k++) {
                        int xorValue = result[i - 4][k];
                        result[i][k] = sBoxValues[k] ^ xorValue;
                    }

                } else {
                    for (int k = 0; k < 4; k++) {
                        result[i][k] = result[i - 1][k] ^ result[i - 4][k];
                    }
                }
            }
        }
        return result;
    }
}

