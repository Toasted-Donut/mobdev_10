package com.example.mobdev10;

public class Node {
    byte corr = 0;
    Boolean up_fit = null;
    Boolean down_fit = null;
    Boolean right_fit = null;
    Boolean left_fit = null;

    public static Node[][] New_Map(int size) {
        Node[][] new_map = new Node[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                new_map[i][j] = new Node();
            }
        }
        return new_map;
    }
    public static Node[][] Import_Byte_Map(byte[][] map_byte){
        Node[][] map = New_Map(map_byte.length);
        for (int i = 0; i < map_byte.length; i++) {
            for (int j = 0; j < map_byte[0].length; j++) {
                map[i][j].corr=map_byte[i][j];
            }
        }
        return map;
    }
    public Node[][]  Generation(int map_size){
        Node[][] map = Node.New_Map(map_size);
        int start_x = map_size-2;
        int start_y = map_size/2+1;
        map[start_x][start_y].corr=22;
        map[start_x][start_y].up_fit=true;
        map[start_x][start_y].down_fit=false;
        map[start_x][start_y].left_fit=false;
        map[start_x][start_y].right_fit=false;
        for (int i=0;i<map_size;i++){
            map[0][i].up_fit=false;map[0][i].down_fit=false;map[0][i].left_fit=false;map[0][i].right_fit=false;
            map[map_size-1][i].up_fit=false;map[map_size-1][i].down_fit=false;map[map_size-1][i].left_fit=false;map[map_size-1][i].right_fit=false;
            map[i][0].up_fit=false;map[i][0].down_fit=false;map[i][0].left_fit=false;map[i][0].right_fit=false;
            map[i][map_size-1].up_fit=false;map[i][map_size-1].down_fit=false;map[i][map_size-1].left_fit=false;map[i][map_size-1].right_fit=false;
            map[0][i].corr=-1;map[map_size-1][i].corr=-1;map[i][0].corr=-1;map[i][map_size-1].corr=-1;

        }
        int x = start_x;
        int y = start_y;
        int corr=0;
        int direction=0;
        boolean IsFit;
        int n1;
        int n2;
        int n3;
        int n4;
        int num_of_ends=0;
        boolean stop;
        boolean map_is_built=false;
        boolean map_is_completed = false;
        boolean isEnd=false;
        while (!map_is_completed){
            map_is_built=false;
            while(!isEnd){
                n1=0;
                n2=0;
                n3=0;
                n4=0;
                IsFit=false;
                while (!IsFit){
                    direction=(int)(Math.random()*4);
                    if (direction==0){
                        if (map[x][y].up_fit!=null && map[x-1][y].corr==0&&map[x][y].up_fit){
                            IsFit=true;
                        }
                        else n1=1;
                    }
                    else if (direction==1){
                        if (map[x][y].down_fit!=null && map[x+1][y].corr==0&&map[x][y].down_fit){
                            IsFit=true;
                        }
                        else n2=1;
                    }
                    else if (direction==2){
                        if (map[x][y].left_fit!=null && map[x][y-1].corr==0&&map[x][y].left_fit){
                            IsFit=true;
                        }
                        else n3=1;
                    }
                    else if (direction==3){
                        if (map[x][y].right_fit!=null && map[x][y+1].corr==0&&map[x][y].right_fit){
                            IsFit=true;
                        }
                        else n4=1;
                    }
                    if (n1+n2+n3+n4==4){
                        isEnd=true;
                        break;
                    }
                }
                if (isEnd)break;
                if(direction==0){
                    x=x-1;
                }
                else if(direction==1){
                    x=x+1;
                }
                else if(direction==2){
                    y=y-1;
                }
                else if(direction==3){
                    y=y+1;
                }
                map=choose_corridor(x,y,map);
                corr=map[x][y].corr;
                if (corr==21){break;}
                if(corr==22){break;}
                if(corr==23){break;}
                if(corr==24){break;}
            }
            map_is_built=false;

            stop=false;
            for (int i=1;i<map_size-1;i++){
                for (int j=1;j<map_size-1;j++){
                    if (map[i][j].corr!=0){
                        if (map[i][j].up_fit!=null && map[i][j].up_fit&&map[i-1][j].down_fit==null){
                            x=i; y=j; stop=true;  isEnd=false; break;
                        }
                        else if (map[i][j].down_fit!=null && map[i][j].down_fit&&map[i+1][j].up_fit==null){
                            x=i; y=j; stop=true;  isEnd=false; break;
                        }
                        else if (map[i][j].left_fit!=null && map[i][j].left_fit&&map[i][j-1].right_fit==null){
                            x=i; y=j; stop=true;  isEnd=false; break;
                        }
                        else if (map[i][j].right_fit!=null && map[i][j].right_fit&&map[i][j+1].left_fit==null){
                            x=i; y=j; stop=true;  isEnd=false; break;
                        }
                        else {x=x; y=y;}
                    }
                }
                if (stop){break;}
            }
            if (!stop){
                map_is_built=true;
            }
            if (map_is_built){
                num_of_ends=0;
                for (int i=1;i<map_size-1;i++){
                    for (int j=1;j<map_size-1;j++){
                        if (map[i][j].corr==21||map[i][j].corr==22||map[i][j].corr==23||map[i][j].corr==24){
                            num_of_ends=num_of_ends+1;
                        }
                    }
                }
            }
            if (map_is_built&&num_of_ends>1){
                map_is_completed=true;
            }
            else if (map_is_built&&num_of_ends==1){
                for (int i=1;i<map_size-1;i++){
                    for (int j=1;j<map_size-1;j++){
                        map[i][j].corr=0;
                        map[i][j].up_fit=null;map[i][j].down_fit=null;map[i][j].left_fit=null;map[i][j].right_fit=null;
                    }
                }
                map[start_x][start_y].corr=22;
                map[start_x][start_y].up_fit=true;
                map[start_x][start_y].down_fit=false;
                map[start_x][start_y].left_fit=false;
                map[start_x][start_y].right_fit=false;
                x=start_x; y=start_y;
                map_is_built=false;
                map_is_completed=false;
            }
        }



        boolean exit=false;
        for (int i=1;i<map_size-1;i++){
            for (int j=1;j<map_size-1;j++){
                if (map[i][j].corr==21){
                    if (i==start_x&&j==start_y){
                        exit=false;
                    }
                    else {
                        map[i][j].corr=31;
                        exit=true;
                    }
                }
                else if (map[i][j].corr==22){
                    if (i==start_x&&j==start_y){
                        exit=false;
                    }
                    else {
                        map[i][j].corr=32;
                        exit=true;
                    }
                }
                else if (map[i][j].corr==23){
                    if (i==start_x&&j==start_y){
                        exit=false;
                    }
                    else {
                        map[i][j].corr=33;
                        exit=true;
                    }
                }
                else if (map[i][j].corr==24){
                    if (i==start_x&&j==start_y){
                        exit=false;
                    }
                    else {
                        map[i][j].corr=34;
                        exit=true;
                    }
                }

                if (exit)break;
            }
            if (exit)break;
        }
        return map;
    }
    public Node[][] choose_corridor( int x, int y, Node[][] map) {
        Boolean up = map[x - 1][y].down_fit;
        Boolean down = map[x + 1][y].up_fit;
        Boolean left = map[x][y - 1].right_fit;
        Boolean right = map[x][y + 1].left_fit;
        byte chosen_corridor=0;

        byte[] Up_fit = {1, 2, 4, 6, 7, 10, 11};
        byte[] Up_with_NotLeft_fit = {2, 7, 10};
        byte[] Up_with_NotDown_fit = {4, 10, 11};
        byte[] Up_with_NotRight_fit = {2, 6, 11};
        byte Up_with_NotLeft_and_NotRight_fit = 2;
        byte Up_with_NotLeft_and_NotDown_fit = 10;
        byte Up_with_NotDown_and_NotRight_fit = 11;
        byte Up_with_NotAny_fit = 22;
        byte[] Down_fit = {1, 2, 5, 6, 7, 8, 9};
        byte[] Down_with_NotLeft_fit = {2, 7, 8};
        byte[] Down_with_NotUp_fit = {5, 8, 9};
        byte[] Down_with_NotRight_fit = {2, 6, 9};
        byte Down_with_NotLeft_and_NotRight_fit = 2;
        byte Down_with_NotLeft_and_NotUp_fit = 8;
        byte Down_with_NotUp_and_NotRight_fit = 9;
        byte Down_with_NotAny_fit = 24;
        byte[] Left_fit = {1, 3, 4, 5, 6, 9, 11};
        byte[] Left_with_NotUp_fit = {3, 5, 9};
        byte[] Left_with_NotDown_fit = {3, 4, 11};
        byte[] Left_with_NotRight_fit = {6, 9, 11};
        byte Left_with_NotUp_and_NotRight_fit = 9;
        byte Left_with_NotUp_and_NotDown_fit = 3;
        byte Left_with_NotDown_and_NotRight_fit = 11;
        byte Left_with_NotAny_fit = 21;
        byte[] Right_fit = {1, 3, 4, 5, 7, 8, 10};
        byte[] Right_with_NotLeft_fit = {7, 8, 10};
        byte[] Right_with_NotDown_fit = {3, 4, 10};
        byte[] Right_with_NotUp_fit = {3, 5, 8};
        byte Right_with_NotLeft_and_NotUp_fit = 8;
        byte Right_with_NotLeft_and_NotDown_fit = 10;
        byte Right_with_NotDown_and_NotUp_fit = 3;
        byte Right_with_NotAny_fit = 23;

        byte[] Up_and_Down_fit = {1, 2, 6, 7};
        byte[] Up_and_Down_with_NotLeft_fit = {2, 7};
        byte[] Up_and_Down_with_NotRight_fit = {2, 6};
        byte Up_and_Down_with_NotAny_fit = 2;
        byte[] Up_and_Left_fit = {1, 4, 6, 11};
        byte[] Up_and_Left_with_NotRight_fit = {6, 11};
        byte[] Up_and_Left_with_NotDown_fit = {4, 11};
        byte Up_and_Left_with_NotAny_fit = 11;
        byte[] Up_and_Right_fit = {1, 4, 7, 10};
        byte[] Up_and_Right_with_NotLeft_fit = {7, 10};
        byte[] Up_and_Right_with_NotDown_fit = {4, 10};
        byte Up_and_Right_with_NotAny_fit = 10;
        byte[] Down_and_Left_fit = {1, 5, 6, 9};
        byte[] Down_and_Left_with_NotUp_fit = {5, 9};
        byte[] Down_and_Left_with_NotRight_fit = {6, 9};
        byte Down_and_Left_with_NotAny_fit = 9;
        byte[] Down_and_Right_fit = {1, 5, 7, 8};
        byte[] Down_and_Right_with_NotUp_fit = {5, 8};
        byte[] Down_and_Right_with_NotLeft_fit = {7, 8};
        byte Down_and_Right_with_NotAny_fit = 8;
        byte[] Right_and_Left_fit = {1, 3, 4, 5};
        byte[] Right_and_Left_with_NotUp_fit = {3, 5};
        byte[] Right_and_Left_with_NotDown_fit = {3, 4};
        byte Right_and_Left_with_NotAny_fit = 3;

        byte[] Up_and_Down_and_Right_fit = {1, 7};
        byte Up_and_Down_and_Right_with_NotAny_fit = 7;
        byte[] Up_and_Down_and_Left_fit = {1, 6};
        byte Up_and_Down_and_Left_with_NotAny_fit = 6;
        byte[] Up_and_Left_and_Right_fit = {1, 4};
        byte Up_and_Left_and_Right_with_NotAny_fit = 4;
        byte[] Down_and_Left_and_Right_fit = {1, 5};
        byte Down_and_Left_and_Right_with_NotAny_fit = 5;

        byte Cross = 1;


        if(up==null){
            if(down==null){
                if(left==null){
                    if(right!=null && right){
                        chosen_corridor = Right_fit[(int) (Math.random() * 7)];
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Left_fit[(int) (Math.random() * 7)];
                    }
                    else if(right){
                        chosen_corridor = Right_and_Left_fit[(int) (Math.random() * 4)];
                    }
                    else { //!right
                        chosen_corridor = Left_with_NotRight_fit[(int) (Math.random() * 3)];
                    }
                }
                else { //!left
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotLeft_fit[(int) (Math.random() * 3)];
                    }

                }
            }
            else if(down){
                if(left==null){
                    if(right==null){
                        chosen_corridor = Down_fit[(int) (Math.random() * 7)];
                    }
                    else if(right){
                        chosen_corridor = Down_and_Right_fit[(int) (Math.random() * 4)];
                    }
                    else { //!right
                        chosen_corridor = Down_with_NotRight_fit[(int) (Math.random() * 3)];
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Down_and_Left_fit[(int) (Math.random() * 4)];
                    }
                    else if(right){
                        chosen_corridor = Down_and_Left_and_Right_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Down_and_Left_with_NotRight_fit[(int) (Math.random() * 2)];
                    }
                }
                else { //!left
                    if(right==null){
                        chosen_corridor = Down_with_NotLeft_fit[(int) (Math.random() * 3)];
                    }
                    else if(right){
                        chosen_corridor = Down_and_Right_with_NotLeft_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Down_with_NotLeft_and_NotRight_fit;
                    }
                }
            }
            else { //!down
                if(left==null){
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotDown_fit[(int) (Math.random() * 3)];
                    }

                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Left_with_NotDown_fit[(int) (Math.random() * 3)];
                    }
                    else if(right){
                        chosen_corridor = Right_and_Left_with_NotDown_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Left_with_NotDown_and_NotRight_fit;
                    }
                }
                else { //!left
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotLeft_and_NotDown_fit;
                    }

                }
            }
        }
        else if(up){
            if(down==null){
                if(left==null){
                    if(right==null){
                        chosen_corridor = Up_fit[(int) (Math.random() * 7)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Right_fit[(int) (Math.random() * 4)];
                    }
                    else { //!right
                        chosen_corridor = Up_with_NotRight_fit[(int) (Math.random() * 3)];
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Up_and_Left_fit[(int) (Math.random() * 4)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Left_and_Right_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Up_and_Left_with_NotRight_fit[(int) (Math.random() * 2)];
                    }
                }
                else { //!left
                    if(right==null){
                        chosen_corridor = Up_with_NotLeft_fit[(int) (Math.random() * 3)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Right_with_NotLeft_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Up_with_NotLeft_and_NotRight_fit;
                    }
                }
            }
            else if(down){
                if(left==null){
                    if(right==null){
                        chosen_corridor = Up_and_Down_fit[(int) (Math.random() * 4)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Down_and_Right_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Up_and_Down_with_NotRight_fit[(int) (Math.random() * 2)];
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Up_and_Down_and_Left_fit[(int) (Math.random() * 2)];
                    }
                    else if(right){
                        chosen_corridor = Cross;
                    }
                    else { //!right
                        chosen_corridor = Up_and_Down_and_Left_with_NotAny_fit;
                    }
                }
                else { //!left
                    if(right==null){
                        chosen_corridor = Up_and_Down_with_NotLeft_fit[(int) (Math.random() * 2)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Down_and_Right_with_NotAny_fit;
                    }
                    else { //!right
                        chosen_corridor = Up_and_Down_with_NotAny_fit;
                    }
                }
            }
            else { //!down
                if(left==null){
                    if(right==null){
                        chosen_corridor = Up_with_NotDown_fit[(int) (Math.random() * 3)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Right_with_NotDown_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Up_with_NotDown_and_NotRight_fit;
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Up_and_Left_with_NotDown_fit[(int) (Math.random() * 2)];
                    }
                    else if(right){
                        chosen_corridor = Up_and_Left_and_Right_with_NotAny_fit;
                    }
                    else { //!right
                        chosen_corridor = Up_and_Left_with_NotAny_fit;
                    }
                }
                else { //!left
                    if(right==null){
                        chosen_corridor = Up_with_NotLeft_and_NotDown_fit;
                    }
                    else if(right){
                        chosen_corridor = Up_and_Right_with_NotAny_fit;
                    }
                    else { //!right
                        chosen_corridor = Up_with_NotAny_fit;
                    }
                }
            }
        }
        else { //!up
            if(down==null){
                if(left==null){
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotUp_fit[(int) (Math.random() * 3)];
                    }

                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Left_with_NotUp_fit[(int) (Math.random() * 3)];
                    }
                    else if(right){
                        chosen_corridor = Right_and_Left_with_NotUp_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Left_with_NotUp_and_NotRight_fit;
                    }
                }
                else { //!left
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotLeft_and_NotUp_fit;
                    }

                }
            }
            else if(down){
                if(left==null){
                    if(right==null){
                        chosen_corridor = Down_with_NotUp_fit[(int) (Math.random() * 3)];
                    }
                    else if(right){
                        chosen_corridor = Down_and_Right_with_NotUp_fit[(int) (Math.random() * 2)];
                    }
                    else { //!right
                        chosen_corridor = Down_with_NotUp_and_NotRight_fit;
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Down_and_Left_with_NotUp_fit[(int) (Math.random() * 2)];
                    }
                    else if(right){
                        chosen_corridor = Down_and_Left_and_Right_with_NotAny_fit;
                    }
                    else { //!right
                        chosen_corridor = Down_and_Left_with_NotAny_fit;
                    }
                }
                else { //!left
                    if(right==null){
                        chosen_corridor = Down_with_NotLeft_and_NotUp_fit;
                    }
                    else if(right){
                        chosen_corridor = Down_and_Right_with_NotAny_fit;
                    }
                    else { //!right
                        chosen_corridor = Down_with_NotAny_fit;
                    }
                }
            }
            else { //!down
                if(left==null){
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotDown_and_NotUp_fit;
                    }
                }
                else if(left){
                    if(right==null){
                        chosen_corridor = Left_with_NotUp_and_NotDown_fit;
                    }
                    else if(right){
                        chosen_corridor = Right_and_Left_with_NotAny_fit;
                    }
                    else { //!right
                        chosen_corridor = Left_with_NotAny_fit;
                    }
                }
                else { //!left
                    if(right!=null && right){
                        chosen_corridor = Right_with_NotAny_fit;
                    }
                }
            }
        }

        if(chosen_corridor==1){map[x][y].up_fit=true;map[x][y].down_fit=true;map[x][y].left_fit=true;map[x][y].right_fit=true;}
        else if(chosen_corridor==2){map[x][y].up_fit=true;map[x][y].down_fit=true;map[x][y].left_fit=false;map[x][y].right_fit=false;}
        else if(chosen_corridor==3){map[x][y].up_fit=false;map[x][y].down_fit=false;map[x][y].left_fit=true;map[x][y].right_fit=true;}
        else if(chosen_corridor==4){map[x][y].up_fit=true;map[x][y].down_fit=false;map[x][y].left_fit=true;map[x][y].right_fit=true;}
        else if(chosen_corridor==5){map[x][y].up_fit=false;map[x][y].down_fit=true;map[x][y].left_fit=true;map[x][y].right_fit=true;}
        else if(chosen_corridor==6){map[x][y].up_fit=true;map[x][y].down_fit=true;map[x][y].left_fit=true;map[x][y].right_fit=false;}
        else if(chosen_corridor==7){map[x][y].up_fit=true;map[x][y].down_fit=true;map[x][y].left_fit=false;map[x][y].right_fit=true;}
        else if(chosen_corridor==8){map[x][y].up_fit=false;map[x][y].down_fit=true;map[x][y].left_fit=false;map[x][y].right_fit=true;}
        else if(chosen_corridor==9){map[x][y].up_fit=false;map[x][y].down_fit=true;map[x][y].left_fit=true;map[x][y].right_fit=false;}
        else if(chosen_corridor==10){map[x][y].up_fit=true;map[x][y].down_fit=false;map[x][y].left_fit=false;map[x][y].right_fit=true;}
        else if(chosen_corridor==11){map[x][y].up_fit=true;map[x][y].down_fit=false;map[x][y].left_fit=true;map[x][y].right_fit=false;}
        else if(chosen_corridor==21){map[x][y].up_fit=false;map[x][y].down_fit=false;map[x][y].left_fit=true;map[x][y].right_fit=false;}
        else if(chosen_corridor==22){map[x][y].up_fit=true;map[x][y].down_fit=false;map[x][y].left_fit=false;map[x][y].right_fit=false;}
        else if(chosen_corridor==23){map[x][y].up_fit=false;map[x][y].down_fit=false;map[x][y].left_fit=false;map[x][y].right_fit=true;}
        else if(chosen_corridor==24){map[x][y].up_fit=false;map[x][y].down_fit=true;map[x][y].left_fit=false;map[x][y].right_fit=false;}

        map[x][y].corr = chosen_corridor;
        return map;
    }
}
